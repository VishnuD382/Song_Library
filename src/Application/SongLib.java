/* Authors: Vishnu Dhanasekaran and Manish Namburi */
package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import View.Controller;


public class SongLib extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(
                getClass().getResource("../View/Addition.fxml"));

        BorderPane root = (BorderPane)loader.load();

        Controller controller = loader.getController();
        controller.start(primaryStage);


        primaryStage.setTitle("Hello World Testing");


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
