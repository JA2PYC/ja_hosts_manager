package ja_hosts_manager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;

public class JaHostsManager extends Application {

	private static final String HOSTS_PATH = "C:\\Users\\gr2na\\Desktop\\hosts.txt";
	// private static final String HOSTS_PATH =
	// "C:\\Windows\\System32\\drivers\\etc\\hosts";
	private TextArea logArea;
	VBox FieldBox = new VBox(10);

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hosts Manager");

		// IP와 도메인 입력 필드
		TextField ipField = new TextField();
		ipField.setPromptText("IP Address");
		TextField domainField = new TextField();
		domainField.setPromptText("Domain");
		Button toggleButton = new Button("On");
		toggleButton.setOnAction(e -> {
			logArea.appendText("Event: " + e);
			toggleSwitch(ipField.getText(), domainField.getText());
			addFieldLine();
		});
		// toggleButton.setOnAction(e -> toggleSwitch(ipField.getText(),
		// domainField.getText(), e));

		// 추가 및 삭제 버튼
		Button addButton = new Button("Add Entry");
		Button removeButton = new Button("Remove Entry");
		Button loadFileButton = new Button("Load File");

		// 로그 출력 영역
		logArea = new TextArea();
		logArea.setEditable(false);

		// 이벤트 처리
		addButton.setOnAction(e -> addEntry(ipField.getText(), domainField.getText()));
		removeButton.setOnAction(e -> removeEntry(domainField.getText()));

		// 레이아웃 구성
		HBox inputBox = new HBox(0, ipField, domainField, toggleButton);
		inputBox.setPadding(new Insets(0));
		HBox buttonBox = new HBox(10, addButton, removeButton);
		buttonBox.setPadding(new Insets(10));
		HBox fileButtonBox = new HBox(10, loadFileButton);
		fileButtonBox.setPadding(new Insets(10));
		VBox mainLayout = new VBox(10, FieldBox, inputBox, buttonBox, fileButtonBox, logArea);

		Scene scene = new Scene(mainLayout, 500, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addFieldLine() {
		TextField ipField = new TextField();
		ipField.setPromptText("IP Adress");
		TextField domainField = new TextField();
		domainField.setPromptText("Domain");
		Button toggleButton = new Button();
		toggleButton.setOnAction(e -> {
			logArea.appendText("Set On Action");
		});
		
		HBox fieldBox = new HBox(0, ipField, domainField, toggleButton);
		FieldBox.getChildren().add(fieldBox);
		
	}

	private void toggleSwitch(String ip, String domain) {

	}

	// hosts 파일에 항목 추가
	private void addEntry(String ip, String domain) {
		String entry = ip + " " + domain;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(HOSTS_PATH, true))) {
			writer.write(entry);
			writer.newLine();
			logArea.appendText("Added: " + entry + "\n");
		} catch (IOException e) {
			logArea.appendText("Error: " + e.getMessage() + "\n");
		}
	}

	// hosts 파일에서 항목 삭제
	private void removeEntry(String domain) {
		try {
			Path path = Paths.get(HOSTS_PATH);
			StringBuilder newContent = new StringBuilder();

			Files.lines(path).forEach(line -> {
				if (!line.contains(domain)) {
					newContent.append(line).append(System.lineSeparator());
				}
			});

			Files.write(path, newContent.toString().getBytes());
			logArea.appendText("Removed entry for: " + domain + "\n");
		} catch (IOException e) {
			logArea.appendText("Error: " + e.getMessage() + "\n");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
