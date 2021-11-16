package com.exmobile.employeefamilyandroid.widget;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

/**
 * tabhost
 * @author zcc
 * @version 创建时间：2016年3月7日 下午2:27:51
 * 
 */

public class MyFragmentTabHost extends FragmentTabHost {
	
	private String mCurrentTag;
	
	private String mNoTabChangedTag;
	
	public MyFragmentTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void onTabChanged(String tag) {
		
		if (tag.equals(mNoTabChangedTag)) {
			setCurrentTabByTag(mCurrentTag);
		} else {
			super.onTabChanged(tag);
			mCurrentTag = tag;
		}
	}
	
	public void setNoTabChangedTag(String tag) {
		this.mNoTabChangedTag = tag;
	}
}
