package com.pzhu.english.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.pzhu.english.R;
import com.pzhu.english.tools.DataUtils;

public class GuideActivity extends BaseActivity{
    private static final String TAG = "GuideActivity";
    int[] imageID = new int[]{R.drawable.guide1,R.drawable.guide2,R.drawable.guide3,R.drawable.guide4};
    ImageView[] images = new ImageView[imageID.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        final Button into = (Button) findViewById(R.id.btn_into);
        initData();
        vp.setAdapter(new ImageAdapter());
        into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = DataUtils.getDefalutSharedPreferences(GuideActivity.this);
                sp.edit().putBoolean("show_guide",true).commit();
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });

        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if(position == imageID.length - 1){
                    into.setVisibility(View.VISIBLE);
                }else{
                    into.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initData() {
        for(int i = 0;i<imageID.length;i++){
            ImageView iv = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageID[i]);
            iv.setImageBitmap(bitmap);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            images[i] = iv;
        }
    }

    class ImageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images[position]);
            return images[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
