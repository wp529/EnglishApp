package com.pzhu.english.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.pzhu.english.domain.Vocabulary;
import com.pzhu.english.manage.DatabaseManage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    private static ArrayList<Vocabulary> searchList = new ArrayList<>();

    /**
     * 获取SharedPreferences
     * @param ctx
     * @return
     */
    public static SharedPreferences getDefalutSharedPreferences(Context ctx){
        return ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
    }

    /**
     * 解析读的结果
     *
     * @param json 从服务器返回的对比json
     * @return 解析完成的结果
     */
    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener token = new JSONTokener(json);
            JSONObject joResult = new JSONObject(token);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * 获得一个从数据库随机查找出来的10个单词的集合
     *
     * @return 查找出来的集合
     */
    public static List<Vocabulary> getVocabulariesFromDatabase() {
        List<Vocabulary> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseManage.getDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseManage.DICTIONARY_TABLE + " ORDER BY RANDOM() limit 10", null);
        if (cursor.moveToFirst()) {
            do {
                String english = cursor.getString(cursor.getColumnIndex(DatabaseManage.DICTIONARY_ENGLISH));
                String chinese = cursor.getString(cursor.getColumnIndex(DatabaseManage.DICTIONARY_CHINESE));
                String example = cursor.getString(cursor.getColumnIndex(DatabaseManage.DICTIONARY_EXAMPLE));
                Vocabulary voc = new Vocabulary(english, chinese,example);
                list.add(voc);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }

    /**
     * 根据用户的输入从数据库查询出的结果
     * @return
     */
    public static ArrayList<Vocabulary> searchVocabulary(String input){
        searchList.clear();
        //查询数据库获得结果
        //模糊查询sql语句
        String sql = null;
        if(!TextUtils.isEmpty(input)){
            if(DataUtils.isLetter(input)){
                //如果是字母，查询english字段
                sql =  "SELECT  * FROM "+DatabaseManage.DICTIONARY_TABLE +" where "+ DatabaseManage.DICTIONARY_ENGLISH +" like '" + input + "%'";
            }else{
                //查询chinese字段
                sql = "SELECT  * FROM "+DatabaseManage.DICTIONARY_TABLE +" where "+ DatabaseManage.DICTIONARY_CHINESE +" like '%" + input + "%'";
            }
        }

        SQLiteDatabase db = DatabaseManage.getDatabase();
        Cursor cursor;
        if(sql != null){
            cursor = db.rawQuery(sql, null);
        }else {
            return null;
        }
        if(cursor.moveToFirst()){
            do{
                String english = cursor.getString(cursor.getColumnIndex(DatabaseManage.DICTIONARY_ENGLISH));
                String chinese = cursor.getString(cursor.getColumnIndex(DatabaseManage.DICTIONARY_CHINESE));
                String example = cursor.getString(cursor.getColumnIndex(DatabaseManage.DICTIONARY_EXAMPLE));
                Vocabulary voc = new Vocabulary(english, chinese,example);
                searchList.add(voc);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return searchList;
    }

    /**
     * 判断一个字符串首位是否为字母
     * @param s 需要判断的字符串
     * @return true 是字母 false 不是字母
     */
    public static boolean isLetter(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }
}
