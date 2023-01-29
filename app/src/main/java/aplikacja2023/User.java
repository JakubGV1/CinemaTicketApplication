package aplikacja2023;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    private final UUID userId;
    private final ArrayList<Ticket> userTickets;

    public User(){
        userTickets = new ArrayList<>();
        userId = UUID.randomUUID();
    }
    public UUID getUserId(){
        return userId;
    }
    public ArrayList<Ticket> getUserTickets(){
        return userTickets;
    }
    public void addMultipleTicketsToUser(ArrayList<Ticket> tickets){
        userTickets.addAll(tickets);
    }

}
