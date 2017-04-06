package com.cacheserverdeploy.deploy;

import com.cacheserverdeploy.model.Edge;
import com.cacheserverdeploy.model.NetInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 4/1/17.
 */
public class Tool {
    public NetInfo readData(String[] graphContent) {
        NetInfo netInfo = new NetInfo();

        List<Integer> T_list = new ArrayList<Integer>();
        List<Integer> T_cost = new ArrayList<Integer>();

        //文件设置
        int lines = graphContent.length;
        String[] arr1 = graphContent[0].split("\\s");
        int NN = Integer.valueOf(arr1[0]);  //节点的个数
        int E = Integer.valueOf(arr1[1]); //边的个数
        int num_T = Integer.valueOf(arr1[2]); //消费节点的个数
        int cost_server = Integer.valueOf(graphContent[2]);

        int N = NN + 2;
        netInfo.setEdgeNum(E);
        netInfo.setNodeNum(NN);
        netInfo.setSuperNodeNum(N);

        netInfo.setCost_server(cost_server);
//        netInfo.setEdges(new Edge[netInfo.getEdgeNum()]);
        int[] head = new int[N];
        for (int i = 0; i < N; i++) {
            head[i] = -1;
        }
        netInfo.setHead(head);


        //每个节点所提供的带宽。
        for (int i = 4; i < lines - num_T - 1; i++) {
            String[] str = graphContent[i].split("\\s");
            int a = Integer.valueOf(str[0]);
            int b = Integer.valueOf(str[1]);
            int c = Integer.valueOf(str[2]);
            int d = Integer.valueOf(str[3]);
            netInfo.addedge(a, b, c, d);
            netInfo.addedge(b, a, c, d);
        }


        for (int i = lines - num_T; i < lines; i++) {
            String[] str = graphContent[i].split("\\s");
            int a = Integer.valueOf(str[1]);
            int b = Integer.valueOf(str[2]);
            T_list.add(a);
            T_cost.add(b);
        }
        netInfo.setT_list(T_list);
        netInfo.setT_cost(T_cost);

        for (int i = 0; i < T_list.size(); i++) {
            netInfo.addedge(NN, T_list.get(i), T_cost.get(i), 0); //添加消费节点到超级汇点
        }

        return netInfo;
    }
}
