package com.home_app.model.hue;

public class HueColorLamp {
    private State state;
    private String type;
    private String name;
    private String uniqueid;
    private String swversion;
    private String swconfigid;
    private String productid;

    // getters and setters

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getSwversion() {
        return swversion;
    }

    public void setSwversion(String swversion) {
        this.swversion = swversion;
    }

    public String getSwconfigid() {
        return swconfigid;
    }

    public void setSwconfigid(String swconfigid) {
        this.swconfigid = swconfigid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
