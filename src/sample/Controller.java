package sample;

import java.awt.event.ActionEvent;
import java.io.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
>>>>>>> parent of d0d2b1b (Adding Switching of scenes)
=======
>>>>>>> parent of d0d2b1b (Adding Switching of scenes)
=======
>>>>>>> parent of d0d2b1b (Adding Switching of scenes)
    private ObservableList<songInformation> obsList;

    public Controller(ObservableList<songInformation> obsList) {
        this.obsList= obsList ;
    }


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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of d0d2b1b (Adding Switching of scenes)
=======
>>>>>>> parent of d0d2b1b (Adding Switching of scenes)
    public void insertSong(javafx.event.ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Song");
        dialog.setHeaderText("Add a song to the List");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Song Name");
        TextField artist = new TextField();
        artist.setPromptText("Song artist");
        TextField album = new TextField();
        album.setPromptText("Song album");
        TextField year = new TextField();
        year.setPromptText("Song year");


        grid.add(new Label("Song Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Song Artist:"), 0, 1);
        grid.add(artist, 1, 1);
        grid.add(new Label("Song album:"), 0, 2);
        grid.add(album, 1, 2);
        grid.add(new Label("Song Year:"), 0, 3);
        grid.add(year, 1, 3);

        // enabling and disabling add button
        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            artist.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                addButton.setDisable(newValue.trim().isEmpty());
            });
        });


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> name.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(name.getText(), artist.getText());
            }
            return null;
        });
<<<<<<< HEAD
<<<<<<< HEAD

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

=======

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

>>>>>>> parent of d0d2b1b (Adding Switching of scenes)
=======

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

>>>>>>> parent of d0d2b1b (Adding Switching of scenes)
            //obsList.add(txtAddItem.getText());
        });
    }
>>>>>>> parent of d0d2b1b (Adding Switching of scenes)
}