package cn.domarvel.web.controller;

import cn.domarvel.model.network.SimpleHttp;
import cn.domarvel.po.HttpResponseMessage;
import cn.domarvel.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * Created by __MoonFollow on 2017/8/31.
 */
@Controller
public class TestSimpleHttp {

    @RequestMapping("/sendGet")
    public @ResponseBody String sendHttpView(HttpServletRequest request,String url){
        System.out.println(url);
        HttpResponseMessage responseMessage = SimpleHttp.sendGet(url,null);
        InputStream inputStream = responseMessage.getResponseInputStream();
        String htmlReuslt = StringUtils.getLinesStringByInputStream(inputStream,"utf8");
        return htmlReuslt;
    }


}
