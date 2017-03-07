package com.example.appdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.qianmo.common.view.AppCommonLoadRecyclerView;
import com.qianmo.common.view.RecyclerLoadListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements RecyclerLoadListener {

    private AppCommonLoadRecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mData = new ArrayList<String>();
        mRecyclerView = (AppCommonLoadRecyclerView) findViewById(R.id.recyclerview);

        View view1 = LayoutInflater.from(this).inflate(R.layout.common_load_view, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.common_load_view, null);
        mAdapter = new ListAdapter(this, mData);
        for (int i = 0; i < 20; i++) {
            mData.add("新增加的数据哦：" + i);
        }
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setLoadEnable(true);
        mRecyclerView.setLoadView(view1);
        mRecyclerView.setRecyclerLoadListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    mRecyclerView.onLoadComplete();
                    for (int i = 0; i < 20; i++) {
                        mData.add("新增加的数据哦：" + i);
                    }

                    mAdapter.notifyDataSetChanged();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
