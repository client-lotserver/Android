package com.ruyicai.activity.buy.ssc;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.activity.buy.HighFrequencyNoticeHistroyActivity;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.constant.Constants;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.GetLotNohighFrequency;
import com.ruyicai.util.PublicMethod;
import com.umeng.analytics.MobclickAgent;

public class Ssc extends BuyActivityGroup implements HandlerMsg {

	private String[] titles = { "一星", "二星", "三星", "五星", "大小" };
	private String[] topTitles = { "重庆时时彩", "重庆时时彩", "重庆时时彩", "重庆时时彩", "重庆时时彩" };
	public static String batchCode;
	private int lesstime = 0;
	private Class[] allId = { SscOneStar.class, SscTwoStar.class,
			SscThreeStar.class, SscFiveStar.class, SscBigSmall.class };
	private MyHandler handler = new MyHandler(this);
	private boolean isRun = true;// 线程是否运行变量

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*Add by fansm 20130418 start*/
		if (Constants.isDebug) PublicMethod.outLog(this.getClass().getSimpleName(), 
				"onCreate()");
		/*Add by fansm 20130418 end*/
		batchCode = "";
		setLotno(Constants.LOTNO_SSC);
		setlastbatchcode(Constants.LOTNO_SSC);
		time = (TextView) findViewById(R.id.layout_main_text_timessc);
		time.setVisibility(View.VISIBLE);
		refreshBtn.setVisibility(View.VISIBLE);
		init(titles, topTitles, allId);
		mTabHost.setCurrentTab(2);
		setIssue();
		MobclickAgent.onEvent(this, "shishicai"); // BY贺思明 点击首页的“时时彩”图标
		MobclickAgent.onEvent(this, "gaopingoucaijiemian ");// BY贺思明 高频购彩页面
	}

	public void turnHosity() {
		Intent intent = new Intent(Ssc.this,
				HighFrequencyNoticeHistroyActivity.class);
		intent.putExtra("lotno", Constants.LOTNO_SSC);
		startActivity(intent);
	}

	@Override
	public boolean getIsLuck() {
		return true;
	}

	/**
	 * 赋值给当前期
	 * 
	 * @param type彩种编号
	 */
	public void setIssue() {
		final Handler sscHandler = new Handler();
		issue.setText("期号获取中....");
		time.setText("获取中...");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String error_code = "00";
				String re = "";
				String message = "";
				batchCode = "";
				re = GetLotNohighFrequency.getInstance().getInfo(
						Constants.LOTNO_SSC);
				if (!re.equalsIgnoreCase("")) {
					try {
						JSONObject obj = new JSONObject(re);
						message = obj.getString("message");
						error_code = obj.getString("error_code");
						lesstime = Integer.valueOf(obj
								.getString("time_remaining"));
						batchCode = obj.getString("batchcode");
						while (isRun) {
							if (isEnd(lesstime)) {
								sscHandler.post(new Runnable() {
									public void run() {
										issue.setText("第" + batchCode + "期");
										time.setText("剩余时间:"
												+ PublicMethod
														.isTen(lesstime / 60)
												+ ":"
												+ PublicMethod
														.isTen(lesstime % 60));
									}
								});
								Thread.sleep(1000);
								lesstime--;
							} else {
								sscHandler.post(new Runnable() {
									public void run() {
										issue.setText("第" + batchCode + "期");
										time.setText("剩余时间:00:00");
										nextIssue();
									}
								});
								break;
							}
						}
					} catch (Exception e) {
						sscHandler.post(new Runnable() {
							public void run() {
								issue.setText("获取期号失败");
								time.setText("获取失败");
							}
						});
					}
				} 
			}
		});
		thread.start();
	}

	private boolean isEnd(int time) {
		if (time > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void nextIssue() {
		new AlertDialog.Builder(Ssc.this)
				.setTitle("提示")
				.setMessage("时时彩第" + batchCode + "期已经结束,是否转入下一期")
				.setNegativeButton("转入下一期", new Dialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						setIssue();
					}

				})
				.setNeutralButton("返回主页面",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								Ssc.this.finish();
							}
						}).create().show();
	}

	public void updatePage() {
		Intent intent = new Intent(Ssc.this, Ssc.class);
		Ssc.this.startActivity(intent);
		finish();
	}

	protected void onStart() {
		super.onStart();

	}

	protected void onResume() {
		super.onResume();
		ZixuanAndJiXuan.lotnoStr = Constants.LOTNO_SSC;
	}

	protected void onPause() {
		super.onPause();

	}

	protected void onStop() {
		super.onStop();
	}

	@Override
	public void errorCode_0000() {

	}

	@Override
	public void errorCode_000000() {

	}

	@Override
	public Context getContext() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ActivityGroup#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (Constants.isDebug) {
			PublicMethod.outLog(this.getClass().getSimpleName(), "onDestroy()");
		}
		isRun = false;
	}

}