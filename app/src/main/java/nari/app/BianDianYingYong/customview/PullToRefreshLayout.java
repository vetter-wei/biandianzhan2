package nari.app.BianDianYingYong.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.base.BasePullToRefresh;


/**
 * Created by TQM on 2017/6/20.
 * 上拉加载下拉刷新控件
 */

public class PullToRefreshLayout extends BasePullToRefresh {
    private boolean isLoading;
    private boolean isRefreshing;

    public PullToRefreshLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        this.setRefreshView(R.layout.refresh_normal, new RefeshListener());
        this.setLoadmoreView(R.layout.loadmore_normal, new LoadmoreListener());
        this.setAnimationDuration(300);
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public boolean isRefreshing() {
        return this.isRefreshing;
    }

    private class LoadmoreListener implements LoadmoreViewListener {
        private TextView textView;
        private ProgressBar progressBar;

        private LoadmoreListener() {
        }

        public void onPulling(View loadmoreView, float percent) {
            this.findView(loadmoreView);
            if (percent < 1.0F) {
                this.textView.setText("上拉加载");
            } else {
                this.textView.setText("松开加载");
            }

        }

        public void onReset(View loadmoreView) {
            this.findView(loadmoreView);
            this.progressBar.setVisibility(GONE);
            this.textView.setText("上拉加载");
        }

        public void onLoadmore(View loadmoreView) {
            this.findView(loadmoreView);
            this.progressBar.setVisibility(VISIBLE);
            this.textView.setText("正在加载");
            PullToRefreshLayout.this.isLoading = true;
        }

        public void onSuccess(View loadmoreView) {
            this.findView(loadmoreView);
            this.progressBar.setVisibility(GONE);
            this.textView.setText("加载成功");
            PullToRefreshLayout.this.isLoading = false;
        }

        public void onFailed(View loadmoreView) {
            this.findView(loadmoreView);
            this.progressBar.setVisibility(GONE);
            this.textView.setText("加载失败");
            PullToRefreshLayout.this.isLoading = false;
        }

        private void findView(View fartherView) {
            if (this.textView == null || this.progressBar == null) {
                this.textView = (TextView) fartherView.findViewById(R.id.loadmore_textview);
                this.progressBar = (ProgressBar) fartherView.findViewById(R.id.loadmore_progressbar);
            }

        }
    }

    private class RefeshListener implements RefreshViewListener {
        private final int ROTATE_ANIM_DURATION;
        private Animation mRotateUpAnim;
        private Animation mRotateDownAnim;
        private ImageView arrowView;
        private TextView textView;
        private ProgressBar progressBar;
        private boolean pull_min1;
        private boolean pull_max1;

        private RefeshListener() {
            this.ROTATE_ANIM_DURATION = 180;
            this.pull_min1 = true;
            this.pull_max1 = false;
            this.mRotateUpAnim = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
            this.mRotateUpAnim.setDuration(180L);
            this.mRotateUpAnim.setFillAfter(true);
            this.mRotateDownAnim = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
            this.mRotateDownAnim.setDuration(180L);
            this.mRotateDownAnim.setFillAfter(true);
        }

        public void onPulling(View refreshView, float percent) {
            this.findView(refreshView);
            if (percent < 1.0F) {
                if (this.pull_max1) {
                    this.textView.setText("下拉刷新");
                    this.arrowView.startAnimation(this.mRotateDownAnim);
                }

                this.pull_min1 = true;
                this.pull_max1 = false;
            } else {
                if (this.pull_min1) {
                    this.textView.setText("松开刷新");
                    this.arrowView.startAnimation(this.mRotateUpAnim);
                }

                this.pull_min1 = false;
                this.pull_max1 = true;
            }

        }

        public void onReset(View refreshView) {
            this.findView(refreshView);
            this.arrowView.clearAnimation();
            this.arrowView.setVisibility(VISIBLE);
            this.progressBar.setVisibility(INVISIBLE);
            this.textView.setText("下拉刷新");
        }

        public void onRefresh(View refreshView) {
            this.findView(refreshView);
            this.arrowView.clearAnimation();
            this.arrowView.setVisibility(INVISIBLE);
            this.progressBar.setVisibility(VISIBLE);
            this.textView.setText("正在刷新");
            PullToRefreshLayout.this.isRefreshing = true;
        }

        public void onSuccess(View refreshView) {
            this.findView(refreshView);
            this.arrowView.setVisibility(GONE);
            this.progressBar.setVisibility(GONE);
            this.textView.setText("刷新成功");
            PullToRefreshLayout.this.isRefreshing = false;
        }

        public void onFailed(View refreshView) {
            this.findView(refreshView);
            this.arrowView.setVisibility(GONE);
            this.progressBar.setVisibility(GONE);
            this.textView.setText("刷新失败");
            PullToRefreshLayout.this.isRefreshing = false;
        }

        private void findView(View fartherView) {
            if (this.arrowView == null || this.textView == null || this.progressBar == null) {
                this.arrowView = (ImageView) fartherView.findViewById(R.id.refresh_arrow);
                this.textView = (TextView) fartherView.findViewById(R.id.refresh_textview);
                this.progressBar = (ProgressBar) fartherView.findViewById(R.id.refresh_progressbar);
            }

        }
    }
}
