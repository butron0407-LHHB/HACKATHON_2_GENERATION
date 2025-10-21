package com.phonebook.hackhaton.agendahackhaton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("agenda-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 520);
        scene.getStylesheets().add(MainApplication.class.getResource("styles.css").toExternalForm());
        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("icon.png")));
        stage.setTitle("Agenda Telefónica");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
