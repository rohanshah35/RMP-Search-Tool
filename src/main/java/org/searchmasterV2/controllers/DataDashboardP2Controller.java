// Controller for DataDashP2 Scene

package org.searchmasterV2.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.searchmasterV2.Loader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP2Controller implements Initializable {

    @FXML
    private BarChart<String, Number> barChartOne;

    // Initializes the controller after FXML file is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fetches consolidated average professor sentiments
        List<Long> sentiments = Loader.getConsolidatedAvgProfessorSentiments();

        // Customizes X axis
        CategoryAxis xAxis = (CategoryAxis) barChartOne.getXAxis();
        xAxis.setLabel("SENTIMENT");
        xAxis.setTickLabelFont(Font.font("Arial", 14));
        xAxis.setTickLabelFill(Color.DARKGREY);

        // Customizes Y axis
        NumberAxis yAxis = (NumberAxis) barChartOne.getYAxis();
        yAxis.setLabel("REVIEWS");
        yAxis.setTickLabelFont(Font.font("Arial", 14));
        yAxis.setTickLabelFill(Color.DARKGREY);

        // Sets chart title and hides legend
        barChartOne.setTitle("FREQUENCY OF SENTIMENT (#)");
        barChartOne.setLegendVisible(false);

        // Prepares data series for sentiment categories
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("SENTIMENT");

        // Adds sentiment data to the series
        XYChart.Data<String, Number> negative = new XYChart.Data<>("Negative", sentiments.get(0));
        XYChart.Data<String, Number> neutral = new XYChart.Data<>("Neutral", sentiments.get(1));
        XYChart.Data<String, Number> positive = new XYChart.Data<>("Positive", sentiments.get(2));

        series1.getData().add(negative);
        series1.getData().add(neutral);
        series1.getData().add(positive);

        // Adds series to the bar chart
        barChartOne.getData().addAll(series1);

        // Animates bar chart bars with scale effect
        for (XYChart.Data<String, Number> data : series1.getData()) {
            StackPane stackPane = (StackPane) data.getNode();
            Label label = new Label(data.getYValue().toString());
            label.setFont(Font.font("Arial", 14));
            label.setTextFill(Color.WHITE);
            stackPane.getChildren().add(label);

            data.getNode().setScaleY(0);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(data.getNode().scaleYProperty(), 1);
            KeyFrame kf = new KeyFrame(Duration.millis(800), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        }
    }

    // Action method for switching to DataDashP1 scene
    @FXML
    public void pageOneClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0");
        stage.setScene(new Scene(loadFXML("DataDashP1")));
    }

    // Action method for switching to DataDashP3 scene
    @FXML
    public void pageThreeClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("DataDashP3")));
    }
}