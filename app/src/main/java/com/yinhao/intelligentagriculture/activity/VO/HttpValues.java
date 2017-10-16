package com.yinhao.intelligentagriculture.activity.VO;

/**
 * Created by yinhao on 2017/3/20.
 */

public class HttpValues {
    private int airHumidity;//空气湿度
    private int airTemperature;//空气温度
    private int soilTemperature;//土壤温度
    private int soilHumidity;//土壤湿度
    private int co2;
    private int light;

    public HttpValues(int airHumidity, int airTemperature, int soilTemperature, int soilHumidity, int co2, int light) {
        this.airHumidity = airHumidity;
        this.airTemperature = airTemperature;
        this.soilTemperature = soilTemperature;
        this.soilHumidity = soilHumidity;
        this.co2 = co2;
        this.light = light;
    }

    public HttpValues() {

    }

    @Override
    public String toString() {
        return "HttpValues{" +
                "airHumidity=" + airHumidity +
                ", airTemperature=" + airTemperature +
                ", soilTemperature=" + soilTemperature +
                ", soilHumidity=" + soilHumidity +
                ", co2=" + co2 +
                ", light=" + light +
                '}';
    }

    public int getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(int airHumidity) {
        this.airHumidity = airHumidity;
    }

    public int getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(int airTemperature) {
        this.airTemperature = airTemperature;
    }

    public int getSoilTemperature() {
        return soilTemperature;
    }

    public void setSoilTemperature(int soilTemperature) {
        this.soilTemperature = soilTemperature;
    }

    public int getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(int soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }


}
