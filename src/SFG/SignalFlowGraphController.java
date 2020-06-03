package SFG;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class SignalFlowGraphController {

	public Label label;
	@FXML
	TextField numOfNodes = new TextField();
	
	@FXML
	Button okButton = new Button();

	public void setNumOfNodes(ActionEvent event) {
		try {
			GuiHandler g = GuiHandler.getOneClass();
			int num = Integer.parseInt(numOfNodes.getText());
			g.signalFlowGraphHandler.createGraph(Math.abs(num));
			GuiHandler.linesWithGains = new LinkedList<>();
			Parent loader = FXMLLoader.load(getClass().getResource("Nodes.fxml"));
			Scene scene = new Scene(loader);
			Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app.setScene(scene);
			app.show();
		}catch (NumberFormatException e){
			System.out.println("Error! "+numOfNodes.getText()+" is not a positive integer.");
		}catch (IOException ignored){
			System.out.println("Error when loading Nodes.fxml");
		}
	}
	public void numIsSet(ActionEvent event) {
		setNumOfNodes(event);
	}
}
