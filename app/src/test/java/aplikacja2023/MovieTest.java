package aplikacja2023;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    @Test
    public void testGetAllSeats() {
        Movie movie = new Movie("Test Movie", 5, 10);
        LocalDate date = LocalDate.now();
        boolean[][] filledSeats = new boolean[5][10];
        movie.insertValue(filledSeats, date);
        assertArrayEquals(filledSeats, movie.getAllSeats(date));
    }

    @Test
    public void check_if_there_is_a_free_seat() {
        Movie movie = new Movie("Test Movie", 2, 2);

        LocalDate date = LocalDate.now();

        boolean[][] seats = {{false, true}, {true, false}};
        movie.insertValue(seats, date);

        assertFalse(movie.allSeatsOccupied(date));
    }
    @Test
    public void check_if_all_seats_are_occupied() {
        Movie movie = new Movie("Test Movie", 2, 2);

        LocalDate date = LocalDate.now();

        boolean[][] seats = {{true, true}, {true, true}};
        movie.insertValue(seats, date);

        assertTrue(movie.allSeatsOccupied(date));
    }


}