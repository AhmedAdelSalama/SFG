package SFG;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SignalFlowGraphHandler {
	private float[][] gainGraph;
    private LoopsGenerator loopsGenerator;
    private GainCalculator gainCalculator;
    private LoopsHandler loopsHandler;
    private Delta delta ;
    private int numOfNodes =0;

    public SignalFlowGraphHandler() {
    	loopsGenerator = new LoopsGenerator();
    	gainCalculator = new GainCalculator();
    	delta = new Delta();
    	loopsHandler = new LoopsHandler();

	}
	public void createGraph(int nodes) {
		numOfNodes = nodes;
		gainGraph = new float[nodes][nodes];
		for (int i = 0; i < nodes; i++) {
			for(int j=0;j< nodes ;j++) {
				gainGraph[i][j] = 0;
			}
		}
	}

	public float[][] getGainGraph() {
		return gainGraph;
	}
	
	public LinkedList<Queue<Integer>> getForwardPaths(float[][] graph) {
		LinkedList<Queue<Integer>> paths = new LinkedList<Queue<Integer>>();
			paths =	new ForwardPathsGenerator().getForwardPaths(graph);
		return paths;
	}

	public LinkedList<Queue<Integer>> getLoops(float[][] graph) {
		LinkedList<Stack<Integer>> stackLoops = loopsGenerator.getLoops(this.gainGraph);
		LinkedList<Queue<Integer>> loopsQueue = new LinkedList<>();
		for (Stack<Integer> stackLoop : stackLoops) {
			Queue<Integer> q = new LinkedList<>(stackLoop);
			loopsQueue.add(q);
		}
		return loopsQueue;
	}

	public LinkedList<LinkedList<Object[]>> getNonTouchingLoops(LinkedList<Queue<Integer>> loops) {
		return new NonTouchingLoops().getNonTouchingLoops(loops);
	}

	public float calculateDelta(LinkedList<LinkedList<Object[]>> nonLoop, LinkedList<Queue<Integer>> loop) {
		delta.setGraph(gainGraph);
		return delta.calculateDelta(nonLoop, loop);
	}

	public void addGain(int fromNode, int toNode, float gain) {
		gainGraph[fromNode-1][toNode-1] = gain;
	}

	public float calculateGain(Object[] q) {
		gainCalculator.setGraph(gainGraph);
		return gainCalculator.calculateGain(q);
	}
	
	public int numOFNodes() {
		return numOfNodes;
	}
	
	public void deleteGain(int fromNode, int toNode) {
		gainGraph[fromNode-1][toNode-1] = 0;
	}
	
	public LinkedList<LinkedList<Object[]>> getLoops(Queue<Integer> path, LinkedList<Queue<Integer>> list) {
		return loopsHandler.getNonTouchingPath(path,list);
	}
	
	public LinkedList<Queue<Integer>> getloopsForEachPath() {
		return loopsHandler.deltaLoops();
	}
	
	public double calculateTransferFunction() {
		double transferFn = 0;
	    LinkedList<Queue<Integer>> paths = getForwardPaths(gainGraph);
	    LinkedList<Queue<Integer>> loops = getLoops(gainGraph);
		float delta = calculateDelta(getNonTouchingLoops(loops), loops);
		for (Queue<Integer> path : paths) {
			LinkedList<LinkedList<Object[]>> loopPath = getLoops(path, loops);
			LinkedList<Queue<Integer>> loopPath2 = this.loopsHandler.deltaLoops();
			transferFn = transferFn + (calculateGain(path.toArray()) * calculateDelta(loopPath, loopPath2));
		}
		return transferFn/delta;
	}

}
