package com.example.pieperj.powerschool;

public class Assignment {

    private String name;
    private int pointsTotal;

    public Assignment(String name, int pointsTotal) {
        this.name = name;
        this.pointsTotal = pointsTotal;
    }

    public Assignment() {
        this.name = "";
        this.pointsTotal = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public String toString() {
        return String.format("%s - %d pts", name, pointsTotal);
    }


}
