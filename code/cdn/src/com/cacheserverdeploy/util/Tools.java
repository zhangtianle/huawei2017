package com.cacheserverdeploy.util;

import com.cacheserverdeploy.model.ConNode;
import com.cacheserverdeploy.model.Edge;
import com.cacheserverdeploy.model.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 3/30/17.
 */
public class Tools {
    public Network getNetWorkFromArgs(String[] graphContent) {
        String grapgInfo = graphContent[0];
        String[] split = grapgInfo.split(" ");
        int nodeNum = Integer.parseInt(split[0]);
        int edgeNum = Integer.parseInt(split[1]);
        int conNum = Integer.parseInt(split[2]);

        int serverCost = Integer.parseInt(graphContent[2]);

        List<Edge> edges = new ArrayList<Edge>();
        List<ConNode> conNodes = new ArrayList<ConNode>();

        for (int i = 4; i < 4 + edgeNum; i++) {
            String[] split1 = graphContent[i].split(" ");
            int startId = Integer.parseInt(split1[0]);
            int endId = Integer.parseInt(split1[1]);
            int bandwidth = Integer.parseInt(split1[2]);
            int perFee = Integer.parseInt(split1[3]);

            Edge edge = new Edge(startId, endId, bandwidth, perFee);
            edges.add(edge);
        }

        for (int i = 5 + edgeNum; i < graphContent.length; i++) {
            String[] split1 = graphContent[i].split(" ");
            int id = Integer.parseInt(split1[0]);
            int linkedNodeId = Integer.parseInt(split1[1]);
            int bandwidthRequire = Integer.parseInt(split1[2]);

            ConNode conNode = new ConNode(id, linkedNodeId, bandwidthRequire);
            conNodes.add(conNode);
        }

        Network network = new Network(nodeNum, edgeNum, conNum, serverCost, edges, conNodes);

        return network;
    }
}
