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

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.w3c.dom.Text;

//add COmpare To Ignore case.

import java.util.*;

public class Controller {
    public String FILENAME = "src\\sample\\output.txt";

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

    private Stage tempStage;

    //ObsList holds all the songs
    private ObservableList<songInformation> obsList;


    // fucntion to read in a csv file
    private List<String[]> readCSV() {
        List<String[]> content = new ArrayList<>();
        String file = FILENAME;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split("="));
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


    // start function
    public void start(Stage mainStage) {

        List<String[]> content = readCSV();
        songInformation[] songs = new songInformation[content.size()];

        for (int i = 0; i < content.size(); i++) {
            System.out.println(Arrays.toString(content.get(i)) );
        }

        System.out.println(content.size());

        for (int i = 0; i < content.size(); i++) {
            songs[i] = new songInformation(content.get(i));
        }

        obsList = FXCollections.observableArrayList();
        for (songInformation s : songs) {
            obsList.add(s);
        }

        sort(obsList);
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

    private void errorMsg(Stage mainStage, String errorType) {
        songInformation song = listView.getSelectionModel().getSelectedItem();
        String songName = song.getSongName();
        String content;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainStage);
        alert.setTitle("Error Message");
        alert.setHeaderText(
                "Error Adding Song");

        if(errorType.equals("temp")){
            content = "This is a duplicate song!";
        }
        else{
            content = "Input both song name and artist!";
        }


        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showItem(Stage mainStage) {
        songInformation song = listView.getSelectionModel().getSelectedItem();
        System.out.println("etnering");

        name.setText(song.getSongName());
        artist.setText(song.getSongArtist());
        album.setText(song.getSongAlbum());
        year.setText(song.getSongYear());
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

            songInformation newSong = new songInformation(name.getText(), artist.getText(), albumFiller, yearFiller);
            if (!duplicate(obsList, name.getText(), artist.getText())){
                Integer index = 0;
                obsList.add(0,newSong);

                sort(obsList);

                index = findSong(obsList, newSong.getSongName(), newSong.getSongArtist());


                listView.getSelectionModel().select(index);
                saveFile(obsList);

                System.out.println(name.getText());
            }else{
                errorMsg(tempStage, "temp");
            }



        }else{
            System.out.println("Did not enter song name or artist");
            errorMsg(tempStage, "error");
        }
        // Attempting to add sort
        //sort(obsList);
    }

    public void deleteSong(javafx.event.ActionEvent actionEvent) throws IOException {
        songInformation song = listView.getSelectionModel().getSelectedItem();

        obsList.remove(song);
        saveFile(obsList);

    }

    public void editSong(javafx.event.ActionEvent actionEvent) throws IOException {
        songInformation song = listView.getSelectionModel().getSelectedItem();

        Integer index = listView.getSelectionModel().getSelectedIndex();

        songInformation newSong = new songInformation(name.getText(),artist.getText(), album.getText(), year.getText());

        try{
            songInformation replace = obsList.set(index, newSong);
            System.out.println(replace);
        }catch (Exception e) {
            e.printStackTrace();
        }

        sort(obsList);
        saveFile(obsList);

    }





    public static boolean duplicate(ObservableList <songInformation> songList, String songName, String songArtist){

        for (int i = 0; i < songList.size(); i++) {
            if((songList.get(i).getSongName().equals(songName)) && (songList.get(i).getSongArtist().equals(songArtist))){

                return true;
            }
        }
        return false;
    }

    public static void saveFile(ObservableList <songInformation> songList){
        try{
            String fileName = "src\\sample\\output.txt";
            PrintWriter writer = new PrintWriter(fileName);
            for (songInformation song : songList){
                StringJoiner sj = new StringJoiner("=");

                sj.add(song.getSongName());
                sj.add(song.getSongArtist());

                String songAlbum = song.getSongAlbum();
                if (!songAlbum.isEmpty()){
                    sj.add(songAlbum);
                }
                else{
                    sj.add(" ");
                }

                String songYear = song.getSongYear();
                if (!songAlbum.isEmpty()){
                    sj.add(songYear);
                }
                else{
                    sj.add(" ");
                }

                writer.println(sj.toString());
            }
            writer.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
    }

    private static Integer findSong(ObservableList <songInformation> songList, String songName, String songArtist){
        Integer index = 0;
        for (int i = 0; i < songList.size(); i++) {
            if(songName == songList.get(i).getSongName() && songArtist == songList.get(i).getSongArtist()){
                index = i;
                System.out.println("Found " + songName + " " + songArtist+ " at " + index);
                return index;
            }
        }
        return 0;
    }



    // Attempt at sorting
    private static Integer sort(ObservableList <songInformation> songList) {
        Integer index = 0;
        for (int i = 0; i < songList.size(); i++)
        {
            for (int j = i + 1; j < songList.size(); j++)
            {
                if (songList.get(i).toString().compareTo(songList.get(j).toString())>0)
                {
                    songInformation s = new songInformation(songList.get(i).getSongName(),songList.get(i).getSongArtist(),songList.get(i).getSongAlbum(),songList.get(i).getSongYear());
                    songList.set(i, songList.get(j));
                    songList.set(j, s);
                    index = j;
                }
            }
        }
        return index;
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


    // new Scene to add new songs
    public void insertSongScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("add.fxml"));
        Stage stage2 = (Stage) add.getScene().getWindow();
        stage2.getScene().setRoot(newRoot);
    }

}
