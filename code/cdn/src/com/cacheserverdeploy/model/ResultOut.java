package com.cacheserverdeploy.model;

import java.util.List;

/**
 * Created by kyle on 3/30/17.
 */
public class ResultOut {
    private String result = "";

    private boolean finded;
    private int linkNum;

    private List<ResultLinks> linksList;

    public ResultOut(String result, boolean finded, int linkNum, List<ResultLinks> linksList) {
        this.result = result;
        this.finded = finded;
        this.linkNum = linkNum;
        this.linksList = linksList;
    }

    public String getResult() {
        if (finded) {
            result = linkNum + "\n";
            for (ResultLinks l : linksList) {
                result = result + l.getLinks();
            }
        } else {
            result = "NA";
        }
        return result;
    }
}
