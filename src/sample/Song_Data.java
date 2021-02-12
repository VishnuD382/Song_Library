package sample.Project_Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Song_Data {
    private static final String SONG_FILE = "songs.xml";

    private static final String SONG_NAME = "song_name";
    private static final String ARTIST = "artist";
    private static final String ALBUM = "album";
    private static final String YEAR = "year";

    private ObservableList<songInformation> songs;

    public Song_Data() {
        songs = FXCollections.observableArrayList();
    }

    public ObservableList<songInformation> getSongs() {
        return songs;
    }

    public void addSong(songInformation item) {
        songs.add(item);
    }


    public void deleteSong(songInformation item) {
        songs.remove(item);
    }

    public void loadSongs() {
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(SONG_FILE);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            songInformation song = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have a song item, we create a new song
                    if (startElement.getName().getLocalPart().equals(SONG_NAME)) {
                        song = new songInformation();
                        continue;
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ARTIST)) {
                            event = eventReader.nextEvent();
                            song.setArtist(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(ALBUM)) {
                        event = eventReader.nextEvent();
                        song.setAlbum(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(YEAR)) {
                        event = eventReader.nextEvent();
                        song.setYear(event.asCharacters().getData());
                        continue;
                    }
                }

                // If we reach the end of a song element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(SONG_NAME)) {
                        songs.add(song);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    public void saveSongs() {

        try {
            // create an XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            // create XMLEventWriter
            XMLEventWriter eventWriter = outputFactory
                    .createXMLEventWriter(new FileOutputStream(SONG_FILE));
            // create an EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            StartElement songsStartElement = eventFactory.createStartElement("",
                    "", "songs");
            eventWriter.add(songsStartElement);
            eventWriter.add(end);

            for (Song_Data_Skeleton song : songs) {
                saveSongs();
            }

            eventWriter.add(eventFactory.createEndElement("", "", "songs"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Problem with Songs file: " + e.getMessage());
            e.printStackTrace();
        } catch (XMLStreamException e) {
            System.out.println("Problem writing song: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void saveContact(XMLEventWriter eventWriter, XMLEventFactory eventFactory, Song_Data_Skeleton song)
            throws FileNotFoundException, XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");

        // create song open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", SONG_NAME);
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        // Write the different nodes
        createNode(eventWriter, ARTIST, song.getArtist());
        createNode(eventWriter, ALBUM, song.getAlbum());
        createNode(eventWriter, YEAR, song.getYear());

        eventWriter.add(eventFactory.createEndElement("", "", SONG_NAME));
        eventWriter.add(end);
    }

    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

}
