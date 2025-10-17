package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;



public class QueryOne {

    // Takes database connection parameter (con) which is needed for executing queries
    public static void queryOne(Connection con) throws SQLException {

        // Display menu options
        System.out.println("COUNTRY POPULATION INFORMATION\n" +
                "1 - All the countries in the world organised by largest population to smallest.\n" +
                "2 - All the countries in a continent organised by largest population to smallest.\n" +
                "3 - All the countries in a region organised by largest population to smallest\n" +
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
                QueryUtils.question(con, "", "countries", false);
                break;
            case 2:
                QueryUtils.question(con, "Continent", "countries", false);
                break;
            case 3:
                QueryUtils.question(con, "Region", "countries", false);
                break;
        }

        // Return to submenu
        queryOne(con);
    }
}
