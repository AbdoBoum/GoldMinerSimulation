package Gui;

import Gui.Components.GridDisplay;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static Environment.MiningField.MAP_HEIGHT;
import static Environment.MiningField.MAP_WIDTH;

public class GoldMiningApp extends Application {

    private GridDisplay gridDisplay;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gridDisplay = new GridDisplay(MAP_HEIGHT, MAP_WIDTH);
        BorderPane mainPanel = new BorderPane();
        mainPanel.setCenter(gridDisplay.getDisplay());

        Scene scene = new Scene(mainPanel, 800, 800);
        primaryStage.setTitle("Gold Mining");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
