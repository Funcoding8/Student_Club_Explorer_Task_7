package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class StudentClubExplorer extends Application {
    private final ArrayList<Member> members = new ArrayList<>();
    private final TextArea resultArea = new TextArea();
    private final TextField searchField = new TextField();

    @Override
    public void start(Stage stage) {
        members.add(new Member("Amina", "Robotics", 1));
        members.add(new Member("Ben", "Gaming", 2));
        members.add(new Member("Carlos", "Robotics", 3));
        members.add(new Member("Dina", "Art", 1));
        members.add(new Member("Aidan", "Coding", 2));
        members.add(new Member("Elena", "Robotics", 4));
        members.add(new Member("Felix", "Music", 1));
        members.add(new Member("Grace", "Hiking", 3));

        Label heading = new Label("Student Club Explorer");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        searchField.setPromptText("Enter a full or partial name");
        Button search = new Button("Search");
        search.setOnAction(event -> searchByName());
        searchField.setOnAction(event -> searchByName());
        HBox searchRow = new HBox(10, searchField, search);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Button showAll = new Button("Show All");
        showAll.setOnAction(event -> displayMembers(members));

        Button robotics = new Button("Robotics Members");
        robotics.setOnAction(event -> {
            List<Member> matches = members.stream()
                    .filter(m -> m.getInterest().equalsIgnoreCase("Robotics"))
                    .toList();
            displayMembers(matches);
        });

        Button namesOnly = new Button("Names Only");
        namesOnly.setOnAction(event -> {
            List<String> names = members.stream()
                    .map(m -> m.getName())
                    .toList();
            resultArea.setText(String.join("\n", names));
        });

        Button firstYear = new Button("First Year Members");
        firstYear.setOnAction(event -> {
            List<Member> matches = members.stream()
                    .filter(m -> m.getYear() == 1)
                    .toList();
            displayMembers(matches);
        });

        FlowPane buttons = new FlowPane(10, 10, showAll, robotics, namesOnly, firstYear);
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        VBox root = new VBox(14, heading, searchRow, buttons, resultArea);
        root.setPadding(new Insets(20));
        VBox.setVgrow(resultArea, Priority.ALWAYS);
        stage.setTitle("Student Club Explorer");
        stage.setScene(new Scene(root, 680, 430));
        stage.setMinWidth(440);
        stage.setMinHeight(320);
        displayMembers(members);
        stage.show();
    }

    /** Validates input, then finds names without regard to capitalization. */
    private void searchByName() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            resultArea.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(searchField.getScene().getWindow());
            alert.setTitle("Enter a name");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a name to search.");
            alert.showAndWait();
            return;
        }
        String normalizedQuery = query.toLowerCase(Locale.ROOT);
        List<Member> matches = members.stream()
                .filter(m -> m.getName().toLowerCase(Locale.ROOT).contains(normalizedQuery))
                .toList();
        displayMembers(matches);
    }

    /** Replaces results with one member per line using a regular loop. */
    private void displayMembers(List<Member> selectedMembers) {
        if (selectedMembers.isEmpty()) {
            resultArea.setText("No matching members found.");
            return;
        }
        StringBuilder text = new StringBuilder();
        for (Member member : selectedMembers) {
            text.append(member).append('\n');
        }
        resultArea.setText(text.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
