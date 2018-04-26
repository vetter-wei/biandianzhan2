package nari.app.BianDianYingYong.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lx on 2017/9/26.
 */

public class CustomListView extends ListView {

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

  /*  *
     * 只需要重写这个方法即可
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        setFocusable(false);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
