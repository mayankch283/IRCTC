package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.stylesheets.LinkStyle;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserBookingService {
    private User user;

    private List<User> userList;

    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User user) throws IOException {
        this.user = user;
        File users  = new File(USERS_PATH);
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});

    }


    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1->{
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();

    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }
        catch(IOException ex){
            return Boolean.FALSE;
        }

    }

    private void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }

    //Need to test this function
    public Boolean cancelBooking(String ticketId){
        if(user.getTicketsBooked().contains(ticketId)){

            List<Ticket> newBookedTickets = user.getTicketsBooked().stream().filter(ticketsBooked->{
                return !(user.getTicketsBooked().equals(ticketId));
            }).collect(Collectors.toList());

            user.setTicketsBooked(newBookedTickets);

            try {
                saveUserListToFile();
            }catch(Exception e){
                e.printStackTrace();
            }

            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }
}

