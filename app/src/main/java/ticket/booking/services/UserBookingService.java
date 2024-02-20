package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.stylesheets.LinkStyle;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;
import ticket.booking.services.TrainService;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
        loadUsers();

    }

    public UserBookingService() throws IOException{
        loadUsers();
    }

    public void loadUsers()throws IOException{
        userList = objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>()
        {}); //TypeReference will preserve generic information during runtime so that objectmapper can use it to deserialize.
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream()
                .filter(user1-> user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword()))
                .findFirst();
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

    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        }catch(IOException ex){
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true; // Booking successful
                } else {
                    return false; // Seat is already booked
                }
            } else {
                return false; // Invalid row or seat index
            }
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }
}

