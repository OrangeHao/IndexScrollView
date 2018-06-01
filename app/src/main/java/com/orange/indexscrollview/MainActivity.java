package com.orange.indexscrollview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tabLayout)
    CommonTabLayout tabLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private boolean actionFinish=true;
    private AppBarStateChangeListener.State mState=AppBarStateChangeListener.State.EXPANDED;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                mState=state;
                if (state == State.EXPANDED) {
                } else if (state == State.COLLAPSED) {

                } else {

                }
            }
        });


        ArrayList<CustomTabEntity> tabData=new ArrayList<>();
        tabData.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "one";
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });
        tabData.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "two";
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });
        tabData.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "three";
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });
        tabLayout.setTabData(tabData);
        tabLayout.setIndicatorAnimEnable(true);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Log.d("czh","onTabSelect:"+position);

                actionFinish=false;
                appBarLayout.setExpanded(false,true);

                move(position);
            }

            @Override
            public void onTabReselect(int position) {
                Log.d("czh","onTabReselect:"+position);
            }
        });

        initRecyclerView();

    }


    private TestAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private final List<TestBean> mDataList = new ArrayList<>();

    private void initRecyclerView() {
        for (int i = 0; i < 4; i++) {
            mDataList.add(new TestBean());
        }
        mAdapter = new TestAdapter(mDataList);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(mAdapter);


        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("czh","state:"+newState);
                if (newState==0){
                    actionFinish=true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (actionFinish){
                    if (mLayoutManager.findFirstVisibleItemPosition()==0) {
                        if (tabLayout.getCurrentTab()!=0){
                            tabLayout.setCurrentTab(0);
                        }
                    } else if (mLayoutManager.findFirstVisibleItemPosition()==1) {
                        if (tabLayout.getCurrentTab()!=1){
                            tabLayout.setCurrentTab(1);
                        }
                    } else if (mLayoutManager.findFirstVisibleItemPosition()==2) {
                        if (tabLayout.getCurrentTab()!=2){
                            tabLayout.setCurrentTab(2);
                        }
                    }
                }

                changeNestScrollStateByPosition();

                //在这里进行第二次滚动（最后的100米！）
                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - mLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < recyclerview.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = recyclerview.getChildAt(n).getTop();
                        //最后的移动
                        recyclerview.smoothScrollBy(0, top);
                    }
                }

            }
        });
    }


    private void changeNestScrollStateByPosition(){
        View firstView=mLayoutManager.findViewByPosition(0);
        if (firstView!=null){
            int top = firstView.getTop();
            if (top<=1){
                recyclerview.setNestedScrollingEnabled(true);
            }else {
                recyclerview.setNestedScrollingEnabled(false);
            }
        }else {
            recyclerview.setNestedScrollingEnabled(false);
        }

    }





    boolean move = false;
    private int mIndex = 0;

    private void move(int n) {
        mIndex = n;
        recyclerview.stopScroll();
        smoothMoveToPosition(n);
    }

    private void smoothMoveToPosition(int n) {

        int firstItem = mLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLayoutManager.findLastVisibleItemPosition();

        Log.d("czh",firstItem+"/"+lastItem);
        if (n <= firstItem) {
            recyclerview.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mLayoutManager.findViewByPosition(n).getTop();
            recyclerview.smoothScrollBy(0, top);
        } else {
            recyclerview.smoothScrollToPosition(n);
            move = true;
        }

    }


    public static class TestBean {

    }

    public static class TestAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

        public TestAdapter(@Nullable List<TestBean> data) {
            super(R.layout.item_test_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TestBean item) {
            if (helper.getLayoutPosition() == 0) {
                helper.setText(R.id.content, "0");
                helper.setBackgroundColor(R.id.content, helper.itemView.getContext().getResources().getColor(R.color.text_green));
            } else if (helper.getLayoutPosition() == 1) {
                helper.setText(R.id.content, "1");
                helper.setBackgroundColor(R.id.content, helper.itemView.getContext().getResources().getColor(R.color.text_orange));

            } else if (helper.getLayoutPosition() == 2) {
                helper.setText(R.id.content, "2");
                helper.setBackgroundColor(R.id.content, helper.itemView.getContext().getResources().getColor(R.color.text_red));

            }
        }
    }

}
