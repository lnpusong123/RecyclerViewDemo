package com.song.recyclerviewdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private List<String> mDatas;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter mAdapter;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    initData();
                    break;
                case 1:
                    loadData();
                    break;
            }
        }
    };

    private ListView lvList;
    private int lastVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        lvList = (ListView) findViewById(R.id.lv_list);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
        swipeRefreshLayout.setOnRefreshListener(this);

        initData();

        mAdapter = new MyAdapter();
        lvList.setAdapter(mAdapter);

        lvList.setOnScrollListener(this);

    }

    private void initData() {

        if (mDatas == null){
            mDatas = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                mDatas.add(i + ".我是一个条目");
            }
        }else {
            mDatas.clear();
            for (int i = 0; i < 20; i++) {
                mDatas.add(i + ".我是一个条目");
            }
            mAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    private void loadData() {
        for (int i = 0; i < 20; i++) {
            mDatas.add(i + ".我是一个条目");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(0, 4000);
    }

    public static final int MORE_ITEM_TYPE = 0;
    public static final int COMMON_ITEM_TYPE = 1;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastVisiblePosition == mAdapter.getCount()-1){
//            mHandler.sendEmptyMessageDelayed(1, 4000);
//        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastVisiblePosition = lvList.getLastVisiblePosition();
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mDatas.size() + 1;
        }

        @Override
        public String getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getCount() - 1){
                return MORE_ITEM_TYPE;
            }else {
                return COMMON_ITEM_TYPE;
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BaseHolder holder;
            if (convertView == null){
                if (getItemViewType(position) == MORE_ITEM_TYPE){
                    holder = new MoreHolder();
                    convertView = View.inflate(getApplicationContext(), R.layout.list_more_loading, null);
                    convertView.setTag(holder);
                }else{
                    convertView = View.inflate(getApplicationContext(), R.layout.item_listview, null);
                    holder = new ViewHolder();
                    ((ViewHolder)holder).tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                    convertView.setTag(holder);
                }
            }else{
                holder = (BaseHolder) convertView.getTag();
            }
            if (getItemViewType(position) == COMMON_ITEM_TYPE){
                ((ViewHolder)holder).tvTitle.setText(getItem(position));
            }else {
                mHandler.sendEmptyMessageDelayed(1, 4000);
            }

            return convertView;
        }
    }

    static class ViewHolder extends BaseHolder{
        public TextView tvTitle;
    }

    static class MoreHolder extends BaseHolder{

    }

}
