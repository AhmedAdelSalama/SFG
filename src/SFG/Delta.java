package SFG;

import java.util.LinkedList;
import java.util.Queue;

public class Delta {
	private LinkedList<LinkedList<Object[]>> nonLoops;
	private SFG.GainCalculator gainCalculator;
	private LinkedList<Queue<Integer>> loops;

	public Delta() {
		nonLoops = new LinkedList<>();
		loops = new LinkedList<>();
		gainCalculator = new SFG.GainCalculator();

	}

	public float calculateDelta(LinkedList<LinkedList<Object[]>> nonLoop, LinkedList<Queue<Integer>> loop) {
		float myDelta;
		nonLoops = new LinkedList<>();
		loops = new LinkedList<>();
		loops = loop;
		nonLoops = nonLoop;
		myDelta = (1) - sum();
		float tempDelta;
		for (int i = 0; i < nonLoops.size(); i++) {
			tempDelta = 1;
			for (int j = 0; j < nonLoops.get(i).size(); j++) {
				Object[] temp = nonLoop.get(i).get(j);
				tempDelta = tempDelta * (gainCalculator.calculateGain(temp));
			}
			if (nonLoops.get(i).size() % 2 == 0) {
				myDelta = myDelta + tempDelta;
			} else {
				myDelta = myDelta - tempDelta;
			}
		}
		return myDelta;

	}

	private float sum() {
		float sum = 0;
		for (int i = 0; i < loops.size(); i++) {
			Object[] temp = loops.get(i).toArray();
			sum = sum + gainCalculator.calculateGain(temp);
		}
		return sum;
	}
	public void setGraph(float[][] graph) {
		gainCalculator.setGraph(graph);
	}

}
