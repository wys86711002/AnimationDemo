package com.example.wuyongsen.animationdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mIv;
    private ImageView mIv1;
    private ImageView mIv2;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = findViewById(R.id.iv);
        mIv1 = findViewById(R.id.iv1);
        mIv2 = findViewById(R.id.iv2);
        mBtn = findViewById(R.id.btn_count_down);
    }

    public void startAnim1(View v){
        animByObjectAnimator();
    }
    public void startAnim2(View v){
        animByAnimatorSet();
    }
    public void startAnim3(View v){
        animByProperty();
    }

    private void animByObjectAnimator() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mIv,"translationX",0f,400f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mIv,"translationY",0f,400f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mIv,"rotation",0f,360f);
        animator1.setDuration(3000);
        animator1.start();
        animator2.setDuration(3000);
        animator2.start();
        animator3.setDuration(3000);
        animator3.start();
    }

    private void animByAnimatorSet(){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mIv,"translationX",0f,400f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mIv,"translationY",0f,400f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mIv,"rotation",0f,360f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1,animator2,animator3);
        set.setDuration(3000);
        set.start();
    }

    private void animByProperty(){
        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX",0f,400f);
        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("translationY",0f,400f);
        PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("rotation",0f,360f);
        final ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(mIv,pvh1, pvh2, pvh3);
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    public void startCountDown(View view){
        ValueAnimator animator = ValueAnimator.ofInt(0,100);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final Integer animatedValue = (Integer)animation.getAnimatedValue();
                mBtn.setText(String.valueOf(animatedValue));
            }
        });
        animator.start();
    }

    public void startGroupAnim(View view){
        mIv1.setVisibility(View.VISIBLE);
        mIv2.setVisibility(View.VISIBLE);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mIv1,"translationY",0f,200f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mIv2,"translationY",0f,400f);
        animator2.setStartDelay(500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1,animator2);
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(1000);
        set.start();
    }


    public void startFreeFalling(View view){
        final ValueAnimator animator = ValueAnimator.ofObject(new PointFTypeEvaluator(), new PointF(0, 0), new PointF
                (400, 800));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mIv.setTranslationX(pointF.x);
                mIv.setTranslationY(pointF.y);
            }
        });
//        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    class PointFTypeEvaluator implements TypeEvaluator<PointF> {
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF result = new PointF();
            result.x = startValue.x + (endValue.x - startValue.x) * fraction;
            result.y = startValue.y + (endValue.y - startValue.y) * fraction;
            return result;
        }
    }

}
