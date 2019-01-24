import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main/view/main.fxml"));
            primaryStage.setTitle("El Interpretore");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
        }
    }

    public static void main(String... args) {
        launch(args);
    }

}

