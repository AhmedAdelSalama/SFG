package SFG;

import java.util.LinkedList;
import java.util.Queue;

public class NonTouchingLoops {
	private LinkedList<LinkedList<Object[]>> nonTouching;
	private LinkedList<Queue<Integer>> loops;

	public NonTouchingLoops() {
		nonTouching = new LinkedList<>();
		loops = new LinkedList<>();
	}

	public LinkedList<LinkedList<Object[]>> getNonTouchingLoops(LinkedList<Queue<Integer>> loop) {
		nonTouching = new LinkedList<>();
		loops = new LinkedList<>();
		this.loops = loop;
        int max = loops.size();
		int count = 2;
       while (count <= max) {
			rec(count);
			count++;
		}
		return nonTouching;
	}

	public boolean compare(Object[] loop1, Object[] loop2) {
		for (Object value : loop1) {
			for (Object o : loop2) {
				int l1 = (int) value;
				int l2 = (int) o;

				if (l1 == l2) {
					return false;
				}
			}
		}
		return true;

	}

	private void rec(int count) {
		Object[] loop1;
		Object[] loop2;
		for (int i = 0; i < loops.size(); i++) {
			int remove = 1;
			loop1 = loops.get(i).toArray();
			LinkedList<Object[]> temp = new LinkedList<>();
			temp.add(loop1);
			for (int j = i + 1; j < loops.size(); j++) {
				loop2 = loops.get(j).toArray();
				if (compare(loop1, loop2)) {
					boolean flag = false;
					for (int l = 1; l < temp.size(); l++) {
						if (!compare(loop2, temp.get(l))) {
							flag = true;
						}
					}
					if (!flag) {
						temp.add(loop2);
					}
					if (temp.size() == count) {
						LinkedList<Object[]> tempNon = new LinkedList<>(temp);
						nonTouching.add(tempNon);
						temp.remove(temp.size() - 1);

					}
				}
				if (j == loops.size() - 1) {
					if (remove < temp.size()) {
						temp.remove(remove);
						remove++;
						j = i + 1;
					}
				}
			}
		}
	}


}
