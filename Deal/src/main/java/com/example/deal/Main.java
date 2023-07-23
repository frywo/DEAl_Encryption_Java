package com.example.deal;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Deal");
        stage.setMaxWidth(700); stage.setMaxHeight(500);
        stage.setMinWidth(700); stage.setMinHeight(500);
        stage.setScene(scene);
        stage.getIcons().add(new Image("testimj.png"));
        stage.show();

    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}