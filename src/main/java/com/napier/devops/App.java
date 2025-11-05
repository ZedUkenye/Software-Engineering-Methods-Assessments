package com.napier.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@SpringBootApplication
@RestController
public class App {

    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    /**
     * Connect to the MySQL database.
     * @param location Database host:port
     */
    public static void connect(String location) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for DB to start
                Thread.sleep(5000);

                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );

                System.out.println("Successfully connected");
                break;

            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());

            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public static void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    @RequestMapping("country")
    public ArrayList<Country> getCountries(
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "limit", required = false) String limit) {
        try {
            //used to send queries to the database
            Statement stmt = con.createStatement();

            //sql select statement
            String sqlSelect = "SELECT country.Code AS code, country.Name AS name, country.Continent AS continent, country.Region AS region, country.Population as population, city.name AS capital " +
                    "FROM country " +
                    "JOIN city ON city.Id = country.Capital ";

            if (continent != null && !continent.isEmpty()){
                sqlSelect += " WHERE country.Continent = '" + continent + "'";
            }
            if (region != null && !region.isEmpty()){
                sqlSelect += " WHERE country.Region = '" + region + "'";
            }

            sqlSelect += " ORDER BY country.Population DESC ";

            if (limit != null && !limit.isEmpty()){
                sqlSelect += " LIMIT " + limit;
            }

            //used to send queries to the database
            ResultSet sqlResults = stmt.executeQuery(sqlSelect);

            ArrayList<Country> country = new ArrayList<>();

            while (sqlResults.next()) {
                Country ctry = new Country();
                ctry.country_code = sqlResults.getString("code");
                ctry.country_name = sqlResults.getString("name");
                ctry.country_continent = sqlResults.getString("continent");
                ctry.country_region = sqlResults.getString("region");
                ctry.country_population = sqlResults.getInt("population");
                ctry.country_capital = sqlResults.getString("capital");

                country.add(ctry);
            }
            return country;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    @RequestMapping("city")
    public ArrayList<City> getCities(
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "limit", required = false) String limit) {
        try {
            //used to send queries to the database
            Statement stmt = con.createStatement();

            //sql select statement
            String sqlSelect = "SELECT city.Name AS name, country.Name AS country, city.District AS district, city.Population AS population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code ";

            if (continent != null && !continent.isEmpty()){
                sqlSelect += " WHERE country.Continent = '" + continent + "'";
            }
            if (region != null && !region.isEmpty()){
                sqlSelect += " WHERE country.Region = '" + region + "'";
            }
            if (country != null && !country.isEmpty()){
                sqlSelect += " WHERE country.Name = '" + country + "'";
            }
            if (district != null && !district.isEmpty()){
                sqlSelect += " WHERE city.District = '" + district + "'";
            }

            sqlSelect += " ORDER BY city.Population DESC ";

            if (limit != null && !limit.isEmpty()){
                sqlSelect += " LIMIT " + limit;
            }

            //used to send queries to the database
            ResultSet sqlResults = stmt.executeQuery(sqlSelect);

            ArrayList<City> city = new ArrayList<>();

            while (sqlResults.next()) {
                City cty = new City();
                cty.city_name = sqlResults.getString("name");
                cty.city_country_name = sqlResults.getString("country");
                cty.city_district = sqlResults.getString("district");
                cty.city_population = sqlResults.getInt("population");

                city.add(cty);
            }
            return city;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

    }




















    /**
     * Main method to start the application and connect to database.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            connect("localhost:33060");
        } else {
            connect(args[0]);
        }

        SpringApplication.run(App.class, args);
    }
}
