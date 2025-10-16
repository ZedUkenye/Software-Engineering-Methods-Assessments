package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;
import java.util.Scanner;



public class QueryThree {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryThree(Connection con) throws SQLException {

        // Create Scanner object for user input
        Scanner input = new Scanner(System.in);

        System.out.println("CITY POPULATION INFORMATION\n" +
                "1 - All the cities in the world organised by largest population to smallest.\n" +
                "2 - All the cities in a continent organised by largest population to smallest.\n" +
                "3 - All the cities in a region organised by largest population to smallest.\n" +
                "4 - All the cities in a country organised by largest population to smallest.\n" +
                "5 - All the cities in a district organised by largest population to smallest.\n" +
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
                QueryUtils.question(con, "", "cities", false);
                break;
            case 2:
                QueryUtils.question(con, "Continent", "cities", false);
                break;
            case 3:
                QueryUtils.question(con, "Region", "cities", false);
                break;
            case 4:
                QueryUtils.question(con, "Name", "cities", false);
                break;
            case 5:
                QueryUtils.question(con, "District", "cities", false);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        // Return to submenu
        queryThree(con);
    }
}

