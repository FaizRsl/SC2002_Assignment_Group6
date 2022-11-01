package Model.Cinema;

import java.io.Serializable;
import java.util.ArrayList;

public class Cineplex implements Serializable {
    private String cineplexName;

    private ArrayList<Cinema> cinemas;

    public Cineplex(String cineplexName, ArrayList<Cinema> cinemaList){
        this.cineplexName = cineplexName;
        this.cinemas = new ArrayList<>();
    }

    public String getCineplexName() {
        return cineplexName;
    }

    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }

    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }


    public void addCinema(Cinema cinema){
        this.cinemas.add(cinema);
    }

}