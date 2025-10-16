package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;
import java.util.Scanner;



public class QueryOne {

    // Takes database connection parameter (con) which is needed for executing queries
    public static void queryOne(Connection con) throws SQLException {

        // Create Scanner object for user input
        Scanner input = new Scanner(System.in);

        System.out.println("COUNTRY POPULATION INFORMATION\n" +
                "1 - All the countries in the world organised by largest population to smallest.\n" +
                "2 - All the countries in a continent organised by largest population to smallest.\n" +
                "3 - All the countries in a region organised by largest population to smallest\n" +
                "0 - Return to Main Menu\n"
        );


        //calls getUserInput method to get user input making sure it's in range
        int numberInput = MainMenu.getUserInput(0, 3);


        // run method based on user input
        switch (numberInput) {
            case 0:
                MainMenu.menu(con);
                break;
            case 1:
                QueryUtils.question(con, "", "countries", false);
                break;
            case 2:
                QueryUtils.question(con, "Continent", "countries", false);
                break;
            case 3:
                QueryUtils.question(con, "Region", "countries", false);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        // Return to submenu
        queryOne(con);
    }
}
