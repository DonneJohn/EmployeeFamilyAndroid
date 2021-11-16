package com.exmobile.employeefamilyandroid.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * @Desp:Service下载apk通过notification显示下载进度 下载完成自动安装
 */
public class DownAPKService extends Service {
    public static final String ACTION = "android.intent.action.UpdateSoftService";
    private static final String KEY_URL = "url";
    private static final String KEY_NAME = "name";

    private final int TYPE_DOWNLOAD_FAIL = 1;// 下载失败
    private final int TYPE_DOWNLOAD_SUCC = 2;// 下载成功
    private final int TYPE_DOWNLOAD_PORGRESS = 3;// 下载中

    private PendingIntent contentIntent = null;
    private NotificationManager notificationManager = null;
    private HashMap<String, NotificationInfo> myMap = null;


    public static void startService(Context activity, String name, String url) {
        Intent intent = new Intent(activity, DownAPKService.class);
        intent.putExtra(DownAPKService.KEY_NAME, name);
        intent.putExtra(DownAPKService.KEY_URL, url);
        activity.startService(intent);
    }

    /**
     * @see Service#onBind(Intent)
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO 将代码放在此处
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        if (intent == null) {
            return;
        }

        final String name = intent.getStringExtra(KEY_NAME);
        final String url = intent.getStringExtra(KEY_URL);

        if (getHashMap().containsKey(url)) {
            return;
        }

        sendNotification(name, url);
        new MyTask(url).execute();
    }

    private class MyTask extends AsyncTask<Object, Integer, Message> {
        private String url = null;


        public MyTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Message doInBackground(Object... params) {
            // TODO Auto-generated method stub
            Message message = Message.obtain();
            message.what = TYPE_DOWNLOAD_FAIL;

            String filePath = getStoragePath() + "/"
                    + getFileNameOfTailFromUrl(url);
            File file = new File(filePath);
            filePath = null;


            boolean res = download(url, file,
                    new AppDownUpdateListener() {
                        @Override
                        public void onUpdate(int percent) {
                            // TODO Auto-generated method stub
                            publishProgress(percent);
                        }
                    });
            if (res) {
                // 下载成功
                message.what = TYPE_DOWNLOAD_SUCC;
                message.obj = url;
            } else {
                // 下载失败
                message.what = TYPE_DOWNLOAD_FAIL;
                message.obj = url;
            }
            return message;
        }

        @Override
        protected void onPostExecute(Message result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            handMessage(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            Message msg = Message.obtain();
            msg.what = TYPE_DOWNLOAD_PORGRESS;
            msg.arg1 = values[0];
            msg.obj = url;
            handMessage(msg);
        }
    }

    // 发送通知
    @Deprecated
    public void sendNotification(String name, String url) {


        Notification.Builder builder = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setContentTitle(name)
                .setContentText("已下载完成:" + "0%")
                .setContentIntent(geUpdateIntent())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);
        Notification notification = builder.getNotification();


//        Notification notification = new Notification(R.mipmap.ic_launcher,
//                "开始下载" + name, System.currentTimeMillis());
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        notification.setLatestEventInfo(this, name, "已下载完成:" + "0%",
//                geUpdateIntent());

        int id = (int) System.currentTimeMillis();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);

        getHashMap().put(url, new NotificationInfo(id, name, notification));
    }

    private HashMap<String, NotificationInfo> getHashMap() {
        if (myMap == null) {
            myMap = new HashMap<String, NotificationInfo>();
        }

        return myMap;
    }

    private PendingIntent geUpdateIntent() {
        if (contentIntent == null) {
            Intent intent = new Intent();
            contentIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
        return contentIntent;
    }

    private PendingIntent getInstallIntent(String path) {
        Uri uri = Uri.fromFile(new File(path));
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        PendingIntent updatePendingIntent = PendingIntent.getActivity(this, 0,
                installIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return updatePendingIntent;
    }

    /**
     * 开始下载文件
     *
     * @param fileUrl 文件的保存路径
     */
    private boolean download(String fileUrl, File file,
                             AppDownUpdateListener listener) {
        boolean res = false;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        int fileSize;
        try {
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "*/*");

            fileSize = conn.getContentLength();

            byte[] buffer = new byte[1024];
            int len = 0;
            int startPos = 0;
            int currPos = 0;
            int percent = 0;

            if (!file.exists()) {
                file.createNewFile();
            } else {
                startPos = (int) file.length();
                // 断点下载,设置获取数据的范围
                conn.setRequestProperty("Range", "bytes=" + startPos + "-"
                        + fileSize);
                if (startPos == fileSize) {
                    return true;
                }
            }

            currPos = startPos;
            percent = (int) (1.0 * startPos / fileSize * 100);
//            percent = (int) (1.0 * startPos / fileSize);
            if (listener != null) {
                listener.onUpdate(percent);
            }

            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK
                    || resCode == HttpURLConnection.HTTP_PARTIAL) {
                inputStream = conn.getInputStream();
                outputStream = new FileOutputStream(file, true);
                do {
                    len = inputStream.read(buffer);
                    if (len > 0) {
                        // 写入数据时要使用write(byte[],offer,count);,不要使用write(byte[]);
                        outputStream.write(buffer, 0, len);
                        currPos = currPos + len;

                        int tempPercent = (int) (1.0 * currPos / fileSize * 100);
                        if (tempPercent > percent && listener != null) {
                            percent = tempPercent;
                            listener.onUpdate(percent);
                        }
                    }
                } while (len != -1);
                res = true;
            } else {
                res = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        } finally {
            if (conn != null) {
                conn = null;
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            handleMessage(msg);
        }
    };

    private void handMessage(Message msg) {
        if (msg == null) {
            return;
        }

        switch (msg.what) {
            case TYPE_DOWNLOAD_FAIL:
                // 下载失败
                onDownFail((String) msg.obj);
                break;
            case TYPE_DOWNLOAD_SUCC:
                // 下载完成
                String url = (String) msg.obj;
                installApk(getStoragePath() + "/" + getFileNameOfTailFromUrl(url));
                onDownOver(url);
                break;
            case TYPE_DOWNLOAD_PORGRESS:
                // 下载中
                onUpdateProgress((String) msg.obj, msg.arg1);
                break;
        }
    }

    @Deprecated
    private void onDownFail(String url) {
        NotificationInfo info = getHashMap().get(url);
        if (info != null) {
            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    .setAutoCancel(true)
                    .setContentTitle(info.name)
                    .setContentText("下载失败,请重新下载")
                    .setContentIntent(geUpdateIntent())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true);
            info.notification = builder.getNotification();


            notificationManager.notify(info.id, info.notification);
            getHashMap().remove(url);
        }

        if (getHashMap().size() <= 0) {
            DownAPKService.this.stopSelf();
        }
    }

    @Deprecated
    private void onDownOver(String url) {
        NotificationInfo info = getHashMap().get(url);
        if (info != null) {
            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    .setAutoCancel(true)
                    .setContentTitle(info.name)
                    .setContentText("下载完成,点击安装")
                    .setContentIntent(getInstallIntent(getStoragePath() + "/"
                            + getFileNameOfTailFromUrl(url)))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true);
            info.notification = builder.getNotification();
            notificationManager.notify(info.id, info.notification);
            getHashMap().remove(url);
        }

        if (getHashMap().size() <= 0) {
            DownAPKService.this.stopSelf();
        }
    }

    @Deprecated
    private void onUpdateProgress(String url, int percent) {
        NotificationInfo info = getHashMap().get(url);
        if (info != null) {
            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    .setAutoCancel(true)
                    .setContentTitle(info.name)
                    .setContentText("已下载完成:"
                            + percent + "%")
                    .setContentIntent(geUpdateIntent())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true);
            info.notification = builder.getNotification();

            notificationManager.notify(info.id, info.notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class NotificationInfo {
        public int id = 0;
        public String name = null;
        public Notification notification = null;

        public NotificationInfo(int id, String name, Notification notification) {
            this.id = id;
            this.name = name;
            this.notification = notification;
        }
    }

    private interface AppDownUpdateListener {
        public void onUpdate(int percent);
    }

    private String getStoragePath() {
        /* 检测是否存在SD卡 */
        File filePath = null;
        String exStorageState = Environment.getExternalStorageState();
        if (exStorageState != null
                && Environment.MEDIA_SHARED.equals(exStorageState)) {
            // 有SD卡，手机直接连接到电脑作为u盘使用之后的状态
            return null;
        }

        if (exStorageState != null
                && Environment.MEDIA_MOUNTED.equals(exStorageState)) {
            // sd卡在手机上正常使用状态
            // 是否存在外存储器(优先判断)
            filePath = Environment.getExternalStorageDirectory();
        } else if (isExitInternalStorage()) {
            // 如果外存储器不存在，则判断内存储器
            filePath = AppManager.getInstance().getFilesDir();
        }

        if (filePath != null) {
            return filePath.getPath() + "/";
        } else {
            return null;
        }
    }

    // 是否存在内存储器
    private static boolean isExitInternalStorage() {
        File file = AppManager.getInstance().getFilesDir();

        if (file != null) {
            return true;
        }

        return false;
    }

    /**
     * 通过Url获得文件名(带后缀名)
     */
    private String getFileNameOfTailFromUrl(String url) {
        String fileName = "";

        if (url == null || url.trim().length() <= 0) {
            return fileName;
        }

        int start = url.lastIndexOf("/") + 1;
        int end = url.length();

        if (start > 0 && end > 0 && end > start) {
            fileName = url.substring(start, end);
        }

        return fileName;
    }

    /**
     * 安装版本更新的APK
     */
    public static void installApk(String path) {
        File apkfile = new File(path);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppManager.context.startActivity(i);
    }
}
