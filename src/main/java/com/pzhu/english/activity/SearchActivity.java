package com.pzhu.english.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pzhu.english.R;
import com.pzhu.english.domain.Vocabulary;
import com.pzhu.english.tools.DataUtils;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private TextView mDetail,mExample;
    private String[] data;
    private ArrayList<Vocabulary> vocabularies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText editText = (EditText) findViewById(R.id.et_search);
        mDetail = (TextView) findViewById(R.id.tv_detail);
        mExample = (TextView) findViewById(R.id.tv_example);
        final ListView mList = (ListView) findViewById(R.id.lv_search);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //数据库查询出来的数据有点小瑕疵，所以这段操作改善一下
                mDetail.setText(data[i]);
                String example = vocabularies.get(i).getExample();
                if(example.contains("/r/n")){
                    example = example.replace("/r/n", "");
                }
                mExample.setText("例句:\n" + example);
                editText.setText("");
                mDetail.setVisibility(View.VISIBLE);
                mExample.setVisibility(View.VISIBLE);
                mList.setVisibility(View.INVISIBLE);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mDetail.getVisibility() != View.INVISIBLE){
                    mDetail.setVisibility(View.INVISIBLE);
                    mExample.setVisibility(View.INVISIBLE);
                }
                vocabularies = DataUtils.searchVocabulary(charSequence.toString());
                if(vocabularies == null){
                    mList.setVisibility(View.INVISIBLE);
                    return;
                }
                //每次查询最多显示4条数据
                if(vocabularies.size() > 4){
                    data = new String[4];
                }else{
                    data = new String[vocabularies.size()];
                }
                for(int j=0;j<data.length;j++){
                    String chinses = vocabularies.get(j).getChinses();
                    if(chinses.contains("<br>")){
                        chinses = chinses.replace("<br>","");
                    }
                    data[j] = vocabularies.get(j).getEnglish() + "\n" + chinses;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchActivity.this,android.R.layout.simple_list_item_1,data);
                mList.setAdapter(adapter);
                mList.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

}
