package com.filetool.util;

import com.cacheserverdeploy.deploy.Deploy;
import com.cacheserverdeploy.deploy.Deploy.evaluate;

import java.util.ArrayList;
import java.util.List;

import static com.cacheserverdeploy.deploy.Deploy.calC;

public class GeneticAlgorithm {
    private ArrayList<Chromosome> population = new ArrayList<Chromosome>();
    private int popSize; // 种群数量
    private int geneSize; // 基因长度编码
    private int maxIter = 100;
    private double pm = 0.02;
    private double pc = 0.5;
    private int[] bandwidth;    //消费节带宽需求
    private int consumptionNum; // 消费节点个数
    private int generation = 1;//当前遗传到第几代
    private evaluate bestScore; //最好的情况

    public GeneticAlgorithm(int popSize, int geneSize, int maxIter,
                            double pm, double pc, int[] bandwidth) {
        super();
        this.popSize = popSize;
        this.geneSize = geneSize;  //基因长度
        this.maxIter = maxIter;
        this.pm = pm;
        this.pc = pc;
        this.bandwidth = bandwidth;
        this.consumptionNum = bandwidth.length;
        this.bestScore.error = 0.0;
        this.bestScore.cost = 100000;
    }

    public GeneticAlgorithm(int popSize, int maxIter) {
        this.bestScore = new evaluate();
        this.bestScore.cost = Integer.MAX_VALUE;
        this.bestScore.error = 10.0;
        this.popSize = popSize;
        this.maxIter = maxIter;
    }

    public evaluate calculate() {
        generation = 1;
        init();
        while (generation <= maxIter) {
            evolve();
            generation++;
            System.out.println(generation);
            System.out.println(bestScore.cost);
        }
        System.out.println(bestScore.list);
        return bestScore;
    }

    private void init() {
        float p = (float) Deploy.T_list.size() / (float) Deploy.NN;
        for (int i = 0; i < popSize; i++) {
            boolean[] booleans = new boolean[Deploy.NN];
            for (int j = 0; j < booleans.length; j++) {
                double random = Math.random();
                if (random < p) {
                    booleans[j] = true;
                }
            }
            Chromosome chro = new Chromosome(booleans);
            population.add(chro);
        }
    }


    private void evolve() {
//		ArrayList<Chromosome> childPopulation = new ArrayList<Chromosome>();
        ArrayList<Chromosome> pops = new ArrayList<Chromosome>();
        ArrayList<evaluate> resPopulation = new ArrayList<evaluate>();
        pops = clone(population);
        while (pops.size() > 0) {
            int p1 = (int) (Math.random() * pops.size() % pops.size());
            Chromosome c1 = pops.get(p1);
            pops.remove(p1);
            int p2 = (int) (Math.random() * pops.size() % pops.size());
            Chromosome c2 = pops.get(p2);
            pops.remove(p2);
            ///无放回的随机取样
            ArrayList<Chromosome> children = Chromosome.genetic(c1, c2, pc);
            if (children != null) {
                for (Chromosome child : children) {
                    population.add(child);
                }
            }
        }
        mutation();
        resPopulation = calculateScore();
        selected(resPopulation);

//		calculateScore();
    }

    private void selected(ArrayList<evaluate> resPopulation) {
        ArrayList<Chromosome> nextPopulation = new ArrayList<Chromosome>();
        nextPopulation = clone(population);
        population = new ArrayList<Chromosome>();
        while (nextPopulation.size() > 0) {
            int p1 = (int) (Math.random() * nextPopulation.size() % nextPopulation.size());
            Chromosome c1 = nextPopulation.get(p1);
            evaluate r1 = resPopulation.get(p1);
            nextPopulation.remove(p1);
            resPopulation.remove(p1);
            int p2 = (int) (Math.random() * nextPopulation.size() % nextPopulation.size());
            Chromosome c2 = nextPopulation.get(p2);
            nextPopulation.remove(p2);
            evaluate r2 = resPopulation.get(p2);
            resPopulation.remove(p2);
            /////选择机制

            if (r1.error <= 0 && r2.error > 0) {
                population.add(c1);
            } else if (r1.error > 0 && r2.error <= 0) {
                population.add(c2);
            } else if (r1.error <= 0 && r2.error <= 0) {
                if (r1.cost <= r2.cost) {
                    if (bestScore.cost > r1.cost) {
                        bestScore = r1;
                    }
                    population.add(c1);
                } else {
                    if (bestScore.cost > r2.cost) {
                        bestScore = r2;
                    }
                    population.add(c2);
                }
            } else {
                if (r1.error <= r2.error) {
                    population.add(c1);
                } else {
                    population.add(c2);
                }
            }


        }
    }

    private void mutation() {
        for (Chromosome chro : population) chro.mutation(pm);

    }

    private ArrayList<evaluate> calculateScore() {
        ArrayList<evaluate> output = new ArrayList<evaluate>();
        for (Chromosome chro : population) {
            boolean[] gene = chro.getGene();
            List<Integer> server = new ArrayList<Integer>();
            for (int i = 0; i < gene.length; i++) {
                if (gene[i] == true) {
                    server.add(i);
                }
            }
            evaluate sc = calC(server);
            output.add(sc);
        }
        return output;
    }

    //克隆一个群体
    private ArrayList<Chromosome> clone(ArrayList<Chromosome> pop) {
        ArrayList<Chromosome> pops = new ArrayList<Chromosome>();
        if (pop == null) return null;
        for (int i = 0; i < pop.size(); i++) pops.add(pop.get(i));
        return pops;
    }

    public void setPopulation(ArrayList<Chromosome> population) {
        this.population = population;
    }

    public void setGeneSize(int geneSize) {
        this.geneSize = geneSize;
    }

    public void setmaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    public void setpc(double pc) {
        this.pc = pc;
    }

    public void setpm(double pm) {
        this.pm = pm;
    }

    public evaluate getBestScore() {
        return bestScore;
    }

}
