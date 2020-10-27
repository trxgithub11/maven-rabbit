package com.trx.rabbitmq.demo.dlx;

import java.util.ResourceBundle;


public class ResourceUtil {
    private static final ResourceBundle resourceBundle;

    static{
        resourceBundle = ResourceBundle.getBundle("config");
    }

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }

}
