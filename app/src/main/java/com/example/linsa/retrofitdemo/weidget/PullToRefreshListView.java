package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;


/**
 * 自定义的listview
 */
public class PullToRefreshListView extends ListView implements OnScrollListener{


	private View headerView;
	private int headermeasuredHeight;
	private RotateAnimation up;
	private RotateAnimation down;
	private int downY;

	/** 下拉刷新 **/
	private static final int PULL_DOWN = 1;
	/** 松开刷新 **/
	private static final int RELEASE_REFRESH = 2;
	/** 正在刷新 **/
	private static final int REFRESHING = 3;
	/** 当前状态 **/
	private int CUREENTSTATE = PULL_DOWN;
	private TextView mText;
	private ProgressBar mLoading;
	private ImageView mArrow;
	
	/**是否进行加载更多操作,true:是,false:不是**/
	private boolean isLoadMore;

	public PullToRefreshListView(Context context) {
		// super(context);
		this(context, null);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		// super(context, attrs);
		this(context, attrs, -1);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		addHeader();
		addFooter();
		
		setOnScrollListener(this);//设置listview的滚动监听
		
	}
	/**
	 * 添加底部条目
	 * 2016-9-3  上午11:16:14
	 */
	private void addFooter() {
		footerView = View.inflate(getContext(), R.layout.listview_footer, null);
		
		//隐藏底部条目
		footerView.measure(0, 0);
		footermeasuredHeight = footerView.getMeasuredHeight();
		footerView.setPadding(0, 0, 0, -footermeasuredHeight);
		
		addFooterView(footerView);//添加listview的底部条目
	}

	/**
	 * 添加刷新头 2016-9-3 上午9:21:37
	 */
	private void addHeader() {
		headerView = View.inflate(getContext(), R.layout.listview_header, null);
		
		mArrow = (ImageView) headerView.findViewById(R.id.arrow);
		mLoading = (ProgressBar) headerView.findViewById(R.id.loading);
		mText = (TextView) headerView.findViewById(R.id.text);
		

		// 隐藏头条目
		// 因为litview是在布局文件中使用的,而布局文件是在activity的oncrate方法中通过setContentView加载
		// 也就是说在加载listview执行构造函数的时候,Acivity的oncreate方法还没有走完,所以这个时候是获取不到控件的宽高
		// 参数:不是宽高,表示测量规则
		headerView.measure(0, 0);
		headermeasuredHeight = headerView.getMeasuredHeight();
		headerView.setPadding(0, -headermeasuredHeight, 0, 0);

		addHeaderView(headerView);// 给listview添加头条目

		// 初始化动画
		initAnimation();
	}

	/**
	 * 初始化动画 2016-9-3 上午9:48:58
	 */
	private void initAnimation() {
		// 箭头向上的动画,旋转动画
		up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		up.setDuration(500);
		up.setFillAfter(true);// 保持动画结束的状态
		// 箭头向下的动画
		down = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		down.setDuration(500);
		down.setFillAfter(true);// 保持动画结束的状态
	}

	// 下拉显示刷新头
	// 1.触摸事件
	// 2.下拉操作
	// 3.当前界面显示的第一个条目是listview的第一个条目
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveY = (int) ev.getY();
			// 获取移动点和按下点之间的距离
			int distance = moveY - downY;
			// getFirstVisiblePosition() : 获取当前界面显示的第一个条目的位置
			if (distance > 0 && getFirstVisiblePosition() == 0) {
				// 计算出空白区域 = 下拉的距离 - 刷新头的高度
				int paddingTop = distance - headermeasuredHeight;
				// 下拉显示刷新头
				headerView.setPadding(0, paddingTop, 0, 0);

				// 1.下拉刷新 -> 松开刷新
				if (paddingTop > 0 && CUREENTSTATE == PULL_DOWN) {
					CUREENTSTATE = RELEASE_REFRESH;
					switchOption();
				}
				// 2.松开刷新 -> 下拉刷新
				if (paddingTop < 0 && CUREENTSTATE == RELEASE_REFRESH) {
					CUREENTSTATE = PULL_DOWN;
					switchOption();
				}

				// 因为系统的listview是没有下拉空白区域的操作,所以不能使用系统地方listview,如果使用会出空白区域显示问题
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			// 松开刷新 -> 正在刷新
			if (CUREENTSTATE == RELEASE_REFRESH) {
				CUREENTSTATE = REFRESHING;
				// 将刷新头显示出来
				headerView.setPadding(0, 0, 0, 0);
				switchOption();
				//下拉加载数据
				if (refreshListener != null) {
					refreshListener.refresh();
				}
			}
			// 下拉刷新 -> 重新隐藏刷新头
			if (CUREENTSTATE == PULL_DOWN) {
				headerView.setPadding(0, -headermeasuredHeight, 0, 0);
			}

			break;
		}
		// 因为只有下拉空白区域不需要使用系统的操作,但是下拉其他条目还是需要系统的操作
		return super.onTouchEvent(ev);
	}

	// 根据状态,更改控件的显示内容
	/**
	 * 根据状态,更改控件的显示内容 2016-9-3 上午10:38:19
	 */
	private void switchOption() {
		switch (CUREENTSTATE) {
		case PULL_DOWN:
			mText.setText("下拉刷新");
			mArrow.startAnimation(down);
			break;
		case RELEASE_REFRESH:
			mText.setText("松开刷新");
			mArrow.startAnimation(up);
			break;
		case REFRESHING:
			mText.setText("正在刷新");
			mArrow.clearAnimation();//清除动画
			mArrow.setVisibility(View.GONE);
			mLoading.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	
	//下拉刷新数据
	
	
	private View footerView;
	private int footermeasuredHeight;
	private onRefreshListener refreshListener;
	public void setOnRefreshListener(onRefreshListener refreshListener){
		this.refreshListener = refreshListener;
	}
	
	public interface onRefreshListener{
		/**
		 * 下拉刷新
		 * 2016-9-3  上午11:37:24
		 */
		public void refresh();
		/**
		 * 上拉加载方法
		 */
		public void loadMore();
	}
	
	/**
	 * 取消刷新
	 * 2016-9-3  上午10:54:59
	 */
	public void finish(){
		//正在刷新 -> 下拉刷新
		if (CUREENTSTATE == REFRESHING) {
			CUREENTSTATE = PULL_DOWN;
			mText.setText("下拉刷新");
			mLoading.setVisibility(View.GONE);
			mArrow.setVisibility(View.VISIBLE);
			//隐藏刷新头
			headerView.setPadding(0, -headermeasuredHeight, 0, 0);
		}
		//因为取消下拉刷新和取消上拉加载是在一起,所以需要进行区分操作
		//取消加载更多的操作
		if (isLoadMore) {
			footerView.setPadding(0, 0, 0, -footermeasuredHeight);
			isLoadMore = false;//表示已经取消加载更多
		}
		
	}
	
	//加载更多
	//1.当前界面显示的最后一个条目是listview的最后一个条目
	//2.listview停止滑动操作
	
	//当listview的滚动状态改变的时候调用的方法
	//scrollState : Listview的滚动的状态
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//SCROLL_STATE_IDLE;//空闲状态
		//SCROLL_STATE_TOUCH_SCROLL;//触摸滑动状态
		//SCROLL_STATE_FLING;//快速滑动,惯性滑动
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition() == getCount() -1 && isLoadMore == false) {
			isLoadMore = true;
			footerView.setPadding(0, 0, 0, 0);
			
			setSelection(getCount()-1);//将listview定位到哪个条目的位置,position:条目的位置
			
			//实现加载更多数据的操作
			if (refreshListener != null) {
				refreshListener.loadMore();
			}
		}
	}
	//当listview滚动的时候调用的方法
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	

}
