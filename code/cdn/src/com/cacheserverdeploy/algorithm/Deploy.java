package com.cacheserverdeploy.algorithm;

import java.util.ArrayList;

public class Deploy {
    /**
     * todo
     *
     * @param graphContent caseinfo
     * @return caseouput info
     * @see [huawei]
     */
    static class edge {
        int from;
        int to;
        int cap;
        int cost;
        int next;
    }

    static class evaluate {
        public double error;
        public int cost;
        ArrayList<String> list;
    }

    static int E;  //边的个数
    static int EE;  //添加方向边和反向边的总个数
    static int NN;
    static int N;  //节点加上超级原点，汇点。
    static int S;  //超级原点
    static int T;  //超级汇点
    static int costServer;  //服务器单价
    static int numServer;
    static edge[] e;
    static int en = 0;
    static int[] head;
    static int[] pre;
    static int[] dist;
    static boolean[] visited;
    static ArrayList<Integer> T_list = new ArrayList<Integer>();
    static ArrayList<Integer> T_cost = new ArrayList<Integer>();

    static public void add(int x, int y, int f, int c) {
        if (e[en] == null) e[en] = new edge();
        e[en].from = x;
        e[en].to = y;
        e[en].cap = f;
        e[en].cost = c;
        e[en].next = head[x];
        head[x] = en++;
    }

    static public void addedge(int x, int y, int f, int c) {
        add(x, y, f, c);
        add(y, x, 0, -c);
    }

    static public boolean SPFA(int s, int t, int n) {
        ArrayList<Integer> list = new ArrayList<Integer>();
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
            for (int i = head[current]; i != -1; i = e[i].next) {
                if (e[i].cap != 0) {
                    int v = e[i].to;
                    if (dist[v] > dist[current] + e[i].cost) {
                        dist[v] = dist[current] + e[i].cost;
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

    static public evaluate MCMF(int s, int t, int n) {
        int mincost = 0;  //最小费用
        int flow = 0;
        int[] output = new int[T_cost.size()]; //初始化为0,每个消费节点获得的流量总数
        for (int i = 0; i < T_cost.size(); i++) output[i] = 0;
        ArrayList<String> string = new ArrayList<String>();
        while (SPFA(s, t, n)) {
            int minflow = Integer.MAX_VALUE;         //路径最小流量
            ArrayList<Integer> path = new ArrayList<Integer>();
            for (int i = pre[t], k1 = e[i].from; i != -1; k1 = e[i].from, i = pre[k1]) {
                minflow = Math.min(minflow, e[i].cap);
                path.add(k1);
            }
            flow += minflow;
            for (int i = pre[t], k1 = e[i].from; i != -1; k1 = e[i].from, i = pre[k1]) {
                e[i].cap -= minflow;   //当前边减去最小流量
                e[i ^ 1].cap += minflow;  //反向边加上最小流量
            }
//			System.out.println(path);
            if (minflow != Integer.MAX_VALUE) {
                for (int i =1;i< path.size();i++) {
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
        double error = sum_error / T_cost.size();
        evaluate out = new evaluate();
        out.cost = mincost + numServer * costServer;
        out.error = error;
        out.list = string;
        return out;
    }

    public static evaluate calC(String[] graphContent, ArrayList<Integer> server) {
        //文件设置
        int lines = graphContent.length;
        String[] arr1 = graphContent[0].split("\\s");
        NN = Integer.valueOf(arr1[0]);  //节点的个数
        E = Integer.valueOf(arr1[1]);  //节点的个数
        int num_T = Integer.valueOf(arr1[2]); //消费节点的个数
        costServer = Integer.valueOf(graphContent[2]);  //单位服务器价格
        //*************************************************//
        EE = E * 100000;
        e = new edge[EE];
        en = 0;
        N = NN + 2;
        head = new int[N];
        for (int i = 0; i < N; i++) {
            head[i] = -1;
        }
        pre = new int[N];
        dist = new int[N];
        visited = new boolean[N];
        //********************************************//
        for (int i = 4; i < lines - num_T - 1; i++) {
            String[] str = graphContent[i].split("\\s");
            int a = Integer.valueOf(str[0]);
            int b = Integer.valueOf(str[1]);
            int c = Integer.valueOf(str[2]);
            int d = Integer.valueOf(str[3]);
            addedge(a, b, c, d);
            addedge(b, a, c, d);
        }
        for (int i = lines - num_T; i < lines; i++) {
            String[] str = graphContent[i].split("\\s");
            int a = Integer.valueOf(str[1]);
            int b = Integer.valueOf(str[2]);
            T_list.add(a);
            T_cost.add(b);
        }
        //添加超级原点,汇点
        S = NN + 0;   //超级原点
        T = NN + 1;  //超级汇点
        numServer = server.size();
        for (int i = 0; i < T_list.size(); i++) addedge(S, T_list.get(i), T_cost.get(i), 0); //添加消费节点到超级汇点
        for (int i = 0; i < server.size(); i++) addedge(server.get(i), T, Integer.MAX_VALUE, 0);
        evaluate res = MCMF(S, T, N);
        return res;

    }


    public static String[] deployServer(String[] graphContent) {

        ArrayList<Integer> server = new ArrayList<Integer>();
        server.add(6);
        server.add(7);
        server.add(13);
        server.add(17);
        server.add(35);
        server.add(41);
        server.add(48);
        evaluate res = calC(graphContent, server);
        System.out.println(res.cost);
        System.out.println(res.error);
        System.out.println(res.list);

        res = calC(graphContent, server);
        System.out.println(res.cost);
        System.out.println(res.error);
        System.out.println(res.list);

        return null;

    }
}

