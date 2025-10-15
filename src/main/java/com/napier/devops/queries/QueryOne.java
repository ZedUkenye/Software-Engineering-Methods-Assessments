package com.napier.devops.queries;

import com.napier.devops.MainMenu;

import java.sql.*;
import java.util.Scanner;



public class QueryOne {

    // Takes database connection parameter (con) which is need for executing queries
    public static void queryOne(Connection con) throws SQLException {

        // Create Scanner object for user input
        Scanner input = new Scanner(System.in);

        System.out.println("COUNTRY POPULATION INFORMATION\n" +
                "1 - All the countries in the world organised by largest population to smallest.\n" +
                "2 - All the countries in a continent organised by largest population to smallest.\n" +
                "3 - All the countries in a region organised by largest population to smallest\n" +
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
        String sql = "SELECT * FROM country ORDER BY Population DESC";

        //used to send queries to the database
        Statement stmt = con.createStatement();

        //used to store the results of queries
        ResultSet rset = stmt.executeQuery(sql);

        //process query results
        try {
            while (rset.next()) {
                System.out.println(rset.getString("Code") + " " +
                        rset.getString("Name") + " " +
                        rset.getString("Continent") + " " +
                        rset.getString("Region") + " " +
                        rset.getInt("Population") + " " +
                        rset.getInt("Capital")
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
            new Scanner(System.in).nextLine(); // Wait for user input
            // Return to QueryOne menu
            queryOne(con);
        }
    }

    // All the countries in a chosen area organised by largest population to smallest.
    private static void caseTwoAndThree(Connection con, String area, Scanner input) throws SQLException {

        //used to send queries to the database
        Statement stmt = con.createStatement();

        //store the results of the query
        ResultSet resultSetArea = stmt.executeQuery("SELECT DISTINCT " + area + " FROM country;");
        System.out.println("\nAvailable " + area.toLowerCase() + ":");

        // Display available areas
        while (resultSetArea.next()) {
            System.out.println("- " + resultSetArea.getString(area));
        }

        System.out.println("Please enter the " + area.toLowerCase() + " you would like to see the countries of:");

        // Get user input and validate
        String inputArea = input.nextLine();

        // Flag to track if input is valid
        boolean validInput = false;

        // Loop until valid input is received
        while (!validInput) {
            // Reset cursor to before the first row
            resultSetArea.beforeFirst();

            // Check if input matches any available area
            while (resultSetArea.next()) {
                if (resultSetArea.getString(area).equalsIgnoreCase(inputArea)) {
                    validInput = true;
                    break;
                }
            }
            // If input is not valid
            if (!validInput) {
                System.out.println("Please enter a valid " + area.toLowerCase() + "name:");
                inputArea = input.nextLine();
            }
        }

        String sql = "SELECT * FROM country WHERE " + area + " = '" + inputArea + "' ORDER BY Population DESC";

        //used to send queries to the database
        ResultSet rset = stmt.executeQuery(sql);

        //process query results
        try {
            while (rset.next()) {
                System.out.println(rset.getString("Code") + " " +
                        rset.getString("Name") + " " +
                        rset.getString("Continent") + " " +
                        rset.getString("Region") + " " +
                        rset.getInt("Population") + " " +
                        rset.getInt("Capital")
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
            resultSetArea.close();
            System.out.println("Enter to continue...");
            new Scanner(System.in).nextLine(); // Wait for user input
            // Return to QueryOne menu
            queryOne(con);

        }
    }

}


