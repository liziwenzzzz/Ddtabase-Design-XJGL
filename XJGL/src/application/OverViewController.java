package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.sun.javafx.robot.impl.FXRobotHelper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OverViewController {
	@FXML
	private Button but1;
	@FXML
	private Button but2;
	@FXML
	private Button but3;
	@FXML
	private TextArea textarea;
	@FXML
	private TextField textfield;
	@FXML
	private Button returnButton;
	@FXML
	private Button but4;

	@FXML
	private Button but5;
	@FXML
	private Button but6;
	@FXML
	private Button but7;
	@FXML
	private Button but8;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/mydb"
			+ "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	static final String USER = "root";
	static final String PASS = "lzw19971206";

	@FXML
	public void selectPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("select.fxml"));
		ObservableList<Stage> stages = FXRobotHelper.getStages();
		Stage stage = stages.get(0);
		Scene scene = new Scene(root);
		stage.setTitle("select");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void searchTotalTrans(ActionEvent event) throws IOException, SQLException {
		String input = textfield.getText();
		String pattern = "^\\d+$";
		if (!Pattern.matches(pattern, input)) {
			textarea.setText("must be a number,please input again");
			return;
		}
		int num = Integer.parseInt(input);
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "select avg(transcript.result) from transcript where transcript.student_student_id=" + num
					+ ";";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					double avg = rs.getDouble("avg(transcript.result)");
					if (avg < 1) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("信息提示对话框");
						alert.setHeaderText("there is a problem!");
						alert.setContentText("not exists,please input again");
						alert.showAndWait();
					} else {
						String result = "number:" + num + "    avg all result:" + avg;
						textarea.setText(result);
					}

				}
			} else {
				System.out.println("ooo");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	@FXML
	public void searchPartTrans(ActionEvent event) throws IOException, SQLException {
		String input = textfield.getText();
		String pattern = "^\\d+$";
		if (!Pattern.matches(pattern, input)) {
			textarea.setText("must be a number,please input again");
			return;
		}
		int num = Integer.parseInt(input);
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "select avg(transcript.result) from transcript where transcript.student_student_id=" + num
					+ " and transcript.course_course_id in (select course_course_id from plan where compulsory=1 and (profession_profession_id) in (select class.profession_profession_id from class where exists (select * from student where student_id="
					+ num + ")));";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					double total = rs.getDouble("avg(transcript.result)");
					if (total < 1) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("信息提示对话框");
						alert.setHeaderText("there is a problem!");
						alert.setContentText("not exists,please input again");
						alert.showAndWait();
					} else {
						String result = "number:" + num + "  average compolsory transcript:" + total + "\n";
						textarea.setText(result);
					}

				}
			} else {
				System.out.println("aa");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	@FXML
	public void searchCourse(ActionEvent event) throws IOException, SQLException {
		String input = textfield.getText();
		String pattern = "^\\d+$";
		if (!Pattern.matches(pattern, input)) {
			textarea.setText("must be a number,please input again");
			return;
		}
		int num = Integer.parseInt(input);
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = String.format(
					"select course.name,course.credit,transcript.result,plan.compulsory,plan.semester from course,transcript,plan "
							+ "where course.course_id  in (select course_id from transcript where student_student_id=%d) and "
							+ "transcript.student_student_id=%d and transcript.course_course_id in (select course_id from transcript where student_student_id=%d) and "
							+ "plan.course_course_id in (select course_id from transcript where student_student_id=%d) and plan.profession_profession_id in (select class.profession_profession_id from class where exists (select * from student where student_id=%d));",
					num, num, num, num, num);
			ResultSet rs = stmt.executeQuery(sql);
			StringBuilder result = new StringBuilder();
			if (rs.next()) {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String name = rs.getString("name");
					int credit = rs.getInt("credit");
					int goal = rs.getInt("result");
					int compolsory = rs.getInt("compulsory");
					int semester = rs.getInt("semester");
					result.append("name:" + name + " credit:" + credit + " goal:" + goal + " compolsory:" + compolsory
							+ " semester:" + semester + "\n");
					textarea.setText(result.toString());
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("信息提示对话框");
				alert.setHeaderText("there is a problem!");
				alert.setContentText("not exists,please input again");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	@FXML
	public void searchTeacher(ActionEvent event) throws IOException, SQLException {
		String input = textfield.getText();
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "select teacher.name from teacher where teacher.teacher_id in "
					+ "(select teacher_teacher_id from arrangement where arrangement.class_class_id in "
					+ "(select class_class_id from student where student.name='" + input + "')); ";
			ResultSet rs = stmt.executeQuery(sql);
			StringBuilder result = new StringBuilder("教" + input + "的老师有:");
			if (rs.next()) {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String name = rs.getString("name");
					result.append(name + " ");
					textarea.setText(result.toString());
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("信息提示对话框");
				alert.setHeaderText("there is a problem!");
				alert.setContentText("not exists,please input again");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	// Event Listener on Button[#but1].onAction
	@FXML
	public void searchByNum(ActionEvent event) throws SQLException {
		String input = textfield.getText();
		String pattern = "^\\d+$";
		if (!Pattern.matches(pattern, input)) {
			textarea.setText("must be a number,please input again");
			return;
		}
		int num = Integer.parseInt(input);
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "select name,sex,birthday from student where student_id = " + num + ";";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				// sql = "select name,sex,birthday from student where student_id
				// = " + num + ";";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String name = rs.getString("name");
					String sex = rs.getString("sex");
					int birthday = rs.getInt("birthday");
					String result = "name:" + name + " sex:" + sex + " birthday:" + birthday;
					textarea.setText(result);
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("信息提示对话框");
				alert.setHeaderText("there is a problem!");
				alert.setContentText("not exists,please input again");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	// Event Listener on Button[#but2].onAction
	@FXML
	public void searchByName(ActionEvent event) throws SQLException {
		String input = textfield.getText();
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "select name,sex,birthday from student where student.name = '" + input + "';";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				// sql = "select name,sex,birthday from student where
				// student.name = '" + input + "';";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String name = rs.getString("name");
					String sex = rs.getString("sex");
					int birthday = rs.getInt("birthday");
					String result = "name:" + name + " sex:" + sex + " birthday:" + birthday;
					textarea.setText(result);
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("信息提示对话框");
				alert.setHeaderText("there is a problem!");
				alert.setContentText("not exists,please input again");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	// Event Listener on Button[#but3].onAction
	@FXML
	public void searchByProfession(ActionEvent event) throws SQLException {
		String input = textfield.getText();
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "select * from student where student.class_class_id in (select class_id from class where class.profession_profession_id in (select profession_id from profession where profession.name='"
					+ input + "'));";
			ResultSet rs = stmt.executeQuery(sql);
			StringBuilder result = new StringBuilder();
			if (rs.next()) {
				sql = "select * from student where student.class_class_id in (select class_id from class where class.profession_profession_id in (select profession_id from profession where profession.name='"
						+ input + "'));";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String name = rs.getString("name");
					String sex = rs.getString("sex");
					int birthday = rs.getInt("birthday");
					result.append("name:" + name + " sex:" + sex + " birthday:" + birthday + "\n");
					textarea.setText(result.toString());
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("信息提示对话框");
				alert.setHeaderText("there is a problem!");
				alert.setContentText("not exists,please input again");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	@FXML
	public void searchDanger(ActionEvent event) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		List<Integer> idList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		List<Integer> creditList = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			StringBuilder result = new StringBuilder();
			String sql = "select student_id,name from student";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				idList.add(rs.getInt("student_id"));
				nameList.add(rs.getString("name"));
			}
			for (int i = 0; i < idList.size(); i++) {
				int x = idList.get(i);
				String sql2 = "select sum(course.credit) from course where course_id in (select transcript.course_course_id from transcript where transcript.result<60 and transcript.second_result<60 and transcript.student_student_id="
						+ x
						+ ") and course_id in (select plan.course_course_id from plan where plan.compulsory=1 and plan.profession_profession_id in (select class.profession_profession_id from class where exists (select * from student where student_id="
						+ x + ")));";
				rs = stmt.executeQuery(sql2);
				while (rs.next()) {
					int fail = rs.getInt("sum(course.credit)");
					if (fail > 0)
						result.append("name:" + nameList.get(i) + " lose credit:" + fail + "\n");
				}
			}
			textarea.setText(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
}
