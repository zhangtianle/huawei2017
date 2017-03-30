package com.cacheserverdeploy.model;

import java.util.List;

/**
 * Created by kyle on 3/30/17.
 */
public class ResultLinks {
    private List<Integer> nodeIds;
    private int conId;
    private int bandwidth;

    public ResultLinks(List<Integer> nodeIds, int conId, int bandwidth) {
        this.nodeIds = nodeIds;
        this.conId = conId;
        this.bandwidth = bandwidth;
    }

    public String getLinks() {
        String result = "";
        for (int i : nodeIds) {
            result = result + i + " ";
        }
        return result + conId + " " + bandwidth + "\n";
    }
}
