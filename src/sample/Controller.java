package sample;

import java.awt.event.ActionEvent;
import java.io.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    ListView<songInformation> listView;

    @FXML
    private Button BtnAdd;

    @FXML
    private Button add;

    @FXML
    private TextField name;

    @FXML
    private TextField artist;

    @FXML
    private TextField album;

    @FXML
    private TextField year;


    //ObsList holds all the songs

    private ObservableList<songInformation> obsList;



    @FXML
    private TextField txtAddItem;

    private List<String[]> readCSV() {
        List<String[]> content = new ArrayList<>();
        String file = "src\\sample\\test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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



    public void start(Stage mainStage) {

        List<String[]> content = readCSV();
        songInformation[] songs = new songInformation[content.size()];

        for (int i = 0; i < content.size(); i++) {
            songs[i] = new songInformation(content.get(i));
        }

        obsList = FXCollections.observableArrayList(
                new songInformation("SongTItle", "songArtists", "songalb", "songyear"));

        for (songInformation s : songs) {
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
                                showItem(mainStage));

    }

    private void showItem(Stage mainStage) {
        songInformation song = listView.getSelectionModel().getSelectedItem();
        String songName = song.getSongName();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainStage);
        alert.setTitle("List Item");
        alert.setHeaderText(
                "Selected list item properties");

        String content = "Name: " +
                songName +
                "\nArtist : " +
                song.getSongArtist() +
                "\nAlbum: " + song.getSongAlbum() +
                "\nYear: " + song.getSongYear();

        alert.setContentText(content);
        alert.showAndWait();
    }



    // new Scene to add new songs
    public void insertSongScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("add.fxml"));
        Stage stage2 = (Stage) add.getScene().getWindow();
        stage2.getScene().setRoot(newRoot);
    }



    public ObservableList <songInformation> getList(){
        return obsList;
    }

    // will handle the add of new songs
    public void insertSong(javafx.event.ActionEvent actionEvent) throws IOException {

        ObservableList<songInformation> newObsList;

        String albumFiller = "";
        String yearFiller = "";
        if(!name.getText().isEmpty() && !artist.getText().isEmpty()){
            if(!album.getText().isEmpty()){
                albumFiller = album.getText();
            }
            if (!year.getText().isEmpty()){
                yearFiller = year.getText();
            }
            songInformation newSong = new songInformation(name.getText(), artist.getText(), year.getText(), album.getText());
            //obsList.add(newSong);
            System.out.println(getList());
            System.out.println(name.getText());

        }else{
            System.out.println("Did not enter song name or artist");
        }
    }




    // not in use
    private void showItemInputDialog(Stage mainStage) {
        songInformation song = listView.getSelectionModel().getSelectedItem();
        String songName = song.getSongName();
        int index = listView.getSelectionModel().getSelectedIndex();


        TextInputDialog dialog = new TextInputDialog(songName);
        dialog.initOwner(mainStage);
        dialog.setTitle("List Item");
        dialog.setHeaderText("Selected Item (Index: " + index + ")");
        dialog.setContentText("Enter name: ");

        Optional<String> result = dialog.showAndWait();
        songInformation test = listView.getSelectionModel().getSelectedItem();
        if (result.isPresent()) {
            obsList.set(index, song);
        }
    }

}