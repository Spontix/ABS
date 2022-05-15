package application.desktop;

import absController.ABSController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
//
public class DesktopApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("MyGeneralView.fxml");
        fxmlLoader.setLocation(url);
        Parent playersRoot = (Parent) fxmlLoader.load(fxmlLoader.getLocation().openStream());




        Scene scene = new Scene(playersRoot);

        primaryStage.setTitle("Players Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
