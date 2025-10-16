package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;



public class QueryTwo {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryTwo(Connection con) throws SQLException {

        // Display menu options
        System.out.println("TOP POPULATED COUNTRIES\n" +
                "1 - The top N populated countries in the world where N is provided by the user.\n" +
                "2 - The top N populated countries in a continent where N is provided by the user.\n" +
                "3 - The top N populated countries in a region where N is provided by the user.\n" +
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
                // area = what area the user wants to filter by (WHERE clause)
                // questionType = what type of question (countries, cities, capital)
                // selectLimit = whether to limit the number of results based on user input (LIMIT clause)
                QueryUtils.question(con, "", "countries", true);
                break;
            case 2:
                QueryUtils.question(con, "Continent", "countries", true);
                break;
            case 3:
                QueryUtils.question(con, "Region", "countries", true);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        // Return to submenu
        queryTwo(con);
    }
}
