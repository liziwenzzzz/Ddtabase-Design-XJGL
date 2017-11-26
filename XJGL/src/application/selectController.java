package application;

import java.io.IOException;

import com.sun.javafx.robot.impl.FXRobotHelper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class selectController {
	@FXML
	private Button searchButton;
	@FXML
	private Button insertButton;

	// Event Listener on Button[#searchButton].onAction
	@FXML
	public void SearchPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("OverView.fxml"));
		ObservableList<Stage> stages=FXRobotHelper.getStages();
		Stage stage = stages.get(0);
		Scene scene = new Scene(root);
		stage.setTitle("select");
		stage.setScene(scene);
		stage.show();
	}

	// Event Listener on Button[#insertButton].onAction
	@FXML
	public void InsertPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("insert.fxml"));
		ObservableList<Stage> stages=FXRobotHelper.getStages();
		Stage stage = stages.get(0);
		Scene scene = new Scene(root);
		stage.setTitle("insert");
		stage.setScene(scene);
		stage.show();
	}
}
