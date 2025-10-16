package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class QueryUtils {

    // Method to validate user input against database results
    private static String checkValidInput(Scanner input, ResultSet result, String area) throws SQLException {

        // Get user input and validate
        String inputArea = input.nextLine();

        // Loop until valid input is received
        while (true) {
            // Reset cursor to before the first row
            result.beforeFirst();

            // Check if input matches any available area
            while (result.next()) {
                if (result.getString(area).equalsIgnoreCase(inputArea)) {
                    return inputArea;
                }
            }
            System.out.println("Please enter a valid " + area.toLowerCase() + " name:");
            System.out.print("Select an option: ");
            inputArea = input.nextLine();
        }
    }

    // Method to get query limit from user
    private static int setQueryLimit() {
        System.out.println("Please enter the number of results you would like to see (N):");
        System.out.println("Enter 0 to see all results.");
        // Get and validate user input using MainMenu method
        int choice = MainMenu.getUserInput(0, Integer.MAX_VALUE);
        // If 0 is entered, set to max int value to show all results
        if (choice == 0) {
            choice = Integer.MAX_VALUE;
        }
        return choice;
    }


    public static void question(Connection con, String area, String questionType, Boolean selectLimit) throws SQLException {

        // Create Scanner object for user input
        Scanner input = new Scanner(System.in);


        //used to send queries to the database
        Statement stmt = con.createStatement();


        ResultSet result = null;
        String inputArea = "";
        int queryLimit = 0;

        if (!area.isEmpty()) {

            if (area.equals("District")) {
                result = stmt.executeQuery("SELECT DISTINCT " + area + " FROM city;");
            } else {
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
            inputArea = QueryUtils.checkValidInput(input, result, area);
        }

        if (selectLimit) {
            queryLimit = QueryUtils.setQueryLimit();
        }

        String sql = "";


        // Create SQL WHERE clause based on area and user input
        String district = area.isEmpty() ? "" : "WHERE " + (area.equals("District") ? "city." : "country.") + area + " = '" + inputArea + "' ";

        //sql select statement based on question type
        if (questionType.equals("countries")) {
            sql = ("SELECT country.Code, country.Name AS country_name, country.Continent, country.Region, country.Population, city.name AS city_name " +
                    "FROM country " +
                    "JOIN city ON city.Id = country.Capital " +
                    district +
                    "ORDER BY c.Population DESC " +
                    (selectLimit ? "LIMIT " + queryLimit + ";" : ";")
            );
        } else if (questionType.equals("cities")) {
            sql = ("SELECT city.Name AS city_name, country.Name AS country_name, city.District, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    district +
                    "ORDER BY city.Population DESC " +
                    (selectLimit ? "LIMIT " + queryLimit + ";" : ";")
            );
        } else if (questionType.equals("capital")) {
            sql = ("SELECT city.Name AS city_name, country.Name AS country_name, city.Population " +
                    "FROM city " +
                    "JOIN country ON country.Capital = city.ID " +
                    district +
                    "ORDER BY city.Population DESC " +
                    (selectLimit ? "LIMIT " + queryLimit + ";" : ";")
            );
        }

        //used to send queries to the database
        ResultSet rset = stmt.executeQuery(sql);

        //display query results
        QueryUtils.displayQueryResults(rset, stmt, questionType);

        //close the ResultSet's, Statement and result if not null after displaying results
        rset.close();
        stmt.close();
        if (result != null) {
            result.close();
        }
        System.out.println("Enter to continue...");
        // Wait for user input
        input.nextLine();
    }

    private static void displayQueryResults(ResultSet rset, Statement stmt, String questionType) throws SQLException {
        try {
            // Display query results
            while (rset.next()) {

                if (questionType.equals("cities")) {
                    System.out.println(rset.getString("city_name") + " " +
                            rset.getString("country_name") + " " +
                            rset.getString("District") + " " +
                            rset.getInt("Population")
                    );
                } else if (questionType.equals("countries")) {
                    System.out.println(rset.getString("Code") + " " +
                            rset.getString("country_name") + " " +
                            rset.getString("Continent") + " " +
                            rset.getString("Region") + " " +
                            rset.getInt("Population") + " " +
                            rset.getString("city_name")
                    );
                } else if (questionType.equals("capital")) {
                    System.out.println(rset.getString("city_name") + " " +
                            rset.getString("country_name") + " " +
                            rset.getInt("Population")
                    );
                }
            }
        }
        //handle any SQL errors
        catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
