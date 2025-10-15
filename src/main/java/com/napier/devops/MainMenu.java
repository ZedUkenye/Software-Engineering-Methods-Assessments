package com.napier.devops;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    // class to create a main menu to direct users
    public static void menu() {
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
}
