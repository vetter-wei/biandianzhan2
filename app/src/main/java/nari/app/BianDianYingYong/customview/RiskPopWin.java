package nari.app.BianDianYingYong.customview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.RiskAdapter;


/**
 * Created by TQM on 2016/8/8 0008.
 */
public class RiskPopWin extends PopupWindow {
    private View shareView;
    public RiskPopWin(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shareView = inflater.inflate(R.layout.risk_pop_win, null);
        ListView lv_risk_pop = shareView.findViewById(R.id.lv_risk_pop);
        RiskAdapter adapter = new RiskAdapter(context);
        lv_risk_pop.setAdapter(adapter);
        setOutsideTouchable(true);
        //设置SelectPicPopupWindow的View
       /* this.setContentView(shareView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation);
        //实例化一个ColorDrawable颜色为半透明
         // ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
       // setBackgroundDrawable(dw);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popwindow_bg));*/
          /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(shareView);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
       // setBackgroundDrawable(new ColorDrawable());
        // 设置弹出窗体显示时的动画，从底部向上弹出
      // this.setAnimationStyle(R.style.take_photo_anim);
        lv_risk_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dismiss();

            }
        });
    }
}
