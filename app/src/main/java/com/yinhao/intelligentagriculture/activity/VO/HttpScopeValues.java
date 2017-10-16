package com.yinhao.intelligentagriculture.activity.VO;

/**
 * Created by yinhao on 2017/3/22.
 */

public class HttpScopeValues {
    private int maxCo2;
    private int minCo2;
    private int maxLight;
    private int minLight;
    private int maxSoilHumidity;
    private int minSoilHumidity;
    private int maxSoilTemperature;
    private int minSoilTemperature;
    private int controlAuto;
    private int maxAirTemperature;
    private int minAirTemperature;
    private int maxAirHumidity;
    private int minAirHumidity;

    public HttpScopeValues(int maxCo2, int minCo2, int maxLight, int minLight, int maxSoilHumidity, int minSoilHumidity, int maxSoilTemperature, int minSoilTemperature, int controlAuto, int maxAirTemperature, int minAirTemperature, int maxAirHumidity, int minAirHumidity) {
        this.maxCo2 = maxCo2;
        this.minCo2 = minCo2;
        this.maxLight = maxLight;
        this.minLight = minLight;
        this.maxSoilHumidity = maxSoilHumidity;
        this.minSoilHumidity = minSoilHumidity;
        this.maxSoilTemperature = maxSoilTemperature;
        this.minSoilTemperature = minSoilTemperature;
        this.controlAuto = controlAuto;
        this.maxAirTemperature = maxAirTemperature;
        this.minAirTemperature = minAirTemperature;
        this.maxAirHumidity = maxAirHumidity;
        this.minAirHumidity = minAirHumidity;
    }

    public int getMaxCo2() {
        return maxCo2;
    }

    public void setMaxCo2(int maxCo2) {
        this.maxCo2 = maxCo2;
    }

    public int getMinCo2() {
        return minCo2;
    }

    public void setMinCo2(int minCo2) {
        this.minCo2 = minCo2;
    }

    public int getMaxLight() {
        return maxLight;
    }

    public void setMaxLight(int maxLight) {
        this.maxLight = maxLight;
    }

    public int getMinLight() {
        return minLight;
    }

    public void setMinLight(int minLight) {
        this.minLight = minLight;
    }

    public int getMaxSoilHumidity() {
        return maxSoilHumidity;
    }

    public void setMaxSoilHumidity(int maxSoilHumidity) {
        this.maxSoilHumidity = maxSoilHumidity;
    }

    public int getMinSoilHumidity() {
        return minSoilHumidity;
    }

    public void setMinSoilHumidity(int minSoilHumidity) {
        this.minSoilHumidity = minSoilHumidity;
    }

    public int getMaxSoilTemperature() {
        return maxSoilTemperature;
    }

    public void setMaxSoilTemperature(int maxSoilTemperature) {
        this.maxSoilTemperature = maxSoilTemperature;
    }

    public int getMinSoilTemperature() {
        return minSoilTemperature;
    }

    public void setMinSoilTemperature(int minSoilTemperature) {
        this.minSoilTemperature = minSoilTemperature;
    }

    public int getControlAuto() {
        return controlAuto;
    }

    public void setControlAuto(int controlAuto) {
        this.controlAuto = controlAuto;
    }

    public int getMaxAirTemperature() {
        return maxAirTemperature;
    }

    public void setMaxAirTemperature(int maxAirTemperature) {
        this.maxAirTemperature = maxAirTemperature;
    }

    public int getMinAirTemperature() {
        return minAirTemperature;
    }

    public void setMinAirTemperature(int minAirTemperature) {
        this.minAirTemperature = minAirTemperature;
    }

    public int getMaxAirHumidity() {
        return maxAirHumidity;
    }

    public void setMaxAirHumidity(int maxAirHumidity) {
        this.maxAirHumidity = maxAirHumidity;
    }

    public int getMinAirHumidity() {
        return minAirHumidity;
    }

    public void setMinAirHumidity(int minAirHumidity) {
        this.minAirHumidity = minAirHumidity;
    }
}
