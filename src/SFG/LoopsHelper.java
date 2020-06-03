package SFG;

import java.util.LinkedList;
import java.util.Stack;

public class LoopsHelper {

    private LinkedList<Stack<Integer>> stacksOfLoops;

    public LoopsHelper() {
        stacksOfLoops = new LinkedList<>();
    }

    public LinkedList<Stack<Integer>> removeAdditional(LinkedList<Stack<Integer>> stacksOfLoops){
        this.stacksOfLoops = new LinkedList<>();
        this.stacksOfLoops = stacksOfLoops;
        for(int i = 0; i< this.stacksOfLoops.size(); i++) {
            int flag = 0;
            for(int j = i+1; j< this.stacksOfLoops.size(); j++) {
                flag = 0;
                if(this.stacksOfLoops.get(i).size()!= this.stacksOfLoops.get(j).size()) {
                    continue;
                }else {
                    for(int iter = 0; iter< this.stacksOfLoops.get(i).size(); iter++) {
                        if(this.stacksOfLoops.get(j).contains(this.stacksOfLoops.get(i).get(iter))) {
                            flag++;
                        }
                    }
                    if(flag == this.stacksOfLoops.get(i).size()&& flag == this.stacksOfLoops.get(j).size()) {
                        this.stacksOfLoops.remove(j);
                        j--;
                    }
                }
            }
        }
        return this.stacksOfLoops;
    }

}
