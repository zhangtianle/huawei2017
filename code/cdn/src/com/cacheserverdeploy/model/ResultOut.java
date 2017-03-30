package com.cacheserverdeploy.model;

import java.util.List;

/**
 * Created by kyle on 3/30/17.
 */
public class ResultOut {
    private String[] result = {"", "\r\n", ""};

    private boolean finded;
    private int linkNum;

    private List<ResultLinks> resultLinksList;

    public ResultOut(boolean finded, int linkNum, List<ResultLinks> resultLinksList) {
        this.finded = finded;
        this.linkNum = linkNum;
        this.resultLinksList = resultLinksList;
    }

    public String[] getResult() {
        if (finded) {
            result[0] = linkNum + "";
            result[1] = "\n";
            for (ResultLinks l : resultLinksList) {
                result[2] = result[2] + l.getLinks();
            }
        } else {
            return null;
        }
        return result;
    }
}
