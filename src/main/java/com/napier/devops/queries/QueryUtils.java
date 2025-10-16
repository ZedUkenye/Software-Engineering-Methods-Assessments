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

        // Get user input
        String inputArea = input.nextLine();

        // Loop to check if input is valid
        while (true) {
            // Reset cursor to before the first row of the ResultSet to re-iterate
            result.beforeFirst();

            // Check if input matches any available area by iterating through ResultSet
            while (result.next()) {
                //if match found, return the valid input
                // check each input against the first and only column of the ResultSet
                if (result.getString(1).equalsIgnoreCase(inputArea)) {
                    return inputArea;
                }
            }
            // If no match found, prompt user to enter a valid area name
            System.out.println("Please enter a valid " + area.toLowerCase() + " name:");
            System.out.print("Select an option: ");
            inputArea = input.nextLine();
        }
    }

    // Method to get query limit from user
    private static int setQueryLimit() {
        System.out.println("Please enter the number of results you would like to see (N):");
        System.out.println("Enter 0 to see all results.");
        // Get and validate user input using getUserInput method
        int choice = MainMenu.getUserInput(0, Integer.MAX_VALUE);
        // If 0 is entered, set to max int value to show all results
        if (choice == 0) {
            choice = Integer.MAX_VALUE;
        }
        return choice;
    }


    // Main method to handle user questions and execute queries
    public static void question(Connection con, String area, String questionType, Boolean selectLimit) throws SQLException {

        // Initialize variables
        ResultSet result = null;
        String inputArea = "";
        String sql = "";
        int queryLimit = 0;

        // Create Scanner object for user input
        Scanner input = new Scanner(System.in);

        //used to send queries to the database
        Statement stmt = con.createStatement();

        // If area is NOT empty, display available areas and get user input
        if (!area.isEmpty()) {

            // Query to get distinct area names from the database
            if (area.equals("District")) {
                result = stmt.executeQuery("SELECT DISTINCT " + area + " FROM city;");
            } else {
                result = stmt.executeQuery("SELECT DISTINCT " + area + " FROM country;");
            }

            System.out.println("\nAvailable " + area.toLowerCase() + ":");

            // Display available areas
            while (result.next()) {
                // print each area name from the ResultSet first and only column
                System.out.println("- " + result.getString(1));
            }

            System.out.println("Please enter the " + area.toLowerCase() + " you would like to see the cities of:");
            System.out.print("Select an option: ");


            // Get and validate user input
            inputArea = QueryUtils.checkValidInput(input, result, area);
        }

        // If selectLimit is true get limit input from user
        if (selectLimit) {
            queryLimit = QueryUtils.setQueryLimit();
        }

        // Create SQL WHERE clause based on area and user input
        String district = "";

        //ony add where if area is not empty
        if (!area.isEmpty()){
            // Determine the correct column to filter on based on area type
            String column = area.equals("District") ? "city." : "country.";
            // Construct the WHERE clause
            district = "WHERE " + column + area + " = '" + inputArea + "' ";
        }


        //sql select statement based on question type
        if (questionType.equals("countries")) {
            sql = ("SELECT country.Code, country.Name AS country_name, country.Continent, country.Region, country.Population, city.name AS city_name " +
                    "FROM country " +
                    "JOIN city ON city.Id = country.Capital " +
                    // WHERE clause if area is not empty
                    district +
                    "ORDER BY country.Population DESC " +
                    (selectLimit ? "LIMIT " + queryLimit + ";" : ";")
            );
        } else if (questionType.equals("cities")) {
            sql = ("SELECT city.Name AS city_name, country.Name AS country_name, city.District, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    // WHERE clause if area is not empty
                    district +
                    "ORDER BY city.Population DESC " +
                    (selectLimit ? "LIMIT " + queryLimit + ";" : ";")
            );
        } else if (questionType.equals("capital")) {
            sql = ("SELECT city.Name AS city_name, country.Name AS country_name, city.Population " +
                    "FROM city " +
                    "JOIN country ON country.Capital = city.ID " +
                    // WHERE clause if area is not empty
                    district +
                    "ORDER BY city.Population DESC " +
                    (selectLimit ? "LIMIT " + queryLimit + ";" : ";")
            );
        }

        //used to send queries to the database
        ResultSet rset = stmt.executeQuery(sql);

        //display query results
        QueryUtils.displayQueryResults(rset, questionType);

        //close the ResultSet's, Statement and result if not null after running displayQueryResults
        rset.close();
        stmt.close();
        if (result != null) {
            result.close();
        }
        System.out.println("Enter to continue...");
        // Wait for user input
        input.nextLine();
    }

    private static void displayQueryResults(ResultSet rset, String questionType) throws SQLException {
        try {
            // Display query results
            while (rset.next()) {

                // Print results based on question type
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
