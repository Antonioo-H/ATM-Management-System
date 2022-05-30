package com.repository;

import com.config.DataBaseConfig;
import com.model.Address;
import com.model.BankBranch;

import java.sql.*;

public class BankBranchRepository {

    public void createTable() {

        Connection connection = DataBaseConfig.getDatabaseConnection();

        String createTable = "CREATE TABLE IF NOT EXISTS BankBranches " +
                "(id int AUTO_INCREMENT, " +
                "street varchar(30) not null," +
                "numberOnStreet varchar(5) not null," +
                "county varchar(30) not null," +
                "city varchar(30) not null," +
                "telephone varchar(10) unique not null CHECK(LENGTH(telephone) = 10)," +
                "bankCode varchar(4) not null CHECK(LENGTH(bankCode) = 4)," +
                "constraint pk_bb primary key (id))";

        try {
            Statement s = connection.createStatement();
            s.execute(createTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBankBranch(BankBranch bb) {

        String sql = "INSERT INTO bankbranches (street,numberOnStreet,county,city,telephone,bankCode) VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DataBaseConfig.getDatabaseConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, bb.getBankBranchAddress().getStreet());
            ps.setString(2, bb.getBankBranchAddress().getNumber());
            ps.setString(3, bb.getBankBranchAddress().getCounty());
            ps.setString(4, bb.getBankBranchAddress().getCity());
            ps.setString(5, bb.getBankBranchTelephone());
            ps.setString(6, bb.getBankCode());

            ps.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BankBranch getBankBranch(String telephone) {
        String sql = "SELECT * FROM bankbranches WHERE telephone=?";

        try(PreparedStatement preparedStatement = DataBaseConfig.getDatabaseConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, telephone);

            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                BankBranch bb = new BankBranch();
                Address a1 = new Address();
                a1.setStreet(res.getString(2));
                a1.setNumber(res.getString(3));
                a1.setCounty(res.getString(4));
                a1.setCity(res.getString(5));
                bb.setBankBranchAddress(a1);
                bb.setBankBranchTelephone(res.getString(6));
                bb.setBankCode(res.getString(7));

                return bb;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteBankBranch(BankBranch bb) {
        String sql = "DELETE FROM bankbranches WHERE telephone=?";

        try(PreparedStatement preparedStatement = DataBaseConfig.getDatabaseConnection().prepareStatement(sql)) {

            preparedStatement.setString(1, bb.getBankBranchTelephone());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBankBranch(BankBranch bb, String telephone) {
        String sql = "UPDATE bankbranches SET telephone=? WHERE telephone=?";

        Connection connection = DataBaseConfig.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, telephone);
            preparedStatement.setString(2, bb.getBankBranchTelephone());

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void displayBankBranches() {
        String sql = "SELECT * FROM bankbranches";

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
                System.out.println("Telephone: " + resultSet.getString(6));
                System.out.println("BankCode: " + resultSet.getString(7));
                System.out.println("------------------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
