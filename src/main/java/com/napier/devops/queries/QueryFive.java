package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;


public class QueryFive {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryFive(Connection con) throws SQLException {

        // Display menu options
        System.out.println("TOP POPULATED CITIES\n" +
                "1 - All the capital cities in the world organised by largest population to smallest.\n" +
                "2 - All the capital cities in a continent organised by largest population to smallest.\n" +
                "3 - All the capital cities in a region organised by largest to smallest.\n" +
                "0 - Return to Main Menu\n"
        );


        //calls getUserInput method to get user input making sure it's an int and in the set range
        int numberInput = MainMenu.getUserInput(0, 3);


        // call method based on user input
        switch (numberInput) {
            case 0:
                MainMenu.menu(con);
                break;
            case 1:
                QueryUtils.question(con, "", "capital", false);
                break;
            case 2:
                QueryUtils.question(con, "Continent", "capital", false);
                break;
            case 3:
                QueryUtils.question(con, "Region", "capital", false);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        // Return to submenu
        queryFive(con);
    }
}



