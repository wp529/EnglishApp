package com.pzhu.english.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechRecognizer;
import com.pzhu.english.R;
import com.pzhu.english.adapter.RecognizerAdapter;
import com.pzhu.english.domain.Vocabulary;
import com.pzhu.english.manage.SpeechManage;
import com.pzhu.english.tools.DataUtils;
import com.pzhu.english.tools.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RememberActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RememberActivity";
    private TextView mTimes, mWord, mTran, mAnswer, mNumber;
    private FloatingActionButton mSay, mNext, mAgain,mShow;
    private SpeechRecognizer speechRecognizer;
    private List<Vocabulary> vocabularies, currentTimesList, currentList;
    private Vocabulary showVoca;
    private Random rand;
    private int currentTimes = 1;
    private int answerNumber = 1;
    private int press = 0;

    private RecognizerListener mRecListener = new RecognizerAdapter() {
        String result = "不能识别，请重试";

        @Override
        public void onEndOfSpeech() {
            //当前正在答的单词
//            remove = currentList.remove(currentWord);

            if (showVoca.getChinses().contains(result)) {
                //回答正确,答下一个单词
                Toast.makeText(RememberActivity.this, showVoca.getEnglish() + ",回答正确", Toast.LENGTH_SHORT).show();
                if (currentList.isEmpty()) {
                    //这一轮回答完毕，进入下一轮
                    nextTurn();
                } else {
                    nextWord();
                }
            } else {
                //回答错误
                mAnswer.setText("你的答案是:" + result);
                mAnswer.setVisibility(View.VISIBLE);
                if("不能识别，请重试".equals(result)){
                    UIUtils.makeToast(RememberActivity.this,"请检查网络是否连接");
                    return;
                }
                mNext.setVisibility(View.VISIBLE);
                mTran.setText("回答错误，正确答案为:" + showVoca.getChinses());
                mTran.setVisibility(View.VISIBLE);
                mAgain.setVisibility(View.VISIBLE);
                //重来
            }
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            if (!b) {
                String readResult = DataUtils.parseIatResult(recognizerResult.getResultString());
                if (!TextUtils.isEmpty(readResult)) {
                    result = readResult;
                }
            }
        }
    };

    //下一个回答的单词
    private void nextWord() {
        if (!currentList.isEmpty()) {
            answerNumber++;
            mNumber.setText("本轮第" + answerNumber + "个单词，再回答对" + (currentTimes - answerNumber) + " 个过关");
            mAnswer.setVisibility(View.INVISIBLE);
            mTran.setVisibility(View.INVISIBLE);
            mAgain.setVisibility(View.INVISIBLE);
            mNext.setVisibility(View.INVISIBLE);
            int remainSize = currentList.size();//当前这轮的单词还没答的数量
            int currentWord = rand.nextInt(remainSize);//随机再取下一个单词出来
            showVoca = currentList.remove(currentWord);
            mWord.setText(showVoca.getEnglish());
        } else {
            nextTurn();
        }
    }

    //下一轮
    private void nextTurn() {
        currentTimes++;
        //完成一次
        if (currentTimes > 10) {
            SharedPreferences sp = DataUtils.getDefalutSharedPreferences(this);
            long passTime = sp.getLong("pass_time", 0);
            passTime++;
            sp.edit().putLong("pass_time",passTime).commit();
            UIUtils.showSuccessDialog(this);
            return;
        }
        answerNumber = 1;
        mNumber.setText("本轮第" + answerNumber + "个单词，再回答对" + (currentTimes - answerNumber) + "个过关");
        mAnswer.setVisibility(View.INVISIBLE);
        mTran.setVisibility(View.INVISIBLE);
        mAgain.setVisibility(View.INVISIBLE);
        mNext.setVisibility(View.INVISIBLE);
        mTimes.setText("第" + currentTimes + "轮，还有" + (10 - currentTimes) + "轮就通关");
        currentTimesList.add(vocabularies.get(currentTimes - 1));
        currentList = new ArrayList<>(currentTimesList);
        showVoca = currentList.remove(rand.nextInt(currentList.size()));
        mWord.setText(showVoca.getEnglish());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initOnclick();
        initData();
    }

    private void initData() {
        speechRecognizer = SpeechManage.getDefaultSpeechManage().initDefaultSpeech(this);
        vocabularies = DataUtils.getVocabulariesFromDatabase();
        currentTimesList = new ArrayList<>();
        currentTimesList.add(vocabularies.get(0));
        currentList = new ArrayList<>();
        currentList.add(vocabularies.get(0));
        showVoca = currentList.remove(0);
        mWord.setText(showVoca.getEnglish());
        rand = new Random();
        //remove = currentList.get(0);
    }

    private void initOnclick() {
        mSay.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mAgain.setOnClickListener(this);
        mShow.setOnClickListener(this);
    }

    private void initUI() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remember);
        mTimes = (TextView) findViewById(R.id.tv_times);
        mTimes.setText("第1轮，还有9轮就通关");
        mWord = (TextView) findViewById(R.id.tv_word);
        mAnswer = (TextView) findViewById(R.id.tv_answer);
        mTran = (TextView) findViewById(R.id.tv_translate);
        mSay = (FloatingActionButton) findViewById(R.id.btn_say);
        mAgain = (FloatingActionButton) findViewById(R.id.btn_again);
        mNext = (FloatingActionButton) findViewById(R.id.btn_next);
        mShow = (FloatingActionButton) findViewById(R.id.btn_show);
        mNumber = (TextView) findViewById(R.id.tv_number);
        mNumber.setText("本轮第1个单词，再回答对0个过关");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_say:
                if(mAnswer.getVisibility() == View.VISIBLE){
                    mAnswer.setVisibility(View.INVISIBLE);
                }

                if(mNext.getVisibility() == View.VISIBLE){
                    mNext.setVisibility(View.INVISIBLE);
                }

                if (mTran.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, "看着答案回答问题，你好意思吗？", Toast.LENGTH_SHORT).show();
                } else {
                    startSpeech();
                }
                break;
            case R.id.btn_next:
                nextWord();
                break;
            case R.id.btn_show:
                mTran.setText(showVoca.getChinses());
                mTran.setVisibility(View.VISIBLE);
                mAgain.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_again:
                //重来
                again();
                break;
        }
    }

    //重来
    private void again() {
        if(mTran.getVisibility() == View.VISIBLE && mAnswer.getVisibility() == View.INVISIBLE){
            mTran.setVisibility(View.INVISIBLE);
            mAgain.setVisibility(View.INVISIBLE);
        }else{
            answerNumber = 1;
            mNumber.setText("本轮第" + answerNumber + "个单词，再回答对" + (currentTimes - answerNumber) + " 个过关");
            currentList = new ArrayList<>(currentTimesList);
            showVoca = currentList.remove(rand.nextInt(currentList.size()));
            mWord.setText(showVoca.getEnglish());
            mTran.setVisibility(View.INVISIBLE);
            mAnswer.setVisibility(View.INVISIBLE);
            mAgain.setVisibility(View.INVISIBLE);
            mNext.setVisibility(View.INVISIBLE);
        }
    }


    private void startSpeech() {
        speechRecognizer.startListening(mRecListener);
    }

    @Override
    protected void onDestroy() {
        speechRecognizer.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (press == 0) {
            press++;
            UIUtils.showFirstDialog(this);
        } else {
            UIUtils.showSecondDialog(this);
        }
    }
}
