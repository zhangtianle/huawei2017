package com.cacheserverdeploy.pso;

import com.cacheserverdeploy.model.Edge;
import com.cacheserverdeploy.model.ResultOut;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 3/30/17.
 */
public class PSO {

    List<Edge> edgeList = new ArrayList<Edge>();
    List<List<Edge>> totalList = new ArrayList<List<Edge>>();

    public int cost() {
        int totalFee = 0;
        for (List<Edge> el : totalList) {
            for (Edge e : el) {
                e.getPerFee();
            }
        }

        return totalFee;
    }
}
