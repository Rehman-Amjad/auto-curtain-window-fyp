package com.technogenis.autocurtainwindow.model;

public class HistoryModel {

    String Id;
    String Dated;
    String Gas;
    String Humidity;
    String Img;
    String LDR;
    String Temperature;
    String Timed;

    public HistoryModel(String id, String dated, String gas, String humidity, String img, String LDR, String temperature, String timed) {
        Id = id;
        Dated = dated;
        Gas = gas;
        Humidity = humidity;
        Img = img;
        this.LDR = LDR;
        Temperature = temperature;
        Timed = timed;
    }

    public HistoryModel() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDated() {
        return Dated;
    }

    public void setDated(String dated) {
        Dated = dated;
    }

    public String getGas() {
        return Gas;
    }

    public void setGas(String gas) {
        Gas = gas;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getLDR() {
        return LDR;
    }

    public void setLDR(String LDR) {
        this.LDR = LDR;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getTimed() {
        return Timed;
    }

    public void setTimed(String timed) {
        Timed = timed;
    }
}
