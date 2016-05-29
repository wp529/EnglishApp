package com.pzhu.english.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.pzhu.english.activity.MainActivity;
import com.pzhu.english.activity.RememberActivity;
import com.pzhu.english.manage.ActivityManage;

public class UIUtils {

    public static void makeToast(Context ctx, String content) {
        Toast.makeText(ctx, content, Toast.LENGTH_SHORT).show();
    }

    public static void showFirstDialog(Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("你就不背了?");
        builder.setMessage("你这样你过得了四级吗?照照镜子,翻翻钱包,接着背!!!");
        builder.setCancelable(false);
        builder.setPositiveButton("必过四级!", null);
        builder.show();
    }

    public static void showSecondDialog(final Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("我永远叫不醒装睡的人");
        builder.setMessage("铁了心要走是呗?行吧,我也不拦你,不过要是你这样过了四级,请联系我,我叫你大哥");
        builder.setCancelable(false);
        builder.setNegativeButton("我觉得我还能抢救一下", null);
        builder.setPositiveButton("其实我是想去查查字典", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ctx.startActivity(new Intent(ctx,MainActivity.class));
                RememberActivity ac = (RememberActivity) ctx;
                ac.finish();
            }
        });
        builder.show();
    }

    public static void showSuccessDialog(final Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("少年天赋绝伦,必成大器");
        builder.setMessage("毅力惊人,距离过四级又前进了一大步,是否再战?");
        builder.setCancelable(false);
        builder.setNegativeButton("战便战", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ctx.startActivity(new Intent(ctx, MainActivity.class));
                RememberActivity ac = (RememberActivity) ctx;
                ac.finish();
            }
        });
        builder.setPositiveButton("待我养精蓄锐,明日再战", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityManage.finishAll();
            }
        });
        builder.show();
    }
}
