package SFG;
import java.util.LinkedList;
import java.util.Queue;


public class ForwardPathsGenerator {
	private float[][] graph;
	private LinkedList<Queue<Integer>> paths;
	private Queue<Integer> path;

	public ForwardPathsGenerator() {
		paths = new LinkedList<>();
		path = new LinkedList<>();
	}

	public LinkedList<Queue<Integer>> getForwardPaths(float[][] myGraph) {
		paths = new LinkedList<>();
		path = new LinkedList<>();
		this.graph = myGraph;
     	findPath(0);
        return paths;
	}

	private void findPath(int index) {
		if (index == graph.length - 1) {
			path.add(index+1);
			Queue<Integer> temp = new LinkedList<>();
			Queue<Integer> tempQ = new LinkedList<>();
			int size = path.size();
			for (int k = 0; k < size; k++) {
				temp.add(path.poll());
			}
			for (int k = 0; k < size; k++) {
				tempQ.add(temp.peek());
				path.add(temp.poll());
			}
			paths.add(tempQ);

			path.remove(index+1);
			path.remove(index );
			return;
		}
		int j;
		for (j = index + 1; j < this.graph.length; j++) {
			if (graph[index][j] != 0) {
				path.add(index +1);
				findPath(j);
				path.remove(index +1);
			}
		}
	}
}
