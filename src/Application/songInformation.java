/* Authors: Vishnu Dhanasekaran and Manish Namburi */
package Application;
import javafx.beans.property.SimpleStringProperty;

import java.util.Arrays;

public class songInformation {

    private final SimpleStringProperty songName = new SimpleStringProperty("");
    private final SimpleStringProperty songArtist = new SimpleStringProperty("");
    private final SimpleStringProperty songAlbum = new SimpleStringProperty("");
    private final SimpleStringProperty songYear = new SimpleStringProperty("");

    public songInformation(String[] line) {
        songName.setValue(line[0]);
        songArtist.setValue(line[1]);

        if(line[2].equals(" ")) {
            songAlbum.setValue("");
        }else{
            songAlbum.setValue(line[2]);
        }

        if(line[3].equals(" ")) {
            songYear.setValue("");
        }else{
            songYear.setValue(line[3]);
        }
    }

    public String getSongName() {
        return songName.get();
    }


    public String getSongArtist() {
        return songArtist.get();
    }


    public String getSongAlbum() {
        return songAlbum.get();
    }


    public String getSongYear() {
        return songYear.get();
    }

    public void setSongName(String songName) {
        this.songName.set(songName);
    }

    public void setSongArtist(String songArtist) {
        this.songArtist.set(songArtist);
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum.set(songAlbum);
    }

    public void setSongYear(String songYear) {
        this.songYear.set(songYear);
    }


    public songInformation(String songName, String songArtist, String songAlbum, String songYear) {
        setSongName(songName);
        setSongArtist(songArtist);
        setSongAlbum(songAlbum);
        setSongYear(songYear);
    }

    @Override
    public String toString() {
        return getSongName() + " | " + getSongArtist();
    }

    public String stringCompare(){
        return getSongName() + " " + getSongArtist();
    }
}
