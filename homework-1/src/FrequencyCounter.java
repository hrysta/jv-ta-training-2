import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class FrequencyCounter extends Application {
    private final TextArea outputTextArea = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Frequency Counter");
		
        Image icon = new Image("https://content.r9cdn.net/rimg/provider-logos/common/socialmedia/kayak-logo.png?width=440&height=220&crop=false");

        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(100);
        iconView.setPreserveRatio(true);


		Label iconLabel = new Label("", iconView);
        Label inputLabel = new Label("Enter a list of integers separated by commas:");
        TextField inputField = new TextField();
        Button calculateButton = new Button("Calculate Frequencies");
		outputTextArea.setPrefSize(400, 400); 


        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(iconLabel, inputLabel, inputField, calculateButton, outputTextArea);


        Scene scene = new Scene(vBox, 400, 600);


        calculateButton.setOnAction(event -> calculateFrequencies(inputField.getText()));


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateFrequencies(String input) {
        List<Integer> integerList = parseInput(input);
        Map<Integer, Integer> frequencyMap = calculateFrequencies(integerList);


        StringBuilder output = new StringBuilder("Output in list format:\n");
        output.append("Frequency: ");
        output.append(formatList(frequencyMap));
        output.append("\nNumber: ");
        output.append(formatNumbers(frequencyMap));
        output.append("\n\n");


        output.append("Output in histogram format:\n");
        output.append(formatHistogram(frequencyMap));

        outputTextArea.setText(output.toString());
    }

    private List<Integer> parseInput(String input) {
        List<Integer> integerList = new ArrayList<>();
        String[] tokens = input.split(",");
        for (String token : tokens) {
            try {
                int number = Integer.parseInt(token.trim());
                integerList.add(number);
            } catch (NumberFormatException e) {
                outputTextArea.setText("Invalid input. Please enter a valid list of integers.");
                return Collections.emptyList();
            }
        }
        return integerList;
    }

    private Map<Integer, Integer> calculateFrequencies(List<Integer> integerList) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int number : integerList) {
            frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
        }
        return frequencyMap;
    }

    private String formatList(Map<Integer, Integer> frequencyMap) {
        int minNumber = Collections.min(frequencyMap.keySet());
        int maxNumber = Collections.max(frequencyMap.keySet());

        StringBuilder result = new StringBuilder();
        for (int i = minNumber; i <= maxNumber; i++) {
            result.append(frequencyMap.getOrDefault(i, 0)).append(", ");
        }
        return result.toString();
    }

    private String formatNumbers(Map<Integer, Integer> frequencyMap) {
        int minNumber = Collections.min(frequencyMap.keySet());
        int maxNumber = Collections.max(frequencyMap.keySet());

        StringBuilder result = new StringBuilder();
        for (int i = minNumber; i <= maxNumber; i++) {
            result.append(i).append(", ");
        }
        return result.toString();
    }

    private String formatHistogram(Map<Integer, Integer> frequencyMap) {
        int minNumber = Collections.min(frequencyMap.keySet());
        int maxNumber = Collections.max(frequencyMap.keySet());

        StringBuilder result = new StringBuilder();
        for (int i = maxFrequency(frequencyMap); i > 0; i--) {
            for (int j = minNumber; j <= maxNumber; j++) {
                if (frequencyMap.getOrDefault(j, 0) >= i) {
                    result.append("*\t");
                } else {
                    result.append(" \t");
                }
            }
            result.append("\n");
        }

        for (int i = minNumber; i <= maxNumber; i++) {
            result.append(i).append("\t");
        }
        return result.toString();
    }

    private int maxFrequency(Map<Integer, Integer> frequencyMap) {
        return frequencyMap.values().stream()
                .max(Integer::compare)
                .orElse(0);
    }
}
