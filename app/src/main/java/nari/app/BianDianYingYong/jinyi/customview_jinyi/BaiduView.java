package nari.app.BianDianYingYong.jinyi.customview_jinyi;





import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import nari.app.BianDianYingYong.R;

public class BaiduView extends RelativeLayout {

    private Bitmap firstBitmap;
    private Bitmap secondBitmap;
    private Bitmap thirdBitmap;
    private Bitmap defaultBitmap;

    private ImageView firstView;
    private ImageView secondView;
    private ImageView thirdView;
    private ImageView defaultView;

    private int width;
    private int height;

    private boolean isShowing = false;
    private boolean isAnimating = false;

    private OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    public BaiduView(Context context, AttributeSet attrs) {
        super(context, attrs);

        defaultBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_1);

        width = defaultBitmap.getWidth();
        height = defaultBitmap.getHeight();

        LayoutParams params = new LayoutParams(width, height);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        firstBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_save);
        firstView = new ImageView(context);
        firstView.setImageBitmap(firstBitmap);
        firstView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(1);
                    hideMenu();
                }
            }
        });
        addView(firstView, params);

        secondBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_submit);
        secondView = new ImageView(context);
        secondView.setImageBitmap(secondBitmap);
        secondView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(2);
                    hideMenu();
                }
            }
        });
        addView(secondView, params);

        thirdBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_paste);
        thirdView = new ImageView(context);
        thirdView.setImageBitmap(thirdBitmap);
        thirdView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(3);
                    hideMenu();
                }
            }
        });
        addView(thirdView, params);

        defaultView = new ImageView(context);
        defaultView.setImageBitmap(defaultBitmap);
        defaultView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimating) {
                    return;
                }
                if (!isShowing) {
                    isShowing = true;
                    showMenu();

                } else {
                    isShowing = false;
                    hideMenu();
                }

            }
        });
        addView(defaultView, params);

    }

    protected void showMenu() {
        ObjectAnimator firstAnimator = ObjectAnimator.ofFloat(firstView,
                "translationY", 0, -(height + 13) * 3);
        ObjectAnimator secondAnimator = ObjectAnimator.ofFloat(secondView,
                "translationY", 0, -(height + 13) * 2);
        ObjectAnimator thirdAnimator = ObjectAnimator.ofFloat(thirdView,
                "translationY", 0, -(height + 13) * 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.playTogether(firstAnimator, secondAnimator, thirdAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
            }
        });
        animatorSet.start();
    }

    protected void hideMenu() {
        ObjectAnimator firstAnimator = ObjectAnimator.ofFloat(firstView,
                "translationY", firstView.getTranslationY(), 0);
        ObjectAnimator secondAnimator = ObjectAnimator.ofFloat(secondView,
                "translationY", secondView.getTranslationY(), 0);
        ObjectAnimator thirdAnimator = ObjectAnimator.ofFloat(thirdView,
                "translationY", thirdView.getTranslationY(), 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(firstAnimator, secondAnimator, thirdAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
            }
        });
        animatorSet.start();
    }

    public interface OnTabClickListener {
        void onTabClick(int currentTab);
    }
}
