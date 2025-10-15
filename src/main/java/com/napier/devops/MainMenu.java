package com.napier.devops;

import com.napier.devops.queries.QueryOne;
import com.napier.devops.queries.QueryTwo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    // Display the main menu and handle user input
    public static void menu(Connection con) throws SQLException {

        System.out.println("MAIN MENU\n" +
                "1 - Country population information\n" +
                "2 - Top populated countries\n" +
                "3 - City population information\n" +
                "4 - Top populated cities\n" +
                "5 - Capital city information\n" +
                "6 - Top populated capital cities\n" +
                "7 - People population information\n" +
                "8 - Total populations\n" +
                "9 - Language informationn\n" +
                "0 - Exit\n");


        //calls method to get user input making sure it's in range
        int numberInput = getUserInput(0, 9);

        // run method based on user input
        switch (numberInput) {
            case 0:
                System.out.println("Exiting program.");
                System.exit(0);
                break;
            case 1:
//                All the countries in the world organised by largest population to smallest.
//                All the countries in a continent organised by largest population to smallest.
//                All the countries in a region organised by largest population to smallest.
                QueryOne.queryOne(con);
                break;
            case 2:
//                The top N populated countries in the world where N is provided by the user.
//                The top N populated countries in a continent where N is provided by the user.
//                The top N populated countries in a region where N is provided by the user.
                QueryTwo.queryTwo(con);
                break;
            case 3:
//                All the cities in the world organised by largest population to smallest.
//                All the cities in a continent organised by largest population to smallest.
//                All the cities in a region organised by largest population to smallest.
//                All the cities in a country organised by largest population to smallest.
//                All the cities in a district organised by largest population to smallest.
                System.out.println("case 3");
                break;
            case 4:
//                The top N populated cities in the world where N is provided by the user.
//                The top N populated cities in a continent where N is provided by the user.
//                The top N populated cities in a region where N is provided by the user.
//                The top N populated cities in a country where N is provided by the user.
//                The top N populated cities in a district where N is provided by the user.
                System.out.println("case 4");
                break;
            case 5:
//                All the capital cities in the world organised by largest population to smallest.
//                All the capital cities in a continent organised by largest population to smallest.
//                All the capital cities in a region organised by largest to smallest.
                System.out.println("case 5");
                break;
            case 6:

                System.out.println("case 6");
                break;
            case 7:
//                The top N populated capital cities in the world where N is provided by the user.
//                The top N populated capital cities in a continent where N is provided by the user.
//                The top N populated capital cities in a region where N is provided by the user.
                System.out.println("case 7");
                break;
            case 8:
//                The population of people, people living in cities, and people not living in cities in each continent.
//                The population of people, people living in cities, and people not living in cities in each region.
//                The population of people, people living in cities, and people not living in cities in each country.
                System.out.println("case 8");
                break;
            case 9:
                System.out.println("case 9");
                break;
            default: System.out.println("Invalid option.");
        }
    }



    // Get user input and ensure it's within range (min to max)
    public static int getUserInput(int min, int max) {

        //scanner object to read user input
        Scanner input = new Scanner(System.in);

        // variable to store user input
        int choice = -1;

        // loop until valid input is received
        while(true) {
            System.out.print("Select an option: ");

            try {

                //save user input
                choice = input.nextInt();

                //if input is within range, return it
                if (choice >= min && choice <= max) return choice;

                //else error message
                System.out.println("Please enter a number between " + min + " and " + max);
            }
            //catch non int inputs
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                // clear invalid input
                input.nextLine();
            }
        }
    }
}
