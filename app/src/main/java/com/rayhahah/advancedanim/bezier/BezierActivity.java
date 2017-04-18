package com.rayhahah.advancedanim.bezier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.rayhahah.advancedanim.R;

import java.util.ArrayList;
import java.util.List;

public class BezierActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar bnb;
    private SecondBezierView secondBV;
    private ThirdBezierView thirdBV;
    private List<View> mViewList = new ArrayList<>();
    private DrawPathView drawPath;
    private WaveView waveView;
    private BallRollView ballRollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        initView();

        initBnb();
    }

    /**
     * BottomNavigationBar初始化
     */
    private void initBnb() {
        bnb.setActiveColor("#ffffff")
                .setInActiveColor("#88000000")
                .setBarBackgroundColor("#ff0000")
                .setMode(BottomNavigationBar.MODE_SHIFTING);
        bnb.addItem(new BottomNavigationItem(R.mipmap.ic_white_flash_128, "二阶"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_white_flash_128, "三阶"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_white_flash_128, "写字板"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_white_flash_128, "波浪"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_white_flash_128, "滚动小球"))
                .initialise();
        bnb.setTabSelectedListener(this);

    }

    private void initView() {

        bnb = (BottomNavigationBar) findViewById(R.id.bnb);
        secondBV = (SecondBezierView) findViewById(R.id.secondBV);
        mViewList.add(secondBV);
        thirdBV = (ThirdBezierView) findViewById(R.id.thirdBV);
        mViewList.add(thirdBV);
        drawPath = (DrawPathView) findViewById(R.id.drawPath);
        mViewList.add(drawPath);
        waveView = (WaveView) findViewById(R.id.waveView);
        mViewList.add(waveView);
        ballRollView = (BallRollView) findViewById(R.id.ballRollView);
        mViewList.add(ballRollView);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                setViewVisible(secondBV);
                break;
            case 1:
                setViewVisible(thirdBV);
                break;
            case 2:
                setViewVisible(drawPath);
                break;
            case 3:
                setViewVisible(waveView);
                break;
            case 4:
                setViewVisible(ballRollView);
                break;
        }


    }

    private void setViewVisible(View view) {
        for (View v : mViewList) {
            if (v.getId() == view.getId()) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
