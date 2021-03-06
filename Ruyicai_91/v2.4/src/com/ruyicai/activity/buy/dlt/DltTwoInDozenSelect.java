package com.ruyicai.activity.buy.dlt;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ScrollView;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.dlt.DltTwoInDozenCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

public class DltTwoInDozenSelect extends ZixuanActivity implements BuyImplement{
	
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };
	private ScrollView scroller;
	
	AreaInfo[] dltTwoInDozenAreaInfos = new AreaInfo[1];
	/**
	 * 大乐透胆托红球区
	 */
	BallTable redBallTable;
	/**
	 * 实例化大乐透直选注码类
	 */
	DltTwoInDozenCode dltTwoInDozencode = new DltTwoInDozenCode();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDltNormalArea();
		createView(dltTwoInDozenAreaInfos, dltTwoInDozencode,this);
		redBallTable = areaNums[0].table;
		
	}
	/**
	 * 初始化大乐透12选2选区
	 */
	public void initDltNormalArea(){
		String redTitle = getResources().getString(R.string.zi_xuanzedezhuma).toString();
        dltTwoInDozenAreaInfos[0] = new AreaInfo(12, 12, redBallResId, 0, 1,Color.RED,redTitle);
        
	}
	@Override
	public void isTouzhu() {
		// TODO Auto-generated method stub
		int iRedHighlights = redBallTable.getHighlightBallNums();
		int iZhuShu = (int) getDltTwoInDozenZhuShu(iRedHighlights,iProgressBeishu);
		if (redBallTable.getHighlightBallNums()  < 2) {
			alertInfo("请至少选择2个小球	");
		} else if (iZhuShu * 2 > 100000) {
			dialogExcessive();
		} else {
			String sTouzhuAlert = "";
			sTouzhuAlert = getTouzhuAlert();
			alert(sTouzhuAlert,getZhuma());
		}
	}
	/**
	 * 投注提示框中的信息
	 */
	private String getTouzhuAlert() {
		int iRedHighlights = redBallTable.getHighlightBallNums();
		int iZhuShu = (int) getDltTwoInDozenZhuShu(iRedHighlights,iProgressBeishu);

		StringBuffer alertcontent = new StringBuffer();
		alertcontent.append("注数：").append(iZhuShu / mSeekBarBeishu.getProgress()).append("注").append("\n")
			.append("倍数：").append(mSeekBarBeishu.getProgress()).append("倍").append("\n")
			.append("追号：").append(mSeekBarQishu.getProgress()).append("期").append("\n")
			.append("金额：").append((iZhuShu * 2)).append("元").append("\n")
			.append("冻结金额：").append((2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu)).append("元").append("\n");
		
		return  alertcontent.toString();

	}
	/**
	 * 投注提示框中的信息(注码)
	 * @return
	 */
	public String getZhuma(){
		String red_zhuma_string = " ";
		int[] redZhuMa = redBallTable.getHighlightBallNOs();
		for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
			red_zhuma_string = red_zhuma_string + String.valueOf(redZhuMa[i]);
			if (i != redBallTable.getHighlightBallNOs().length - 1) {
				red_zhuma_string = red_zhuma_string + ",";
			}
		}
		return "注码：\n" + red_zhuma_string +"\n";
		
	}
	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		int iRedHighlights = areaNum[0].table.getHighlightBallNums();
		StringBuffer iTempString = new StringBuffer();
		int iZhuShu = 0;
		// 红球数 不足
		if (iRedHighlights < 2) {
			int num = 2-iRedHighlights;
			return "至少还需"+num+"个红球";
		} else {
				iZhuShu = (int) getDltTwoInDozenZhuShu(iRedHighlights,iProgressBeishu);
				iTempString.append("共").append(iZhuShu).append("注，共").append(iZhuShu * 2).append("元");	
		} 
		
		return iTempString.toString();
	}
	/**
	 * 大乐透十二选二 的计算方法
	 * @param iRedHighlights
	 * @param iProgressBeishu
	 * @return
	 */
	private long getDltTwoInDozenZhuShu(int iRedHighlights, int iProgressBeishu) {
		long dltZhuShu = 0L;
		dltZhuShu += (PublicMethod.zuhe(2, iRedHighlights) * iProgressBeishu);
		return  dltZhuShu;
	}
	@Override
	public void touzhuNet() {
		int zhuShu	= (int)PublicMethod.zuhe(2, (redBallTable.getHighlightBallNums())) * iProgressBeishu;
		betAndGift.setSellway("0");//1代表机选 0代表自选
		betAndGift.setLotno(Constants.LOTNO_DLT);
		betAndGift.setAmount(""+zhuShu*200);
	}

}
