package com.cacheserverdeploy.model;

import java.util.List;

/**
 * Created by kyle on 3/30/17.
 */
public class ResultOut {
    private String[] result;

    private boolean finded;
    private int linkNum;

    private List<ResultLinks> resultLinksList;

    public ResultOut(boolean finded, int linkNum, List<ResultLinks> resultLinksList) {
        this.finded = finded;
        this.linkNum = linkNum;
        this.resultLinksList = resultLinksList;
    }

    public String[] getResult() {
        int lsize = resultLinksList.size();
        result = new String[lsize + 2];
        if (finded) {
            result[0] = String.valueOf(lsize);
            result[1] = "";
            for (int i = 0; i < lsize; i++) {
                result[i + 2] = resultLinksList.get(i).getLinks();
            }
        } else {
            return null;
        }
        return result;
    }
}
