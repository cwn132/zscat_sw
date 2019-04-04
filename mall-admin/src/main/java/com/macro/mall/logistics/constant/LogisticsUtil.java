package com.macro.mall.logistics.constant;

import com.macro.mall.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class LogisticsUtil {

    public static String joinDefaultLogistics(String expressJson, String json){
		if(StringUtils.isNotBlank(json) && StringUtils.isNotBlank(expressJson)){
			JSONArray expressArray = JSONArray.fromObject(expressJson);
			JSONArray jsonArray = JSONArray.fromObject(json);
			for(int i=0;i<expressArray.size();i++){
				Map<String, String> map = (Map<String,String>)expressArray.get(i);
				if(map.get("source")!=null){
					jsonArray.add(0, JsonUtil.objectToJson(map));
				}
			}
			return jsonArray.toString();//JacksonUtil.obj2json(jsonArray);
		}
		return json;
	}
    
    public static String joinDefaultLogistics(String expressJson, Map<String, String> map){
		if(StringUtils.isNotBlank(expressJson)){
			JSONArray expressArray = JSONArray.fromObject(expressJson);
			expressArray.add(0,JSONObject.fromObject(map));
			return expressArray.toString();
		}
		List<Object> result = new ArrayList<>();
		result.add(0,map);
		return JsonUtil.objectToJson(result);
	}
    
    public static int getMapkey(String json){
    	if(StringUtils.isBlank(json)){
    		return 1;
    	}
    	return JSONArray.fromObject(json).size()+1;
    }
    
    public static Map<String,String> getTradeDetailMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "订单支付成功，已提交至仓库");
		map.put("2", "订单开始处理");
		map.put("3", "订单正在配货");
		map.put("4", "订单正在出库");
		return map;
	}
	
	public static Map<String,String> getBondedDetailMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "订单支付成功，等待海关清关");
		map.put("2", "订单清关完成，等待保税区仓库发货");
		map.put("3", "订单开始处理");
		map.put("4", "订单正在配货");
		map.put("5", "订单正在出库");
		return map;
	}
	
	private static Pattern[] SIGNED_PATTERN_ARR = {Pattern.compile("快件已被 [^ ]+ 签收"), Pattern.compile("您已在.+完成取件，感谢使用菜鸟驿站")};
	public static boolean isSigned(String data) {
		for (Pattern p : SIGNED_PATTERN_ARR) {
			if (p.matcher(data).find()) {
				return true;
			}
		}
		for (String s : LogisticsConstant.RECEIVED_STRING) {
            if (data.contains(s.trim())) {
                return true;
            }
        }
		return false;
	}
}
