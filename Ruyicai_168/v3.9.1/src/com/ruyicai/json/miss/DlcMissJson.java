package com.ruyicai.json.miss;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 多乐彩和十一运夺金遗漏值解析
 * 
 * @author Administrator
 * 
 */
public class DlcMissJson extends MissJson {
	private final String GE = "ge";
	private final String SHI = "shi";
	private final String BAI = "bai";
	private final String MISS = "miss";

	public DlcMissJson() {
	}

	public List<List> jsonToList(String sellWay, JSONObject json) {
		if (sellWay.equals(MissConstant.DLC_MV_Q3)
				|| sellWay.equals(MissConstant.ELV_MV_Q3)
				|| sellWay.equals(MissConstant.gdELV_MV_Q3)) {
			missList = jsonQx(json);
			return missList;
		} else {
			missList = jsonRx(json);
			return missList;
		}
	}

	/**
	 * 解析多乐彩5星通选
	 */
	public List<List> jsonQx(JSONObject json) {
		List<List> missList = new ArrayList<List>();
		try {
			missList.add(getJsonArray(json.getJSONArray(BAI)));
			missList.add(getJsonArray(json.getJSONArray(SHI)));
			missList.add(getJsonArray(json.getJSONArray(GE)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return missList;
	}

	/**
	 * 解析多乐彩
	 */
	public List<List> jsonRx(JSONObject json) {
		List<List> missList = new ArrayList<List>();
		try {
			missList.add(getJsonArray(json.getJSONArray(MISS)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return missList;
	}

	public List<String> getJsonArray(JSONArray jsonArray) throws JSONException {
		List<String> missValues = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			missValues.add(jsonArray.getString(i));
		}
		return missValues;
	}
}
