package com.filetool.util;

import com.cacheserverdeploy.deploy.Deploy;
import com.cacheserverdeploy.deploy.Deploy.Evaluate;

import java.util.ArrayList;

import static com.cacheserverdeploy.deploy.Deploy.calC;

public class GeneticAlgorithm {
    private ArrayList<Chromosome> population = new ArrayList<Chromosome>();
    private int popSize; // 种群数量
    private int geneSize; // 基因长度编码
    private int maxIter;
    private double pm = 0.07;
    private double pc = 0.35;
    private int consumptionNum; // 消费节点个数
    private int generation = 1;//当前遗传到第几代
    private Evaluate bestScore; //最好的情况
    private Chromosome bestChromosome;

    public GeneticAlgorithm(int popSize, int geneSize, int maxIter,
                            double pm, double pc) {
        super();
        this.popSize = popSize;
        this.geneSize = geneSize;  //基因长度
        this.maxIter = maxIter;
        this.pm = pm;
        this.pc = pc;
        this.bestScore.error = 0.0;
        this.bestScore.cost = 100000;
    }

    public GeneticAlgorithm(int popSize, int maxIter) {
        this.bestScore = new Evaluate();
        this.bestScore.cost = Integer.MAX_VALUE;
        this.bestScore.error = 10000.0;
        this.popSize = popSize;
        this.maxIter = maxIter;
    }

    public Evaluate calculate() {
        long startTime = System.currentTimeMillis();
        generation = 1;
        init();
        while (generation <= maxIter) {
            // 计时器
            long endTime = System.currentTimeMillis();
            float excTime = (float) (endTime - startTime) / 1000;
            if (excTime > 78) {
                return bestScore;
            }
            evolve();
            generation++;
            if (generation == 60) {
                intermediate();
            }
            if (generation == 70) {
                intermediate();
            }
            if (generation == 80) {
                intermediate();
            }
            if (generation == 90) {
                intermediate();
            }
            System.out.println("the times is:" + generation);
            System.out.println("the cost is:" + bestScore.cost);
        }
        return bestScore;
    }

    private void init() {
        //******************************************************//
        for (int i = 0; i < popSize; i++) {
            boolean[] bool = new boolean[Deploy.NN];
            ArrayList<Integer> choose1 = new ArrayList<Integer>();
            if (Math.random() < 0.8) {
                int cc = (int) Math.round(Math.random() * (Deploy.T_list.size() - 2) + 1);  //消费节点随机数
                while (choose1.size() < cc) {
                    int initNode = (int) Math.round(Math.random() * (Deploy.T_list.size() - 1));
                    int node = Deploy.T_list.get(initNode);

                    node = Deploy.randwalk(node);

                    if (choose1.indexOf(node) == -1) {
                        choose1.add(node);
                    }

                }
            } else {
                int cc = (int) Math.round(Math.random() * (Deploy.T_list.size() - 2) + 1);  //消费节点随机数
                while (choose1.size() < cc) {
                    int initNode = (int) Math.round(Math.random() * (Deploy.T_list.size() - 1));
//                    int node = Deploy.T_list.get(initNode);
                    if (choose1.indexOf(initNode) == -1) {
                        choose1.add(initNode);
                    }

                }

            }

            Deploy.emptyedge();
            for (int k = 0; k < choose1.size(); k++) {
                bool[choose1.get(k)] = true;
            }
            Chromosome chro = new Chromosome(bool);
            population.add(chro);
        }
    }


    private void evolve() {
//		ArrayList<Chromosome> childPopulation = new ArrayList<Chromosome>();
        ArrayList<Chromosome> pops = new ArrayList<Chromosome>();
        ArrayList<Evaluate> resPopulation = new ArrayList<Evaluate>();
        pops = clone(population);
        while (pops.size() > 0) {
            int p1 = (int) Math.round(Math.random() * (pops.size() - 1));
            Chromosome c1 = pops.get(p1);
            pops.remove(p1);
            int p2 = (int) Math.round(Math.random() * (pops.size() - 1));
            Chromosome c2 = pops.get(p2);
            pops.remove(p2);
            ///无放回的随机取样
            ArrayList<Chromosome> children = Chromosome.genetic2(c1, c2, pc);
            if (children != null) {
                for (Chromosome child : children) {
                    population.add(child);
                }
            }
        }
        mutation();
        ArrayList<Evaluate> resultP = calculateScore();
        selected(resultP);

//		calculateScore();
    }

    private void selected(ArrayList<Evaluate> resultP) {
        ArrayList<Chromosome> nextPopulation = new ArrayList<Chromosome>();
        nextPopulation = clone(population);
        population = new ArrayList<Chromosome>();
        while (nextPopulation.size() > 0) {
            int p1 = (int) (Math.random() * nextPopulation.size() % nextPopulation.size());
            Chromosome c1 = nextPopulation.get(p1);
            Evaluate r1 = resultP.get(p1);
            nextPopulation.remove(p1);
            resultP.remove(p1);
            int p2 = (int) (Math.random() * nextPopulation.size() % nextPopulation.size());
            Chromosome c2 = nextPopulation.get(p2);
            nextPopulation.remove(p2);
            Evaluate r2 = resultP.get(p2);
            resultP.remove(p2);
            /////选择机制

            if (r1.error <= 0 && r2.error > 0) {
                population.add(c1);
            } else if (r1.error > 0 && r2.error <= 0) {
                population.add(c2);
            } else if (r1.error <= 0 && r2.error <= 0) {
                if (r1.cost <= r2.cost) {
                    if (bestScore.cost > r1.cost) {
                        bestScore = r1;
                        bestChromosome = c1;
                    }
                    population.add(c1);
                } else {
                    if (bestScore.cost > r2.cost) {
                        bestScore = r2;
                        bestChromosome = c2;
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

    private void intermediate() {
        ArrayList<Evaluate> resultP = calculateScore();
        selected(resultP);

        while (population.size() < popSize) {
            boolean[] bool = new boolean[Deploy.NN];
            ArrayList<Integer> choose = new ArrayList<Integer>();
            int cc = (int) Math.round(Math.random() * (Deploy.T_list.size() - 2) + 1);  //消费节点随机数
            while (choose.size() < cc) {
//                int initNode = (int) Math.round(Math.random() * (Deploy.T_list.size() - 1));
//
//                if (choose.indexOf(initNode) == -1) {
//                    choose.add(initNode);
//                }
                int initNode = (int) Math.round(Math.random() * (Deploy.T_list.size() - 1));
                int node = Deploy.T_list.get(initNode);

                node = Deploy.randwalk(node);

                if (choose.indexOf(node) == -1) {
                    choose.add(node);
                }

            }
            for (int k = 0; k < choose.size(); k++) {
                bool[choose.get(k)] = true;
            }
            Chromosome chro = new Chromosome(bool);
            population.add(chro);
        }
    }


    private ArrayList<Evaluate> calculateScore() {
        ArrayList<Evaluate> output = new ArrayList<Evaluate>();
        for (Chromosome chro : population) {
            boolean[] gene = chro.getGene();
            ArrayList<Integer> server = new ArrayList<Integer>();
            for (int i = 0; i < gene.length; i++) {
                if (gene[i] == true) {
                    server.add(i);
                }
            }
            Evaluate sc = calC(server);
            output.add(sc);
        }
        return output;
    }

    //克隆一个群体
    private ArrayList<Chromosome> clone(ArrayList<Chromosome> pop) {
        ArrayList<Chromosome> pops = new ArrayList<Chromosome>();
        if (pop == null) return null;
        for (Chromosome Chro : pop) pops.add(Chro);
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

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    public void setBestChromosome(Chromosome bestChromosome) {
        this.bestChromosome = bestChromosome;
    }
}
