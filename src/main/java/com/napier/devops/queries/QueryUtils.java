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

    public static int setQueryLimit() {
        System.out.println("Please enter the number of results you would like to see (N):");
        return MainMenu.getUserInput(1, Integer.MAX_VALUE);
    }




    public static void processQueryResults(ResultSet rset, Statement stmt) throws SQLException {
        try {
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
            System.out.println("Enter to continue...");
            new Scanner(System.in).nextLine(); // Wait for user input
        }
    }

    public static void processQueryResults(ResultSet rset, ResultSet result, Statement stmt) throws SQLException {
        try {
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
            new Scanner(System.in).nextLine(); // Wait for user input
        }
    }



}
