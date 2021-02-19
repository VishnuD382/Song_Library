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
            case "year" -> {
                alert.setHeaderText(
                        "Error Invalid Year");
                content = "Year needs to be an integer!";
            }
            case "negYear" -> {
                alert.setHeaderText(
                        "Error Invalid Year");
                content = "Year needs to be a positive integer!";
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


    public static boolean isNumeric(String year) {
        try {
            Integer.parseInt(year);
        } catch(NullPointerException e) {
            return false;
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }


    // will handle the add of new songs
    public void insertSong(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage mainStage = (Stage) BtnAdd.getScene().getWindow();
        String albumFiller = "";
        String yearFiller = "";

        if(!name.getText().isEmpty() && !artist.getText().isEmpty()){
            if(!album.getText().isEmpty()){
                albumFiller = album.getText();
            }
            if (!year.getText().isEmpty()){
                yearFiller = year.getText();
                boolean yearTest = isNumeric(yearFiller);


                if(!yearTest){
                    errorMsg(mainStage, "year");
                    return;
                }

                int yearInt = Integer.parseInt(year.getText().trim());
                if(yearInt < 0 ){
                    errorMsg(mainStage, "negYear");
                    return;
                }


            }

            songInformation newSong = new songInformation(name.getText().trim(), artist.getText().trim(), albumFiller.trim(), yearFiller.trim());
            if (!duplicate(obsList, name.getText().trim(), artist.getText().trim())){
                int index = 0;
                obsList.add(0,newSong);

                sort(obsList);
                saveFile(obsList);

                index = findSong(obsList, newSong.getSongName().trim(), newSong.getSongArtist().trim());



                listView.getSelectionModel().select(index);
                showItem(mainStage);




                //System.out.println(name.getText());
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
        Stage mainStage = (Stage) BtnAdd.getScene().getWindow();
        int index = listView.getSelectionModel().getSelectedIndex();

        if (!year.getText().isEmpty()){
            if (!isNumeric(year.getText().trim())){
                errorMsg(mainStage,"year");
                return;
            }
            int yearInt = Integer.parseInt(year.getText().trim());
            if(yearInt < 0 ){
                errorMsg(mainStage, "negYear");
                return;
            }
        }


        songInformation newSong = new songInformation(name.getText().trim(),artist.getText().trim(), album.getText().trim(), year.getText().trim());


        try{
            if(!editCheck(obsList, newSong.getSongName().trim(), newSong.getSongArtist().trim(),index)){
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




    public static boolean editCheck(ObservableList <songInformation> songList, String songName, String songArtist, int index){

        for (int i = 0; i < songList.size(); i++) {
            if (i != index){
                if((songList.get(i).getSongName().toLowerCase().equals(songName.toLowerCase())) && (songList.get(i).getSongArtist().toLowerCase().equals(songArtist.toLowerCase()))){

                    return true;
                }
            }
            else{
                continue;
            }
        }
        return false;
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
                //System.out.println("Found " + songName + " " + songArtist+ " at " + index);
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
