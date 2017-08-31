package cn.domarvel.model.network.init;

/**
 * Created by __MoonFollow on 2017/8/31.
 */

import cn.domarvel.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 这里面加载配置文件。
 */
public class LoadConfig {

    //保存着所有的路径
    private  List<String> configLocations = new LinkedList<>();

    //保存所有被加载的配置文件
    private  Map<String,Properties> configMaps = new HashMap<>();
    /**
     * 在构造方法里面调用配置文件加载方法 来 加载所有的配置文件。
     */
    public LoadConfig(){
        loadDefaultHttpHeaderConfig();
    }

    /**
     * 这种加载文件的方式有个缺点，那就是不能够扫描文件夹加载需要的配置文件，必须要是被配置了的属性文件
     */
    public void loadDefaultHttpHeaderConfig(){
        for (String configLocation : configLocations) {
            InputStream configInputStream = LoadConfig.class.getResourceAsStream(configLocation);
            String key = StringUtils.getFileNameEscapeFileType(configLocation);//不包含后缀的文件名称作为key值。
            Properties value = new Properties();
            try {
                value.load(configInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(key+"="+value);
            configMaps.put(key,value);
        }
    }



    public List<String> getConfigLocations() {
        return configLocations;
    }

    public List<String> setConfigLocations(List<String> configLocations) {
        this.configLocations = configLocations;
        return this.configLocations;
    }

    public Map<String, Properties> getConfigMaps() {
        return configMaps;
    }
}
