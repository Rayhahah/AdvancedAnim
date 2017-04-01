package com.rayhahah.advancedanim;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VectorActivity extends AppCompatActivity {

    static {
        //设置Activity支持5.0以下版本兼容VectorDrawable
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    private TextView textView;
    private TextView tvPath;
    private TextView tvSet;
    private TextView tvStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        initView();
    }

    public void doClick(View view) {

        /**
         * 这个主要针对pathData路径变化
         * 只支持5.0以上的版本
         */
        if (view.getId() == R.id.tv_path) {
            AnimatedVectorDrawable vectorDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.anim_square_play);
            ((TextView) view).setCompoundDrawables(null, vectorDrawable, null, null);
            vectorDrawable.start();
        } else {

            /**
             * TextView中获取资源并启动
             */
            if (view instanceof TextView) {
                Drawable[] drawables = ((TextView) view).getCompoundDrawables();
                ((Animatable) drawables[1]).start();
            }

            /**
             * ImageView中获取资源并启动
             */
            if (view instanceof ImageView) {
                ((Animatable) ((ImageView) view).getDrawable()).start();
            }
        }
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        tvPath = (TextView) findViewById(R.id.tv_path);
        tvSet = (TextView) findViewById(R.id.tv_set);
        tvStar = (TextView) findViewById(R.id.tv_star);
    }
}
