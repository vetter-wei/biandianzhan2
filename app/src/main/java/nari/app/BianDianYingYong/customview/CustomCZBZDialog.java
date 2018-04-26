package nari.app.BianDianYingYong.customview;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import nari.app.BianDianYingYong.R;

public class CustomCZBZDialog {
	private Context context;
	private Dialog mDialog;
	private TextView mTv_czbz_title;

	public CustomCZBZDialog(Context context) {
		this.context = context;
		initView();
	}

	private void initView() {
		this.mDialog = new Dialog(context, R.style.dialogSignaStyle);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_czbz, null);
		TextView tv_czbz_content = (TextView) view.findViewById(R.id.tv_czbz_content);
		mTv_czbz_title = (TextView) view.findViewById(R.id.tv_czbz_title);
		ImageView iv_cabz_close = (ImageView) view.findViewById(R.id.iv_cabz_close);
		Button bt_czbz_sure = (Button) view.findViewById(R.id.bt_czbz_sure);
		tv_czbz_content.setMovementMethod(ScrollingMovementMethod.getInstance());
		iv_cabz_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDialog.cancel();
			}
		});
		bt_czbz_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDialog.cancel();
			}
		});
		this.mDialog.setContentView(view);
		this.mDialog.setCanceledOnTouchOutside(true);
		this.mDialog.show();
	}

	public void show() {
		this.mDialog.show();
	}
	public void setTitle(String title) {
		mTv_czbz_title.setText(title);
	}
}
