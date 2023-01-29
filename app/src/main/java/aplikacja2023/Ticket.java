package aplikacja2023;

import java.time.LocalDate;

class Ticket {

    private final String movieName;
    private  final LocalDate date;
    private final int seatCol;
    private final int seatRow;

    public Ticket(int seatCol, int seatRow, String movieName, LocalDate date) {
        this.seatCol = seatCol;
        this.seatRow = seatRow;
        this.movieName = movieName;
        this.date = date;
    }
    public LocalDate getDate(){
        return date;
    }
    public int getSeatRow() {
        return seatRow;
    }

    public int getSeatCol(){
        return seatCol;
    }
    public String getMovieName() {
        return movieName;
    }
}