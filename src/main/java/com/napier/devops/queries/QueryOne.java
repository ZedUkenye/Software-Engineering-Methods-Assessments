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
                questionType1(con);
                break;
            case 2:
                questionType2(con, "Continent", input);
                break;
            case 3:
                questionType2(con, "Region", input);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    // All the countries in the world organised by largest population to smallest.
    private static void questionType1(Connection con) throws SQLException {

        //sql select statement
        String sql = ("SELECT c.Code, c.Name AS country_name, c.Continent, c.Region, c.Population, city.name AS city_name " +
                "FROM country c " +
                "JOIN city ON city.Id = c.Capital " +
                "ORDER BY c.Population DESC;"
        );

        //used to send queries to the database
        Statement stmt = con.createStatement();

        //used to store the results of queries
        ResultSet rset = stmt.executeQuery(sql);

        //display query results
        QueryUtils.displayQueryResultsCountry(rset, stmt);

        // Return to submenu
        queryOne(con);
    }

    // All the countries in a chosen area organised by largest population to smallest.
    private static void questionType2(Connection con, String area, Scanner input) throws SQLException {

        //used to send queries to the database
        Statement stmt = con.createStatement();

        //store the results of the query to get available areas
        ResultSet resultSetArea = stmt.executeQuery("SELECT DISTINCT " + area + " FROM country;");
        System.out.println("\nAvailable " + area.toLowerCase() + ":");

        // Display available areas
        while (resultSetArea.next()) {
            System.out.println("- " + resultSetArea.getString(area));
        }

        System.out.println("Please enter the " + area.toLowerCase() + " you would like to see the countries of:");

        // Get user input and validate
        String inputArea = QueryUtils.checkValidInput(input, resultSetArea, area);

        //sql select statement
        String sql = ("SELECT c.Code, c.Name AS country_name, c.Continent, c.Region, c.Population, city.name AS city_name " +
                "FROM country c " +
                "JOIN city ON city.Id = c.Capital " +
                "WHERE c." + area + " = '" + inputArea + "' " +
                "ORDER BY c.Population DESC;"
        );


        //used to send queries to the database
        ResultSet rset = stmt.executeQuery(sql);

        //display query results
        QueryUtils.displayQueryResultsCountry(rset, stmt);

        // Return to submenu
        queryOne(con);
    }
}


