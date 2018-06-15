package com.orange.indexscrollview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * created by czh on 2018/6/5
 */
public class TabAdapter extends DelegateAdapter.Adapter<TabAdapter.TabViewHolder>{

    private LayoutHelper layoutHelper;
    private Context context;
    private int count;
    private TabSeleteListener mTabSeleteListener;
    public CommonTabLayout mTablayout;

    public TabAdapter(LayoutHelper layoutHelper, Context context, int count, TabSeleteListener mTabSeleteListener) {
        this.layoutHelper = layoutHelper;
        this.context = context;
        this.count = count;
        this.mTabSeleteListener = mTabSeleteListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TabViewHolder((LayoutInflater.from(context).inflate(R.layout.item_tablayout, parent, false)));
    }

    @Override
    public void onBindViewHolder(TabViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return count;
    }

    //定义Viewholder
    class TabViewHolder extends RecyclerView.ViewHolder {

        public TabViewHolder(View root) {
            super(root);
            mTablayout = root.findViewById(R.id.tabLayout);
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
            mTablayout.setTabData(tabData);
            mTablayout.setIndicatorAnimEnable(true);
            mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    if (mTabSeleteListener!=null){
                        mTabSeleteListener.onTabSeleted(position);
                    }
                }

                @Override
                public void onTabReselect(int position) {

                }
            });
        }
    }
}
