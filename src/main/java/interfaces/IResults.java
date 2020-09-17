package interfaces;

import org.jsoup.nodes.Document;

import java.util.ArrayList;


public interface IResults {

    Document getMatchDoc();

    void writeResultLinks() throws Exception;     //write to file 'list of links' with past matches

    ArrayList<String> mapPick() throws Exception;     //list Of Played Maps

    int winner();     //if winner = 1 -> right won

}
