package cn.domarvel.exception;

/**
 * Created by Administrator on 2017/8/22.
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个类将是以后的错误信息的父类。
 */
public class SimpleException extends Exception{

    private String errorType;//这是错误类型，一般是前端会用的一个东西，它标志着 key 。
    private String errorMessage;//这是错误信息，代表具体错误解释。
    private String view;//目标跳转页面

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public SimpleException(String errorType,String errorMessage){
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
    public SimpleException() {
    }

    public SimpleException(String message) {
        super(message);
    }

    public SimpleException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleException(Throwable cause) {
        super(cause);
    }

    /**
     * 返回 提取 自定义exception 里面的错误数据！然后以 Map 形式返回。
     * @param responseJsonMessage
     * @param e
     * @return
     */
    public static Map<String,String> getMapMessage(Map<String,String> responseJsonMessage,Exception e){
        if(e instanceof SimpleException) {
            SimpleException ex = (SimpleException) e;
            responseJsonMessage.put(ex.getErrorType(), ex.getErrorMessage());
        }else{
            e.printStackTrace();
        }
        return responseJsonMessage;
    }

    /**
     * 把信息通过Json的方式响应
     * @param response
     * @param message
     * @param objectMapper
     */
    public static void sendMessage(HttpServletResponse response, Map<String,String> message, ObjectMapper objectMapper){
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(objectMapper.writeValueAsString(message));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void setView(String view,Exception e){
        if(e instanceof SimpleException){
            ((SimpleException) e).setView(view);
        }
    }

    public static void sendSuccessMessage(HttpServletResponse response,ObjectMapper objectMapper){
        Map<String,String> message = new HashMap<>();
        message.put("success","添加成功！");
        SimpleException.sendMessage(response,message,objectMapper);
    }
}
