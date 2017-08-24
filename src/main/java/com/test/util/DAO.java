package com.test.util;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;

public class DAO {

    public static boolean addCustomer(
            String FirstName,
            String LastName,
            String email,
            int phoneNumber,
            String cellProvider,
            String Company,
            String gender,
            String password,
            Integer vehicleMPG
    ) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection mysqlConnection;
            mysqlConnection = DriverManager.getConnection(
                    DAOCredentials.DB_ADDRESS,
                    DAOCredentials.USERNAME,
                    DAOCredentials.PASSWORD);
            //Instantiate Password Encryptor

            StrongPasswordEncryptor enc = new StrongPasswordEncryptor();

            String passEncrypted = enc.encryptPassword(password);


            String addCustomerCommand = "INSERT INTO userinfo " +
                    "(FirstName, LastName, email, phoneNumber, cellProvider, Company, " +
                    "gender, passEncrypted, vehicleMPG) " +
                    "VALUES ('" +
                    FirstName + "', '" +
                    LastName + "', '" +
                    email + "', '" +
                    phoneNumber + "', '" +
                    cellProvider + "', '" +
                    Company + "', '" +
                    gender + "', '" +
                    passEncrypted + "', '" +
                    vehicleMPG + "')";

            System.out.println("SQL Query " + addCustomerCommand);

            Statement st = mysqlConnection.createStatement();// creates the statement

            int result = st.executeUpdate(addCustomerCommand);// executes the statement
            // array list of customers

            //if (result == 1)
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false; //null result indicates an issue
        }

    }

    public static boolean addrequest(
            String UserID,
            String departure,
            String arrival,
            String time,
            String date,
            String frequency,
            String message

    ) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection mysqlConnection;
            mysqlConnection = DriverManager.getConnection(
                    DAOCredentials.DB_ADDRESS,
                    DAOCredentials.USERNAME,
                    DAOCredentials.PASSWORD);

            String addRequestCommand = "INSERT INTO request " +
                    "(UserID, departure, arrival, time, date, frequency, message)" +
                    "VALUES ('" +
                    UserID + "', '" +
                    departure + "', '" +
                    arrival + "', '" +
                    time + "', '" +
                    date + "', '" +
                    frequency + "', '" +
                    message + "')";

            System.out.println("SQL Query " + addRequestCommand);

            Statement st = mysqlConnection.createStatement();// creates the statement

            int result = st.executeUpdate(addRequestCommand);// executes the statement
            // array list of customers

            //if (result == 1)
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false; //null result indicates an issue
        }
    }

    public static ArrayList<matches> getMatches(String departure, String arrival, String date, String time)

    {

        try {
            //Load driver
            // below is more dynamic
            Class.forName("com.mysql.jdbc.Driver");

            // below is static
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            Connection mysqlConnection;
            mysqlConnection = DriverManager.getConnection(
                    DAOCredentials.DB_ADDRESS,
                    DAOCredentials.USERNAME,
                    DAOCredentials.PASSWORD);

            // Next step is to create db statement
            // the select statement can be changed to insert into, update, delete

            PreparedStatement ps = mysqlConnection.prepareStatement("select userinfo.FirstName, userinfo.Company, userinfo.gender, request.departure, request.arrival, " +
                    "request.date, request.message\n" +
                    "from userinfo\n" +
                    "inner join request on userinfo.UserId = request.UserID\n" +
                    "WHERE request.departure= ? AND request.arrival= ? AND request.date= ? AND request.time= ? ");

            ps.setString(1, departure);
            ps.setString(2, arrival);
            ps.setString(3, date);
            ps.setString(4, time);

            ResultSet results = ps.executeQuery();

            //Array list of customers
            ArrayList<matches> matchList = new ArrayList<matches>();

            // if you have more rows to read it continues
            while (results.next()) {
                // gets data from column 1 and column 2
                matches temp = new matches(results.getString(1), results.getString(2), results.getString(3),
                        results.getString(4), results.getString(5), results.getString(6), results.getString(7));

                // added the temp customer to the arrayList
                matchList.add(temp);
            }

            return matchList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null; //null result indicates an issue
        }
        // doing return null for now, but this can be an error for the user on a new view/page
        // create an error page with custom error message
    }
}
