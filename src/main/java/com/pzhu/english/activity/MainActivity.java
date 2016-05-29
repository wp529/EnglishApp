package com.pzhu.english.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.pzhu.english.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ImageView remember;
    private ImageView search;
    private ImageView mine;
    private ScaleAnimation scal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remember = (ImageView) findViewById(R.id.remember);
        search = (ImageView) findViewById(R.id.search);
        mine = (ImageView) findViewById(R.id.mine);
        scal = new ScaleAnimation(remember.getMeasuredWidth(),0,remember.getMeasuredHeight(),remember.getMeasuredHeight(), 0.5f,0);
        scal.setDuration(10);
        scal.setAnimationListener(new MyAnimationListener());
        search.setOnClickListener(this);
        remember.setOnClickListener(this);
        mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remember:
                search.setClickable(false);
                mine.setClickable(false);
                remember.startAnimation(scal);
                break;
            case R.id.search:
                mine.setClickable(false);
                remember.setClickable(false);
                search.startAnimation(scal);
                break;
            case R.id.mine:
                search.setClickable(false);
                remember.setClickable(false);
                mine.startAnimation(scal);
                break;
        }
    }

    @Override
    protected void onPause() {
        search.setClickable(true);
        mine.setClickable(true);
        remember.setClickable(true);
        super.onPause();
    }

    class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(remember.getAnimation() == animation){
                startActivity(new Intent(MainActivity.this, RememberActivity.class));
            }else if(search.getAnimation() == animation){
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }else{
                startActivity(new Intent(MainActivity.this, MineActivity.class));
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
