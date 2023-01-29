package aplikacja2023;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class CinemaManager extends Application {
    private Scene mainMenuScene, movieSelectionScene, seatsScene;

    private final Movie[] movies = new Movie[4];
    private User user;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikacja kinowa");


        movies[0] = new Movie("Avatar", 5, 6);
        movies[1] = new Movie("Matrix", 8, 8);
        movies[2] = new Movie("John Wick: Chapter 4", 10, 10);
        movies[3] = new Movie("Top Gun: Maverick", 2,2);
        user = new User();


        boolean [][] maverickSeats = movies[3].getAllSeats(LocalDate.now());
        if(maverickSeats == null){
            maverickSeats = new boolean[movies[3].getRows()][movies[3].getCols()];
            maverickSeats[0][0] = true;
            maverickSeats[0][1] = true;
            maverickSeats[1][1] = true;
            maverickSeats[1][0] = true;
            movies[3].insertValue(maverickSeats, LocalDate.now());
        }

        createMainMenuScene(primaryStage);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    void createMainMenuScene(final Stage primaryStage) {

        VBox mainMenu = new VBox();
        mainMenu.setPadding(new Insets(10));
        mainMenu.setSpacing(8);
        mainMenu.setAlignment(Pos.CENTER);

        Label title = new Label("Witaj w kinie");
        mainMenu.getChildren().add(title);

        Button movieSelectionButton = new Button("Wybierz film");
        movieSelectionButton.setOnAction(e -> {
            createMovieSelectionScene(primaryStage);
            primaryStage.setScene(movieSelectionScene);
        });
        mainMenu.getChildren().add(movieSelectionButton);
        mainMenuScene = new Scene(mainMenu, 300, 250);
    }
    private void createMovieSelectionScene(final Stage primaryStage) {

        VBox movieSelection = new VBox();
        movieSelection.setPadding(new Insets(10));
        movieSelection.setSpacing(8);
        movieSelection.setAlignment(Pos.CENTER);

        DatePicker datePicker = new DatePicker();
        LocalDate today = LocalDate.now();

        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(today) || date.isAfter(today.plusDays(6)));
            }
        });
        datePicker.setValue(today);
        movieSelection.getChildren().add(datePicker);


        Label title = new Label("Wybierz film:");
        movieSelection.getChildren().add(title);
        int moviecount = 0;
        for (Movie movie : movies) {
            Button newButton = new Button(movie.getName());
            int finalMoviecount = moviecount;
            newButton.setOnAction(e -> {

                if(movies[finalMoviecount].allSeatsOccupied(datePicker.getValue()) && user.getUserTickets().stream().noneMatch(x-> x.getMovieName().equals(movies[finalMoviecount].getName()) && x.getDate().equals(datePicker.getValue()))){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacja");
                    alert.setHeaderText("Wszystkie miejsca zajete");
                    alert.showAndWait();
                } else {
                    createSeatsScene(primaryStage, finalMoviecount, datePicker.getValue());
                    primaryStage.setScene(seatsScene);
                }


            });
            movieSelection.getChildren().add(newButton);
            moviecount++;
        }

        Button backButton = new Button("Wroc");
        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));
        movieSelection.getChildren().add(backButton);
        movieSelectionScene = new Scene(movieSelection, 300, 250);
    }



    private void createSeatsScene(final Stage primaryStage, int movienumber, LocalDate selectedDate) {

        boolean[][] newSeats = movies[movienumber].getAllSeats(selectedDate);
        boolean[][] filledSeats;
        if(newSeats != null){
           filledSeats = newSeats;
        } else {
            filledSeats = new boolean[movies[movienumber].getRows()][movies[movienumber].getCols()];
        }

        boolean[][] seatscopy = Arrays.stream(filledSeats).map(el -> Arrays.copyOf(el, el.length)).toArray(boolean[][]::new);

        GridPane seatSelectionPane = new GridPane();
        ArrayList<Ticket> tickets = new ArrayList<>();

        ArrayList<Ticket> userTickets = user.getUserTickets();

        Button buyButton = new Button("Zarezerwuj");
        buyButton.setVisible(false);
        buyButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            StringBuilder result = new StringBuilder();

            for(Ticket T : tickets){
                result.append("Film: ").append(T.getMovieName()).append(" rzad: ").append(T.getSeatRow()+1).append(" miejsce: ").append(T.getSeatCol()+1).append(" \n");
            }
            if(tickets.size()>1){
                alert.setHeaderText("Zarezerwowano miejsca na dzien - "+ selectedDate);
            } else {
                alert.setHeaderText("Zarezerwowano miejsce  na dzien - "+ selectedDate);
            }
            alert.setContentText(result.toString());
            alert.showAndWait();
            user.addMultipleTicketsToUser(tickets);
            movies[movienumber].insertValue(seatscopy, selectedDate);
            primaryStage.setScene(movieSelectionScene);

        });

        Button backButton = new Button("Wroc");
        backButton.setOnAction(e -> primaryStage.setScene(movieSelectionScene));

        seatSelectionPane.add(buyButton, seatscopy[0].length, 0);
        seatSelectionPane.add(backButton, seatscopy[0].length, 1);


        for (int row = 0; row < seatscopy.length; row++) {
            for (int col = 0; col < seatscopy[row].length; col++) {
                Button seatButton = new Button();
                seatButton.setPrefWidth(50);
                seatButton.setPrefHeight(50);

                int finalRow = row;
                int finalCol = col;


                if(userTickets.stream().anyMatch(x-> x.getSeatRow() == finalRow && x.getSeatCol() == finalCol && x.getMovieName().equals(movies[movienumber].getName()) && x.getDate().equals(selectedDate))){
                    seatButton.setStyle("-fx-background-color: yellow;");
                }
                else if (seatscopy[row][col]) {
                    seatButton.setStyle("-fx-background-color: red;");
                } else {
                    seatButton.setStyle("-fx-background-color: green;");
                }

                seatButton.setOnAction(e -> {
                    int seatRow = GridPane.getRowIndex(seatButton);
                    int seatCol = GridPane.getColumnIndex(seatButton);



                    if(!seatButton.getStyle().equals("-fx-background-color: red;") && !seatButton.getStyle().equals("-fx-background-color: yellow;")){
                        if (seatscopy[seatRow][seatCol]) {
                            seatButton.setStyle("-fx-background-color: green;");
                            seatscopy[seatRow][seatCol] = false;
                            tickets.removeIf(x-> x.getSeatRow() == seatRow && x.getSeatCol() == seatCol);
                            if(tickets.size() == 0){
                                buyButton.setVisible(false);
                            }

                        } else {
                            seatButton.setStyle("-fx-background-color: grey;");
                            seatscopy[seatRow][seatCol] = true;
                            buyButton.setVisible(true);
                            tickets.add(new Ticket(seatCol, seatRow, movies[movienumber].getName(), selectedDate));
                        }
                    }


                });
                seatButton.setText(row + 1 + ", " + (col + 1));
                seatSelectionPane.add(seatButton, col, row);
            }
        }

        seatsScene = new Scene(seatSelectionPane, 600, 400);
    }
}
