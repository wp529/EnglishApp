package com.pzhu.english.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pzhu.english.R;
import com.pzhu.english.tools.DataUtils;

public class MineActivity extends BaseActivity {

    private TextView level;
    private RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        level = (TextView) findViewById(R.id.tv_level);
        back = (RelativeLayout) findViewById(R.id.rl_back);
        long time = DataUtils.getDefalutSharedPreferences(this).getLong("pass_time", 0);
        showLevel(time);
    }

    private void showLevel(long time) {
        if(time == 0){
            level.setText("一次都没背完你还好意思点进来看，下次四级考试负分!你的前途看到没有，就像这背景一样黑暗。\n预测成绩:负分！");
        }else if(time == 1){
            level.setText("背完一次了，可以，四级考试应该能超过个位数，加油！\n预测成绩:15分");
        }else if(time > 1 && time < 5){
            level.setText("看来是在认真干了，四级考试能上一百分了，还需继续努力。\n预测成绩:150分");
        }else if(time >= 5 && time < 15){
            back.setBackgroundColor(Color.GRAY);
            level.setText("能上两百分了，前途也没那么黑暗了，再多背背，fighting!\n预测成绩:240分");
        }else{
            int score = (int) (time * 17);
            if(score > 750){
                score = 750;
            }
            level.setText("fighting! \n 预测成绩:" + score);
        }
    }
}
