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

    private static String server, password, name;

    // đổi tên đăng nhâp database theo đúng trong cơ sở dữ liệu
    public static String testConnection() {
        try (
                Connection conn = getServerConnection(server, password)) { // thay user/pass
            if (conn != null) {
                return "Execellent";
            } else {
                return "Fall";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Loi SQL";
        } catch (ClassNotFoundException e) {
            return "Driver not found";
        }
    }

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        return getConnection(name, server, password);
    }

    public static void setDatabase(String server, String password, String name) {
        DataBaseConnect.server = server;
        DataBaseConnect.password = password;
        DataBaseConnect.name = name;
    }

    private static Connection getConnection(String base) throws SQLException, ClassNotFoundException {
        return getConnection(name, server, password);
    }

    private static Connection getServerConnection(String user, String pass)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String dbURL = "jdbc:sqlserver://localhost;"
                + "encrypt=true;trustServerCertificate=true";
        return DriverManager.getConnection(dbURL, user, pass);
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
            pstmt.setString(1, matchID);
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

    public static ArrayList<String> getStadiumID() {
        String sql = "Select stadium_id FROM Stadiums";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            ArrayList<String> data = new ArrayList<>();
            while (rs.next()) {
                data.add(rs.getString("stadium_id"));
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
            System.exit(0);
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
            System.exit(0);
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
            System.exit(0);
        }
        return 0;
    }

    public static int intertTicket(Ticket t) {
        String sql = "INSERT INTO Tickets (ticket_id, order_id, match_id, seat_id, price) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, t.getTicket_id());
            pstmt.setString(2, t.getOrder_id());
            pstmt.setInt(5, t.getPrice());
            pstmt.setString(3, t.getMatch_id());
            pstmt.setString(4, t.getSeat_id());

            return pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean insertStadium(Stadium s) {
        String sql = "INSERT INTO Stadiums (stadium_id, name, location, capacity) VALUES (?, ?, ?, ?)";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, s.getStadium_id());
            pstmt.setString(2, s.getName());
            pstmt.setString(3, s.getLocation());
            pstmt.setInt(4, s.getCapacity());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean insertMatch(Stadium s) {
        String sql = "INSERT INTO Stadiums (stadium_id, name, location, capacity) VALUES (?, ?, ?, ?)";

        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, s.getStadium_id());
            pstmt.setString(2, s.getName());
            pstmt.setString(3, s.getLocation());
            pstmt.setInt(4, s.getCapacity());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean insertMatch(Match m) {
        String sqlMatch = "INSERT INTO Matches (match_id, home_team, "
                + "away_team, stadium_id, match_date, tournament) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlSeat = "INSERT INTO Seats (match_id, stadium_id, seat_id, status) "
                + "VALUES (?, ?, ?, 0)";

        try (
                Connection con = getConnection(); PreparedStatement pstmtMatch = con.prepareStatement(sqlMatch); PreparedStatement pstmtSeat = con.prepareStatement(sqlSeat);) {
            con.setAutoCommit(false);

            pstmtMatch.setString(1, m.getMatch_id());
            pstmtMatch.setString(2, m.getHome_team());
            pstmtMatch.setString(3, m.getAway_team());
            pstmtMatch.setString(4, m.getStadium_id());
            pstmtMatch.setTimestamp(5, Timestamp.valueOf(m.getMatch_date()));
            pstmtMatch.setString(6, m.getTournament());
            int rows = pstmtMatch.executeUpdate();
            if (rows <= 0) {
                con.rollback();
                return false;
            }

            char[] rowsLabel = {'A', 'B', 'C', 'D', 'E', 'F'};
            for (char rowLabel : rowsLabel) {
                for (int num = 1; num <= 10; num++) {
                    String seatId = String.format("%c%02d", rowLabel, num);
                    pstmtSeat.setString(1, m.getMatch_id());
                    pstmtSeat.setString(2, m.getStadium_id());
                    pstmtSeat.setString(3, seatId);
                    pstmtSeat.addBatch();
                }
            }

            pstmtSeat.executeBatch();
            con.commit(); // xác nhận tất cả
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static int resetAllData() throws SQLException, ClassNotFoundException {
        // Các lệnh SQL
        String resetSeatsStatus = "UPDATE Seats SET status = 0;";
        String deleteSeats = "DELETE FROM Seats;";
        String deleteMatches = "DELETE FROM Matches;";
        String deleteStadiums = "DELETE FROM Stadiums;";
        String deletePayments = "DELETE FROM Payments;";
        String deleteTickets = "DELETE FROM Tickets;";
        String deleteOrders = "DELETE FROM Orders;";
        String deleteUsers = "DELETE FROM Users;";

        try (Connection conn = DataBaseConnect.getConnection()) {
            conn.setAutoCommit(false);

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deletePayments);
                stmt.executeUpdate(deleteTickets);
                stmt.executeUpdate(deleteOrders);
                stmt.executeUpdate(deleteUsers);
                stmt.executeUpdate(deleteSeats);
                stmt.executeUpdate(deleteMatches);
                stmt.executeUpdate(deleteStadiums);

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

    public static ArrayList<Seat> selectListSeat() {
        ArrayList<Seat> list = new ArrayList<>();
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

    public static ArrayList<Payment> selectListPayment() {
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

    public static ArrayList<Order> selectListOrder() {
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

    public static ArrayList<Match> selectListMatch() {
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

    public static ArrayList<ArrayList<Object>> selectSeatAvailabilityReport() {
        ArrayList<ArrayList<Object>> res = new ArrayList();
        String sql = "SELECT  "
                + "    s.match_id, "
                + "    st.name AS stadium_name, "
                + "    m.home_team, "
                + "    m.away_team, "
                + "    SUM(CASE WHEN s.status = 1 THEN 1 ELSE 0 END) AS booked_seats, "
                + "    SUM(CASE WHEN s.status = 0 THEN 1 ELSE 0 END) AS available_seats "
                + "FROM Seats AS s "
                + "JOIN Matches AS m ON s.match_id = m.match_id "
                + "JOIN Stadiums AS st ON m.stadium_id = st.stadium_id "
                + "GROUP BY s.match_id, st.name, m.home_team, m.away_team";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                ArrayList<Object> tuple = new ArrayList<>();
                tuple.add(rs.getString("match_id"));
                tuple.add(rs.getString("stadium_name"));
                tuple.add(rs.getString("home_team"));
                tuple.add(rs.getString("away_team"));
                tuple.add(rs.getInt("booked_seats"));
                tuple.add(rs.getInt("available_seats"));
                res.add(tuple);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static ArrayList<ArrayList<Object>> selectTop5HighestSpendingCustomers() {
        ArrayList<ArrayList<Object>> res = new ArrayList();
        String sql = """
                        select top 5 u.[user_id], u.full_name, sum(ord.total_amount) as TongChiTieu
                        from Users u
                        join 
                            Orders ord on u.[user_id] = ord.[user_id]
                        group by u.[user_id], u.full_name
                        order by TongChiTieu desc
                      """;
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                ArrayList<Object> tuple = new ArrayList<>();
                tuple.add(rs.getString("user_id"));
                tuple.add(rs.getString("full_name"));
                tuple.add(rs.getInt("TongChiTieu"));
                res.add(tuple);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static ArrayList<ArrayList<Object>> selectAverageTicketPricePerTournament() {
        ArrayList<ArrayList<Object>> res = new ArrayList();
        String sql = """
                        select distinct m.tournament, avg(t.price) as GiaVeTrungBinh
                        from Matches m join Tickets t on m.match_id = t.match_id
                        group by m.tournament
                        order by avg(t.price) desc
                      """;
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                ArrayList<Object> tuple = new ArrayList<>();
                tuple.add(rs.getString("tournament"));
                tuple.add(rs.getInt("GiaVeTrungBinh"));
                res.add(tuple);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static ArrayList<ArrayList<Object>> selectRushHourForBooking() {
        ArrayList<ArrayList<Object>> res = new ArrayList();
        String sql = """
                        select
                        	case
                        		when DATEPART(hour, o.order_date) between 0 and 5 then '0h - 6h (Early Morning)'
                        		when DATEPART(hour, o.order_date) between 6 and 11 then '6h - 11h (Morning)'
                        		when DATEPART(hour, o.order_date) between 12 and 17 then '11h - 17h (Afternoon)'
                        		else '18h - 24h (Evening)'
                        	end as FrameTime,
                        	count(t.ticket_id) as Quantity
                        from Orders o
                        join Tickets t on o.order_id = t.order_id
                        group by 
                                case
                        		when DATEPART(hour, o.order_date) between 0 and 5 then '0h - 6h (Early Morning)'
                        		when DATEPART(hour, o.order_date) between 6 and 11 then '6h - 11h (Morning)'
                        		when DATEPART(hour, o.order_date) between 12 and 17 then '11h - 17h (Afternoon)'
                        		else '18h - 24h (Evening)'
                        	end
                        order by Quantity DESC
                      """;
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                ArrayList<Object> tuple = new ArrayList<>();
                tuple.add(rs.getString("FrameTime"));
                tuple.add(rs.getInt("Quantity"));
                res.add(tuple);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static ArrayList<ArrayList<Object>> selectRankingStadiumRevenue() {
        ArrayList<ArrayList<Object>> res = new ArrayList();
        String sql = """
                        WITH StadiumPayment AS (
                            SELECT DISTINCT 
                                s.name, 
                                p.payment_id,
                                p.amount
                            FROM Stadiums s
                            JOIN Matches m ON s.stadium_id = m.stadium_id
                            JOIN Tickets t ON m.match_id = t.match_id
                            JOIN Orders o ON o.order_id = t.order_id
                            JOIN Payments p ON p.order_id = o.order_id
                        )
                        , TotalRevenue AS (
                            SELECT 
                                Name,
                                SUM(amount) AS TotalRevenue
                            FROM StadiumPayment
                            GROUP BY name
                        )
                        SELECT 
                            DENSE_RANK() OVER (ORDER BY TotalRevenue DESC) AS Rank,
                            Name,
                            TotalRevenue
                        FROM TotalRevenue
                        ORDER BY Rank;
                      """;
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                ArrayList<Object> tuple = new ArrayList<>();
                tuple.add(rs.getInt("Rank"));
                tuple.add(rs.getString("Name"));
                tuple.add(rs.getInt("TotalRevenue"));
                res.add(tuple);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static ArrayList<ArrayList<Object>> selectTop5TeamsByTotalPaymentRevenue() {
        ArrayList<ArrayList<Object>> res = new ArrayList();
        String sql = """
                        WITH TotalRevenuePerMatch AS (
                            SELECT 
                                ma.match_id, 
                                ma.home_team, 
                                ma.away_team, 
                                SUM(p.amount) AS TotalRevenue
                            FROM Matches ma
                            JOIN (
                                SELECT DISTINCT order_id, match_id
                                FROM Tickets
                            ) ti ON ma.match_id = ti.match_id
                            JOIN Payments p ON p.order_id = ti.order_id
                            GROUP BY ma.match_id, ma.home_team, ma.away_team
                        ),
                        MergeHomeAndAwayTeams AS (
                            SELECT home_team AS TeamName, TotalRevenue FROM TotalRevenuePerMatch
                            UNION ALL
                            SELECT away_team AS TeamName, TotalRevenue FROM TotalRevenuePerMatch
                        )
                        SELECT TOP 5 
                            TeamName, 
                            SUM(TotalRevenue) AS TotalRevenue
                        FROM MergeHomeAndAwayTeams
                        GROUP BY TeamName
                        ORDER BY SUM(TotalRevenue) DESC;
                      """;
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                ArrayList<Object> tuple = new ArrayList<>();
                tuple.add(rs.getString("TeamName"));
                tuple.add(rs.getInt("TotalRevenue"));
                res.add(tuple);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static User checkAccount(String userID, String pass) {
        String sql = """
                 SELECT user_id, full_name, email, phone, pass
                 FROM Users
                 WHERE user_id = ? AND pass = ?
                 """;
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, userID);
            pstmt.setString(2, pass);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("user_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("pass")
                    );
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Integer getCountMatch() {
        String sql = "SELECT COUNT(*) "
                + "FROM Matches;";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            return -1;
        }
    }

    public static Integer getCountStadium() {
        String sql = "SELECT COUNT(*) "
                + "FROM Stadiums;";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getTournament(String match_id) {
        String sql = "Select tournament from Matches where match_id = ?";
        try (
                Connection con = getConnection(); PreparedStatement pstmt = con.prepareCall(sql);) {
            pstmt.setString(1, match_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("tournament");
            }
        } catch (Exception e) {
            return null;
        }
        return "Other";
    }

    public static int createDateToBI() {
        String sql = """
                     USE Database_name_here;
                     GO
                     
                     -- Tao sequences sinh cac to hop
                     IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'OrderSeq')
                         CREATE SEQUENCE OrderSeq START WITH 1 INCREMENT BY 1;
                     GO
                     IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'TicketSeq')
                         CREATE SEQUENCE TicketSeq START WITH 1 INCREMENT BY 1;
                     GO
                     IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'PaymentSeq')
                         CREATE SEQUENCE PaymentSeq START WITH 1 INCREMENT BY 1;
                     GO
                     IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'SeatSeq')
                         CREATE SEQUENCE SeatSeq START WITH 1 INCREMENT BY 1;
                     GO
                     
                     ----------------------------------------------------------------------
                     -- BƯỚC 1: DỌN SẠCH TOÀN BỘ DỮ LIỆU CŨ
                     ----------------------------------------------------------------------
                     PRINT 'STEP 1: Deleting old data...';
                     DELETE FROM Payments;
                     DELETE FROM Tickets;
                     DELETE FROM Orders;
                     DELETE FROM Seats;
                     DELETE FROM Matches;
                     DELETE FROM Users;
                     DELETE FROM Stadiums;
                     GO
                     
                     ----------------------------------------------------------------------
                     -- BƯỚC 2: THÊM DỮ LIỆU SÂN VẬN ĐỘNG (STADIUMS)
                     ----------------------------------------------------------------------
                     PRINT 'STEP 2: Inserting Stadiums...';
                     INSERT INTO Stadiums (stadium_id, name, location, capacity) VALUES
                     ('STD001', N'Lusail Iconic Stadium, Lusail', N'Qatar', 88966),
                     ('STD002', N'Al Bayt Stadium, Al Khor', N'Qatar', 68895),
                     ('STD003', N'Education City Stadium, Al Rayyan', N'Qatar', 44667),
                     ('STD004', N'Ahmad bin Ali Stadium, Al Rayyan', N'Qatar', 45032),
                     ('STD005', N'Khalifa International Stadium, Al Rayyan', N'Qatar', 45857),
                     ('STD006', N'Al Thumama Stadium, Doha', N'Qatar', 44400),
                     ('STD007', N'Stadium 974, Doha', N'Qatar', 44089),
                     ('STD008', N'Al Janoub Stadium, Al Wakrah', N'Qatar', 44325),
                     ('STD009', N'Luzhniki Stadium, Moscow', N'Russia', 78011),
                     ('STD010', N'Saint Petersburg Stadium, Saint Petersburg', N'Russia', 64468),
                     ('STD011', N'Fisht Olympic Stadium, Sochi', N'Russia', 44287),
                     ('STD012', N'Volgograd Arena, Volgograd', N'Russia', 43713),
                     ('STD013', N'Spartak Stadium, Moscow', N'Russia', 44190),
                     ('STD014', N'Nizhny Novgorod Stadium, Nizhny Novgorod', N'Russia', 43319),
                     ('STD015', N'Rostov Arena, Rostov-on-Don', N'Russia', 43472),
                     ('STD016', N'Kazan Arena, Kazan', N'Russia', 42873),
                     ('STD017', N'Samara Arena, Samara', N'Russia', 41970),
                     ('STD018', N'Mordovia Arena, Saransk', N'Russia', 41685),
                     ('STD019', N'Kaliningrad Stadium, Kaliningrad', N'Russia', 33973),
                     ('STD020', N'Central Stadium, Yekaterinburg', N'Russia', 33061),
                     ('STD021', N'Estádio do Maracanã, Rio de Janeiro', N'Brazil', 74738),
                     ('STD022', N'Estádio Nacional Mané Garrincha, Brasília', N'Brazil', 69349),
                     ('STD023', N'Arena de São Paulo, São Paulo', N'Brazil', 63267),
                     ('STD024', N'Estádio Castelão, Fortaleza', N'Brazil', 60342),
                     ('STD025', N'Estádio Mineirão, Belo Horizonte', N'Brazil', 58170),
                     ('STD026', N'Arena Fonte Nova, Salvador', N'Brazil', 51900),
                     ('STD027', N'Estádio Beira-Rio, Porto Alegre', N'Brazil', 43012),
                     ('STD028', N'Arena Pernambuco, Recife', N'Brazil', 42610),
                     ('STD029', N'Arena Pantanal, Cuiabá', N'Brazil', 41112),
                     ('STD030', N'Arena da Amazônia, Manaus', N'Brazil', 39118),
                     ('STD031', N'Arena das Dunas, Natal', N'Brazil', 39971),
                     ('STD032', N'Arena da Baixada, Curitiba', N'Brazil', 39131);
                     GO
                     
                     ----------------------------------------------------------------------
                     -- BƯỚC 3: THÊM DỮ LIỆU NGƯỜI DÙNG (USERS) - ĐÃ SỬA ID VỀ CHAR(9)
                     ----------------------------------------------------------------------
                     PRINT N'BƯỚC 3: Thêm Người Dùng (PER000001 - 9 ký tự)...';
                     INSERT INTO Users (user_id, full_name, email, phone, pass) VALUES
                     ('PER000001', N'Nguyễn Hoàng Anh', 'anh.nguyen@email.com', '0912345601', 'pass123'),
                     ('PER000002', N'Trần Minh Tuấn', 'tuan.tran@email.com', '0987654302', 'pass123'),
                     ('PER000003', N'Lê Thị Thuỳ Linh', 'linh.le@email.com', '0905558803', 'pass123'),
                     ('PER000004', N'Phạm Gia Hân', 'han.pham@email.com', '0918123404', 'pass123'),
                     ('PER000005', N'Võ Quốc Bảo', 'bao.vo@email.com', '0935678905', 'pass123'),
                     ('PER000006', N'Đỗ Khánh Huyền', 'huyen.do@email.com', '0977888906', 'pass123'),
                     ('PER000007', N'Hoàng Trung Kiên', 'kien.hoang@email.com', '0944555607', 'pass123'),
                     ('PER000008', N'Mai Phương Thảo', 'thao.mai@email.com', '0913456708', 'pass123'),
                     ('PER000009', N'Lý Đức Minh', 'minh.ly@email.com', '0988111209', 'pass123'),
                     ('PER000010', N'Bùi Thanh Trúc', 'truc.bui@email.com', '0902333410', 'pass123'),
                     ('PER000011', N'Trương Công Vinh', 'vinh.truong@email.com', '0965874111', 'pass123'),
                     ('PER000012', N'Dương Ngọc Ánh', 'anh.duong@email.com', '0911223312', 'pass123'),
                     ('PER000013', N'Huỳnh Tấn Phát', 'phat.huynh@email.com', '0922334413', 'pass123'),
                     ('PER000014', N'Đặng Minh Châu', 'chau.dang@email.com', '0933445514', 'pass123'),
                     ('PER000015', N'Ngô Gia Khiêm', 'khiem.ngo@email.com', '0944556615', 'pass123'),
                     ('PER000016', N'Trần Thị Mai', 'mai.tran@email.com', '0901234567', 'pass123'),
                     ('PER000017', N'Lê Văn Toàn', 'toan.le@email.com', '0901234568', 'pass123'),
                     ('PER000018', N'Phạm Minh Đức', 'duc.pham@email.com', '0901234569', 'pass123'),
                     ('PER000019', N'Hoàng Thu Huyền', 'huyen.hoang@email.com', '0901234570', 'pass123'),
                     ('PER000020', N'Nguyễn Đình Hùng', 'hung.nguyen@email.com', '0901234571', 'pass123'),
                     ('PER000021', N'Vũ Thị Kim Oanh', 'oanh.vu@email.com', '0901234572', 'pass123'),
                     ('PER000022', N'Đỗ Quang Vinh', 'vinh.do@email.com', '0901234573', 'pass123'),
                     ('PER000023', N'Bùi Thị Thanh Tâm', 'tam.bui@email.com', '0901234574', 'pass123'),
                     ('PER000024', N'Đặng Văn Trung', 'trung.dang@email.com', '0901234575', 'pass123'),
                     ('PER000025', N'Dương Thùy Linh', 'linh.duong@email.com', '0901234576', 'pass123'),
                     ('PER000026', N'Nguyễn Thành Nam', 'nam.nguyen@email.com', '0901234577', 'pass123'),
                     ('PER000027', N'Trần Văn An', 'an.tran@email.com', '0901234578', 'pass123'),
                     ('PER000028', N'Lê Thị Hương', 'huong.le@email.com', '0901234579', 'pass123'),
                     ('PER000029', N'Phạm Quang Hải', 'hai.pham@email.com', '0901234580', 'pass123'),
                     ('PER000030', N'Hoàng Văn Minh', 'minh.hoang@email.com', '0901234581', 'pass123'),
                     ('PER000031', N'Vũ Thị Hồng', 'hong.vu@email.com', '0901234582', 'pass123'),
                     ('PER000032', N'Đỗ Trọng Nghĩa', 'nghia.do@email.com', '0901234583', 'pass123'),
                     ('PER000033', N'Bùi Quang Huy', 'huy.bui@email.com', '0901234584', 'pass123'),
                     ('PER000034', N'Đặng Minh Khang', 'khang.dang@email.com', '0901234585', 'pass123'),
                     ('PER000035', N'Dương Văn Kiên', 'kien.duong@email.com', '0901234586', 'pass123'),
                     ('PER000036', N'Nguyễn Thị Thu', 'thu.nguyen@email.com', '0901234587', 'pass123'),
                     ('PER000037', N'Trần Đình Phúc', 'phuc.tran@email.com', '0901234588', 'pass123'),
                     ('PER000038', N'Lê Văn Trường', 'truong.le@email.com', '0901234589', 'pass123'),
                     ('PER000039', N'Phạm Thu Hiền', 'hien.pham@email.com', '0901234590', 'pass123'),
                     ('PER000040', N'Hoàng Minh Nhật', 'nhat.hoang@email.com', '0901234591', 'pass123'),
                     ('PER000041', N'Vũ Văn Công', 'cong.vu@email.com', '0901234592', 'pass123'),
                     ('PER000042', N'Đỗ Thị Thảo', 'thao.do@email.com', '0901234593', 'pass123'),
                     ('PER000043', N'Bùi Văn Sơn', 'son.bui@email.com', '0901234594', 'pass123'),
                     ('PER000044', N'Đặng Thị Ngọc', 'ngoc.dang@email.com', '0901234595', 'pass123'),
                     ('PER000045', N'Dương Văn Lợi', 'loi.duong@email.com', '0901234596', 'pass123'),
                     ('PER000046', N'Nguyễn Văn Tuấn', 'tuan.nguyen@email.com', '0901234597', 'pass123'),
                     ('PER000047', N'Trần Thanh Ngân', 'ngan.tran@email.com', '0901234598', 'pass123'),
                     ('PER000048', N'Lê Minh Quân', 'quan.le@email.com', '0901234599', 'pass123'),
                     ('PER000049', N'Phạm Văn Quý', 'quy.pham@email.com', '0901234600', 'pass123'),
                     ('PER000050', N'Hoàng Kim Chi', 'chi.hoang@email.com', '0901234601', 'pass123'),
                     ('PER000051', N'Vũ Minh Khôi', 'khoi.vu@email.com', '0901234602', 'pass123'),
                     ('PER000052', N'Đỗ Anh Tú', 'tu.do@email.com', '0901234603', 'pass123'),
                     ('PER000053', N'Bùi Lê Thảo My', 'my.bui@email.com', '0901234604', 'pass123'),
                     ('PER000054', N'Đặng Văn Thiện', 'thien.dang@email.com', '0901234605', 'pass123'),
                     ('PER000055', N'Dương Thị Hoài', 'hoai.duong@email.com', '0901234606', 'pass123'),
                     ('PER000056', N'Nguyễn Hồng Phúc', 'phuc.nguyen@email.com', '0901234607', 'pass123'),
                     ('PER000057', N'Trần Minh Luân', 'luan.tran@email.com', '0901234608', 'pass123'),
                     ('PER000058', N'Lê Thị Yến', 'yen.le@email.com', '0901234609', 'pass123'),
                     ('PER000059', N'Phạm Hữu Tài', 'tai.pham@email.com', '0901234610', 'pass123'),
                     ('PER000060', N'Hoàng Minh Châu', 'chau.hoang@email.com', '0901234611', 'pass123'),
                     ('PER000061', N'Vũ Quang Đại', 'dai.vu@email.com', '0901234612', 'pass123'),
                     ('PER000062', N'Đỗ Văn Kiên', 'kien.do@email.com', '0901234613', 'pass123'),
                     ('PER000063', N'Bùi Văn Tùng', 'tung.bui@email.com', '0901234614', 'pass123'),
                     ('PER000064', N'Đặng Thị Hà', 'ha.dang@email.com', '0901234615', 'pass123'),
                     ('PER000065', N'Dương Văn Sỹ', 'sy.duong@email.com', '0901234616', 'pass123'),
                     ('PER000066', N'Nguyễn Thị Tuyết', 'tuyet.nguyen@email.com', '0901234617', 'pass123'),
                     ('PER000067', N'Trần Hữu Việt', 'viet.tran@email.com', '0901234618', 'pass123'),
                     ('PER000068', N'Lê Văn Cường', 'cuong.le@email.com', '0901234619', 'pass123'),
                     ('PER000069', N'Phạm Thu Nguyệt', 'nguyet.pham@email.com', '0901234620', 'pass123'),
                     ('PER000070', N'Hoàng Văn Thắng', 'thang.hoang@email.com', '0901234621', 'pass123'),
                     ('PER000071', N'Vũ Thị Phương', 'phuong.vu@email.com', '0901234622', 'pass123'),
                     ('PER000072', N'Đỗ Minh Quang', 'quang.do@email.com', '0901234623', 'pass123'),
                     ('PER000073', N'Bùi Văn Thọ', 'tho.bui@email.com', '0901234624', 'pass123'),
                     ('PER000074', N'Đặng Đình Phong', 'phong.dang@email.com', '0901234625', 'pass123'),
                     ('PER000075', N'Dương Thị Lành', 'lanh.duong@email.com', '0901234626', 'pass123'),
                     ('PER000076', N'Nguyễn Minh Hiếu', 'hieu.nguyen@email.com', '0901234627', 'pass123'),
                     ('PER000077', N'Trần Đình Khải', 'khai.tran@email.com', '0901234628', 'pass123'),
                     ('PER000078', N'Lê Thị Nga', 'nga.le@email.com', '0901234629', 'pass123'),
                     ('PER000079', N'Phạm Văn Đức', 'duc.pham2@email.com', '0901234630', 'pass123'),
                     ('PER000080', N'Hoàng Thị Lan', 'lan.hoang@email.com', '0901234631', 'pass123'),
                     ('PER000081', N'Vũ Văn Hoàng', 'hoang.vu@email.com', '0901234632', 'pass123'),
                     ('PER000082', N'Đỗ Thanh Tùng', 'tung.do@email.com', '0901234633', 'pass123'),
                     ('PER000083', N'Bùi Thị Mai Hương', 'huong.bui@email.com', '0901234634', 'pass123'),
                     ('PER000084', N'Đặng Văn Sơn', 'son.dang@email.com', '0901234635', 'pass123'),
                     ('PER000085', N'Dương Minh Hải', 'hai.duong@email.com', '0901234636', 'pass123'),
                     ('PER000086', N'Nguyễn Thị Ánh', 'anh.nguyen@email.com', '0901234637', 'pass123'),
                     ('PER000087', N'Trần Văn Long', 'long.tran@email.com', '0901234638', 'pass123'),
                     ('PER000088', N'Lê Minh Tuấn', 'tuan.le@email.com', '0901234639', 'pass123'),
                     ('PER000089', N'Phạm Văn Lực', 'luc.pham@email.com', '0901234640', 'pass123'),
                     ('PER000090', N'Hoàng Thị Kim', 'kim.hoang@email.com', '0901234641', 'pass123'),
                     ('PER000091', N'Vũ Đình Khải', 'khai.vu@email.com', '0901234642', 'pass123'),
                     ('PER000092', N'Đỗ Văn Nam', 'nam.do@email.com', '0901234643', 'pass123'),
                     ('PER000093', N'Bùi Minh Hiền', 'hien.bui@email.com', '0901234644', 'pass123'),
                     ('PER000094', N'Đặng Văn Lý', 'ly.dang@email.com', '0901234645', 'pass123'),
                     ('PER000095', N'Dương Văn Vinh', 'vinh.duong@email.com', '0901234646', 'pass123'),
                     ('PER000096', N'Nguyễn Thị Hà', 'ha.nguyen@email.com', '0901234647', 'pass123'),
                     ('PER000097', N'Trần Văn Dũng', 'dung.tran@email.com', '0901234648', 'pass123'),
                     ('PER000098', N'Lê Thị Phương Thảo', 'thao.le@email.com', '0901234649', 'pass123'),
                     ('PER000099', N'Phạm Minh Triết', 'triet.pham@email.com', '0901234650', 'pass123'),
                     ('PER000100', N'Hoàng Văn Hưng', 'hung.hoang@email.com', '0901234651', 'pass123');
                     GO
                     
                     ----------------------------------------------------------------------
                     -- B??C 4: THÊM D? LI?U CÁC TR?N ??U (MATCHES) - DÙNG ĐỊNH DẠNG M001 (CHAR(4))
                     ----------------------------------------------------------------------
                     PRINT 'STEP 4: Inserting Matches (M001 - 4 chars)...';
                     INSERT INTO Matches (match_id, home_team, away_team, stadium_id, match_date, tournament) VALUES
                     -- ID 2022
                     ('M001', N'Qatar', N'Ecuador', 'STD002', '2022-11-20 19:00:00', N'Group stage'),
                     ('M002', N'England', N'Iran', 'STD005', '2022-11-21 16:00:00', N'Group stage'),
                     ('M003', N'Senegal', N'Netherlands', 'STD006', '2022-11-21 19:00:00', N'Group stage'),
                     ('M004', N'United States', N'Wales', 'STD004', '2022-11-21 22:00:00', N'Group stage'),
                     ('M005', N'Argentina', N'Saudi Arabia', 'STD001', '2022-11-22 13:00:00', N'Group stage'),
                     ('M006', N'Denmark', N'Tunisia', 'STD003', '2022-11-22 16:00:00', N'Group stage'),
                     ('M007', N'Mexico', N'Poland', 'STD007', '2022-11-22 19:00:00', N'Group stage'),
                     ('M008', N'France', N'Australia', 'STD008', '2022-11-22 22:00:00', N'Group stage'),
                     ('M009', N'Morocco', N'Croatia', 'STD002', '2022-11-23 13:00:00', N'Group stage'),
                     ('M010', N'Germany', N'Japan', 'STD005', '2022-11-23 16:00:00', N'Group stage'),
                     ('M011', N'Spain', N'Costa Rica', 'STD006', '2022-11-23 19:00:00', N'Group stage'),
                     ('M012', N'Belgium', N'Canada', 'STD004', '2022-11-23 22:00:00', N'Group stage'),
                     ('M013', N'Switzerland', N'Cameroon', 'STD008', '2022-11-24 13:00:00', N'Group stage'),
                     ('M014', N'Uruguay', N'Korea Republic', 'STD003', '2022-11-24 16:00:00', N'Group stage'),
                     ('M015', N'Portugal', N'Ghana', 'STD007', '2022-11-24 19:00:00', N'Group stage'),
                     ('M016', N'Brazil', N'Serbia', 'STD001', '2022-11-24 22:00:00', N'Group stage'),
                     ('M017', N'Croatia', N'Morocco', 'STD005', '2022-12-17 18:00:00', N'Third place'),
                     ('M018', N'Argentina', N'France', 'STD001', '2022-12-18 18:00:00', N'Final'),
                     -- ID 2018
                     ('M019', N'Russia', N'Saudi Arabia', 'STD009', '2018-06-14 18:00:00', N'Group stage'),
                     ('M020', N'Egypt', N'Uruguay', 'STD020', '2018-06-15 17:00:00', N'Group stage'),
                     ('M021', N'Morocco', N'Iran', 'STD010', '2018-06-15 18:00:00', N'Group stage'),
                     ('M022', N'Portugal', N'Spain', 'STD011', '2018-06-15 21:00:00', N'Group stage'),
                     ('M023', N'France', N'Australia', 'STD016', '2018-06-16 13:00:00', N'Group stage'),
                     ('M024', N'Argentina', N'Iceland', 'STD013', '2018-06-16 16:00:00', N'Group stage'),
                     ('M025', N'Peru', N'Denmark', 'STD018', '2018-06-16 19:00:00', N'Group stage'),
                     ('M026', N'Croatia', N'Nigeria', 'STD019', '2018-06-16 21:00:00', N'Group stage'),
                     ('M027', N'Belgium', N'England', 'STD010', '2018-07-14 17:00:00', N'Third place'),
                     ('M028', N'France', N'Croatia', 'STD009', '2018-07-15 18:00:00', N'Final'),
                     -- ID 2014
                     ('M029', N'Brazil', N'Croatia', 'STD023', '2014-06-12 17:00:00', N'Group stage'),
                     ('M030', N'Mexico', N'Cameroon', 'STD031', '2014-06-13 13:00:00', N'Group stage'),
                     ('M031', N'Spain', N'Netherlands', 'STD026', '2014-06-13 16:00:00', N'Group stage'),
                     ('M032', N'Chile', N'Australia', 'STD029', '2014-06-13 18:00:00', N'Group stage'),
                     ('M033', N'Colombia', N'Greece', 'STD025', '2014-06-14 13:00:00', N'Group stage'),
                     ('M034', N'Brazil', N'Netherlands', 'STD022', '2014-07-12 17:00:00', N'Third place'),
                     ('M035', N'Germany', N'Argentina', 'STD021', '2014-07-13 16:00:00', N'Final'),
                     -- ID 2025
                     ('M036', N'America', N'Russia', 'STD021', '2025-07-30 22:00:00', N'Final');
                     GO
                     
                     ----------------------------------------------------------------------
                     -- BƯỚC 5: MÔ PHỎNG QUÁ TRÌNH BÁN VÉ (LOGIC GIỎ HÀNG 1-N)
                     ----------------------------------------------------------------------
                     PRINT 'STEP 5: Auto-simulating transactions with new ID formats (Block Seats A001, B002, ...)...';
                     
                     -- KIỂM TRA VÀ GIẢI PHÓNG CURSOR CŨ (KHẮC PHỤC LỖI 16915 VÀ 16905)
                     IF CURSOR_STATUS('local', 'match_cursor') >= 0
                     BEGIN
                         CLOSE match_cursor;
                         DEALLOCATE match_cursor;
                     END
                     
                     -- Khai báo các biến để lặp (cursor)
                     DECLARE @match_id CHAR(4), @stadium_id CHAR(10), @tournament NVARCHAR(100), @match_date DATETIME;
                     DECLARE match_cursor CURSOR LOCAL FOR
                     SELECT match_id, stadium_id, tournament, match_date
                     FROM Matches;
                     
                     OPEN match_cursor;
                     FETCH NEXT FROM match_cursor INTO @match_id, @stadium_id, @tournament, @match_date;
                     
                     -- Bắt đầu lặp qua từng TRẬN ĐẤU
                     WHILE @@FETCH_STATUS = 0
                     BEGIN
                         PRINT ' -> Processing match: ' + @match_id;
                     
                         -- Biến chung cho mỗi trận
                         DECLARE @base_price DECIMAL(12, 2);
                         DECLARE @num_orders_per_match INT;
                     
                         -- 1. Thiết lập MỨC GIÁ CƠ SỞ theo giải đấu (Đơn vị: VND)
                         SELECT @base_price = CASE
                             WHEN @tournament = 'Final' THEN 200000 
                             WHEN @tournament = 'Third place' THEN 100000
                             WHEN @tournament LIKE '%Group%' THEN 30000
                             ELSE 80000 
                         END;
                         
                         -- Sinh ngẫu nhiên 50-100 đơn hàng cho mỗi trận
                         SET @num_orders_per_match = CAST(RAND() * 50 AS INT) + 50;
                         
                         -- *** KHỞI TẠO BIẾN ĐẾM GHẾ CHO TRẬN ĐẤU NÀY ***
                         DECLARE @seat_row_counter INT = 1;
                     
                         -- 2. Vòng lặp bên trong: Tạo các ĐƠN HÀNG
                         DECLARE @order_counter INT = 1;
                         WHILE @order_counter <= @num_orders_per_match
                         BEGIN
                             -- *** T?O ORDER ID ***
                             DECLARE @order_id CHAR(10) = 'ORD' + FORMAT(NEXT VALUE FOR OrderSeq, '0000000');
                             
                             DECLARE @user_id CHAR(9) = 'PER' + FORMAT(CAST(RAND() * 99 AS INT) + 1, '000000');
                             DECLARE @order_date DATETIME = DATEADD(DAY, -CAST(RAND() * 30 AS INT) - 1, @match_date);
                             DECLARE @order_status NVARCHAR(20) = CASE WHEN RAND() > 0.1 THEN 'Paid' ELSE 'Pending' END;
                             DECLARE @total_amount_for_order DECIMAL(12, 2) = 0;
                             
                             -- ==========================================================
                             -- TẠO ĐƠN HÀNG (CHA) TRƯỚC
                             -- ==========================================================
                             INSERT INTO Orders (order_id, user_id, order_date, total_amount, status)
                             VALUES (@order_id, @user_id, @order_date, 0, @order_status); 
                     
                             -- 3. Vòng lặp trong cùng: Tạo các VÉ (CON) cho đơn hàng
                             DECLARE @num_tickets_per_order INT = CAST(RAND() * 3 AS INT) + 1;
                             DECLARE @ticket_counter INT = 1;
                             
                             WHILE @ticket_counter <= @num_tickets_per_order
                             BEGIN
                                 -- *** T?O SEAT ID M?I THEO DÃY (A001, B002, ...) ***
                                 -- Lấy chữ cái dãy ghế (luân phiên A, B, C, D)
                                 DECLARE @seat_block CHAR(1) = 
                                     CASE (@seat_row_counter - 1) % 4
                                         WHEN 0 THEN 'A'
                                         WHEN 1 THEN 'B'
                                         WHEN 2 THEN 'C'
                                         ELSE 'D' -- Xử lý số dư 3
                                     END;
                     
                                 -- Số thứ tự ghế trong dãy, tăng lên sau mỗi vé được bán
                                 DECLARE @seat_number CHAR(3) = FORMAT(@seat_row_counter, '000');
                     
                                 -- Ghép lại: Ví d?: A001, A002, B003, B004, C005, ...
                                 DECLARE @seat_id CHAR(10) = @seat_block + @seat_number;
                                 
                                 SET @seat_row_counter = @seat_row_counter + 1; -- Tăng biến đếm ghế
                     
                                 DECLARE @final_price DECIMAL(12, 2) = @base_price + (RAND() * 10000);
                                 DECLARE @ticket_id CHAR(10) = 'TIC' + FORMAT(NEXT VALUE FOR TicketSeq, '0000000');
                     
                                 -- Thêm ghế vào bang Seats.
                                 INSERT INTO Seats (match_id, stadium_id, seat_id, status) 
                                 VALUES (@match_id, @stadium_id, @seat_id, 1);
                                 
                                 -- Thêm vé vào bảng Tickets
                                 INSERT INTO Tickets (ticket_id, order_id, match_id, seat_id, price)
                                 VALUES (@ticket_id, @order_id, @match_id, @seat_id, @final_price);
                     
                                 -- Cộng dồn vào tổng tiền của đơn hàng
                                 SET @total_amount_for_order = @total_amount_for_order + @final_price;
                                 SET @ticket_counter = @ticket_counter + 1;
                             END; -- K?t thúc vòng l?p VÉ
                     
                             -- ==========================================================
                             -- CẬP NHẬT LẠI ĐƠN HÀNG VỚI TỔNG TIỀN CHÍNH XÁC
                             -- ==========================================================
                             UPDATE Orders
                             SET total_amount = @total_amount_for_order
                             WHERE order_id = @order_id;
                     
                            -- 5. Thêm THANH TOÁN (nếu đơn hàng 'Paid')
                             IF @order_status = 'Paid'
                             BEGIN
                                 DECLARE @payment_id CHAR(10) = 'PID' + FORMAT(NEXT VALUE FOR PaymentSeq, '0000000');
                                 
                                 DECLARE @method NVARCHAR(20) = CASE CAST(RAND() * 3 AS INT)
                                                                 WHEN 0 THEN 'card'
                                                                 WHEN 1 THEN 'momo'
                                                                 ELSE 'paypal'
                                                               END;
                                 INSERT INTO Payments (payment_id, order_id, method, amount, status, payment_date)
                                 VALUES (@payment_id, @order_id, @method, @total_amount_for_order, 'success', DATEADD(MINUTE, 1, @order_date));
                             END;
                             
                             SET @order_counter = @order_counter + 1;
                         END; -- Kết thúc vòng lặp đơn hàng
                     
                         -- Lấy trận đấu tiếp theo
                         FETCH NEXT FROM match_cursor INTO @match_id, @stadium_id, @tournament, @match_date;
                     END;
                     
                     -- Giải phóng cursor (Quan trọng: phải làm cuối cùng!)
                     CLOSE match_cursor;
                     DEALLOCATE match_cursor;
                     GO
                     
                     PRINT '------------------------------------------------';
                     PRINT 'THÀNH CÔNG: Hoàn tất tạo dữ liệu với ID ghế theo dãy (A001, B002, v.v.) và giá vé được điều chỉnh.';
                     PRINT '------------------------------------------------';
                     GO                              
                     """;
        sql = sql.replaceAll("Database_name_here", name);
        try (
                Connection con = getConnection(); 
                Statement stmt = con.createStatement()
                ) {   
            String[] batches = sql.split("(?m)^GO\\s*$");
            for (String batch : batches) {
                String trimmed = batch.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int createTablesAndData() throws SQLException, ClassNotFoundException {
        try (
                Connection con = getServerConnection(server, password); Statement stmt = con.createStatement()) {
            String sql = "CREATE DATABASE " + name;
            stmt.execute(sql);
        } catch (Exception e) {
            return 0;
        }

        String sql = """            
                CREATE TABLE Users(
                    user_id CHAR(9) NOT NULL PRIMARY KEY,
                    full_name NVARCHAR(100) NOT NULL,
                    email NVARCHAR(100) NOT NULL,
                    phone NVARCHAR(20) NOT NULL,
                    pass NVARCHAR(20) NOT NULL,
                    created DATETIME NOT NULL DEFAULT GETDATE()
                );

                CREATE TABLE Stadiums(
                    stadium_id CHAR(10) NOT NULL PRIMARY KEY,
                    name NVARCHAR(100) NOT NULL,
                    location NVARCHAR(100) NOT NULL,
                    capacity INT
                );

                CREATE TABLE Matches(
                    match_id CHAR(10) NOT NULL PRIMARY KEY,
                    home_team NVARCHAR(100) NOT NULL,
                    away_team NVARCHAR(100) NOT NULL,
                    stadium_id CHAR(10) NOT NULL,
                    match_date DATETIME NOT NULL,
                    tournament NVARCHAR(100),
                    CONSTRAINT fk_Matches_Stadium FOREIGN KEY (stadium_id) REFERENCES Stadiums(stadium_id)
                );

                CREATE TABLE Seats (
                    match_id CHAR(10) NOT NULL,
                    stadium_id CHAR(10) NOT NULL,
                    seat_id CHAR(10) NOT NULL,
                    status BIT NOT NULL,
                    CONSTRAINT pk_Seats PRIMARY KEY (match_id, seat_id),
                    CONSTRAINT fk_Seats_Match FOREIGN KEY (match_id) REFERENCES Matches(match_id)
                );

                CREATE TABLE Orders (
                    order_id CHAR(10) NOT NULL PRIMARY KEY,
                    user_id CHAR(9) NOT NULL,
                    order_date DATETIME DEFAULT GETDATE(),
                    total_amount DECIMAL(12,2) NOT NULL,
                    status NVARCHAR(20) NOT NULL DEFAULT 'Paid'
                        CHECK (status IN ('Pending','Paid','Cancelled')),
                    CONSTRAINT fk_Orders_User FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
                );

                CREATE TABLE Tickets (
                    ticket_id CHAR(10) NOT NULL PRIMARY KEY,
                    order_id CHAR(10) NOT NULL,
                    match_id CHAR(10) NOT NULL,
                    seat_id CHAR(10) NOT NULL,
                    price DECIMAL(12,2) NOT NULL,
                    CONSTRAINT fk_Tickets_Order FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
                    CONSTRAINT fk_Tickets_Match FOREIGN KEY (match_id) REFERENCES Matches(match_id) ON DELETE CASCADE,
                    CONSTRAINT fk_Tickets_Seat FOREIGN KEY (match_id, seat_id) REFERENCES Seats(match_id, seat_id) ON DELETE CASCADE
                );

                CREATE TABLE Payments (
                    payment_id CHAR(10) NOT NULL PRIMARY KEY,
                    order_id CHAR(10) NOT NULL,
                    method NVARCHAR(20) NOT NULL
                        CHECK (method IN ('card','momo','paypal','cash')),
                    amount DECIMAL(12,2) NOT NULL,
                    status  NVARCHAR(20) NOT NULL DEFAULT 'success'
                        CHECK (status  IN ('success','failed','pending')),
                    payment_date DATETIME DEFAULT GETDATE(),
                    CONSTRAINT fk_Payments_Order FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE
                );

                INSERT INTO Stadiums VALUES
                ('STD001', N'Sân Mỹ Đình', N'Hà Nội', 48),
                ('STD002', N'Sân Thống Nhất', N'Hồ Chí Minh', 48);

                INSERT INTO Matches VALUES
                ('M001', N'Việt Nam', N'Thái Lan', 'STD001', '2025-12-29 19:00:00', N'Group State'),
                ('M002', N'Anh', N'Uruguay', 'STD002', '2025-12-30 19:00:00', N'Final'),
                ('M003', N'Brazil', N'Đức', 'STD001', '2025-11-20 19:00:00', N'Third Place'),
                ('M004', N'Nhật Bản', N'Bồ Đào Nha', 'STD002', '2025-12-24 19:00:00', N'Olympic');
                     
                INSERT INTO Seats (match_id, stadium_id, seat_id, status)
                SELECT 
                    m.match_id,
                    m.stadium_id,
                    r.c + RIGHT('00' + CAST(n.n AS VARCHAR(2)), 2) AS seat_id,
                    0 AS status
                FROM Matches m
                CROSS JOIN (VALUES ('A'),('B'),('C'),('D'),('E'),('F')) AS r(c)   -- Hàng ghế
                CROSS JOIN (VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10)) AS n(n); -- Số ghế
        """;

        try (
                Connection con = getConnection(); Statement stmt = con.createStatement()) {
            for (String s : sql.split(";")) {
                if (!s.trim().isEmpty()) {
                    stmt.execute(s);
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

}
