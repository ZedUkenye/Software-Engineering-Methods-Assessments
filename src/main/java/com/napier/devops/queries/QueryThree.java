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
                caseOne(con);
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
    private static void caseOne(Connection con) throws SQLException {

        //sql select statement
        String sql = ("SELECT city.Name AS city_name, country.Name AS country_name, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY city.Population DESC;"
        );

        //used to send queries to the database
        Statement stmt = con.createStatement();

        //used to store the results of queries
        ResultSet rset = stmt.executeQuery(sql);

        //display query results
        try {
            // Display query results
            while (rset.next()) {
                System.out.println(rset.getString("city_name") + " " +
                        rset.getString("country_name") + " " +
                        rset.getString("District") + " " +
                        rset.getInt("Population")
                );
            }
        }
        //handle any SQL errors
        catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        // close the ResultSet's and Statement
        finally {
            rset.close();
            stmt.close();
            System.out.println("Enter to continue...");
            // Wait for user input
            new Scanner(System.in).nextLine();
        }


        // Return to submenu
        queryThree(con);

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

        //sql select statement
        String sql = ("SELECT c.Code, c.Name AS country_name, c.Continent, c.Region, c.Population, city.name AS city_name " +
                "FROM country c " +
                "JOIN city ON city.Id = c.Capital " +
                "WHERE c." + area + " = '" + inputArea + "' " +
                "ORDER BY c.Population DESC " +
                "LIMIT " + queryLimit + ";"
        );

        //used to send queries to the database
        ResultSet rset = stmt.executeQuery(sql);

        //display query results
        QueryUtils.displayQueryResults(rset, result, stmt);

        // Return to submenu
        queryThree(con);

    }
}


