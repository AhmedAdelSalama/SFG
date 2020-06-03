package SFG;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OverallTransferFunctionController implements Initializable {

	@FXML
	ListView forwardPathsList = new ListView();

	@FXML
	ListView deltasList = new ListView();
	@FXML
	ListView loopsList = new ListView();

	@FXML
	ListView nonTouchingLoopsList = new ListView();

	@FXML
	Label transferFnLabel = new Label();
	@FXML
	Label deltaILabel = new Label();
	@FXML
	Label pathLabel = new Label();
	@FXML
	Label loopLabel = new Label();
	@FXML
	Label deltaLabel = new Label();
	@FXML
	Label nonTouchingLabel = new Label();
	@FXML
	Button NewButton = new Button();

	@FXML
	TextField transferFunctionTextField = new TextField();
	@FXML
	TextField deltaTextField = new TextField();
	GuiHandler guiHandler;

	public void NewGraph(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("SignalFlowGraph.fxml"));
		Scene scene = new Scene(loader);
		Stage app = (Stage)((Node)event.getSource()).getScene().getWindow();
		app.setScene(scene);
		app.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiHandler gg = guiHandler.getOneClass();
		//adding forward paths to listView
		LinkedList<Queue<Integer>> path = gg.signalFlowGraphHandler.getForwardPaths(gg.signalFlowGraphHandler.getGainGraph());
		ArrayList<Queue<Integer>> pathItem = new ArrayList<>();
		for (Queue<Integer> integers : path) {
			pathItem.add(integers);
			ObservableList<Queue<Integer>> items = FXCollections.observableArrayList(pathItem);
			forwardPathsList.setItems(items);
		}

		//adding loops to listView
		LinkedList<Queue<Integer>> loops = gg.signalFlowGraphHandler.getLoops(gg.signalFlowGraphHandler.getGainGraph());
		ArrayList<Queue<Integer>> loopItem = new ArrayList<>();
		for (Queue<Integer> iterator : loops) {
			loopItem.add(iterator);
			ObservableList<Queue<Integer>> items = FXCollections.observableArrayList(loopItem);
			loopsList.setItems(items);
		}
		//adding deltas to listView
		ArrayList<Float> deltaItem = new ArrayList<>();
		for (Queue<Integer> integers : path) {
			LinkedList<LinkedList<Object[]>> loopPath = gg.signalFlowGraphHandler.getLoops(integers, loops);
			deltaItem.add(gg.signalFlowGraphHandler.calculateDelta(loopPath, gg.signalFlowGraphHandler.getloopsForEachPath()));
			ObservableList<Float> items = FXCollections.observableArrayList(deltaItem);
			deltasList.setItems(items);
		}
		//adding non-touching loops to listView
		LinkedList<LinkedList<Object[]>> non = gg.signalFlowGraphHandler.getNonTouchingLoops(loops);
		ArrayList<LinkedList<Queue>> lists = new ArrayList<>();
		for (LinkedList<Object[]> objects : non) {
			LinkedList<Queue> q2 = new LinkedList<>();
			for (Object[] obj : objects) {
				Queue q = new LinkedList(Arrays.asList(obj));
				q2.add(q);
			}
			lists.add(q2);
			ObservableList<LinkedList<Queue>> items = FXCollections.observableArrayList(lists);
			nonTouchingLoopsList.setItems(items);
		}
		//calculating delta
		String delta = String
				.valueOf(gg.signalFlowGraphHandler.calculateDelta(gg.signalFlowGraphHandler.getNonTouchingLoops(gg.signalFlowGraphHandler.getLoops(gg.signalFlowGraphHandler.getGainGraph())),
						gg.signalFlowGraphHandler.getLoops(gg.signalFlowGraphHandler.getGainGraph())));
		deltaTextField.setText(delta);
		//calculating overall TF
		String tf = String.valueOf(gg.signalFlowGraphHandler.calculateTransferFunction());
		if(delta.equals("0.0")) {
			transferFunctionTextField.setText("Infinity");
		}else {
		transferFunctionTextField.setText(tf);
		}
	}
}
