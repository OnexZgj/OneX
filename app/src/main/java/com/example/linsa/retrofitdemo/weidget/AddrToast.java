package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;


public class AddrToast implements OnTouchListener {
	private View mView;
	private Context mContext;
	private WindowManager mWM;

	// 起始点坐标
	private int startX;
	private int startY;
	private WindowManager.LayoutParams mParams;

	public AddrToast(Context context) {
		this.mContext = context;
	}

	/**
	 * 显示归属地
	 * 
	 * @param addr
	 */
	public void showAddr(String addr, int layoutId) {
		// 每次显示的时候 先移除上一次的显示 防止多次来电
		hide();
		// 要显示的view
		mView = View.inflate(mContext, layoutId, null);

		//TODO
		TextView tvAddr = mView.findViewById(0);
		// 显示归属地
		tvAddr.setText(addr);
		// 取出保存的背景色 默认半透明

		// 设置背景
		mView.setBackgroundColor(Color.BLUE);

		// view的触摸监听
		mView.setOnTouchListener(this);

		// 窗口管理器 管理窗口 可以直接通过addview的方式 添加一个顶级布局
		// activity dialog toast 界面 控件 // 都是显示在窗口上
		mWM = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		// Window窗口

		// LayoutParams 布局参数 在布局文件里 以layout_开头的属性 也可以在代码里设置
		mParams = new WindowManager.LayoutParams();
		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		// | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE //这里不要添加 否则不能监听触摸
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		mParams.format = PixelFormat.TRANSLUCENT;
		mParams.gravity = Gravity.BOTTOM|Gravity.RIGHT;
		// 调整级别 让可以监听触摸 权限 "android.permission.SYSTEM_ALERT_WINDOW"
		mParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		mParams.setTitle("Toast");
		// 参1 显示的view
		mWM.addView(mView, mParams);
	}

	public void hide() {
		if (mView != null) {
			// note: checking parent() just to make sure the view has
			// been added... i have seen cases where we get here when
			// the view isn't yet added, so let's try not to crash.
			if (mView.getParent() != null) {
				mWM.removeView(mView);
			}
			mView = null;
		}
	}

	// 参1 触摸的view 参2 触摸事件
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// 触摸的动作
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			// System.out.println("按下");
			// 获取起始点坐标

			startX = (int) event.getRawX();
			startY = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			// 移动
			// System.out.println(" 移动");
			// 获取移动后坐标
			int moveX = (int) event.getRawX();
			int moveY = (int) event.getRawY();
			// 手指移动距离
			int disX = moveX - startX;
			int disY = moveY - startY;
			// 移动触摸的view
			mParams.x += disX;
			mParams.y += disY;
			// 更新view的布局
			mWM.updateViewLayout(mView, mParams);
			// 更改起始点的坐标
			startX = moveX;
			startY = moveY;
			break;
		case MotionEvent.ACTION_UP:
			// 抬起
			// System.out.println(" 抬起");
			break;
		default:
			break;
		}
		return false;
	}
}
