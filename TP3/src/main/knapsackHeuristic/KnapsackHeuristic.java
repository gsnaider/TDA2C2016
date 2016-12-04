package knapsackHeuristic;

import java.util.BitSet;
import java.util.List;

import knapsack.Item;
import knapsack.KnapsackSolution;

public class KnapsackHeuristic {
	
	private List<Item> items;
	private Integer maxWeight;
	private Integer sumOfValues;
	private KnapsackSolution solution;

	private int[][] dynamicProgrammingMatrix;
	
	public KnapsackHeuristic(List<Item> items, Integer maxWeight) {
		this.items = items;
		this.maxWeight = maxWeight;
		
		this.sumOfValues = 0;
		
		for (Item item : items) {
			this.sumOfValues += item.getValue();
		}
		
		this.dynamicProgrammingMatrix = new int[items.size()+1][sumOfValues+1];

		Long startTimer = System.nanoTime();
		this.solve();
		long finalTime = System.nanoTime() - startTimer;
		System.out.println("Tardo "+finalTime);
		this.buildSolution(finalTime);
	}
	
	
	// Algoritmo de http://math.mit.edu/~goemans/18434S06/knapsack-katherine.pdf
//	private void solve() {
//
//		dynamicProgrammingMatrix[0][items.get(0).getValue()] = items.get(0).getWeight();
//
//		for (int i = 1; i < items.size(); i++) {
//			Integer currentWeight = items.get(i).getWeight();
//			Integer currentValue = items.get(i).getValue();
//			
//			for (int v = 0; v <= sumOfValues; v++) {				
//				if (v >= currentValue){					
//					dynamicProgrammingMatrix[i][v] = Math.min(this.getFromDP(i-1, v), this.getFromDP(i-1, v - currentValue,currentWeight ));
//				} else {
//					dynamicProgrammingMatrix[i][v] = this.getFromDP(i-1, v);
//				}
//			}
//		}
//	}
//	private Integer getFromDP(Integer i,Integer j){
//	return (dynamicProgrammingMatrix[i][j] == null) ? Integer.MAX_VALUE : dynamicProgrammingMatrix[i][j];
//}
//
//private Integer getFromDP(Integer i,Integer j,Integer currentWeight){
//	return (dynamicProgrammingMatrix[i][j] == null || dynamicProgrammingMatrix[i][j].equals(Integer.MAX_VALUE)) ? Integer.MAX_VALUE : dynamicProgrammingMatrix[i][j] + currentWeight;
//}
	
	private void solve() {

		Integer max = 0;
		
		for (int i = 1; i <= items.size();i++){
			int currentWeight = items.get(i-1).getWeight();
			int currentValue = items.get(i-1).getValue();
			
			for (int v = 1; v <= max+currentValue; v++){
				if (v > max) {
					this.dynamicProgrammingMatrix[i][v] = currentWeight + this.dynamicProgrammingMatrix[i-1][Math.max(0, v-currentValue)];
				} else {
					this.dynamicProgrammingMatrix[i][v] = Math.min(dynamicProgrammingMatrix[i-1][v], currentWeight+ dynamicProgrammingMatrix[i-1][Math.max(0, v-currentValue)]);
				}
			}
			
			max += currentValue;
			
		}
	}

	private void buildSolution(Long time) {
		Integer bestValue = null;
		BitSet bitset = new BitSet();
		
		for (int j = this.sumOfValues; j >= 0; j--){
			if (this.dynamicProgrammingMatrix[this.items.size()][j] <= this.maxWeight && bestValue == null){
					bestValue = j;
					for (int i = this.items.size(); i > 0; i--) {
						if (this.dynamicProgrammingMatrix[i][j] != this.dynamicProgrammingMatrix[i-1][j]){
							bitset.set(i);
							j-=items.get(i-1).getValue();
						}
					}
					break;
			}
		}
		
		this.solution = new KnapsackSolution(bitset, bestValue, time);
	}
	
	public KnapsackSolution getSolution() {
		return this.solution;
	}
	
}
