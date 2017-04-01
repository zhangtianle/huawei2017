package com.cacheserverdeploy.algorithm;

import com.cacheserverdeploy.model.Edge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 3/31/17.
 */
public class NetFlow {

    private int[] result;

    public boolean getShortestPaths(int n, int s, List<Edge> A) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        result = new int[n];
        boolean[] used = new boolean[n];
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = Integer.MAX_VALUE;
            used[i] = false;
        }
        result[s] = 0;     //第s个顶点到自身距离为0
        used[s] = true;    //表示第s个顶点进入数组队
        num[s] = 1;       //表示第s个顶点已被遍历一次
        list.add(s);      //第s个顶点入队
        while (list.size() != 0) {
            int a = list.get(0);   //获取数组队中第一个元素
            list.remove(0);         //删除数组队中第一个元素
            for (int i = 0; i < A.size(); i++) {
                //当list数组队的第一个元素等于边A[i]的起点时
                int startId = A.get(i).getStartId();
                int endId = A.get(i).getEndId();
                int bandwidth = A.get(i).getBandwidth();
                if (a == startId && result[endId] > result[startId] + bandwidth) {
                    result[endId] = result[startId] + bandwidth;
                    if (!used[endId]) {
                        list.add(endId);
                        num[endId]++;
                        if (num[endId] > n)
                            return false;
                        used[endId] = true;   //表示边A[i]的终点b已进入数组队
                    }
                }
            }
            used[a] = false;        //顶点a出数组对
        }
        return true;
    }
}
