package com.napier.devops;
import java.util.Scanner;  // Import the Scanner class
import java.sql.*;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to Database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(10000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected TEST");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    public void countryPop() throws SQLException {}
    public void countryTop() throws SQLException {}
    public void cityTop() throws SQLException {}
    public void popInformation() throws SQLException {}
    public void languageInfo() throws SQLException {}
    public void capitalCity() throws SQLException {}
    public void capitalCityTop() throws SQLException {}
    public void totalPop() throws SQLException {}

/*
*All the cities in the world organised by largest population to smallest.
All the cities in a continent organised by largest population to smallest.
All the cities in a region organised by largest population to smallest.
All the cities in a country organised by largest population to smallest.
All the cities in a district organised by largest population to smallest.
* */
    public void cityPop() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT * FROM city LIMIT 20;");
        while (rset.next()) {
            System.out.println(rset.getString("Name"));
        }

    }

// class to create a main menu to direct users
    public static void mainMenu() {
    int item;
        Scanner input = new Scanner(System.in);

        // Create new Application
    App a = new App();

    // Connect to database
    a.connect();

        /* create a menu in which the user will select
           which report they would like to view
         */
    System.out.println("Welcome to the main menu!\n 1-  countries population information\n 2- top number of countries \n 3- cities population information \n 4- top populated cities \n 5- capital city information\n 6- top populated capital cities\n 7- people population information 8 - total populations 9- language information");
    // create new variable called menuItem of type int that will store input from keyboard
        System.out.println("Select an option: ");
        if(input.hasNext()) {
            item = input.nextInt();
        }
        else{
            item = 0;
        }
        // switch statment to guide user to the needed method
    switch (item) {
        case 1:

            try {
                a.countryPop();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        case 2:
            try {
                a.countryTop();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        case 3:
            try {
                a.cityPop();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        case 4:
            try {
                a.cityTop();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        case 5:
            try {
                a.capitalCity();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        case 6:
            try {
                a.capitalCityTop();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        case 7:
            try {
                a.popInformation();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        case 8:
            try {
                a.totalPop();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        case 9:
            try {
                a.languageInfo();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

    }
    // Disconnect from database
    a.disconnect();
 }


    public static void main(String[] args)
    {
        mainMenu();



        }


    }
