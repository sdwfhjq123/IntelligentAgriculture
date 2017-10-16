package com.yinhao.intelligentagriculture.activity.utils;


/**
 * Created by yinhao on 2017/3/23.
 */
public class GetIpUtil {
    public static String IP = "172.20.10.7";

    static String getSensor = "http://" + IP + ":8890/type/jason/action/getSensor";
    static String getConfig = "http://" + IP + ":8890/type/jason/action/getConfig";
    static String setConfig = "http://" + IP + ":8890/type/jason/action/setConfig";
    static String control = "http://" + IP + ":8890/type/jason/action/control";
    static String getContorllerStatus = "http://" + IP + ":8890/type/jason/action/getContorllerStatus";


    public static String getGetSensor() {
        return getSensor;
    }

    public static String getGetConfig() {
        return getConfig;
    }

    public static String getSetConfig() {
        return setConfig;
    }

    public static String getControl() {
        return control;
    }

    public static String getGetContorllerStatus() {
        return getContorllerStatus;
    }
}