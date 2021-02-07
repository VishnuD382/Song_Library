package sample;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    @FXML
    ListView<String> listView;

    private ObservableList<String> obsList;

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
}