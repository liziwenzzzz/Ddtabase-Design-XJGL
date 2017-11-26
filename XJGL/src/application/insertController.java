package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.sun.javafx.robot.impl.FXRobotHelper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class insertController {
	@FXML
	private TextField text1;
	@FXML
	private TextField text2;
	@FXML
	private TextField text3;
	@FXML
	private TextField text4;
	@FXML 
	private TextField text5;
	@FXML
	private Button infoButton;
	@FXML
	private Button returnButton1;
	@FXML
	private TextField text6;
	@FXML
	private TextField text7;
	@FXML
	private TextField text8;
	@FXML
	private Button TransButton;
	@FXML
	private Button returnButton2;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/mydb"
			+ "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	static final String USER = "root";
	static final String PASS = "lzw19971206";

	// Event Listener on Button[#infoButton].onAction
	@FXML
	public void insertInfo(ActionEvent event) throws SQLException, ClassNotFoundException {
		int num = Integer.parseInt(text1.getText());
		String name = text2.getText();
		String sex = text3.getText();
		int birthday = Integer.parseInt(text4.getText());
		int classno = Integer.parseInt(text5.getText());

		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = String.format("insert  into student values(%d,'%s','%s',%d,%d);", num, name, sex, birthday,
					classno);
			stmt.executeUpdate(sql);
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("信息提示对话框");
			alert.setHeaderText("插入成功");
			alert.setContentText("you have already insert it successly");
			alert.showAndWait();
		} catch (MySQLIntegrityConstraintViolationException e) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("信息提示对话框");
			alert.setHeaderText("插入失败");
			alert.setContentText("Duplicate entry '"+num+"' for key 'PRIMARY'");
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("信息提示对话框");
			alert.setHeaderText("插入失败");
			alert.setContentText("wrong input！!!");
			alert.showAndWait();
		}finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	// Event Listener on Button[#returnButton1].onAction
	@FXML
	public void selectPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("select.fxml"));
		ObservableList<Stage> stages=FXRobotHelper.getStages();
		Stage stage = stages.get(0);
		Scene scene = new Scene(root);
		stage.setTitle("select");
		stage.setScene(scene);
		stage.show();
	}

	// Event Listener on Button[#TransButton].onAction
	@FXML
	public void insertTrans(ActionEvent event) throws SQLException, ClassNotFoundException {
		int num = Integer.parseInt(text6.getText());
		int course=Integer.parseInt(text7.getText());
		int trans=Integer.parseInt(text8.getText());
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = String.format("insert into transcript values(%d,-1,%d,%d);",trans,num,course);
			stmt.executeUpdate(sql);
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("信息提示对话框");
			alert.setHeaderText("插入成功");
			alert.setContentText("you have already insert it successly");
			alert.showAndWait();
		} catch (MySQLIntegrityConstraintViolationException e) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("信息提示对话框");
			alert.setHeaderText("插入失败");
			alert.setContentText("FOREIGN or PRIMARY fail");
			alert.showAndWait();
		} catch (ClassNotFoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("信息提示对话框");
			alert.setHeaderText("插入失败");
			alert.setContentText("wrong input！!!");
			alert.showAndWait();
		}finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
}
