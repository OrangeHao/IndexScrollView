package com.orange.indexscrollview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VlayoutActivity extends AppCompatActivity implements MyItemClickListener,TabSeleteListener{


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    VirtualLayoutManager mLayoutManager;

    private ArrayList<HashMap<String, Object>> listItem;
    MyAdapter Adapter_linearLayout,Adapter_GridLayout,Adapter_FixLayout,Adapter_ScrollFixLayout
            ,Adapter_FloatLayout,Adapter_ColumnLayout,Adapter_SingleLayout,Adapter_onePlusNLayout,
            Adapter_StickyLayout,Adapter_StaggeredGridLayout;

    TabAdapter mTabAdapter;

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
        mLayoutManager=new VirtualLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);

        RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0,10);
        recyclerview.setRecycledViewPool(viewPool);

        listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 500; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemImage", R.mipmap.ic_launcher);
            listItem.add(map);

        }

        /************************   linearlayout************************************/
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setItemCount(4);// 设置布局里Item个数
        linearLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        linearLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        linearLayoutHelper.setAspectRatio(6);// 设置设置布局内每行布局的宽与高的比
        linearLayoutHelper.setDividerHeight(10);
        linearLayoutHelper.setMarginBottom(100);
        Adapter_linearLayout  = new MyAdapter(this, linearLayoutHelper, 8, listItem) {
            // 参数2:绑定绑定对应的LayoutHelper
            // 参数3:传入该布局需要显示的数据个数
            // 参数4:传入需要绑定的数据

            // 通过重写onBindViewHolder()设置更丰富的布局效果
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 为了展示效果,将布局的第一个数据设置为linearLayout
                if (position == 0) {
                    holder.Text.setText("Linear");
                }

                //为了展示效果,将布局里不同位置的Item进行背景颜色设置
                if (position < 2) {
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 6) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                } else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }

            }
        };
        Adapter_linearLayout.setOnItemClickListener(this);


        /************************   Gridlayout************************************/
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        // 在构造函数设置每行的网格个数

        // 公共属性
        gridLayoutHelper.setItemCount(3);// 设置布局里Item个数
        gridLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        gridLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        gridLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
        gridLayoutHelper.setAspectRatio(6);// 设置设置布局内每行布局的宽与高的比
        // gridLayoutHelper特有属性（下面会详细说明）
        gridLayoutHelper.setWeights(new float[]{40, 30, 30});//设置每行中 每个网格宽度 占 每行总宽度 的比例
        gridLayoutHelper.setVGap(20);// 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(20);// 控制子元素之间的水平间距
        gridLayoutHelper.setAutoExpand(false);//是否自动填充空白区域
        gridLayoutHelper.setSpanCount(3);// 设置每行多少个网格
        Adapter_GridLayout=new MyAdapter(this,gridLayoutHelper,12,listItem){

            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                } else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }

            }
        };
        Adapter_GridLayout.setOnItemClickListener(this);


        StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper();
        stickyLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
        // 特有属性
        stickyLayoutHelper.setStickyStart(true);
//        Adapter_StickyLayout = new MyAdapter(this, stickyLayoutHelper,1, listItem) {
//            // 设置需要展示的数据总数,此处设置是1
//            // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为Stick
//            @Override
//            public void onBindViewHolder(MainViewHolder holder, int position) {
//                super.onBindViewHolder(holder, position);
//                if (position == 0) {
//                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.red));
//                }
//            }
//        };
//        Adapter_StickyLayout.setOnItemClickListener(this);
        mTabAdapter=new TabAdapter(stickyLayoutHelper,this,1,this);


        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper(4);
        // 在构造函数里传入显示的Item数
        // 最多是1拖4,即5个
        onePlusNLayoutHelper.setItemCount(2);// 设置布局里Item个数
        onePlusNLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        onePlusNLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        onePlusNLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
        onePlusNLayoutHelper.setAspectRatio(3);// 设置设置布局内每行布局的宽与高的比
        Adapter_onePlusNLayout=new MyAdapter(this,onePlusNLayoutHelper,4,listItem){
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                } else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }
            }
        };
        Adapter_onePlusNLayout.setOnItemClickListener(this);



        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(Adapter_linearLayout);
        adapters.add(Adapter_GridLayout);
        adapters.add(mTabAdapter);
        adapters.add(Adapter_onePlusNLayout);
        adapters.add(Adapter_linearLayout);
        adapters.add(Adapter_linearLayout);

        DelegateAdapter delegateAdapter = new DelegateAdapter(mLayoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerview.setAdapter(delegateAdapter);


        recyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(5, 5, 5, 5);
            }
        });





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
                    if (mTabAdapter.mTablayout==null){
                        return;
                    }
                    if (mLayoutManager.findFirstVisibleItemPosition()==21) {
                        if (mTabAdapter.mTablayout.getCurrentTab()!=0){
                            mTabAdapter.mTablayout.setCurrentTab(0);
                        }
                    } else if (mLayoutManager.findFirstVisibleItemPosition()==28) {
                        if (mTabAdapter.mTablayout.getCurrentTab()!=1){
                            mTabAdapter.mTablayout.setCurrentTab(1);
                        }
                    } else if (mLayoutManager.findFirstVisibleItemPosition()==31) {
                        if (mTabAdapter.mTablayout.getCurrentTab()!=2){
                            mTabAdapter.mTablayout.setCurrentTab(2);
                        }
                    }
                }

                changeNestScrollStateByPosition();

                if (move) {
                    move = false;
                    int n = mIndex - mLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < recyclerview.getChildCount()) {
                        int top = recyclerview.getChildAt(n).getTop();
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
    private boolean actionFinish=true;

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

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, (String) listItem.get(position).get("ItemTitle"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabSeleted(int position) {
        Toast.makeText(this, "Tab selete:"+position, Toast.LENGTH_SHORT).show();
        actionFinish=false;

        if (position==0){
            move(21);
        }else if (position==1){
            move(28);
        }else if (position==2){
            move(33);
        }
    }
}
