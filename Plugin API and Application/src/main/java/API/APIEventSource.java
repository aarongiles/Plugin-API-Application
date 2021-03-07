package API;

import java.util.LinkedList;
import java.util.List;

//Provides users to add listeners. Very minimal code, that is needed, even though it is in API
public class APIEventSource
{
    private List<APIObserver> obeservers = new LinkedList<>();

    //Provides user option to add callback after certain events.
    public void addObserver(APIObserver obs)
    {
        obeservers.add(obs);
    }


    //Returns the list of observers currently on the object
    public List<APIObserver> getObeservers()
    {
        return obeservers;
    }
}
