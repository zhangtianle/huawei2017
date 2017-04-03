package com.cacheserverdeploy.algorithm;


import com.cacheserverdeploy.model.Edge;
import com.cacheserverdeploy.model.Evaluate;
import com.cacheserverdeploy.model.NetInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 3/31/17.
 */
public class NetFlow {

    private NetInfo netInfo;
    private int[] pre;
    private int[] dist;
    boolean[] visited;

    public NetFlow(NetInfo netInfo) {
        this.netInfo = netInfo;
        int N = netInfo.getSuperNodeNum();
        pre = new int[N];
        dist = new int[N];
    }

    private boolean SPFA(int s, int t, int n) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        Edge[] e = netInfo.getEdges();
        int[] head = netInfo.getHead();

//        boolean[] visited = new boolean[N];

        for (int i = 0; i < n; i++) {
            pre[i] = -1;
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
        }
        visited[s] = true;
        dist[s] = 0;
        list.add(s);
        while (list.size() != 0) {
            int current = list.get(0);
            list.remove(0);
            visited[current] = false;
            for (int i = head[current]; i != -1; i = e[i].getNext()) {
                if (e[i].getCap() != 0) {
                    int v = e[i].getTo();
                    if (dist[v] > dist[current] + e[i].getCost()) {
                        dist[v] = dist[current] + e[i].getCost();
                        pre[v] = i;
                        if (!visited[v]) {
                            visited[v] = true;
                            list.add(v);
                        }
                    }
                }
            }
        }
        return dist[t] != Integer.MAX_VALUE;        //找不到一条到终点的路
    }

    public Evaluate MCMF(int s, int t, int n) {

        Edge[] e = netInfo.getEdges();

        List<Integer> T_cost = netInfo.getT_cost();
        List<Integer> T_list = netInfo.getT_list();

        int flow = 0;
        int mincost = 0;  //最小费用
        float error;
        int[] output = new int[T_cost.size()]; //初始化为0,每个消费节点获得的流量总数
        for (int i = 0; i < T_cost.size(); i++) output[i] = 0;
        ArrayList<String> string = new ArrayList<String>();
        while (SPFA(s, t, n)) {
            int minflow = Integer.MAX_VALUE;         //路径最小流量
            ArrayList<Integer> path = new ArrayList<Integer>();
            for (int i = pre[t], k1 = e[i].getFrom(); i != -1; k1 = e[i].getFrom(), i = pre[k1]) {
                minflow = Math.min(minflow, e[i].getCap());
                path.add(k1);
            }
            flow += minflow;
            for (int i = pre[t], k1 = e[i].getFrom(); i != -1; k1 = e[i].getFrom(), i = pre[k1]) {
                e[i].setCap(e[i].getCap() - minflow);   //当前边减去最小流量
                e[i ^ 1].setCap(e[i ^ 1].getCap() + minflow);   //反向边加上最小流量
            }
            if (minflow != Integer.MAX_VALUE) {
                for (int i = 1; i < path.size(); i++) {
                    string.add(path.get(i) + "");
                }
                String ss = string.get(string.size() - 1);
                int index = T_list.indexOf(Integer.parseInt(ss));
                string.add(index + "");
                string.add(minflow + "");
                string.add("\r\n");
            }
            mincost += dist[t] * minflow;
            if (T_list.contains(path.get(0))) {
                int index = T_list.indexOf(path.get(0));
                output[index] += minflow;

            }
        }
        int sum_error = 0;
        for (int i = 0; i < T_cost.size(); i++) sum_error += T_cost.get(i) - output[i];
        error = sum_error / T_cost.size();
        Evaluate out = new Evaluate();
        System.out.println(mincost);

        out.setCost(mincost + netInfo.getServerNum() * netInfo.getCost_server());
        out.setError(error);
        out.setList(string);
        return out;
    }

    public Evaluate calC(boolean[] gene) {

        int N = netInfo.getSuperNodeNum();
        int NN = netInfo.getNodeNum();
        List<Integer> T_cost = netInfo.getT_cost();
        List<Integer> T_list = netInfo.getT_list();

        pre = new int[N];
        dist = new int[N];
        visited = new boolean[N];

        //添加超级原点,汇点
        int S = NN + 0;   //超级原点
        int T = NN + 1;  //超级汇点
        for (int i = 0; i < T_list.size(); i++) netInfo.addedge(S, T_list.get(i), T_cost.get(i), 0); //添加消费节点到超级汇点
//        for (int i = 0; i < T_list.size(); i++) netInfo.addedge(T_list.get(i), T, T_cost.get(i), 0); //添加消费节点到超级汇点
        ArrayList<Integer> inNode = new ArrayList<Integer>();   //服务器个数
        int serverNum = 0;
        for (int i = 0; i < gene.length; i++) {
            if (gene[i] == true) {
                netInfo.addedge(i, T, Integer.MAX_VALUE, 0);
                serverNum++;
            }
        }
        netInfo.setServerNum(serverNum);
        Evaluate output = MCMF(S, T, N);
        return output;
    }
}
