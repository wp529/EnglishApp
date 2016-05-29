package com.pzhu.english.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.pzhu.english.R;
import com.pzhu.english.manage.DatabaseManage;
import com.pzhu.english.tools.DataUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SplashActivity extends BaseActivity {

    private static final String DICTIONARY_NAME = "dictionary.db";
    private static final String TAG = "SplashActivity";
    private RelativeLayout rl;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rl = (RelativeLayout) findViewById(R.id.rl);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        startAni();
        copyDB();
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    private void startAni() {
        AnimationSet set = new AnimationSet(false);
        // 旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(1000);// 动画时间
        rotate.setFillAfter(true);// 保持动画状态
        // 缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale.setDuration(1000);// 动画时间
        scale.setFillAfter(true);// 保持动画状态
        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences sp = DataUtils.getDefalutSharedPreferences(SplashActivity.this);
                boolean showGuide = sp.getBoolean("show_guide", false);
                if (showGuide) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rl.startAnimation(set);

    }

    private void copyDB() {
        File outFile = new File(DatabaseManage.DICTIONARY_PATH);
        if (outFile.exists()) {
            //不需要再导入数据库

        } else {
            InputStream is = getClass().getClassLoader().getResourceAsStream("assets/" + DICTIONARY_NAME);
            File databaseFile = new File("/data/data/com.pzhu.english/database/");
            if (!databaseFile.exists()) {
                databaseFile.mkdir();
            }
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(outFile);
                byte[] bys = new byte[1024];
                int len;
                while ((len = is.read(bys)) != -1) {
                    fos.write(bys, 0, len);
                }
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
