package cn.domarvel.model.network;

import cn.domarvel.model.network.init.LoadConfig;
import cn.domarvel.po.HttpResponseMessage;
import cn.domarvel.utils.HttpUtils;
import cn.domarvel.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

public class SimpleHttp {

    private static LoadConfig loadConfig;
    private static String defaultLocation = "/HttpConfig/DefaultHttpHeaderSetting.properties";//默认的加载路径

    static {
        loadConfig = new LoadConfig();
        loadConfig.getConfigLocations().add(defaultLocation);
        loadConfig.loadDefaultHttpHeaderConfig();
    }

    /**
     *
     * @param url
     * @param params key是参数名，value是Map的value。
     * @return
     */
    public static HttpResponseMessage sendGet(String url, Map<String,String> params){
        HttpResponseMessage responseMessage = null;
        try {
            url = HttpUtils.getParamUrl(url,params);//获取真实的url（url+参数）
            URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();

            // 设置通用的请求属性
            setDefaultHttpHeader(connection);
            // 建立实际的连接
            connection.connect();
            //获取所有响应头字段
            Map<String,List<String>> responseHeaderField = connection.getHeaderFields();
            //获取响应流
            InputStream responseInputStream = connection.getInputStream();
            //构建响应消息
            responseMessage = new HttpResponseMessage();
            responseMessage.setResponseHeaders(responseHeaderField);
            responseMessage.setResponseInputStream(responseInputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回响应消息
        return responseMessage;
    }

    public static HttpResponseMessage sendPost(String url,Map<String,String> params){
        HttpResponseMessage responseMessage = null;
        try {
            URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            setDefaultHttpHeader(connection);//设置默认的消息头
            // 发送POST请求必须设置如下
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);//设置输出打印流
            writer.write(HttpUtils.paramerFormate(params));


            InputStream inputStream = connection.getInputStream();
            Map<String,List<String>> responseHeaders = connection.getHeaderFields();
            responseMessage = new HttpResponseMessage(inputStream,outputStream,responseHeaders);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

    public static void setDefaultHttpHeader(URLConnection connection){
        Properties headerProperties = loadConfig.getConfigMaps().get(StringUtils.getFileNameEscapeFileType(defaultLocation));
        Enumeration<String> keys = (Enumeration<String>) headerProperties.propertyNames();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = headerProperties.getProperty(key);
            connection.setRequestProperty(key,value);
        }
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(String defaultLocation) {
        SimpleHttp.defaultLocation = defaultLocation;
    }

    public static void main(String[] args) {
        Map<String,String> params = new HashMap<>();
        params.put("content","4楼，这跳下去，不死也得惨吧。");
        HttpResponseMessage responseMessage = SimpleHttp.sendPost("http://www.peiyinge.com/make/getSynthSign",params);
        System.out.println(StringUtils.getLinesStringByInputStream(responseMessage.getResponseInputStream(),"utf8"));
    }
}
