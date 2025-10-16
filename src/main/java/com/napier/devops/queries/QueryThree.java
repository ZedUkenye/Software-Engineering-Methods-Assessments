package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;



public class QueryThree {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryThree(Connection con) throws SQLException {

        // Display menu options
        System.out.println("CITY POPULATION INFORMATION\n" +
                "1 - All the cities in the world organised by largest population to smallest.\n" +
                "2 - All the cities in a continent organised by largest population to smallest.\n" +
                "3 - All the cities in a region organised by largest population to smallest.\n" +
                "4 - All the cities in a country organised by largest population to smallest.\n" +
                "5 - All the cities in a district organised by largest population to smallest.\n" +
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
                // area = what area the user wants to filter by (WHERE clause)
                // questionType = what type of question (countries, cities, capital)
                // selectLimit = whether to limit the number of results based on user input (LIMIT clause)
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

