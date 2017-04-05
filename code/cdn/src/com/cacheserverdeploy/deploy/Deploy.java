package com.cacheserverdeploy.deploy;

import com.filetool.util.Chromosome;
import com.filetool.util.GeneticAlgorithm;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Deploy {
    /**
     * todo
     *
     * @return caseouput info
     * @see [huawei]
     */
    public static class edge {
        int from;
        int to;
        int cost;
        int cap;
        int v;
        int next;
        int re;
        int f;
    }

    public static class Evaluate {
        public double error;
        public int cost;
        public ArrayList<List> list;
    }

    static float timeLo = 0;
    static int E;  //边的个数
    static int EE;  //添加方向边和反向边的总个数
    public static int NN;
    static int N;  //节点加上超级原点，汇点。
    static int S;  //超级原点
    static int T;  //超级汇点
    static int costServer;  //服务器单价
    public static int numServer;
    static int lines; //文件行数
    static int num_T;
    static List<String> link = new ArrayList<String>();
    public static edge[] e;
    static int en = 0;
    public static int[] head;
    static int[] pre;
    static int[] dist;
    static int[] visited;
    static int ans; //结果
    static int cost; //花费
    public static ArrayList<Integer> T_list = new ArrayList<Integer>();
    public static ArrayList<Integer> T_cost = new ArrayList<Integer>();

    static boolean[] visitedDFS;

    static List<List<Integer>> pathList = new ArrayList<List<Integer>>(); // 所有的路径

    static public void add(int x, int y, int f, int c) {
        e[en].from = x;
        e[en].to = y;
        e[en].v = y;
        e[en].cap = f;
        e[en].cost = c;
        e[en].re = en + 1;
        e[en].next = head[x];
        head[x] = en++;

        e[en].from = y;
        e[en].to = x;
        e[en].v = y;
        e[en].cap = 0;
        e[en].cost = -c;
        e[en].re = en - 1;
        e[en].next = head[y];
        head[y] = en++;
    }

    //***********************************************************************//
    static public int aug(int u, int f) {
        if (u == T) {
            ans += cost * f;

            return f;
        }
        visited[u] = -1;
        int temp = f;
        for (int i = head[u]; i != -1; i = e[i].next) {
            if (e[i].cap != 0 && e[i].cost == 0 && visited[e[i].v] == 0) {
                int delta = aug(e[i].v, temp < e[i].cap ? temp : e[i].cap);
                e[i].cap -= delta;
                e[e[i].re].cap += delta;
                e[i].f += delta;
                temp -= delta;
                if (temp == 0) return f;
            }
        }
        return f - temp;
    }

    static public boolean modlabel() {
        int delta = Integer.MAX_VALUE;
        for (int u = 0; u < N; u++) {
            if (visited[u] != 0) {
                for (int i = head[u]; i != -1; i = e[i].next) {
                    if (e[i].cap != 0 && visited[e[i].v] == 0 && e[i].cost < delta)
                        delta = e[i].cost;
                }
            }
        }
        if (delta == Integer.MAX_VALUE) return false;
        for (int u = 0; u < N; u++) {
            if (visited[u] != 0) {
                for (int i = head[u]; i != -1; i = e[i].next) {
                    e[i].cost -= delta;
                    e[e[i].re].cost += delta;
                }
            }
        }
        cost += delta;
        return true;
    }

    static public void costflow() {
        do {
            do {
                for (int i = 0; i < visited.length; i++) visited[i] = 0;
            } while (aug(S, Integer.MAX_VALUE) != 0);
        } while (modlabel());
    }


    static public boolean SPFA(int s, int t, int n) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            pre[i] = -1;
            visited[i] = 0;
            dist[i] = Integer.MAX_VALUE;
        }
        visited[s] = 1;
        dist[s] = 0;
        list.add(s);
        while (list.size() != 0) {
            int current = list.get(0);
            list.remove(0);
            visited[current] = 0;
            for (int i = head[current]; i != -1; i = e[i].next) {
                if (e[i].cap != 0) {
                    int v = e[i].to;
                    if (dist[v] > dist[current] + e[i].cost) {
                        dist[v] = dist[current] + e[i].cost;
                        pre[v] = i;
                        if (visited[v] == 0) {
                            visited[v] = 1;
                            list.add(v);
                        }
                    }
                }
            }
        }
        return dist[t] != Integer.MAX_VALUE;        //找不到一条到终点的路
    }

    static private int getPath() {
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> list = new ArrayList<Integer>();
        queue.add(S);
        int minFlow = Integer.MAX_VALUE;
        boolean found = false;
        for (int i = 0; i < N; i++)
            visited[i] = 0;

        while (queue.size() != 0) {
            int current = queue.poll();
            visited[current] = 1;
            for (int i = head[current]; i != -1; i = e[i].next) {
                if (e[i].f != 0) {
                    int v = e[i].to;
                    list.add(i);
                    queue.add(v);
                    if (e[i].f < minFlow) {
                        minFlow = e[i].f;
                    }
                    if (e[i].to == T) {
                        found = true;
                        break;
                    }
                    break;
                }
            }
            int p;
            if (found) {
                for (int i = 0; i < list.size(); i++) {
                    p = list.get(i);
                    e[p].f -= minFlow;
                }
                List<Integer> path = new ArrayList<Integer>(); // 单个路径+最小流量
//                System.out.println(minFlow);
                for (int i = list.size() - 2; i >= 0 ; i--) {
//                    System.out.println("edge:" + e[list.get(i)].from + "->" + e[list.get(i)].to);
                    path.add(e[list.get(i)].to);
                }
                int serNum = (int) T_list.indexOf(e[list.get(0)].to);
                path.add(serNum);
                path.add(minFlow);
                pathList.add(path);
                break;
            }

        }
        if (!found) return -1;
        return 0;
    }

    /**
     * @param s 消费总结点
     * @param t 服务器总节点
     * @param n 节点加上超级原点，汇点
     * @return
     */
    static public Evaluate MCMF(int s, int t, int n) {
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
                e[i ^ 1].cap += minflow;//反向边加上最小流量
            }
            if (minflow != Integer.MAX_VALUE) {

                List<Integer> line = new ArrayList<Integer>();

                for (int i = 1; i < path.size(); i++) {
                    line.add(path.get(i));
                }
                int ss = line.get(line.size() - 1);
                int index = T_list.indexOf(ss);

                line.add(index);
                line.add(minflow);

                string.add(line);
            }
            mincost += dist[t] * minflow;
            if (T_list.contains(path.get(0))) {
                int index = T_list.indexOf(path.get(0));
                output[index] += minflow;
            }
        }
        double sum_error = 0.0;
        for (int i = 0; i < T_cost.size(); i++) sum_error += (T_cost.get(i) - output[i]);
        Evaluate out = new Evaluate();
        out.cost = mincost + numServer * costServer;
        out.error = sum_error;
        out.list = string;
        return out;
    }


   /* private static int DFS(int i, int minFlow) {
        System.out.println("node:" + i);
        int p = head[i];

        if (e[p].to == T) {
            if (e[p].f < minFlow) {
                minFlow = e[p].f;
            }
            e[p].f -= minFlow;

            path.add(minFlow);
            List<Integer> pp = new ArrayList<Integer>();
            for (int ii : path) {
                pp.add(ii);
            }
            pathList.add(pp);
            return minFlow;
        }
        System.out.println("111111111");
        while (p != -1) {
            if (e[p].f != 0) {
                break;
            }
            System.out.println(e[p].f);
            p = e[p].next;
        }
        if (p == -1) {
            return -1;
        }
        System.out.println("22222222");
        int minFlow1 = 1;
        // 向消费节点搜索
        while (p != -1 && e[p].f != 0) {
            if (visitedDFS[e[p].to]) {
                continue;
            }
            if (e[p].f < minFlow) {
                minFlow = e[p].f;
            }
            visitedDFS[e[p].to] = true;
            path.add(e[p].to);
            System.out.println("lujing " + path);
            minFlow1 = DFS(e[p].to, minFlow);
            if (minFlow1 != -1) {
                e[p].f -= minFlow1;
                if (e[p].f == 0) {
                    path.remove(path.size() - 1);
                    return 0;
                }
            }
            p = e[p].next;
        }
        return minFlow1;
    }
*/

//*******************************************************************//

    private static void readData(String[] graphContent) {

        //文件设置
        lines = graphContent.length;
        String[] arr1 = graphContent[0].split("\\s");
        NN = Integer.valueOf(arr1[0]);  //节点的个数
        E = Integer.valueOf(arr1[1]);  //节点的个数
        num_T = Integer.valueOf(arr1[2]); //消费节点的个数
        costServer = Integer.valueOf(graphContent[2]);  //单位服务器价格
        T_list = new ArrayList<Integer>();
        T_cost = new ArrayList<Integer>();
        for (int i = 4; i < lines - num_T - 1; i++) {
            link.add(graphContent[i]);
        }
        for (int i = lines - num_T; i < lines; i++) {
            String[] s = graphContent[i].split("\\s");
            int a = Integer.valueOf(s[1]);
            int b = Integer.valueOf(s[2]);
            T_list.add(a);
            T_cost.add(b);
        }
    }

    public static Evaluate calC(ArrayList<Integer> server) {
        //*************************************************//
        float xjbf = xjbf(server);
        timeLo += xjbf;

        costflow();

        ans += costServer * server.size();
        int error = 0;
        for (int i = head[S]; i != -1; i = e[i].next) {
            error += e[i].cap;
        }
        Evaluate res = new Evaluate();
        res.cost = ans;
        res.error = error;
        return res;

    }

    private static float xjbf(ArrayList<Integer> server) {
        long startTime = System.currentTimeMillis();

        EE = (lines - num_T) * 4 + num_T * 8 + 10000; //设置边数
        e = new edge[EE];
        for (int i = 0; i < EE; i++) {
            e[i] = new edge();
        }
        en = 0;
        N = NN + 2;
        head = new int[N];
        for (int i = 0; i < N; i++) head[i] = -1;
        visited = new int[N];
        for (int i = 0; i < N; i++) visited[i] = 0;
        pre = new int[N];
        dist = new int[N];
        ans = 0;
        cost = 0;
        //********************************************//
        for (String str : link) {
            String[] s = str.split("\\s");
            int a = Integer.valueOf(s[0]);
            int b = Integer.valueOf(s[1]);
            int c = Integer.valueOf(s[2]);
            int d = Integer.valueOf(s[3]);
            add(a, b, c, d);
            add(b, a, c, d);
        }
        //添加超级原点,汇点
        S = NN + 0;   //超级原点
        T = NN + 1;  //超级汇点
        numServer = server.size();
        for (int i = 0; i < T_list.size(); i++) add(S, T_list.get(i), T_cost.get(i), 0); //添加消费节点到超级汇点
        for (int i = 0; i < server.size(); i++) add(server.get(i), T, Integer.MAX_VALUE, 0);

        long endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime);
        return excTime;
    }

    private static String listToString(List list) {
        String res = list.get(0) + "";
        for (int i = 1; i < list.size(); i++) {
            res += " " + list.get(i);
        }
        return res;
    }

    private static String[] changeResult(List<List<Integer>> resultgraph) {

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
        int popSize = 200;
        if (N > 500) {
            popSize = 100;
        }

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(popSize, 100);
        Evaluate calculate = geneticAlgorithm.calculate();
        Chromosome bestChromosome = geneticAlgorithm.getBestChromosome();

        ArrayList<Integer> server = new ArrayList<Integer>();
        boolean[] gene = bestChromosome.getGene();
        for (int i = 0; i < gene.length; i++) {
            if (gene[i] == true) {
                server.add(i);
            }
        }
        xjbf(server);
        Evaluate evaluate = calC(server);

        visitedDFS = new boolean[N];
        while (getPath() != -1) {
        }
        //DFS(S, Integer.MAX_VALUE);
//        System.out.println("woshipathList:   " + pathList);

//
//        System.out.println(timeLo);
//        Evaluate mcmf = MCMF(S, T, N);
//        ArrayList<List> resultgraph = mcmf.list;
//
//        System.out.println(mcmf.list);
//        System.out.println(mcmf.cost);
//        System.out.println(mcmf.error + "===============");
//
//        System.out.println(calculate.error);
//        System.out.println(calculate.cost);

        return changeResult(pathList);
    }

}
