package com.ruyicai.android.controller.activity.home.buylotteryhall.jiangxi11xuan5;

import android.view.View;

import com.ruyicai.android.R;
import com.ruyicai.android.controller.activity.viewpagers.PlayMethodTextViewInterface;
import com.ruyicai.android.controller.activity.viewpagers.SelectNumberActivity;
import com.ruyicai.android.controller.compontent.bar.BetBarInterface;
import com.ruyicai.android.controller.compontent.button.PageChangeRadioButtonsInterface;
import com.ruyicai.android.controller.compontent.panel.SelectNumberPanel;
import com.ruyicai.android.controller.compontent.selectnumberpanel.SelectNumberBallType;

/**
 * 江西11选5任选*选号页面基类：包含了页面切换单选按钮组，玩法说明，滑动选号区域，投注栏。适用于江西11选5的任选二、任选三、任选四、任选五、任选六、
 * 任选七、任选八页面。作用： 1.覆盖父类空实现实现了玩法说明栏的添加。 2.实现了选号面板的添加
 * 
 * @author xiang_000
 * @since RYC1.0 2013-5-2
 */
public abstract class JiangXiElevenSelectFiveOptionalSelectActivity extends
		SelectNumberActivity implements BetBarInterface,
		PageChangeRadioButtonsInterface, PlayMethodTextViewInterface {
	{
//		set_fBettingBarInterface(this);
		set_fPageChangeRadioButtonsInterface(this);
		set_fPlayMethodTextViewInterface(this);
	}

	@Override
	protected boolean isAddPlayMethodTextView() {
		return true;
	}

	@Override
	protected boolean isAddPageChangeRadioButtons() {
		return true;
	}

	@Override
	public void setPageChangeRadioButtonTextResouceIds() {
		_fPageChangeRadioButtons.set_fRadioButtonTextResouceIds(new int[] {
				R.string.radiogroup_text_selfselect,
				R.string.radiogroup_text_courageselect });
	}

	@Override
	protected void setSelectNumberPanelNum() {
		int checkedId = _fPageChangeRadioButtons.getCheckedRadioButtonId();
		switch (checkedId) {
		case R.string.radiogroup_text_selfselect:
			_fNumOfSelectNumberPanel = 1;
			break;
		case R.string.radiogroup_text_courageselect:
			_fNumOfSelectNumberPanel = 2;
			break;
		}
	}

	@Override
	protected void initSelectNumberPanelsWithPage(int aPage_i) {
		for (int panel_i = 0; panel_i < _fNumOfSelectNumberPanel; panel_i++) {
			// 获取当前初始化显示的选号面板对象
			SelectNumberPanel selectNumberPanel = _fSelectNumberPanelList.get(
					aPage_i).get(panel_i);

			switch (panel_i) {
			case 0:
				int checkedId = _fPageChangeRadioButtons
						.getCheckedRadioButtonId();

				if (checkedId == R.string.radiogroup_text_selfselect) {
					initBettingNumSelectNumberPanel(aPage_i, selectNumberPanel);
				} else if (checkedId == R.string.radiogroup_text_courageselect) {
					initCourageNumSelectNumberPanel(aPage_i, selectNumberPanel);
				}
				break;
			case 1:
				initDragNumSelectNumberPanel(aPage_i, selectNumberPanel);
				break;
			}
		}
	}

	/**
	 * 初始化拖码选号面板
	 * 
	 * @param aPage_i
	 *            页面索引
	 * @param selectNumberPanel
	 *            选号面板对象
	 */
	private void initDragNumSelectNumberPanel(int aPage_i,
			SelectNumberPanel selectNumberPanel) {
		if (aPage_i == 0) {
			selectNumberPanel.initSelectNumberPanelShow("拖码：", 1, 16, 0, 11,
					SelectNumberBallType.REDBALL, null, false);
		} else {
			int[] lossValues = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
			selectNumberPanel.initSelectNumberPanelShow("拖码：", 1, 16, 0, 11,
					SelectNumberBallType.REDBALL, lossValues, true);
		}
		selectNumberPanel.setRandomButtonVisibiity(View.GONE);
	}

	/**
	 * 初始化胆码选号对象
	 * 
	 * @param aPage_i
	 *            页面索引
	 * @param selectNumberPanel
	 *            选号面板对象
	 */
	private void initCourageNumSelectNumberPanel(int aPage_i,
			SelectNumberPanel selectNumberPanel) {
		if (aPage_i == 0) {
			selectNumberPanel.initSelectNumberPanelShow("胆码：", 1, 16, 0, 11,
					SelectNumberBallType.REDBALL, null, false);
		} else {
			int[] lossValues = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
			selectNumberPanel.initSelectNumberPanelShow("胆码：", 1, 16, 0, 11,
					SelectNumberBallType.REDBALL, lossValues, true);
		}
		selectNumberPanel.setRandomButtonVisibiity(View.GONE);
	}

	/**
	 * 初始化投注号码选号面板
	 * 
	 * @param aPage_i
	 *            页面索引
	 * @param selectNumberPanel
	 *            选号面板
	 */
	private void initBettingNumSelectNumberPanel(int aPage_i,
			SelectNumberPanel selectNumberPanel) {
		if (aPage_i == 0) {
			selectNumberPanel.initSelectNumberPanelShow("请选择投注号码：", 1, 16, 0,
					11, SelectNumberBallType.REDBALL, null, false);
		} else {
			int[] lossValues = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
			selectNumberPanel.initSelectNumberPanelShow("请选择投注号码：", 1, 16, 0,
					11, SelectNumberBallType.REDBALL, lossValues, true);
		}
		selectNumberPanel.setRandomButtonVisibiity(View.GONE);
	}

	@Override
	public void setNumberBasketButton() {

	}

	@Override
	public void setClearSelectedNumberButton() {

	}

	@Override
	public void setAddToNumberBasketButton() {

	}

	@Override
	public void setBettingButton() {

	}
}