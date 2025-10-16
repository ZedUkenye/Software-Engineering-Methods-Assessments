package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;
import java.util.Scanner;



public class QueryFour {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryFour(Connection con) throws SQLException {

        // Create Scanner object for user input
        System.out.println("TOP POPULATED CITIES\n" +
                "1 - The top N populated cities in the world where N is provided by the user.\n" +
                "2 - The top N populated cities in a continent where N is provided by the user.\n" +
                "3 - The top N populated cities in a region where N is provided by the user.\n" +
                "4 - The top N populated cities in a country where N is provided by the user.\n" +
                "5 - The top N populated cities in a district where N is provided by the user.\n" +
                "0 - Return to Main Menu\n"
        );


        //calls method to get user input making sure it's in range
        int numberInput = MainMenu.getUserInput(0, 5);


        // run method based on user input
        switch (numberInput) {
            case 0:
                MainMenu.menu(con);
                break;
            case 1:
                QueryUtils.question(con, "", "cities", true);
                break;
            case 2:
                QueryUtils.question(con, "Continent", "cities", true);
                break;
            case 3:
                QueryUtils.question(con, "Region", "cities", true);
                break;
            case 4:
                QueryUtils.question(con, "Name", "cities", true);
                break;
            case 5:
                QueryUtils.question(con, "District", "cities", true);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        // Return to submenu
        queryFour(con);
    }
}



