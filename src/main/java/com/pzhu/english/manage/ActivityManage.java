package com.pzhu.english.manage;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManage {
    private static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity ac){
        activities.add(ac);
    }

    public static void removeActivity(Activity ac){
        activities.remove(ac);
    }

    public static void finishAll(){
        for(Activity ac : activities){
            if(!ac.isFinishing()){
                ac.finish();
            }
        }
    }
}
