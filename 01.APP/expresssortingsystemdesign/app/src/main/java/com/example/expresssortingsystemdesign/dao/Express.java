package com.example.expresssortingsystemdesign.dao;

public class Express {
    private Integer eid;
    private String city;
    private String area;
    private String pid;
    private String state;
    private String createDateTime;
    private String location;

    @Override
    public String toString() {
        return "Express{" +
                "eid=" + eid +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", pid='" + pid + '\'' +
                ", state='" + state + '\'' +
                ", createDateTime='" + createDateTime + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }
}
