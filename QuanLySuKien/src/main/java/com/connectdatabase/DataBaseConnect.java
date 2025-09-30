
package com.connectdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class DataBaseConnect {
    private static Connection getConnection() throws SQLException, ClassNotFoundException{
        return getConnection("BongDa", "sa", "123");
    }
    
    private static Connection getConnection(String dbName, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String dbURL = "jdbc:sqlserver://localhost;databaseName=%s;"
                + "encrypt=true;trustServerCertificate=true";
        Connection connection =  DriverManager.getConnection(String.format(dbURL, dbName),
                userName, password);
        
        return connection;
    } 
    
    public static ArrayList<Vector> getDataSeatsAccordingToTheMatch(String matchID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * "
                   + "FROM Seats "
                   + "WHERE match_id = ?";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, matchID);   // gán giá trị cho ?
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Vector> data = new ArrayList<>();

            while (rs.next()) {
                Vector rowData = new Vector();
                rowData.add(rs.getString(1));
                rowData.add(rs.getString(2));
                rowData.add(rs.getString(3));
                rowData.add(rs.getInt(4));
                data.add(rowData);
            }

            return data;
        }
    }

    public static ArrayList<String> getMatchID() {
        String sql = "Select match_id FROM Matches";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            ArrayList<String> data = new ArrayList<>();
            while (rs.next()) {
                data.add(rs.getString("match_id"));
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        ArrayList <Vector> dulieu = getDataSeatsAccordingToTheMatch();
//        for (Vector item : dulieu) {
//            System.out.println(item);
//        }
        ArrayList<String> arr = getMatchID();
        for (var item : arr) {
            System.out.println(item);
        }
    }
}

