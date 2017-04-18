package com.rayhahah.advancedanim.pathmeasure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.rayhahah.advancedanim.R;

public class PathMeasureActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private PathMeasureView pathMeasureView;
    private ToggleButton tbNormal;
    private ToggleButton tbWindow;
    private ToggleButton tbArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);
        initView();
    }

    private void initView() {
        pathMeasureView = (PathMeasureView) findViewById(R.id.pathMeasureView);
        tbNormal = (ToggleButton) findViewById(R.id.tb_normal);
        tbWindow = (ToggleButton) findViewById(R.id.tb_window);
        tbArrow = (ToggleButton) findViewById(R.id.tb_arrow);
        tbNormal.setOnCheckedChangeListener(this);
        tbWindow.setOnCheckedChangeListener(this);
        tbArrow.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tb_normal:
                if (isChecked) {
                    pathMeasureView.startAnim();
                } else {
                    pathMeasureView.pauseAnim();
                }

                break;
            case R.id.tb_window:
                if (isChecked) {
                    pathMeasureView.setWindow(true);
                } else {
                    pathMeasureView.setWindow(false);
                }
                break;

            case R.id.tb_arrow:

                if (isChecked) {
                    pathMeasureView.setArrow(true);
                } else {
                    pathMeasureView.setArrow(false);
                }

                break;
        }
    }
}
