package com.repository;

import com.config.DataBaseConfig;
import com.model.ATM;
import com.model.Address;

import java.sql.*;

public class ATMRepository {

    public void createTable() {

        Connection connection = DataBaseConfig.getDatabaseConnection();

        String createTable = "CREATE TABLE IF NOT EXISTS atms " +
                "(id int AUTO_INCREMENT, " +
                "street varchar(30) not null," +
                "numberOnStreet varchar(5) not null," +
                "county varchar(30) not null," +
                "city varchar(30) not null," +
                "amountOfCahDisponible double not null," +
                "bankCode varchar(4) not null CHECK(LENGTH(bankCode) = 4)," +
                "constraint pk_atms primary key (id))";

        try {
            Statement s = connection.createStatement();
            s.execute(createTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addATM(ATM atm) {

        String sql = "INSERT INTO atms (street,numberOnStreet,county,city,amountOfCashDisponible,bankCode) VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DataBaseConfig.getDatabaseConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, atm.getLocation().getStreet());
            ps.setString(2, atm.getLocation().getNumber());
            ps.setString(3, atm.getLocation().getCounty());
            ps.setString(4, atm.getLocation().getCity());
            ps.setDouble(5, atm.getAmountOfCashDisponible());
            ps.setString(6, atm.getBankCode());

            ps.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ATM getATM(Address address) {
        String sql = "SELECT * FROM atms WHERE street=? AND numberOnStreet=? AND county=? AND city=?";

        try(PreparedStatement preparedStatement = DataBaseConfig.getDatabaseConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setString(2, address.getNumber());
            preparedStatement.setString(3, address.getCounty());
            preparedStatement.setString(4, address.getCity());
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                ATM atm = new ATM();
                Address a1 = new Address();
                a1.setStreet(res.getString(2));
                a1.setNumber(res.getString(3));
                a1.setCounty(res.getString(4));
                a1.setCity(res.getString(5));
                atm.setLocation(a1);
                atm.setAmountOfCashDisponible(res.getDouble(6));
                atm.setBankCode(res.getString(7));
                return atm;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteATM(ATM atm) {
        String sql = "DELETE FROM atms WHERE street=? AND numberOnStreet=? AND county=? AND city=?";

        try(PreparedStatement preparedStatement = DataBaseConfig.getDatabaseConnection().prepareStatement(sql)) {

            preparedStatement.setString(1, atm.getLocation().getStreet());
            preparedStatement.setString(2, atm.getLocation().getNumber());
            preparedStatement.setString(3, atm.getLocation().getCounty());
            preparedStatement.setString(4, atm.getLocation().getCity());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateATM(ATM atm, Double amount) {
        String sql = "UPDATE atms SET amountOfCashDisponible=? WHERE street=? AND numberOnStreet=? AND county=? AND city=?";

        Connection connection = DataBaseConfig.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, atm.getLocation().getStreet());
            preparedStatement.setString(3, atm.getLocation().getNumber());
            preparedStatement.setString(4, atm.getLocation().getCounty());
            preparedStatement.setString(5, atm.getLocation().getCity());

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void displayATMs() {
        String sql = "SELECT * FROM atms";

        Connection connection = DataBaseConfig.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString(1));
                System.out.println("Street: " + resultSet.getString(2));
                System.out.println("NumberOnStreet: " + resultSet.getString(3));
                System.out.println("County: " + resultSet.getString(4));
                System.out.println("City: " + resultSet.getString(5));
                System.out.println("AmountOfCashDisponible: " + resultSet.getString(6));
                System.out.println("BankCode: " + resultSet.getString(7));
                System.out.println("------------------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
