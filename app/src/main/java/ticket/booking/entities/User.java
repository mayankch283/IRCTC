package ticket.booking.entities;

import  java.util.List;
public class User {
    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;
    private String userId;


    public String getName(){ return name; }
    public String getPassword(){ return password; }
    public String getHashedPassword(){ return hashedPassword;}
    public List<Ticket> getTicketsBooked(){ return ticketsBooked; }
    public String getUserId(){ return userId; }

    public void setName(String name){ this.name = name; }
    public void setHashedPassword(String hashedPassword){ this.hashedPassword = hashedPassword; }
    public void setTicketsBooked(List<Ticket> ticketsBooked){ this.ticketsBooked = ticketsBooked; }
    public void setUserId(String userId){ this.userId = userId; }
}
