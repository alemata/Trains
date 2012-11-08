package com.trains.model;

public class Edge {

    private String fromId;
    private String toId;
    private Integer cost;


    public Edge(String fromId, String toId, int cost) {
        this.fromId = fromId;
        this.toId = toId;
        this.cost = cost;
    }


    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public int getCost() {
        return cost;
    }
}
