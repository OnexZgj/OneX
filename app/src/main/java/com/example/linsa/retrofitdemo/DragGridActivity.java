package com.example.linsa.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.adapter.DragGridAdapter;
import com.example.linsa.retrofitdemo.designview.DragGridView;

import java.util.ArrayList;
import java.util.List;

public class DragGridActivity extends AppCompatActivity {
    private DragGridView otherGridView;
    private DragGridView mGridView;

    private List<String> list = new ArrayList();

    private List<String> list2 = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_drag_grid);
        mGridView = (DragGridView) findViewById(R.id.mGridView);
        otherGridView = (DragGridView) findViewById(R.id.otherGridView);
        list.add("杭州");
        list.add("宁波");
        list.add("上海");
        list.add("北京");
        list.add("南京");
        list.add("西安");
        final MAdapter mAdapter = new MAdapter(list);

        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("4");
        list2.add("5");
        list2.add("6");
        final MAdapter mAdapter2 = new MAdapter(list2);


        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = list.get(position);
                list.remove(s);
                list2.add(s);
                mAdapter2.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
            }
        });



        otherGridView.setAdapter(mAdapter2);

        otherGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = list2.get(position);
                list2.remove(s);
                list.add(s);
                mAdapter.notifyDataSetChanged();
                mAdapter2.notifyDataSetChanged();
            }
        });
    }




    class MAdapter extends DragGridAdapter<String> {

        public MAdapter(List list) {
            super(list);
        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent) {
            String text = getList().get(position);
            convertView = LayoutInflater.from(DragGridActivity.this).inflate(R.layout.item, null);
            TextView tv_text = (TextView) convertView.findViewById(R.id.tv_text);
            tv_text.setText(text);
            return convertView;
        }
    }
}
