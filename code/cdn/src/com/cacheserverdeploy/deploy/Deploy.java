package com.cacheserverdeploy.deploy;


import com.cacheserverdeploy.model.ConNode;
import com.cacheserverdeploy.model.Network;
import com.cacheserverdeploy.model.ResultLinks;
import com.cacheserverdeploy.model.ResultOut;
import com.cacheserverdeploy.util.Tools;

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
        Tools tools = new Tools();
        Network network = tools.getNetWorkFromArgs(graphContent);

        int rConNum = network.getConNum();
        List<ResultLinks> resultLinksList = new ArrayList<ResultLinks>();

        List<ConNode> conNodeList = network.getConNodeList();
        for (ConNode cnode : conNodeList) {
            int linkedNodeId = cnode.getLinkedNodeId();
            List<Integer> ids = new ArrayList<Integer>();
            ids.add(linkedNodeId);
            ResultLinks resultLinks = new ResultLinks(ids, linkedNodeId, cnode.getBandwidthRequire());
            resultLinksList.add(resultLinks);
        }

        ResultOut resultOut = new ResultOut(true, rConNum, resultLinksList);


        return resultOut.getResult();
    }

}
