package sample;
import javafx.beans.property.SimpleStringProperty;

public class songInformation {

    private final SimpleStringProperty songName = new SimpleStringProperty("");
    private final SimpleStringProperty songArtist = new SimpleStringProperty("");
    private final SimpleStringProperty songAlbum = new SimpleStringProperty("");
    private final SimpleStringProperty songYear = new SimpleStringProperty("");

    public songInformation(String[] line) {

        songName.setValue(line[0]);
        songArtist.setValue(line[1]);
        songAlbum.setValue(line[2]);
        songYear.setValue(line[3]);
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
}
