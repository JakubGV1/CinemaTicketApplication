package aplikacja2023;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Movie {
    private final String name;

    private Map<LocalDate, boolean[][]> AllSeats;

    private final int rows;
    private final int cols;

    public Movie(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        AllSeats = new HashMap<>();
    }
    public int getMaximumSeats(){
        return rows*cols;
    }


    public String getName() {
        return name;
    }

    public boolean[][] getAllSeats(LocalDate selectedDate){
        if(AllSeats != null && AllSeats.containsKey(selectedDate)){
            return AllSeats.get(selectedDate);
        } else {
            return null;
        }
    }

    public void insertValue(boolean[][] filledSeats, LocalDate selectedDate){
            AllSeats.put(selectedDate, filledSeats);
    }

    public int getRows(){
        return rows;
    }
    public int getCols(){
        return cols;
    }

    public boolean allSeatsOccupied(LocalDate date){
        return Arrays.deepToString(this.getAllSeats(date)).replaceAll("[^t]", "").length() == this.getMaximumSeats();
    }

}
