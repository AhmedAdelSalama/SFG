package SFG;

public class GainCalculator {
	private float gain;
	private float[][] graph;
	
	public GainCalculator() {
		gain = 1;
	}

	public float calculateGain(Object[] arr) {
		gain =1;
		int node1 = (int) arr[0],node2;
		for (int i = 1; i < arr.length; i++) {
			node2 = (int) arr[i];
			gain = gain * graph[node1-1][node2-1];
			node1 = node2;
		}
		return gain;
	}

	public void setGraph(float[][] graph) {
		this.graph = new float[graph.length][graph.length];
		this.graph = graph;
	}
}
