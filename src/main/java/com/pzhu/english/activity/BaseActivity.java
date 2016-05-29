package com.pzhu.english.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.pzhu.english.manage.ActivityManage;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManage.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManage.removeActivity(this);
        super.onDestroy();
    }
}
