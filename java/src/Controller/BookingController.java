package Controller;

import Model.Booking.Booking;
import Model.Movie.Movie;
import Model.Ticket.Ticket;
import view.BookingView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Class BookingController.
 */

public class BookingController {

    private DatabaseController databaseController = DatabaseController.getInstance();
    
    /** The booking view. */

    private BookingView bookingView;
    
    /** List of Booking objects, representing bookings by moviegoers. */

    private List<Booking> bookings;
    
    /**
     * Instantiates a new booking controller.
     */

    public BookingController() {
        bookingView = new BookingView();
        bookings = databaseController.getBookingFromDB();
    }

    public void bookingHistory(Scanner sc) {
        System.out.println("Please enter your username:");
        String username = sc.nextLine();
        List<Booking> bookingList = getBookingByUsername(username);
        bookingView.displayBookingHistory(sc,bookingList);
    }
    
    /**
     * Adds the booking.
     *
     * @param booking the booking object to be added to list of Booking items, bookings
     * @see #bookings
     * @return void
     */

    public void addBooking(Booking booking) {
        bookings.add(booking);
        databaseController.updateBookingDB(bookings);
    }
    
    /**
     * Gets the booking by username. Calls findBookingByUsername(username)
     *
     * @param username the username
     * @return the booking by username as a list of Booking object
     */

    public List<Booking> getBookingByUsername(String username){
        return findBookingByUsername(username);
    }
    
    /**
     * Displays the top five movies by ticket sales.
     *
     * @return void and calls displayTopFiveMovies
     * 
     * @see BookingView#displayTopFiveMovies(List, List)
     */

    public void getTopFiveMovieByTicketSales(){
        MovieController movieController = new MovieController();
        List<Movie> movieList = movieController.getMovies();
        Map<String, Double> moviePriceHM = new HashMap<>(movieList.size());
        double totalSales = 0.0;
        for (Movie movie : movieList) {
            moviePriceHM.put(movie.getTitle(),totalSales);
        }
        for (Booking booking : bookings) {
            for (Ticket ticket : booking.getTickets()) {
                totalSales += ticket.getPrice();
                moviePriceHM.put(ticket.getShowtime().getMovie().getTitle(),moviePriceHM.get(ticket.getShowtime().getMovie().getTitle()) + totalSales);
            }
            totalSales = 0;
        }
        SortedMap<String, Double> moviePrice = moviePriceHM.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, TreeMap::new));
        List<String> movies = new ArrayList<>(moviePrice.keySet()).subList(0,5);
        List<Double> salesPriceList = new ArrayList<>(moviePrice.values()).subList(0,5);
        bookingView.displayTopFiveMovies(movies,salesPriceList);
    }
    
    
    
    private List<Booking> findBookingByUsername(String username) {
        List<Booking> bookingList = new ArrayList<>();
        for(int i=(bookings.size()-1); i>=0; i--){
            if(bookings.get(i).getBuyerName().equalsIgnoreCase(username))
                bookingList.add(bookings.get(i));
        }
        return bookingList;
    }
}
