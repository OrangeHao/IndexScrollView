package com.orange.indexscrollview;

import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private AppBarStateChangeListener.State mState=AppBarStateChangeListener.State.EXPANDED;

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
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new Fragment();
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        String[] tabNames = {"one", "two", "three"};
        tabLayout.setViewPager(viewpager, tabNames);

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                recyclerview.smoothScrollToPosition(position);
//                mLayoutManager.scrollToPositionWithOffset(position,0);

                int top=tabLayout.getTop();
                appBarLayout.setExpanded(false,true);

                move(position);
                if (position == 0) {

                }
                if (position == 1) {
                }

                if (position == 2) {
                }
            }

            @Override
            public void onTabReselect(int position) {

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

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                TextView temp = recyclerView.findChildViewUnder(0, 0).findViewById(R.id.content);
                if (temp.getText().toString().equals("0") && viewpager.getCurrentItem()!= 0) {
                    viewpager.setCurrentItem(0);
                } else if (temp.getText().toString().equals("1") && viewpager.getCurrentItem()!= 1) {
                    viewpager.setCurrentItem(1);
                } else if (temp.getText().toString().equals("2") && viewpager.getCurrentItem()!= 2) {
                    viewpager.setCurrentItem(2);
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





    boolean move = true;

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
