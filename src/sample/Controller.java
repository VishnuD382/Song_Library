package sample;
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

    private List<String[]> readCSV(){
        List<String[]> content = new ArrayList<>();
        String file = "src\\sample\\test.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            //Some error logging
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return content;
    }

    String[] datatest = {"Chiefs", "Eagles" };

    public void start(Stage mainStage){

        int count = 0;

        List<String[]> content = readCSV();
        songInformation[] songs = new songInformation[content.size()*4];

        for (int i = 0; i < content.size(); i++) {
            songs[i] = new songInformation(content.get(0));
        }

        System.out.println(content.size());

        System.out.println("Printing contents:");
        for (String[] strings : content) {
            for (String s : strings) {
                System.out.print(s + " ");
            }
            System.out.println();
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