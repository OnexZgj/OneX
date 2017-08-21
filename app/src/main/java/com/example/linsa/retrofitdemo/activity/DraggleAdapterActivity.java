package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.adapter.ItemDragAdapter;
import com.example.linsa.retrofitdemo.bean.Movie;
import com.example.linsa.retrofitdemo.net.HttpMethods;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;

public class DraggleAdapterActivity extends AppCompatActivity {

    @InjectView(R.id.rv_ada_drag)
    RecyclerView rvAdaDrag;
    private List<Movie.SubjectsBean> mRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draggle_adapter);
        ButterKnife.inject(this);

        initData();

    }

    //初始化数据
    private void initData() {
        HttpMethods.getInstance().getTopMovie(new Subscriber<Movie>() {
            @Override
            public void onCompleted() {
                initAdapter();
                Toast.makeText(DraggleAdapterActivity.this, "请求数据成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(DraggleAdapterActivity.this, "请求数据失败!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Movie movie) {
                mRequestList = movie.getSubjects();
            }
        },0,20);
    }

    private void initAdapter() {
        ItemDragAdapter itemDragAdapter=new ItemDragAdapter(mRequestList);

        ItemDragAndSwipeCallback callback= new ItemDragAndSwipeCallback(itemDragAdapter);

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemDragAdapter.onAttachedToRecyclerView(rvAdaDrag);
        // 开启拖拽
        itemDragAdapter.enableDragItem(itemTouchHelper, R.id.tv_ibqa_title, true);
        itemDragAdapter.setOnItemDragListener(onItemDragListener);

// 开启滑动删除
//        itemDragAdapter.enableSwipeItem();
//        itemDragAdapter.setOnItemSwipeListener(onItemSwipeListener);




        rvAdaDrag.setLayoutManager(new LinearLayoutManager(this));
        rvAdaDrag.setAdapter(itemDragAdapter);
    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos){}
        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {}
        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {}
    };

}
