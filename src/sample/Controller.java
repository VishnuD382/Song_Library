package sample;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        songInformation[] songs = new songInformation[content.size()];

        for (int i = 0; i < content.size(); i++) {
            songs[i] = new songInformation(content.get(i));
        }

//        System.out.println("Printing contents:");
//        for (String[] strings : content) {
//            for (String s : strings) {
//                System.out.print(s + " ");
//            }
//            System.out.println();
//        }

        //making a string of titles
        String[] titles = new String[songs.length];
        for (int i = 0; i < songs.length; i++) {
            titles[i] = songs[i].getSongName();
        }


        obsList = FXCollections.observableArrayList();

        for (String s : titles) {
            obsList.add(s);
        }

        listView.setItems(obsList);
        listView.getSelectionModel().select(0);

        // set listener for the items
        listView
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(
                        (obs, oldVal, newVal) ->
                                showItemInputDialog(mainStage));

    }

    private void showItem(Stage mainStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainStage);
        alert.setTitle("List Item");
        alert.setHeaderText(
                "Selected list item properties");

        String content = "Index: " +
                listView.getSelectionModel()
                        .getSelectedIndex() +
                "\nValue: " +
                listView.getSelectionModel()
                        .getSelectedItem();

        alert.setContentText(content);
        alert.showAndWait();
    }


    private void showItemInputDialog(Stage mainStage) {
        String item = listView.getSelectionModel().getSelectedItem();
        int index = listView.getSelectionModel().getSelectedIndex();

        TextInputDialog dialog = new TextInputDialog(item);
        dialog.initOwner(mainStage); dialog.setTitle("List Item");
        dialog.setHeaderText("Selected Item (Index: " + index + ")");
        dialog.setContentText("Enter name: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) { obsList.set(index, result.get()); }
    }

    public void insertSong(javafx.event.ActionEvent actionEvent) {

        obsList.add(txtAddItem.getText());
        //System.out.println("Added: " + printtest);
    }
}