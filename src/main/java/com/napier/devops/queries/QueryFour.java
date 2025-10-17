package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;



public class QueryFour {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryFour(Connection con) throws SQLException {

        // Display menu options
        System.out.println("TOP POPULATED CITIES\n" +
                "1 - The top N populated cities in the world where N is provided by the user.\n" +
                "2 - The top N populated cities in a continent where N is provided by the user.\n" +
                "3 - The top N populated cities in a region where N is provided by the user.\n" +
                "4 - The top N populated cities in a country where N is provided by the user.\n" +
                "5 - The top N populated cities in a district where N is provided by the user.\n" +
                "0 - Return to Main Menu\n"
        );

        //calls getUserInput method to get user input making sure it's an int and in the set range
        int numberInput = MainMenu.getUserInput(0, 5);


        // call method based on user input
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



