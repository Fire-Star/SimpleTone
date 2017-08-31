package cn.domarvel.po;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class HttpResponseMessage {
    /**
     * 在这里做下解释：
     *      GET 请求，响应的消息 只有 responseInputStream ,responseHeaders
     *      POST 请求，响应的消息 下面字段全部都有（responseInputStream , sendMessageOutputStream , responseHeaders）
     */

    private InputStream responseInputStream;//响应给客户端的IO
    private OutputStream sendMessageOutputStream;//响应给客户端的OutputStream
    private Map<String,List<String>> responseHeaders;//响应的消息头

    public HttpResponseMessage() {
    }

    public HttpResponseMessage(InputStream responseInputStream, OutputStream sendMessageOutputStream, Map<String, List<String>> responseHeaders) {
        this.responseInputStream = responseInputStream;
        this.sendMessageOutputStream = sendMessageOutputStream;
        this.responseHeaders = responseHeaders;
    }

    public InputStream getResponseInputStream() {
        return responseInputStream;
    }

    public void setResponseInputStream(InputStream responseInputStream) {
        this.responseInputStream = responseInputStream;
    }

    public OutputStream getSendMessageOutputStream() {
        return sendMessageOutputStream;
    }

    public void setSendMessageOutputStream(OutputStream sendMessageOutputStream) {
        this.sendMessageOutputStream = sendMessageOutputStream;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
