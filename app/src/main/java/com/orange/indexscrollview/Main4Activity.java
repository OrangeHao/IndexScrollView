package com.orange.indexscrollview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main4Activity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ButterKnife.bind(this);

        mContext=this;
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                MainActivity.start(mContext);
                break;
            case R.id.btn2:
                Main2Activity.start(mContext);
                break;
            case R.id.btn3:
                Main3Activity.start(mContext);
                break;
            case R.id.btn4:
                VlayoutActivity.start(mContext);
                break;
        }
    }
}
