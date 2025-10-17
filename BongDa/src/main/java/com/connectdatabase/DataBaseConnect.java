package com.connectdatabase;

import com.objects.Match;
import com.objects.Order;
import com.objects.Payment;
import com.objects.Seat;
import com.objects.Stadium;
import com.objects.Ticket;
import com.objects.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

public class DataBaseConnect {

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        return getConnection("BongDa", "sa", "123");
    }

    private static Connection getConnection(String dbName, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String dbURL = "jdbc:sqlserver://localhost;databaseName=%s;"
                + "encrypt=true;trustServerCertificate=true";
        Connection connection = DriverManager.getConnection(String.format(dbURL, dbName),
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
            return new ArrayList<>();
        }
    }

    public static int resgiterUser(User u) {
        String sql = "INSERT INTO Users (user_id, full_name, email, phone, pass) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, u.getUser_id());
            pstmt.setString(2, u.getFull_name());
            pstmt.setString(3, u.getEmail());
            pstmt.setString(4, u.getPhone());
            pstmt.setString(5, u.getPass());

            // Thực thi câu lệnh INSERT
            return pstmt.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    public static String selectStadiumIDbyMatchID(String matchID) throws SQLException, ClassNotFoundException {

        String sql = "SELECT stadium_id FROM Matches WHERE match_id = ?";

        try (
                Connection conn = DataBaseConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matchID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("stadium_id");
                }
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public static int updateSeat(Seat s) {
        String sql = "UPDATE Seats "
                + "SET status = 1 "
                + "WHERE seat_id = ? and match_id = ?;";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, s.getSeat_id());
            pstmt.setString(2, s.getMatch_id());
            return pstmt.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    public static int insertOrder(Order o) {
        String sql = "INSERT INTO Orders (order_id, user_id, order_date, total_amount, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, o.getOrder_id());
            pstmt.setString(2, o.getUser_id());
            pstmt.setTimestamp(3, Timestamp.valueOf(o.getOrder_date()));
            pstmt.setInt(4, o.getTotal_amount());
            pstmt.setString(5, o.getStatus());

            return pstmt.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    public static int insertPayMent(Payment p) {
        String sql = "INSERT INTO payments (payment_id, order_id, method, amount, status, payment_date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, p.getPayment_id());
            pstmt.setString(2, p.getOrder_id());
            pstmt.setTimestamp(6, Timestamp.valueOf(p.getPayment_date()));
            pstmt.setString(3, p.getMethod());
            pstmt.setInt(4, p.getAmount());
            pstmt.setString(5, p.getStatus());

            return pstmt.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    public static int intertTicket(Ticket t) {
        String sql = "INSERT INTO Tickets (ticket_id, order_id, match_id, stadium_id, seat_id, price) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, t.getTicket_id());
            pstmt.setString(2, t.getOrder_id());
            pstmt.setInt(6, t.getPrice());
            pstmt.setString(3, t.getMatch_id());
            pstmt.setString(4, t.getStadium_id());
            pstmt.setString(5, t.getSeat_id());

            return pstmt.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    public static int resetAllData() throws SQLException, ClassNotFoundException {
        String resetSeats = "UPDATE Seats SET status = 0;";
        String deletePayments = "DELETE FROM payments";
        String deleteTickets = "DELETE FROM tickets";
        String deleteOrders = "DELETE FROM orders";
        String deleteUsers = "DELETE FROM users";

        try (Connection conn = DataBaseConnect.getConnection()) {
            conn.setAutoCommit(false);

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deletePayments);
                stmt.executeUpdate(deleteTickets);
                stmt.executeUpdate(deleteOrders);
                stmt.executeUpdate(deleteUsers);
                stmt.executeUpdate(resetSeats);

                conn.commit();
                return 1;
            } catch (SQLException ex) {
                conn.rollback();
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    public static ArrayList<User> selectListUser() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                User u = new User();
                u.setUser_id(rs.getString("user_id"));
                u.setFull_name(rs.getString("full_name")); 
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setPass(rs.getString("pass"));
                u.setDate(rs.getTimestamp("created").toLocalDateTime());
                
                list.add(u);
            }
        } catch (Exception e) {
        }

        return list;
    }
    
    public static ArrayList<Ticket> selectListTicket() {
        ArrayList<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM Tickets";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setTicket_id(rs.getString("ticket_id"));
                t.setOrder_id(rs.getString("order_id")); 
                t.setMatch_id(rs.getString("match_id"));
                t.setStadium_id(rs.getString("stadium_id"));
                t.setSeat_id(rs.getString("seat_id"));
                t.setPrice(rs.getInt("price"));
                
                list.add(t);
            }
        } catch (Exception e) {
        }

        return list;
    }
    
    public static ArrayList<Stadium> selectListStadium() {
        ArrayList<Stadium> list = new ArrayList<>();
        String sql = "SELECT * FROM Stadiums";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                Stadium s = new Stadium();
                s.setStadium_id(rs.getString("stadium_id"));
                s.setName(rs.getString("name")); 
                s.setLocation(rs.getString("location"));
                s.setCapacity(rs.getInt("capacity"));                
                
                list.add(s);
            }
        } catch (Exception e) {
        }

        return list;
    }
    
    public static ArrayList <Seat> selectListSeat() {
        ArrayList <Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM Seats";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                Seat s = new Seat();
                s.setMatch_id(rs.getString("match_id"));
                s.setStadium_id(rs.getString("stadium_id")); 
                s.setSeat_id(rs.getString("seat_id"));
                s.setStatus(rs.getBoolean("status"));                
                
                list.add(s);
            }
        } catch (Exception e) {
        }

        return list;
    }
    
    public static ArrayList <Payment> selectListPayment() {
        ArrayList<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payments";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                Payment p = new Payment();
                p.setPayment_id(rs.getString("payment_id"));
                p.setOrder_id(rs.getString("order_id")); 
                p.setMethod(rs.getString("method"));
                p.setAmount(rs.getInt("amount"));
                p.setStatus(rs.getString("status"));
                p.setPayment_date(rs.getTimestamp("payment_date").toLocalDateTime());
                
                list.add(p);
            }
        } catch (Exception e) {
        }

        return list;
    }
    
    public static ArrayList <Order> selectListOrder() {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrder_id(rs.getString("order_id"));
                o.setUser_id(rs.getString("user_id")); 
                o.setTotal_amount(rs.getInt("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setOrder_date(rs.getTimestamp("order_date").toLocalDateTime());
                
                list.add(o);
            }
        } catch (Exception e) {
        }

        return list;
    }
    
    public static ArrayList <Match> selectListMatch() {
        ArrayList<Match> list = new ArrayList<>();
        String sql = "SELECT * FROM Matches";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                Match m = new Match();
                m.setMatch_id(rs.getString("match_id"));
                m.setHome_team(rs.getString("home_team")); 
                m.setAway_team(rs.getString("away_team"));
                m.setStadium_id(rs.getString("stadium_id"));
                m.setMatch_date(rs.getTimestamp("match_date").toLocalDateTime());
                m.setTournament(rs.getString("tournament"));
                
                list.add(m);
            }
        } catch (Exception e) {
        }

        return list;
    }

}
