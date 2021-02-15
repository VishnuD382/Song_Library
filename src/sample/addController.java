package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class addController {

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


    private ObservableList<songInformation> obsList ;

    public void setData(ObservableList<songInformation> obsList) {
        this.obsList = obsList;
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
            songInformation newSong = new songInformation(name.getText(), artist.getText(), year.getText(), album.getText());
            //obsList.add(newSong);
            System.out.println(obsList);
        }else{
            System.out.println("Did not enter song name or artist");
        }
    }

}

