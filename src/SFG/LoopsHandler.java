package SFG;

import java.util.LinkedList;
import java.util.Queue;

public class LoopsHandler {

	private LinkedList<LinkedList<Object []>> nonTouchingPath;
	private LinkedList<Queue<Integer>> forwardPathLoops;
	private LinkedList<Queue<Integer>> getNonTouchingPath;
	private SFG.NonTouchingLoops nonTouching;
	public LoopsHandler() {
		forwardPathLoops = new LinkedList<>();
		nonTouchingPath = new LinkedList<>();
		getNonTouchingPath = new LinkedList<>();
		nonTouching = new SFG.NonTouchingLoops();
	}
	public LinkedList<Queue<Integer>> deltaLoops(){
		return getNonTouchingPath;
	}

	public LinkedList<LinkedList<Object []>> getNonTouchingPath(Queue<Integer> path,LinkedList<Queue<Integer>> list){
		forwardPathLoops = new LinkedList<>();
		nonTouchingPath = new LinkedList<>();
		getNonTouchingPath = new LinkedList<>();
		nonTouching = new SFG.NonTouchingLoops();
		forwardPathLoops = list;
		Object[] loop1 = path.toArray();
		Object[] loop2; 
		for(int i = 0; i< forwardPathLoops.size(); i++) {
			loop2 = forwardPathLoops.get(i).toArray();
			if(nonTouching.compare(loop1, loop2)) {
				getNonTouchingPath.add(forwardPathLoops.get(i));
			}
		}
		nonTouchingPath = nonTouching.getNonTouchingLoops(getNonTouchingPath);
		return nonTouchingPath;
	}

}
