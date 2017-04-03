package com.rayhahah.advancedanim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button btnVector;
    private Button btnBezier;
    private Button btnPathmeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn_vector:
                startActivity(new Intent(this, VectorActivity.class));
                break;
            case R.id.btn_bezier:
                startActivity(new Intent(this, BezierActivity.class));
                break;
            case R.id.btn_pathmeasure:
                startActivity(new Intent(this,PathMeasureActivity.class));
                break;

        }

    }

    private void initView() {
        btnVector = (Button) findViewById(R.id.btn_vector);
        btnBezier = (Button) findViewById(R.id.btn_bezier);
        btnPathmeasure = (Button) findViewById(R.id.btn_pathmeasure);
    }
}
