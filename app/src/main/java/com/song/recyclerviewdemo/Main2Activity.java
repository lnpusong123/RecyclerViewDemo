package com.song.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ListView listView;
    private List<String> mDatas;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = (ListView) findViewById(R.id.list);

        initData();

        myAdapter = new MyAdapter();

        listView.setAdapter(myAdapter);

    }

    public void onClick(View v){
        ListAdapter adapter = listView.getAdapter();
        int count = adapter.getCount();
        int mHeight = 100 * listView.getDividerHeight();
        //遍历每一个条目，计算高度，然后累加
        for (int i = 0; i < count; i++){
            View view = adapter.getView(i, null, null);
            view.measure(0, 0);
            int measureHeight = view.getMeasuredHeight();
            mHeight += measureHeight;
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mHeight);

        listView.setLayoutParams(params);

    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            mDatas.add(i + ".哈哈哈，我是item哦");
        }
    }


    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public String getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(Main2Activity.this, R.layout.item_layout, null);
                holder.textView = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            String item = getItem(position);
            holder.textView.setText(item);
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView textView;
    }

}
