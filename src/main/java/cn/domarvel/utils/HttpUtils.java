package cn.domarvel.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class HttpUtils {

    public static String getParamUrl(String url,Map<String,String> params){

        //当没有参数时，返回原始url
        if(params==null || params.keySet().size()<=0){
            return url;
        }
        return url+"?"+paramerFormate(params);
    }

    public static String paramerFormate(Map<String,String> params){
        StringBuilder speedUrl = new StringBuilder("");
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String proName = key;
            String proValue = null;
            try {
                proValue = URLEncoder.encode(params.get(key),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            speedUrl.append(proName+"="+proValue+"&");
        }
        //删除掉最后一个参数连接符 &
        speedUrl.deleteCharAt(speedUrl.length()-1);
        return speedUrl.toString();
    }
}
