package com.cacheserverdeploy.deploy;

import com.filetool.util.GeneticAlgorithm;

import java.util.*;

public class Deploy {
    /**
     * todo
     *
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

    public static class evaluate {
        public double error;
        public int cost;
        public ArrayList<List> list;
    }

    static int E;  //边的个数
    static int EE;  //添加方向边和反向边的总个数
    public static int NN;
    static int N;  //节点加上超级原点，汇点。
    static int S;  //超级原点
    static int T;  //超级汇点
    static int costServer;  //服务器单价
    public static int numServer;
    static edge[] e;
    static int en = 0;
    static int[] head;
    static int[] pre;
    static int[] dist;
    static boolean[] visited;
    public static ArrayList<Integer> T_list = new ArrayList<Integer>();
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
        int distSum = 0;
        Deque<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < n; i++) {
            pre[i] = -1;
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
        }
        visited[s] = true;
        dist[s] = 0;
        queue.add(s);
        int num = 1;
        while (queue.size() != 0) {
            float avgDis = (float) distSum / num;
            int current = queue.getFirst();
            System.out.println(dist[current]);

            while (queue.size() != 0 && avgDis < dist[current]) {
                queue.addLast(current);
                queue.removeFirst();
                current = queue.pollFirst();
            }

            visited[current] = false;
            distSum -= dist[current];
            for (int i = head[current]; i != -1; i = e[i].next) {
                if (e[i].cap != 0) {
                    int v = e[i].to;
                    if (dist[v] > dist[current] + e[i].cost) {
                        dist[v] = dist[current] + e[i].cost;
//                        if (dist[t] != Integer.MAX_VALUE) {
//                            return true;
//                        }
                        pre[v] = i;
                        if (!visited[v]) {
                            distSum += dist[v];
                            visited[v] = true;
                            num++;
                            queue.add(v);
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
        ArrayList<List> string = new ArrayList<List>();
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

                List<Integer> line = new ArrayList<Integer>();

                for (int i = 1; i < path.size(); i++) {
                    line.add(path.get(i));
//                    string.add(path.get(i) + "");
                }
                int ss = line.get(line.size() - 1);
                int index = T_list.indexOf(ss);

                line.add(index);
                line.add(minflow);

                string.add(line);

//                string.add(index + "");
//                string.add(minflow + "");
//                string.add("\r\n");
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

    private static void readData(String[] graphContent) {

        //文件设置
        int lines = graphContent.length;
        String[] arr1 = graphContent[0].split("\\s");
        NN = Integer.valueOf(arr1[0]);  //节点的个数
        E = Integer.valueOf(arr1[1]);  //节点的个数
        int num_T = Integer.valueOf(arr1[2]); //消费节点的个数
        costServer = Integer.valueOf(graphContent[2]);  //单位服务器价格
        //*************************************************//
        EE = E * 1000;
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
        T_list = new ArrayList<Integer>();
        T_cost = new ArrayList<Integer>();
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


    }

    public static evaluate calC(List<Integer> server) {


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

        //添加超级原点,汇点
        S = NN + 0;   //超级原点
        T = NN + 1;  //超级汇点
        numServer = server.size();
        for (int i = 0; i < T_list.size(); i++) addedge(S, T_list.get(i), T_cost.get(i), 0); //添加消费节点到超级汇点
        for (int i = 0; i < server.size(); i++) addedge(server.get(i), T, Integer.MAX_VALUE, 0);
        evaluate res = MCMF(S, T, N);
        return res;

    }

    private static String listToString(List list) {
        String res = list.get(0) + "";
        for (int i = 1; i < list.size(); i++) {
            res += " " + list.get(i);
        }
        return res;
    }

    private static String[] changeResult(List<List> resultgraph) {

        int len = resultgraph.size();

        String[] result = new String[len + 2];
        result[0] = len + "";
        result[1] = "";
        for (int i = 0; i < len; i++) {
            result[i + 2] = listToString(resultgraph.get(i));
        }

        return result;
    }


    public static String[] deployServer(String[] graphContent) {

        readData(graphContent);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100, 20);
        evaluate calculate = geneticAlgorithm.calculate();
//        ArrayList<List> resultgraph = calculate.list;

//        return changeResult(null);
        return null;

    }
}

