package com.cacheserverdeploy.deploy;


import com.cacheserverdeploy.model.ConNode;
import com.cacheserverdeploy.model.Edge;
import com.cacheserverdeploy.model.NetWork;
import com.cacheserverdeploy.model.ResultLinks;

import java.util.ArrayList;
import java.util.List;

public class Deploy {
    /**
     * 你需要完成的入口
     * <功能详细描述>
     *
     * @param graphContent 用例信息文件
     * @return [参数说明] 输出结果信息
     * @see [类、类#方法、类#成员]
     */
    public static String[] deployServer(String[] graphContent) {
        /**do your work here**/
        String grapgInfo = graphContent[0];
        String[] split = grapgInfo.split(" ");
        int nodeNum = Integer.parseInt(split[0]);
        int edgeNum = Integer.parseInt(split[1]);
        int conNum = Integer.parseInt(split[2]);

        int serverCost = Integer.parseInt(graphContent[2]);

        List<Edge> edges = new ArrayList<>();
        List<ConNode> conNodes = new ArrayList<>();

        for (int i = 4; i < 4 + edgeNum; i++) {
            String[] split1 = graphContent[i].split(" ");
            int startId = Integer.parseInt(split1[0]);
            int endId = Integer.parseInt(split1[1]);
            int bandwidth = Integer.parseInt(split1[2]);
            int perFee = Integer.parseInt(split1[3]);

            Edge edge = new Edge(startId, endId, bandwidth, perFee);
            edges.add(edge);
        }

        for (int i = 6 + edgeNum; i < graphContent.length; i++) {
            String[] split1 = graphContent[i].split(" ");
            int id = Integer.parseInt(split1[0]);
            int linkedNodeId = Integer.parseInt(split1[1]);
            int bandwidthRequire = Integer.parseInt(split1[2]);

            ConNode conNode = new ConNode(id, linkedNodeId, bandwidthRequire);
            conNodes.add(conNode);
        }

        NetWork netWork = new NetWork(nodeNum, edgeNum, conNum, serverCost, edges, conNodes);

        int rConNum = netWork.getConNum();
        List<ResultLinks> resultLinksList = new ArrayList<>();
//        ResultLinks resultLinks = new ResultLinks()

        return new String[]{"17", "\r\n", "0 8 0 20"};
    }

}
