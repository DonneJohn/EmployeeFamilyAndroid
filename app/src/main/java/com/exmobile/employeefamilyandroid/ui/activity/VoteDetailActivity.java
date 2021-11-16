package com.exmobile.employeefamilyandroid.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.Vote;
import com.exmobile.employeefamilyandroid.bean.VoteItem;
import com.exmobile.employeefamilyandroid.event.Events;
import com.exmobile.employeefamilyandroid.event.RxBus;
import com.exmobile.employeefamilyandroid.presenter.VoteDetailPresenter;
import com.exmobile.employeefamilyandroid.utils.DialogFactory;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(VoteDetailPresenter.class)
public class VoteDetailActivity extends BaseHoldBackActivity<VoteDetailPresenter> {

    public static String KEY_VOTE = "vote";
    private Dialog dialog;
    private Answer answer;
    private ArrayList<Answer> answers = new ArrayList<>();
    @BindView(R.id.radiogroup_vote_detail)
    RadioGroup radioGroup;
    @BindView(R.id.tv_vote_detail_title)
    TextView tvTitle;
    @BindView(R.id.tv_vote_detail_time)
    TextView tvTime;
    @BindView(R.id.tv_vote_detail_content)
    TextView tvContent;
    @BindView(R.id.btn_vote_detail)
    Button btn;
    @BindColor(R.color.gray)
    int gray;
    @BindView(R.id.tv_vote_category)
    TextView tvVoteCategory;
    @BindView(R.id.rl_vote_detail)
    RecyclerView rvVoteDetail;
    private Vote vote;

    @OnClick(R.id.btn_vote_detail)
    public void OnClick() {
        Gson gson = new Gson();
        if (vote.getVoteCategory().equals("1")) {
            if (answer == null) {
                Toast.makeText(VoteDetailActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<Answer> singleAnswers = new ArrayList<>();
                singleAnswers.add(answer);
                getPresenter().submitVote(gson.toJson(singleAnswers));
            }
        } else if (vote.getVoteCategory().equals("2")) {

            if (answers.size() == 0) {
                Toast.makeText(VoteDetailActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
            } else {
                dialog = DialogFactory.getFactory().getLoadingDialog(this);
                dialog.show();
                getPresenter().submitVote(gson.toJson(answers));
            }

        }
    }

    @Override
    protected String onSetTitle() {
        return "调查报告";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_vote_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        vote = (Vote) getIntent().getSerializableExtra(KEY_VOTE);


        tvTitle.setText(vote.getVoteTitle());
        tvTime.setText(String.format(tvTime.getText().toString(), vote.getCreateTime()));
        tvContent.setText(vote.getVoteShortText());
        if (vote.getIfVoted().equals("1")) {
            btn.setClickable(false);
            btn.setText("已提交");
            btn.setBackgroundResource(R.color.light_gray);
        }

        if (vote.getVoteCategory().equals("1")) {
            tvVoteCategory.setText("单选");
            rvVoteDetail.setVisibility(View.GONE);
            for (int i = 0; i < vote.getVoteItemList().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setButtonDrawable(R.drawable.radio_btn_selector);
                radioButton.setPadding(80, 10, 0, 10);
                radioButton.setText(vote.getVoteItemList().get(i).getVoteItemTitle() + "." + vote.getVoteItemList().get(i).getVoteItemText());
                radioButton.setTextColor(gray);
                radioButton.setGravity(Gravity.CENTER_VERTICAL);
                radioButton.setTag(i);
                radioGroup.addView(radioButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            }


            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                Integer position = (Integer) radioButton.getTag();
                answer = new Answer();
                answer.setVoteRowID(vote.getRowID());
                answer.setAnswer(vote.getVoteItemList().get(position).getRowID());
            });
        } else if (vote.getVoteCategory().equals("2")) {
            tvVoteCategory.setText("多选");

            radioGroup.setVisibility(View.GONE);
            rvVoteDetail.setVisibility(View.VISIBLE);

            rvVoteDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvVoteDetail.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new VoteDetailViewHolder(LayoutInflater.from(VoteDetailActivity.this).inflate(R.layout.list_item_vote_detail, parent, false));
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    VoteItem voteItem = vote.getVoteItemList().get(position);
                    VoteDetailViewHolder viewHolder = (VoteDetailViewHolder) holder;
                    viewHolder.tvVoteDetail.setText(voteItem.getVoteItemText());

                    viewHolder.cbVoteDetail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                answer = new Answer();
                                answer.setVoteRowID(vote.getRowID());
                                answer.setAnswer(voteItem.getRowID());
                                answers.add(answer);
                            } else {

                                if (answers.contains(answer)) {
                                    answers.remove(answer);
                                }
                            }
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return vote.getVoteItemList().size();
                }
            });

        }


    }

    public void onLoadSuccessful(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        if (dialog != null) dialog.dismiss();
        btn.setClickable(false);
        btn.setText("已提交");
        btn.setBackgroundResource(R.color.light_gray);
        RxBus.getInstance().send(Events.EventEnum.DELIVER_VOTE, null);
//        finish();
    }

    public void onLoadFailed(String message) {
        if (dialog != null) dialog.dismiss();
        if (message == null) {
            Toast.makeText(this, "未知原因", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class Answer {
        private String VoteRowID;
        private String Answer;

        public String getVoteRowID() {
            return VoteRowID;
        }

        public void setVoteRowID(String voteRowID) {
            VoteRowID = voteRowID;
        }

        public String getAnswer() {
            return Answer;
        }

        public void setAnswer(String answer) {
            Answer = answer;
        }
    }

    class VoteDetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_vote_detail)
        CheckBox cbVoteDetail;
        @BindView(R.id.tv_vote_detail)
        TextView tvVoteDetail;

        public VoteDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
