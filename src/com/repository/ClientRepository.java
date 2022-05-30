package com.repository;

import com.config.DataBaseConfig;
import com.model.Client;

import java.sql.*;

public class ClientRepository {

    public void createTable() {

        Connection connection = DataBaseConfig.getDatabaseConnection();

        String createTable = "CREATE TABLE IF NOT EXISTS clients " +
                "(id int AUTO_INCREMENT, " +
                "surname varchar(30) not null," +
                "firstName varchar(30) not null," +
                "sex boolean not null," +
                "CNP varchar(13) unique not null," +
                "telephone varchar(10) unique not null," +
                "email varchar(40) unique not null," +
                "constraint pk_clients primary key (id))";

        try {
            Statement s = connection.createStatement();
            s.execute(createTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addClient(Client client) {

        String insertClient = "INSERT INTO clients (surname,firstName,sex,CNP,telephone,email) VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DataBaseConfig.getDatabaseConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(insertClient);

            ps.setString(1, client.getSurname());
            ps.setString(2, client.getFirstName());
            ps.setBoolean(3, client.getSex());
            ps.setString(4, client.getCNP());
            ps.setString(5, client.getTelephone());
            ps.setString(6, client.getEmail());

            ps.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client mapToClient(ResultSet res) throws SQLException {
        if (res.next()) {
            return new Client(res.getString(1),res.getString(2), res.getBoolean(3),res.getString(4),res.getString(5),res.getString(6));
        }
        return null;
    }

    public Client getClient(String CNP){
        String sql = "SELECT * FROM clients WHERE CNP=?";

        try(PreparedStatement preparedStatement = DataBaseConfig.getDatabaseConnection().prepareStatement(sql)) {
            preparedStatement.setString(4, CNP);
            ResultSet res = preparedStatement.executeQuery();
            return mapToClient(res);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateClient(String telephone, Client client) {
        String updateTelephoneSql = "UPDATE clients SET telephone=? WHERE telephone=?";

        try(PreparedStatement preparedStatement = DataBaseConfig.getDatabaseConnection().prepareStatement(updateTelephoneSql)) {
            preparedStatement.setString(1, telephone);
            preparedStatement.setString(2, client.getTelephone());

            preparedStatement.executeUpdate();

            String updateArtistsSql = "UPDATE artists SET artist=? WHERE artist=?";

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(Client client) {
        String sql = "DELETE FROM clients WHERE CNP=?";

        try(PreparedStatement preparedStatement = DataBaseConfig.getDatabaseConnection().prepareStatement(sql)) {

            preparedStatement.setString(1, client.getCNP());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayClients() {
        String sql = "SELECT * FROM clients";

        Connection connection = DataBaseConfig.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString(1));
                System.out.println("surname: " + resultSet.getString(2));
                System.out.println("sex: " + resultSet.getString(3));
                System.out.println("CNP: " + resultSet.getString(4));
                System.out.println("Telephone: " + resultSet.getString(5));
                System.out.println("E-mail: " + resultSet.getString(6));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
