package interfaces;

import java.util.Map;

public interface IHltv {

    //life and future
    Map<String, Boolean> getAllMatches();
    void reloadMatches(Map<String, Boolean> lastListOfMatches) throws Exception;
    //List<String> resultMatches();
}
