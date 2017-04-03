package com.cacheserverdeploy.algorithm;

import com.cacheserverdeploy.model.Evaluate;
import com.filetool.util.Chromosome;

import java.util.ArrayList;

public class GeneticAlgorithm {
    private ArrayList<Chromosome> population = new ArrayList<Chromosome>();
    private int popSize = 100; // 种群数量
    private int geneSize; // 基因长度编码
    private int maxIter = 100;
    private double pm = 0.05;
    private double pc = 0.8;
    private int consumptionNum; // 消费节点个数
    private Evaluate bestScore; //最好的情况

    public GeneticAlgorithm(int popSize, int geneSize, int maxIter,
                            double pm, double pc, int[] bandwidth) {
        super();
        this.popSize = popSize;
        this.geneSize = geneSize;  //基因长度
        this.maxIter = maxIter;
        this.pm = pm;
        this.pc = pc;
        this.consumptionNum = bandwidth.length;
        this.bestScore.setError(0.0f);
        this.bestScore.setCost(100000);
    }

    public Evaluate calculate() {
        int generation = 1;
        init();
        while (generation <= maxIter) {
            evolve();
            generation++;
        }
        // TODO
        return bestScore;
    }

    private void init() {
        /*int[] Roulette = new int[bandwidth.length];
        //轮盘赌进行初始化
        int sum_roulette = 0;
        for (int i = 0; i < geneSize; i++) {
            Roulette[i] = sum_roulette + bandwidth[i];
            sum_roulette = Roulette[i];
        }
        /////轮盘赌数组
        for (int i = 0; i < popSize; i++) {
            Chromosome chro = new Chromosome(geneSize, consumptionNum, Roulette);
            population.add(chro);
        }*/
    }

    private void evolve() {
//		ArrayList<Chromosome> childPopulation = new ArrayList<Chromosome>();
        ArrayList<Chromosome> pops;
        ArrayList<Evaluate> resPopulation;
        pops = clone(population);
        while (pops.size() > 0) {
            int p1 = (int) (Math.random() * pops.size() % pops.size());
            Chromosome c1 = pops.get(p1);
            pops.remove(p1);
            int p2 = (int) (Math.random() * pops.size() % pops.size());
            Chromosome c2 = pops.get(p2);
            pops.remove(p2);
            ///无放回的随机取样
            ArrayList<Chromosome> children = c1.genetic(c1, c2, pc);
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

    private void selected(ArrayList<Evaluate> resPopulation) {
        ArrayList<Chromosome> nextPopulation = new ArrayList<Chromosome>();
        nextPopulation = clone(population);
        population = null;
        while (nextPopulation.size() > 0) {
            int p1 = (int) (Math.random() * nextPopulation.size() % nextPopulation.size());
            Chromosome c1 = nextPopulation.get(p1);
            Evaluate r1 = resPopulation.get(p1);
            nextPopulation.remove(p1);
            resPopulation.remove(p1);
            int p2 = (int) (Math.random() * nextPopulation.size() % nextPopulation.size());
            Chromosome c2 = nextPopulation.get(p2);
            nextPopulation.remove(p2);
            Evaluate r2 = resPopulation.get(p2);
            resPopulation.remove(p2);
            /////选择机制

            if (r1.getError() <= 0 && r2.getError() > 0) {
                population.add(c1);
            } else if (r1.getError() > 0 && r2.getError() <= 0) {
                population.add(c2);
            } else if (r1.getError() <= 0 && r2.getError() <= 0) {
                if (r1.getCost() <= r2.getCost()) {
                    population.add(c1);
                } else {
                    population.add(c2);
                }
            } else if (r1.getError() > 0 && r2.getError() > 0) {
                if (r1.getError() <= r2.getError()) {
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

    private ArrayList<Evaluate> calculateScore() {
        ArrayList<Evaluate> output = new ArrayList<Evaluate>();
/*        for (Chromosome chro : population) {
            Evaluate sc = calC(chro);
            output.add(sc);
        }*/
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

    public Evaluate getBestScore() {
        return bestScore;
    }

}
