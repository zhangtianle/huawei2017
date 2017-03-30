package com.cacheserverdeploy.model;

/**
 * Created by kyle on 3/30/17.
 */
public class ConNode {
    private int id;
    private int linkedNodeId;
    private int bandwidthRequire;

    public ConNode(int id, int linkedNodeId, int bandwidthRequire) {
        this.id = id;
        this.linkedNodeId = linkedNodeId;
        this.bandwidthRequire = bandwidthRequire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLinkedNodeId() {
        return linkedNodeId;
    }

    public void setLinkedNodeId(int linkedNodeId) {
        this.linkedNodeId = linkedNodeId;
    }

    public int getBandwidthRequire() {
        return bandwidthRequire;
    }

    public void setBandwidthRequire(int bandwidthRequire) {
        this.bandwidthRequire = bandwidthRequire;
    }
}
