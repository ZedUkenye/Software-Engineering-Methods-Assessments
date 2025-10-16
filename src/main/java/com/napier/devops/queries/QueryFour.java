package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;
import java.util.Scanner;



public class QueryFour {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryFour(Connection con) throws SQLException {

        // Create Scanner object for user input
        Scanner input = new Scanner(System.in);

        System.out.println("TOP POPULATED CITIES\n" +
                "1 - The top N populated cities in the world where N is provided by the user.\n" +
                "2 - The top N populated cities in a continent where N is provided by the user.\n" +
                "3 - The top N populated cities in a region where N is provided by the user.\n" +
                "4 - The top N populated cities in a country where N is provided by the user.\n" +
                "5 - The top N populated cities in a district where N is provided by the user.\n" +
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
                questionType1(con);
                break;
            case 2:
                questionType2(con, "Continent", input);
                break;
            case 3:
                questionType2(con, "Region", input);
                break;
            case 4:
                questionType2(con, "Name", input);
                break;
            case 5:
                questionType2(con, "District", input);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    // All the countries in the world organised by largest population to smallest.
    private static void questionType1(Connection con) throws SQLException {

        // Get query limit from user
        int queryLimit = QueryUtils.setQueryLimit();


        //sql select statement
        String sql = ("SELECT city.Name AS city_name, country.Name AS country_name, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY city.Population DESC" +
                " LIMIT " + queryLimit + ";"
        );

        //used to send queries to the database
        Statement stmt = con.createStatement();

        //used to store the results of queries
        ResultSet rset = stmt.executeQuery(sql);

        //display query results
        QueryUtils.displayQueryResultsCity(rset, stmt);

        // Return to submenu
        queryFour(con);

    }

    // All the countries in a chosen area organised by largest population to smallest.
    private static void questionType2(Connection con, String area, Scanner input) throws SQLException {

        //used to send queries to the database
        Statement stmt = con.createStatement();

        //store the results of the query
        // Different query if area is District as it is in city table not country table
        ResultSet result;

        if (area.equals("District")) {
            result = stmt.executeQuery("SELECT DISTINCT " + area + " FROM city;");
        }
        else {
            result = stmt.executeQuery("SELECT DISTINCT " + area + " FROM country;");
        }

        System.out.println("\nAvailable " + area.toLowerCase() + ":");
        // Display available areas
        while (result.next()) {
            System.out.println("- " + result.getString(area));
        }

        System.out.println("Please enter the " + area.toLowerCase() + " you would like to see the cities of:");
        System.out.print("Select an option: ");


        // Get and validate user input
        String inputArea = QueryUtils.checkValidInput(input, result, area);

        // Get query limit from user
        int queryLimit = QueryUtils.setQueryLimit();

        //sql select statement
        String sql = ("SELECT city.Name AS city_name, country.Name AS country_name, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE " + (area.equals("District") ? "city." : "country.") + area + " = '" + inputArea + "' " +
                "ORDER BY city.Population DESC " +
                "LIMIT " + queryLimit + ";"
        );

        //used to send queries to the database
        ResultSet rset = stmt.executeQuery(sql);

        //display query results
        QueryUtils.displayQueryResultsCity(rset, result, stmt);

        // Return to submenu
        queryFour(con);

    }
}


