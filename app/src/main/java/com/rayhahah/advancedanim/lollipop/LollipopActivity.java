package com.rayhahah.advancedanim.lollipop;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.rayhahah.advancedanim.R;

public class LollipopActivity extends AppCompatActivity {

    private Intent mIntent;
    private Button btnNormal;
    private Button btnExplode;
    private Button btnSlide;
    private Button btnFade;
    private View ivImageI;
    private View ivImageIi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lollipop);
        initView();
    }

    public void btnClick(View view) {
        mIntent = new Intent(this, TestAnimActivity.class);
        int animType = 0;
        switch (view.getId()) {
            case R.id.btn_normal:
                animType = TestAnimActivity.ANIM_NORMAL;
                break;
            case R.id.btn_explode:
                animType = TestAnimActivity.ANIM_EXPLODE;
                break;
            case R.id.btn_slide:
                animType = TestAnimActivity.ANIM_SLIDE;
                break;
            case R.id.btn_fade:
                animType = TestAnimActivity.ANIM_FADE;
                break;
        }
        mIntent.putExtra(TestAnimActivity.TAG_ANIM, animType);

        if (animType == TestAnimActivity.ANIM_NORMAL)
            startActivity(mIntent);
        else
            startActivity(mIntent,
                    ActivityOptions.makeSceneTransitionAnimation(this,
                            Pair.create(ivImageI, "one"),
                            Pair.create(ivImageIi, "two"))
                            .toBundle());

    }

    private void initView() {
        btnNormal = (Button) findViewById(R.id.btn_normal);
        btnExplode = (Button) findViewById(R.id.btn_explode);
        btnSlide = (Button) findViewById(R.id.btn_slide);
        btnFade = (Button) findViewById(R.id.btn_fade);
        ivImageI = findViewById(R.id.iv_image_i);
        ivImageIi = findViewById(R.id.iv_image_ii);
    }
}
