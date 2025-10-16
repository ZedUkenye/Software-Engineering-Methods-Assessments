package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class QueryUtils {

    // Method to validate user input against database results
    public static String checkValidInput(Scanner input, ResultSet result, String area) throws SQLException {

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
    public static int setQueryLimit() {
        System.out.println("Please enter the number of results you would like to see (N):");
        System.out.println("Enter 0 to see all results.");
        System.out.print("Select an option: ");
        // Get and validate user input using MainMenu method
        int choice = MainMenu.getUserInput(0, Integer.MAX_VALUE);
        // If 0 is entered, set to max int value to show all results
        if (choice == 0){
            choice = Integer.MAX_VALUE;
        }
        return choice;
    }



    // Method to display query results
    public static void displayQueryResultsCountry(ResultSet rset, Statement stmt) throws SQLException {
        try {
            // Display query results
            while (rset.next()) {
                System.out.println(rset.getString("Code") + " " +
                        rset.getString("country_name") + " " +
                        rset.getString("Continent") + " " +
                        rset.getString("Region") + " " +
                        rset.getInt("Population") + " " +
                        rset.getString("city_name")
                );
            }
        }
        //handle any SQL errors
        catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        // close the ResultSet and Statement
        finally {
            rset.close();
            stmt.close();
            System.out.println("Enter to continue...");
            // Wait for user input
            new Scanner(System.in).nextLine();
        }
    }

    // Overloaded method to display query results with additional ResultSet parameter (USED WHEN SELECTING AN AREA)
    public static void displayQueryResultsCountry(ResultSet rset, ResultSet result, Statement stmt) throws SQLException {
        try {
            // Display query results
            while (rset.next()) {
                System.out.println(rset.getString("Code") + " " +
                        rset.getString("country_name") + " " +
                        rset.getString("Continent") + " " +
                        rset.getString("Region") + " " +
                        rset.getInt("Population") + " " +
                        rset.getString("city_name")
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
            result.close();
            System.out.println("Enter to continue...");
            // Wait for user input
            new Scanner(System.in).nextLine();
        }
    }

    public static void displayQueryResultsCity(ResultSet rset, Statement stmt) throws SQLException {
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
    }

    public static void displayQueryResultsCity(ResultSet rset, ResultSet result, Statement stmt) throws SQLException {
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
            result.close();
            System.out.println("Enter to continue...");
            // Wait for user input
            new Scanner(System.in).nextLine();
        }
    }



}
