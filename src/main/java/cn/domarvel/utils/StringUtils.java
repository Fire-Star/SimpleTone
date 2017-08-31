package cn.domarvel.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {
    public static String getLinesStringByInputStream(InputStream inputStream,String charset){
        StringBuilder resultLineString = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,charset));
            while ((line=reader.readLine())!=null){
                line=line.trim();
                if(!line.equals("")){
                    resultLineString.append(line+System.lineSeparator());
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return resultLineString.toString();
    }

    /**
     * 通过文件路径字符串获取文件获取文件路径
     * @param location
     * @return
     */
    public static String getFileNameEscapeFileType(String location){
        String fileName = location.substring(location.lastIndexOf("/")+1);//当前文件名包含文件类型
        return fileName.substring(0,fileName.indexOf("."));
    }
}
