package com.napier.devops;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    // class to create a main menu to direct users
    public static void menu() {
        int item;
        Scanner input = new Scanner(System.in);

        // Create new Application

        Queries q = new Queries();

        // Connect to database
        q.connect();

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
                    q.countryPop();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);
            case 2:
                try {
                    q.countryTop();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);
            case 3:
                try {
                    q.cityPop();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);
            case 4:
                try {
                    q.cityTop();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);
            case 5:
                try {
                    q.capitalCity();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);
            case 6:
                try {
                    q.capitalCityTop();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);
            case 7:
                try {
                    q.popInformation();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);
            case 8:
                try {
                    q.totalPop();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);
            case 9:
                try {
                    q.languageInfo();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.exit(0);

        }
        // Disconnect from database
        q.disconnect();
    }
}
