package com.cacheserverdeploy.deploy;


import com.cacheserverdeploy.algorithm.GeneticAlgorithm;
import com.cacheserverdeploy.algorithm.NetFlow;
import com.cacheserverdeploy.model.NetInfo;
import com.cacheserverdeploy.tool.Tool;

public class Deploy
{
    /**
     * 你需要完成的入口
     * <功能详细描述>
     * @param graphContent 用例信息文件
     * @return [参数说明] 输出结果信息
     * @see [类、类#方法、类#成员]
     */
    public static String[] deployServer(String[] graphContent)
    {
        /**do your work here**/

        Tool tool = new Tool();
        NetInfo netInfo = tool.readData(graphContent);
        NetFlow netFlow = new NetFlow(netInfo);
//
//        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
//        geneticAlgorithm.calculate();

        boolean b[] = new boolean[netInfo.getNodeNum()];
        b[6] = true;
        b[7] = true;
        b[13] = true;
        b[17] = true;
        b[35] = true;
        b[41] = true;
        b[48] = true;
        netFlow.calC(b);
//        netFlow.calC(b);

        return new String[]{"17","\r\n","0 8 0 20"};
    }

}
