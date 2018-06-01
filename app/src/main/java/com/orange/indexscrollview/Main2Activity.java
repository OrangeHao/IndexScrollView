package com.orange.indexscrollview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.first_layout)
    TextView firstLayout;
    @BindView(R.id.second_layout)
    TextView secondLayout;
    @BindView(R.id.third_layout)
    TextView thirdLayout;
    @BindView(R.id.scroll_layout)
    NestedScrollView scrollLayout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;

    private int mCurrentScrollY = 0;

    public static void start(Context context) {
        Intent starter = new Intent(context, Main2Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final Tab tab) {
                int position = tab.getPosition();
                Log.d("czh","tabselected:"+position);
                Log.d("czh","mCurrentScrollY:"+mCurrentScrollY);
                if (position == 0) {
                    smoothToView(firstLayout);
                } else if (position == 1) {
                    smoothToView(secondLayout);
                } else if (position == 2) {
                    smoothToView(thirdLayout);
                }
                appBarLayout.setExpanded(false,true);
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });


        scrollLayout.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.d("czh","top:"+v.getTop());
//                Log.d("czh", "1:" + scrollX+"+"+scrollY+"/"+oldScrollX+"+"+scrollY);
//                Log.d("czh", "1:" + firstLayout.getY() + "+" + firstLayout.getTranslationY() + "+");
//                Log.d("czh", "2:" + secondLayout.getHeight());
//                Log.d("czh", "3:" + thirdLayout.getHeight());
                mCurrentScrollY = scrollY;
                if (scrollY < firstLayout.getHeight()) {
                    tabLayout.setScrollPosition(0, 0, true);
                } else if (scrollY > firstLayout.getHeight() && scrollY < (secondLayout.getHeight() + firstLayout.getHeight())) {
                    tabLayout.setScrollPosition(1, 0, true);
                } else if (scrollY > (secondLayout.getHeight() + firstLayout.getHeight())) {
                    tabLayout.setScrollPosition(2, 0, true);
                }
            }
        });
    }

    private void smoothToView(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int offset = location[1] - scrollLayout.getMeasuredHeight();
        if (offset < 0) {
            offset = 0;
        }
        scrollLayout.smoothScrollTo(0, offset);
    }
}
