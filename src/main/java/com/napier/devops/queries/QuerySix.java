package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;
import java.util.Scanner;



public class QuerySix {

    // Takes database connection parameter (con) which is need for executing queries
    public static void querySix(Connection con) throws SQLException {

        // Create Scanner object for user input
        System.out.println("TOP POPULATED CITIES\n" +
                "1 - All the capital cities in the world organised by largest population to smallest.\n" +
                "2 - All the capital cities in a continent organised by largest population to smallest.\n" +
                "3 - All the capital cities in a region organised by largest to smallest.\n" +
                "0 - Return to Main Menu\n"
        );


        //calls method to get user input making sure it's in range
        int numberInput = MainMenu.getUserInput(0, 3);


        // run method based on user input
        switch (numberInput) {
            case 0:
                MainMenu.menu(con);
                break;
            case 1:
                QueryUtils.question(con, "", "capital", true);
                break;
            case 2:
                QueryUtils.question(con, "Continent", "capital", true);
                break;
            case 3:
                QueryUtils.question(con, "Region", "capital", true);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        // Return to submenu
        querySix(con);
    }
}



