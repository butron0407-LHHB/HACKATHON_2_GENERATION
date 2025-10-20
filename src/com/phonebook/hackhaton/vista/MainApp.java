package com.phonebook.hackhaton.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Cargar archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("Test-View.fxml"));

        // Crear escena
        Scene scene = new Scene(root, 400, 250);

        // Configurar y mostrar ventana
        stage.setTitle("Test FXML");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
