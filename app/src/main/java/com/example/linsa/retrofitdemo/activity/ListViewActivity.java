package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.weidget.PullToRefreshListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListViewActivity extends AppCompatActivity {

    @InjectView(R.id.aap_ptrl_listview)
    PullToRefreshListView aapPtrlListview;

    private int m;
    private int k;

    private ArrayList<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_pay);
        ButterKnife.inject(this);



//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        loadData();
        final MyAdapter adapter = new MyAdapter();
        aapPtrlListview.setAdapter(adapter);

        aapPtrlListview.setOnRefreshListener(new PullToRefreshListView.onRefreshListener() {
            @Override
            public void refresh() {
                //下拉加载数据
                //加载数据的时候有延迟时间效果
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //加载新的数据,刷新界面
                        //list.add("艾欧尼亚"+(++m)+"区");
                        dataList.add(0, "艾欧尼亚" + (++m) + "区");//将数据添加list集合的哪个位置,location:要添加的位置,object:添加的数据
                        //更新界面
                        adapter.notifyDataSetChanged();
                        //刷新数据成功,取消刷新操作
                        aapPtrlListview.finish();
                    }
                }, 3000);
            }

            @Override
            public void loadMore() {
                //加载数据的时候有延迟时间效果
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //加载新的数据,刷新界面
                        //list.add("艾欧尼亚"+(++m)+"区");
                        dataList.add("黑色玫瑰" + (++k) + "区");//将数据添加list集合的哪个位置,location:要添加的位置,object:添加的数据
                        //更新界面
                        adapter.notifyDataSetChanged();
                        //刷新数据成功,取消刷新操作
                        aapPtrlListview.finish();
                    }
                }, 3000);
            }
        });


    }


    private void loadData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            dataList.add(String.valueOf(letter));
            letter++;
        }
    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyHolder holder;
            if (view == null) {
                holder = new MyHolder();
                view = View.inflate(ListViewActivity.this, R.layout.adapter_recyclerview, null);

                holder.textView = (TextView) view.findViewById(R.id.tv_item);


                view.setTag(holder);
            } else {
                holder= (MyHolder) view.getTag();
            }

            holder.textView.setText(dataList.get(i));
            return view;
        }


        class MyHolder {
            TextView textView;
        }
    }


}


