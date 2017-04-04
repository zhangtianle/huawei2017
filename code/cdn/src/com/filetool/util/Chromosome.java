package com.filetool.util;

import com.cacheserverdeploy.deploy.Deploy.evaluate;

import java.util.ArrayList;
import java.util.Arrays;

import static com.cacheserverdeploy.deploy.Deploy.calC;

/**
 *
 * @Description: 基因遗传染色体
 *
 */
public class Chromosome {
	private boolean[] gene; //基因序列
	private evaluate score;  //所得分数
	private int geneLen; //基因长度

	public void getScore(evaluate score){
		this.score = score;
	}

	public Chromosome(boolean[] gene){
		this.gene = gene;
	}


	private void initGeneSize(int size){
		if(size <= 0){
			return;
		}
		gene = new boolean[size];
		for(int i=0;i<size;i++) gene[i] = false;
	}

	public void mutation(double pm){
		int size = gene.length;
		for(int i=0;i<size;i++){
			if (Math.random()<pm) gene[i] = !gene[i];
		}
	}

	public static Chromosome clone(final Chromosome c){
		if(c == null || c.gene == null)
			return null;
		Chromosome copy;
		copy = new Chromosome(Arrays.copyOf(c.gene, c.gene.length));
		return copy;
	}

	public static ArrayList<Chromosome> genetic(Chromosome p1, Chromosome p2, double pc){
		if(p1==null&&p2==null){
			return null;
		}
		if(p1.gene==null || p2.gene==null){
			return null;
		}
		if(p1.gene.length != p2.gene.length){
			return null;
		}
		Chromosome c1 = clone(p1);
		Chromosome c2 = clone(p2);
		int size = c1.gene.length;
		int cut1 = (int) (Math.random()*size) % size;
		int cut2 = (int) (Math.random()*size) % size;
		int minCut = cut1 > cut2 ? cut2 : cut1;
		int maxCut = cut1 > cut2 ? cut1 : cut2;
		if (Math.random()<pc){
			for(int i=minCut;i<=maxCut;i++){
				boolean t = c1.gene[i];
				c1.gene[i] = c2.gene[i];
				c2.gene[i] = t;
			}
		}
		ArrayList<Chromosome> list = new ArrayList<Chromosome>();
		list.add(c1);
		list.add(c2);
		return list;
	}
	public static evaluate getScore(Chromosome p){
		ArrayList<Integer> sever = new ArrayList<Integer>();
		for(int i=0;i<p.geneLen;i++){
			if (p.gene[i]==true) sever.add(i);
		}
		evaluate score = calC(sever);
		return score;

	}

	public boolean[] getGene() {
		return gene;
	}

	public void setGene(boolean[] gene) {
		this.gene = gene;
	}

	public evaluate getScore() {
		return score;
	}

	public void setScore(evaluate score) {
		this.score = score;
	}

	public int getGeneLen() {
		return geneLen;
	}

	public void setGeneLen(int geneLen) {
		this.geneLen = geneLen;
	}
}
