package cn.domarvel.po;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * Created by __MoonFollow on 2017/8/31.
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    private String charset;

    public RequestWrapper(HttpServletRequest request, String charset) {
        super(request);
        this.request=request;
        this.charset=charset;
    }
    @Override
    public String getParameter(String name) {
        String method=request.getMethod();
        String value=null;

        if(method.equalsIgnoreCase("post")){
            try {
                request.setCharacterEncoding(charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(method.equalsIgnoreCase("get")){
            value=request.getParameter(name);
            if(value==null)return null;
            try {
                value=new String(value.getBytes("ISO-8859-1"),charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return value;
        }
        return request.getParameter(name);
    }
    @Override
    public Map<String, String[]> getParameterMap() {
        String method=request.getMethod();

        if(method.equalsIgnoreCase("post")){
            try {
                request.setCharacterEncoding(charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(method.equalsIgnoreCase("get")){
            Set<String> setKey=request.getParameterMap().keySet();
            Map<String, String[]> parram=request.getParameterMap();

            for(String key:setKey){
                String value[]=parram.get(key);
                int len=value.length;
                for(int i=0;i<len;i++){
                    try {
                        value[i]=new String(value[i].getBytes("ISO-8859-1"),charset);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                parram.replace(key, value);
            }

            return parram;
        }
        return request.getParameterMap();
    }
}