package com.orange.indexscrollview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.VirtualLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VlayoutActivity extends AppCompatActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    public static void start(Context context) {
        Intent starter = new Intent(context, VlayoutActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout);
        ButterKnife.bind(this);

        initRecyclerview();
    }


    private void initRecyclerview(){
        VirtualLayoutManager virtualLayoutManager=new VirtualLayoutManager(this);
        recyclerview.setLayoutManager(virtualLayoutManager);

        RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0,10);
        recyclerview.setRecycledViewPool(viewPool);


    }
}
