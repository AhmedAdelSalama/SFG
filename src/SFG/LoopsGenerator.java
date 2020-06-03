package SFG;
import java.util.LinkedList;
import java.util.Stack;

public class LoopsGenerator {
	private float[][] graph;
	private LinkedList<Stack<Integer>> loopsList;
	private int count;
	private Stack<Integer> loopsStack;

	public LoopsGenerator() {
		count = 0;
		loopsStack = new Stack<>();
		loopsList = new LinkedList<>();
	}

	public LinkedList<Stack<Integer>> getLoops(float[][] Matrix) {
		loopsStack = new Stack<>();
		loopsList = new LinkedList<>();
		this.graph = new float[Matrix.length][Matrix.length];
		this.graph = Matrix;
		for (count = 0; count < Matrix.length; count++) {
			for (int column = 0; column < Matrix.length; column++) {
				if (Matrix[count][column] == 0) {
					continue;
				} else if (count == column && Matrix[count][column] != 0) {
					loopsStack.clear();
					loopsStack.push(count);
					loopsStack.push(count);
					Stack<Integer> temp = new Stack<>();
					Object[] stack = new Object[loopsStack.size()];
					stack = loopsStack.toArray();
					for (Object o : stack) {
						temp.add((Integer) o + 1);
					}
					loopsList.add(temp);
					loopsStack.pop();
				} else {
					if (!loopsStack.contains(count)) {
						loopsStack.push(count);
					}
					if (!loopsStack.contains(column)) {
						loopsStack.push(column);
						loops(column);
					}
				}
			}
			if (!loopsStack.empty()) {
				loopsStack.pop();
			}
		}
		SFG.LoopsHelper loopsHelper = new SFG.LoopsHelper();
		loopsList = loopsHelper.removeAdditional(loopsList);

		return loopsList;
	}
	private void loops(int j) {
		for (int i = 0; i < graph.length; i++) {
			if(graph[j][i]==0){
				continue;
			}
			else if (i == count) {
				loopsStack.push(count);
				Stack<Integer> temp = new Stack<>();
				Object[] stack = new Object[loopsStack.size()];
				stack = loopsStack.toArray();
				for (Object o : stack) {
					temp.add((Integer) o + 1);
				}
				loopsList.add(temp);
				loopsStack.pop();
			} else if (!loopsStack.contains(i)) {
				loopsStack.push(i);
				loops(i);
			}
		}
		if (!loopsStack.empty()) {
			loopsStack.pop();
		}
	}

}
