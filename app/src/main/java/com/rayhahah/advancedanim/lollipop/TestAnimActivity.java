package com.rayhahah.advancedanim.lollipop;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rayhahah.advancedanim.R;

public class TestAnimActivity extends AppCompatActivity {


    public static final String TAG_ANIM = "TAG_ANIM";
    public static int ANIM_NORMAL = 0;
    public static int ANIM_EXPLODE = 1;
    public static int ANIM_SLIDE = 2;
    public static int ANIM_FADE = 3;
    private ImageView ivImageI;
    private ImageView ivImageIi;
    private LinearLayout llRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementsUseOverlay(true);
        setTransition(getIntent().getIntExtra(TAG_ANIM, 0));
        setContentView(R.layout.activity_test_anim);
        initView();

        llRoot.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                        llRoot,
                        llRoot.getWidth() / 2,
                        llRoot.getHeight() / 2,
                        50,
                        1000);
                circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
                circularReveal.setDuration(1000);
                circularReveal.start();
            }
        });
        ivImageI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                        ivImageI,
                        0,
                        0,
                        0,
                        ivImageI.getWidth() + 159);
                circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
                circularReveal.setDuration(1000);
                circularReveal.start();
            }
        });
    }

    /**
     * 设置动画过渡模式
     *
     * @param animType
     */
    private void setTransition(int animType) {

        switch (animType) {
            case 0:
                break;
            case 1:
                getWindow().setEnterTransition(new Explode());
                getWindow().setExitTransition(new Explode());
                getWindow().setReenterTransition(new Explode());
                getWindow().setReturnTransition(new Explode());
                break;
            case 2:
                getWindow().setEnterTransition(new Slide());
                getWindow().setExitTransition(new Slide());
                getWindow().setReenterTransition(new Slide());
                getWindow().setReturnTransition(new Slide());
                break;
            case 3:
                getWindow().setEnterTransition(new Fade());
                getWindow().setExitTransition(new Fade());
                getWindow().setReenterTransition(new Fade());
                getWindow().setReturnTransition(new Fade());

                break;

            case 4:
                getWindow().setEnterTransition(new Fade());
                getWindow().setExitTransition(new Fade());
                getWindow().setReenterTransition(new Fade());
                getWindow().setReturnTransition(new Fade());

                getWindow().setSharedElementExitTransition(new ChangeBounds());
                getWindow().setSharedElementEnterTransition(new ChangeBounds());
                getWindow().setSharedElementReenterTransition(new ChangeBounds());
                getWindow().setSharedElementReturnTransition(new ChangeBounds());

                break;
            case 5:
                getWindow().setEnterTransition(new Fade());
                getWindow().setExitTransition(new Fade());
                getWindow().setReenterTransition(new Fade());
                getWindow().setReturnTransition(new Fade());

                getWindow().setSharedElementExitTransition(new ChangeClipBounds());
                getWindow().setSharedElementEnterTransition(new ChangeClipBounds());
                getWindow().setSharedElementReenterTransition(new ChangeClipBounds());
                getWindow().setSharedElementReturnTransition(new ChangeClipBounds());

                break;
            case 6:
                getWindow().setEnterTransition(new Fade());
                getWindow().setExitTransition(new Fade());
                getWindow().setReenterTransition(new Fade());
                getWindow().setReturnTransition(new Fade());

                getWindow().setSharedElementExitTransition(new ChangeTransform());
                getWindow().setSharedElementEnterTransition(new ChangeTransform());
                getWindow().setSharedElementReenterTransition(new ChangeTransform());
                getWindow().setSharedElementReturnTransition(new ChangeTransform());
                break;
            case 7:
                getWindow().setEnterTransition(new Fade());
                getWindow().setExitTransition(new Fade());
                getWindow().setReenterTransition(new Fade());
                getWindow().setReturnTransition(new Fade());

                getWindow().setSharedElementExitTransition(new ChangeImageTransform());
                getWindow().setSharedElementEnterTransition(new ChangeImageTransform());
                getWindow().setSharedElementReenterTransition(new ChangeImageTransform());
                getWindow().setSharedElementReturnTransition(new ChangeImageTransform());
                break;
        }
    }

    private void initView() {
        ivImageI = (ImageView) findViewById(R.id.iv_image_i);
        ivImageIi = (ImageView) findViewById(R.id.iv_image_ii);
        llRoot = (LinearLayout) findViewById(R.id.ll_root);
    }
}
