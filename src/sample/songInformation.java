package sample;
import javafx.beans.property.SimpleStringProperty;

import java.util.Arrays;

public class songInformation {

    private final SimpleStringProperty songName = new SimpleStringProperty("");
    private final SimpleStringProperty songArtist = new SimpleStringProperty("");
    private final SimpleStringProperty songAlbum = new SimpleStringProperty("");
    private final SimpleStringProperty songYear = new SimpleStringProperty("");

    public songInformation(String[] line) {
        System.out.println(Arrays.toString(line));
        System.out.println(line.length);
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



//        if(line.length < 3){
//            songAlbum.setValue("");
//            songYear.setValue("");
//        }
//        else if (line.length < 4){
//            songAlbum.setValue(line[2]);
//            songYear.setValue("");
//        }
//        else{
//            songAlbum.setValue(line[2]);
//            songYear.setValue(line[3]);
//        }
    }

    public String getSongName() {
        return songName.get();
    }

    public SimpleStringProperty songNameProperty() {
        return songName;
    }

    public String getSongArtist() {
        return songArtist.get();
    }

    public SimpleStringProperty songArtistProperty() {
        return songArtist;
    }

    public String getSongAlbum() {
        return songAlbum.get();
    }

    public SimpleStringProperty songAlbumProperty() {
        return songAlbum;
    }

    public String getSongYear() {
        return songYear.get();
    }

    public SimpleStringProperty songYearProperty() {
        return songYear;
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

//    public String[] titleNames(songInformation[] songs){
//        String[] titles = new String[songs.length];
//        for (int i = 0; i < songs.length; i++) {
//            titles[i] = songs[i].getSongName();
//        }
//        return titles;
//    }


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
