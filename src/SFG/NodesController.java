package SFG;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class NodesController implements Initializable {

	@FXML
	TextField fromTextField = new TextField();
	@FXML
	TextField toTextField = new TextField();
	@FXML
	TextField gainTextField = new TextField();
	@FXML
	Button solveButton = new Button();
	@FXML
	Button clearButton = new Button();
	@FXML
	Button addButton = new Button();
	@FXML
	TextField fromTextField2 = new TextField();
	@FXML
	TextField toTextField2 = new TextField();
	@FXML
	Label from = new Label();
	@FXML
	Label to = new Label();
	@FXML
	Label from1 = new Label();
	@FXML
	Label to1 = new Label();
	@FXML
	Label gain = new Label();
	@FXML
	Canvas can = new Canvas();
	private LinkedList<Integer> graphNodes;

	public void drawGain() {
		try {
			//a data structure that holds the start and the end of the line between two nodes, and the gain as well.
			Object[] shape = new Object[3];
			//an object that guarantees there is only one graph to deal with
			GuiHandler guiHandler = GuiHandler.getOneClass();
			int node1 = Integer.parseInt(fromTextField.getText());
			int node2 = Integer.parseInt(toTextField.getText());
			float gain = Float.valueOf(gainTextField.getText());
			//validating the input
			if (node1 <= guiHandler.signalFlowGraphHandler.numOFNodes() && node2 <= guiHandler.signalFlowGraphHandler.numOFNodes() && node1 > 0 && node2 > 0 && gain != 0) {
				guiHandler.signalFlowGraphHandler.addGain(node1, node2, gain);
				shape[0] = node1;
				shape[1] = node2;
				shape[2] = guiHandler.signalFlowGraphHandler.getGainGraph()[node1 - 1][node2 - 1];
				//store data for the later calculation using Mason
				GuiHandler.linesWithGains.add(shape);
				//drawing the line with gain
				drawShapes(GuiHandler.linesWithGains.indexOf(shape));
			}
		}catch (NumberFormatException e){
			System.out.println("invalid positive integer!");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		drawGraph();
	}
	//clears a specific gain
	public void clearGain() {
		GuiHandler gg = GuiHandler.getOneClass();
		GraphicsContext g = can.getGraphicsContext2D();
		int node1 = Integer.parseInt(fromTextField2.getText());
		int node2 = Integer.parseInt(toTextField2.getText());
		gg.signalFlowGraphHandler.deleteGain(node1, node2);
		for (int i = 0; i < GuiHandler.linesWithGains.size(); i++) {
			if ((node1 == (int) GuiHandler.linesWithGains.get(i)[0]) && (node2 == (int) GuiHandler.linesWithGains.get(i)[1])) {
				GuiHandler.linesWithGains.remove(i);
				break;
			}
		}
		//redraw other data
		g.clearRect(0, 0, 1062, 800);
		drawGraph();
	}


	public void calculateOverallTF(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("OverallTransferFunction.fxml"));
		Scene scene = new Scene(loader);
		Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene.getStylesheets().add(getClass().getResource("listStyle.css").toExternalForm());
		app.setScene(scene);
		app.show();
	}
	//a function to draw graph according to specified number of nodes
	private void drawGraph(){
		GraphicsContext g = can.getGraphicsContext2D();
		int nodesNumber = GuiHandler.getOneClass().signalFlowGraphHandler.numOFNodes();
		graphNodes = new LinkedList<>();
		int move = ((int) can.getWidth()) / nodesNumber;
		int spacing = 10;
		g.setLineWidth(3);
		for (int i = 0; i < nodesNumber; i++) {
			g.setLineWidth(3);
			if (i == 0 ) {
				g.setLineWidth(1);
				g.setFill(Color.BLACK);
				g.strokeText("C(s)",spacing-5 , 175);
				g.setLineWidth(2);
				g.setStroke(Color.BLACK);
				g.strokeLine(spacing+20, 175, spacing+30, 175);
				spacing+=30;
				g.setStroke(Color.BLACK);
				g.setFill(Color.RED);
				g.strokeOval(spacing, 150, 50, 50);
				g.fillOval(spacing, 150, 50, 50);
				g.setLineWidth(2);
				g.strokeText(String.valueOf(i + 1), spacing + 20, 180);
			}else if(i == nodesNumber - 1){
				g.setStroke(Color.BLACK);
				g.setFill(Color.RED);
				g.strokeOval(spacing, 150, 50, 50);
				g.fillOval(spacing, 150, 50, 50);
				g.setLineWidth(2);
				g.strokeText(String.valueOf(i + 1), spacing + 20, 180);
				g.setLineWidth(2);
				g.setStroke(Color.BLACK);
				g.strokeLine(spacing+50, 175, spacing+75, 175);
				g.setLineWidth(1);
				g.strokeText("Y(s)",spacing+80 , 175);
				g.setFill(Color.BLACK);
				g.setStroke(Color.BLACK);
			}
			else {
				g.setStroke(Color.BLACK);
				g.setFill(Color.YELLOW);
				g.strokeOval(spacing, 150, 50, 50);
				g.fillOval(spacing, 150, 50, 50);
				g.setLineWidth(2);
				g.strokeText(String.valueOf(i + 1), spacing + 20, 180);
			}
			graphNodes.add(spacing);
			spacing = move + spacing;
		}

		for(int j = 0; j< GuiHandler.linesWithGains.size(); j++) {
			drawShapes(j);
		}
	}

	//a function helps to draw the lines between nodes
	private void drawShapes(int index) {
		GuiHandler.getOneClass();
		int node1 = (int) GuiHandler.linesWithGains.get(index)[0];
		int node2 = (int) GuiHandler.linesWithGains.get(index)[1];
		float gain = (float) GuiHandler.linesWithGains.get(index)[2];
		GraphicsContext g = can.getGraphicsContext2D();
		double radius = (graphNodes.get(node1 - 1) + graphNodes.get(node2 - 1)) / 2;
		double y = graphNodes.get(node2 - 1) - graphNodes.get(node1 - 1);
		g.setLineWidth(3);
		if (node2 - node1 == 1) {
			double x1 = graphNodes.get(node1 - 1) + (y / 2);
			double[] x = new double[] { x1 + 30, x1 + 5, x1 + 5 };
			double y1 = 150 + 25;
			double[] y2 = new double[] { y1, y1 - 7, y1 + 7 };
			g.setStroke(Color.BLACK);
			g.strokeLine(graphNodes.get(node1 - 1) + 50, 175, graphNodes.get(node2 - 1), 175);
			g.setLineWidth(1);
			g.strokeText(String.valueOf(gain), radius, 155);
			g.setFill(Color.BLACK);
			g.fillPolygon(x, y2, 3);
			g.setStroke(Color.GAINSBORO);
			g.setLineWidth(3);
			g.setStroke(Color.BLACK);
		} else if (node1 == node2) {
			double x1 = graphNodes.get(node1 - 1) + (y / 2);
			double[] x = new double[] { x1 + 30, x1 + 10, x1 + 10 };
			double y1 = 98;
			double[] y2 = new double[] { y1, y1 - 5, y1 + 10 };
			g.setLineWidth(3);
			g.setStroke(Color.YELLOWGREEN);
			g.strokeOval(graphNodes.get(node1 - 1), 98, 50, 50);
			g.setLineWidth(1);
			g.strokeText(String.valueOf(gain), radius + 10, 87);
			g.setFill(Color.YELLOWGREEN);
			g.fillPolygon(x, y2, 3);
			g.setLineWidth(3);
		} else if (node2 > node1) {
			double x1 = graphNodes.get(node1 - 1) + (y / 2);
			double[] x = new double[] { x1 + 30, x1 + 5, x1 + 5 };
			double y1 = 150 - (y / 6);
			double[] y2 = new double[] { y1, y1 - 7, y1 + 7 };
			g.setStroke(Color.GOLD);
			g.strokeArc(graphNodes.get(node1 - 1) + 25, 150 - (y / 6), y, y / 3, 0, 180, ArcType.OPEN);
			g.setLineWidth(2);
			g.strokeText(String.valueOf(gain), radius, (150 - (y / 6)) - 10);

			if ((150 - (y / 6)) - 10 <= can.getLayoutY()) {
				g.strokeText(String.valueOf(gain), radius, can.getLayoutY() + 10);
			}
			g.setFill(Color.GOLD);
			g.fillPolygon(x, y2, 3);
			g.setStroke(Color.GOLD);
			g.setLineWidth(3);
		} else {
			double x1 = graphNodes.get(node1 - 1) + (y / 2);
			double[] x = new double[] { x1 + 30, x1 + 30, x1 + 10 };
			double y1 = 150 + (-y / 6) + 50;
			double[] y2 = new double[] { y1 - 7, y1 + 7, y1 };
			g.setStroke(Color.ROYALBLUE);
			g.strokeArc(graphNodes.get(node2 - 1) + 25, 150 - (-y / 6) + 50, -y, -y / 3, 0, -180, ArcType.OPEN);
			g.setLineWidth(2);
			g.strokeText(String.valueOf(gain), radius, 150 + (-y / 6) + 70);
			g.setFill(Color.ROYALBLUE);
			g.fillPolygon(x, y2, 3);
			g.setStroke(Color.ROYALBLUE);
			g.setLineWidth(3);
		}
	}

}
