package sample; //change the name of the package
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class Controller {
    @FXML
    ListView<String> listView;

    @FXML
    private Button BtnAdd;

    private ObservableList<String> obsList;

    @FXML
    private TextField txtAddItem;

    String[] datatest = {"Chiefs", "Eagles" };

    public void start(Stage mainStage){

        int count = 0;
        String file = "data.txt";
        List<String[]> content = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println("printing");
                content.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            //Some error logging
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }


        obsList = FXCollections.observableArrayList(
                "Giants",
                "Patriots",
                "49ers",
                "Rams");

        for (String s : datatest) {
            obsList.add(s);
        }

        listView.setItems(obsList);
    }

    public void insertSong(javafx.event.ActionEvent actionEvent) {

        obsList.add(txtAddItem.getText());
        //System.out.println("Added: " + printtest);
    }
}