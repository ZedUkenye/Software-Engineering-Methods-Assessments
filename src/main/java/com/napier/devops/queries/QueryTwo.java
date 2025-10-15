package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;
import java.util.Scanner;



public class QueryTwo {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryTwo(Connection con) throws SQLException {

        // Create Scanner object for user input
        Scanner input = new Scanner(System.in);

        System.out.println("TOP POPULATED COUNTRIES\n" +
                "1 - The top N populated countries in the world where N is provided by the user.\n" +
                "2 - The top N populated countries in a continent where N is provided by the user.\n" +
                "3 - The top N populated countries in a region where N is provided by the user.\n" +
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
                caseOne(con, input);
                break;
            case 2:
                caseTwoAndThree(con, "Continent", input);
                break;
            case 3:
                caseTwoAndThree(con, "Region", input);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    // All the countries in the world organised by largest population to smallest.
    private static void caseOne(Connection con, Scanner input) throws SQLException {

        int queryLimit = QueryUtils.setQueryLimit();

        String sql = ("SELECT c.Code, c.Name AS country_name, c.Continent, c.Region, c.Population, city.name AS city_name " +
                "FROM country c " +
                "JOIN city ON city.Id = c.Capital " +
                "ORDER BY c.Population DESC " +
                "LIMIT " + queryLimit + ";"
        );


        //used to send queries to the database
        Statement stmt = con.createStatement();

        //used to store the results of queries
        ResultSet rset = stmt.executeQuery(sql);

        //process query results
        QueryUtils.processQueryResults(rset, stmt);

        // Return to submenu
        queryTwo(con);

    }

    // All the countries in a chosen area organised by largest population to smallest.
    private static void caseTwoAndThree(Connection con, String area, Scanner input) throws SQLException {

        //used to send queries to the database
        Statement stmt = con.createStatement();

        //store the results of the query
        ResultSet result = stmt.executeQuery("SELECT DISTINCT " + area + " FROM country;");
        System.out.println("\nAvailable " + area.toLowerCase() + ":");

        // Display available areas
        while (result.next()) {
            System.out.println("- " + result.getString(area));
        }

        System.out.println("Please enter the " + area.toLowerCase() + " you would like to see the countries of:");
        System.out.print("Select an option: ");


        // Get and validate user input
        String inputArea = QueryUtils.checkValidInput(input, result, area);

        // Get query limit from user
        int queryLimit = QueryUtils.setQueryLimit();

        String sql = ("SELECT c.Code, c.Name AS country_name, c.Continent, c.Region, c.Population, city.name AS city_name " +
                "FROM country c " +
                "JOIN city ON city.Id = c.Capital " +
                "WHERE c." + area + " = '" + inputArea + "' " +
                "ORDER BY c.Population DESC " +
                "LIMIT " + queryLimit + ";"
        );

        //used to send queries to the database
        ResultSet rset = stmt.executeQuery(sql);

        //process query results
        QueryUtils.processQueryResults(rset, result, stmt);

        // Return to submenu
        queryTwo(con);

    }
}


