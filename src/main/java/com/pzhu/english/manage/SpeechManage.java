package com.pzhu.english.manage;

import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

public class SpeechManage {
    private static SpeechManage manage = new SpeechManage();
    public static SpeechManage getDefaultSpeechManage(){
        return manage;
    }

    /**
     * 初始化语音引擎
     */
    public SpeechRecognizer initDefaultSpeech(Context ctx) {
        SpeechRecognizer mIat;
        SpeechUtility.createUtility(ctx, SpeechConstant.APPID + "=574823da");
        mIat = SpeechRecognizer.createRecognizer(ctx, null);
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        return mIat;
    }
}
