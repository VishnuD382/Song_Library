/* Authors: Vishnu Dhanasekaran and Manish Namburi */

package View;

import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Application.songInformation;

import java.util.*;

public class Controller {
    public String FILENAME = "src/Data/output.txt";

    @FXML
    ListView<songInformation> listView;

    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnEdit;


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


    // function to read in a csv file
    private List<String[]> readCSV() {
        List<String[]> content = new ArrayList<>();
        String file = FILENAME;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split("\\|"));
            }
        } catch (FileNotFoundException e) {
            //Some error logging
            System.out.println("File not found. Creating a new File!");
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

//        for (int i = 0; i < content.size(); i++) {
//            System.out.println(Arrays.toString(content.get(i)) );
//        }

        for (int i = 0; i < content.size(); i++) {
            songs[i] = new songInformation(content.get(i));
        }

        obsList = FXCollections.observableArrayList();

        obsList.addAll(Arrays.asList(songs));

        //sort(obsList);
        listView.setItems(obsList);


        if(!listView.getItems().isEmpty()){
            listView.getSelectionModel().select(0);
            showItem(mainStage);
        }


         //set listener for the items
        listView
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(
                        (obs, oldVal, newVal) ->
                                showItem(mainStage));

    }

    private void errorMsg(Stage mainStage, String errorType) {

        String content;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainStage);
        alert.setTitle("Error Message");


        switch (errorType) {
            case "temp" -> {
                alert.setHeaderText(
                        "Error Adding Song");
                content = "This is a duplicate song!";
            }
            case "delete" -> {
                alert.setHeaderText(
                        "Error Deleting Song");
                content = "No Song to Delete!";
            }
            case "edit" -> {
                alert.setHeaderText(
                        "Error Editing Song");
                content = "No Song to Edit!";
            }
            default -> content = "Input both song name and artist!";
        }


        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showItem(Stage mainStage) {
        songInformation song = listView.getSelectionModel().getSelectedItem();

        if(song != null){
            name.setText(song.getSongName());
            artist.setText(song.getSongArtist());
            album.setText(song.getSongAlbum());
            year.setText(song.getSongYear());
        }else{
            name.setText("");
            artist.setText("");
            album.setText("");
            year.setText("");
        }

    }






    // will handle the add of new songs
    public void insertSong(javafx.event.ActionEvent actionEvent) throws IOException {


        String albumFiller = "";
        String yearFiller = "";

        if(!name.getText().isEmpty() && !artist.getText().isEmpty()){
            if(!album.getText().isEmpty()){
                albumFiller = album.getText();
            }
            if (!year.getText().isEmpty()){
                yearFiller = year.getText();
            }

            songInformation newSong = new songInformation(name.getText().trim(), artist.getText().trim(), albumFiller.trim(), yearFiller.trim());
            if (!duplicate(obsList, name.getText(), artist.getText())){
                Integer index = 0;
                obsList.add(0,newSong);

                sort(obsList);

                index = findSong(obsList, newSong.getSongName(), newSong.getSongArtist());


                listView.getSelectionModel().select(index);

                Stage mainStage = (Stage) BtnAdd.getScene().getWindow();

                listView
                        .getSelectionModel()
                        .selectedIndexProperty()
                        .addListener(
                                (obs, oldVal, newVal) ->
                                        showItem(mainStage));

                saveFile(obsList);

                System.out.println(name.getText());
            }else{
                errorMsg(tempStage, "temp");
            }



        }else{
            errorMsg(tempStage, "error");
        }
        // Attempting to add sort
        //sort(obsList);
    }

    public void deleteSong(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage mainStage = (Stage) BtnAdd.getScene().getWindow();

        songInformation song = listView.getSelectionModel().getSelectedItem();

        int index = listView.getSelectionModel().getSelectedIndex();

        int listLength = obsList.size();

        if(obsList.size() > 2){
            if(index < listLength){
                index += 1;
            }
            else{
                index -= 1;
            }
            listView.getSelectionModel().select(index);
        }

        if (obsList.size() > 0 ){
            obsList.remove(song);

        }else{
            errorMsg(mainStage, "delete");
        }


        saveFile(obsList);

    }

    public void editSong(javafx.event.ActionEvent actionEvent) throws IOException {

        int index = listView.getSelectionModel().getSelectedIndex();

        songInformation newSong = new songInformation(name.getText(),artist.getText(), album.getText(), year.getText());
        Stage mainStage = (Stage) BtnAdd.getScene().getWindow();

        try{
            if(!duplicate(obsList, newSong.getSongName(), newSong.getSongArtist())){
                if (obsList.size() > 0){
                    songInformation replace = obsList.set(index, newSong);
                }else{
                    errorMsg(mainStage, "edit");
                }
            }else{
                errorMsg(mainStage, "temp");
            }


        }catch (Exception e) {
            e.printStackTrace();
        }

        sort(obsList);
        saveFile(obsList);

    }





    public static boolean duplicate(ObservableList <songInformation> songList, String songName, String songArtist){

        for (int i = 0; i < songList.size(); i++) {
            if((songList.get(i).getSongName().toLowerCase().equals(songName.toLowerCase())) && (songList.get(i).getSongArtist().toLowerCase().equals(songArtist.toLowerCase()))){

                return true;
            }
        }
        return false;
    }

    public void saveFile(ObservableList <songInformation> songList){
        try{
            String fileName = FILENAME;
            PrintWriter writer = new PrintWriter(fileName);
            for (songInformation song : songList){
                StringJoiner sj = new StringJoiner("|");

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
        int index = 0;
        for (int i = 0; i < songList.size(); i++) {
            if(songName.equals(songList.get(i).getSongName()) && songArtist.equals(songList.get(i).getSongArtist())){
                index = i;
                System.out.println("Found " + songName + " " + songArtist+ " at " + index);
                return index;
            }
        }
        return 0;
    }



    // Attempt at sorting
    private static Integer sort(ObservableList <songInformation> songList) {
        int index = 0;
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
    
}
