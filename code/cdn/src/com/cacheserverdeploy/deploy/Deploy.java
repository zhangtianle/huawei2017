package com.cacheserverdeploy.deploy;


import com.cacheserverdeploy.algorithm.NetFlow;
import com.cacheserverdeploy.model.*;
import com.cacheserverdeploy.util.Tools;

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

        NetFlow netFlow = new NetFlow();
        boolean shortestPaths = netFlow.getShortestPaths(network.getNodeNum(), 3, network.getEdgeList());


        return null;
    }

}
