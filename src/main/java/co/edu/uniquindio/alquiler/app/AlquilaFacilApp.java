package co.edu.uniquindio.alquiler.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlquilaFacilApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(AlquilaFacilApp.class.getResource("/ventanas/inicio.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Alquiler App");
        stage.show();


    }

    public static void main(String[] args) {
        launch(AlquilaFacilApp.class, args);
    }

}