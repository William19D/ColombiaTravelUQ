package co.edu.uniquindio.agencia.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(App.class.getResource("/ventanas/inicio.fxml"));

        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("ColombiaTravel App");
        Image icon = new Image(getClass().getResourceAsStream("/imagenes/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }

}