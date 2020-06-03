package SFG;

import java.util.LinkedList;

public class GuiHandler {

	SFG.SignalFlowGraphHandler signalFlowGraphHandler;
	static LinkedList<Object[]> linesWithGains = null;
	private static GuiHandler guiHandler = null;
	private GuiHandler() {
		signalFlowGraphHandler = new SFG.SignalFlowGraphHandler();
	}
	
	public static GuiHandler getOneClass() {
		if(guiHandler ==(null)) {
			guiHandler =new GuiHandler();
			linesWithGains = new LinkedList<>();
		}
		return guiHandler;
	}
}
