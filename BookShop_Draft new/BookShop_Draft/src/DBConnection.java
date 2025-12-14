import java.sql.*;

public class   DBConnection {

    public static Connection getConnection() throws Exception {
        final String URL = "jdbc:mysql://localhost:3307/Book_Shop";  // FIX PORT
        final String USERNAME = "root";
        final String PASSWORD = "1234";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void insertForRegister(String table, String id, String fname, String lname,
                                         String password, String email, String telno, String address)
            throws Exception {
        Connection con = getConnection();

        String sql = "INSERT INTO " + table +
                " (id, fname, lname, password, email, tel_no, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.setString(2, fname);
        stmt.setString(3, lname);
        stmt.setString(4, password);
        stmt.setString(5, email);
        stmt.setString(6, telno);
        stmt.setString(7, address);

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    public static boolean isEmailExist(String tableName, String email) throws Exception {
        boolean exists = false;
        String query = "SELECT COUNT(*) AS count FROM " + tableName + " WHERE email = ?";
        PreparedStatement pst = getConnection().prepareStatement(query);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            exists = rs.getInt("count") > 0;
        }
        rs.close();
        pst.close();
        return exists;
    }

    public static String loginCheck(String table, String id) throws Exception {
        Connection con = getConnection();

        String sql = "SELECT password FROM " + table + " WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        String password = null;

        if (rs.next()) {
            password = rs.getString("password");
        } else {
            password = "Invalid credentials";
        }

        rs.close();
        ps.close();
        con.close();
        return password;
    }

    public static void addBooks(String table, String isbn, int no_of_copies,
                                String author, String title, double price) throws Exception {

        Connection con = getConnection();

        String sql = "INSERT INTO " + table +
                " (isbn, no_of_copies, author, title, price) VALUES (?, ?, ?, ?,?)";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, isbn);
        stmt.setInt(2, no_of_copies);
        stmt.setString(3, author);
        stmt.setString(4, title);
        stmt.setDouble(5, price);

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    public static ResultSet search(String id,String table) throws Exception {
        Connection con = getConnection();

        String sql = "SELECT * FROM "+table+" WHERE id= "+id;
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        javax.sql.rowset.CachedRowSet crs = javax.sql.rowset.RowSetProvider.newFactory().createCachedRowSet();
        crs.populate(rs);

        rs.close();
        stmt.close();
        con.close();

        return crs;
    }
    public static void updateBookStock(String isbn, int newStock) throws Exception {
        Connection con = getConnection();

        String sql = "UPDATE Book SET No_of_Copies = ? WHERE ISBN = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, newStock);
        pst.setString(2, isbn);

        pst.executeUpdate();
        pst.close();
        con.close();
    }
    public static void deleteBook(String isbn) throws Exception {
        Connection con = getConnection();

        String sql = "DELETE FROM Book WHERE ISBN = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, isbn);

        pst.executeUpdate();
        pst.close();
        con.close();
    }
    public static ResultSet view(String table) throws Exception {
        Connection con = getConnection();

        String sql = "SELECT * FROM "+table;
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        javax.sql.rowset.CachedRowSet crs = javax.sql.rowset.RowSetProvider.newFactory().createCachedRowSet();
        crs.populate(rs);

        rs.close();
        ps.close();
        con.close();

        return crs;
    }

    public static String getID(String table) throws Exception{
        Connection con=getConnection();
        String value="";
        String sql = "SELECT id FROM "+table+" ORDER BY id DESC LIMIT 1";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            value=rs.getString(1);
            return value;
        }

        rs.close();
        ps.close();
        con.close();

        return "";
    }

    public static ResultSet orderHistory(String id) throws Exception{
        Connection con = getConnection();

        String sql = "SELECT o.id,o.date_of_order,cart_status FROM  order_table o \n" +
                "       INNER JOIN Cart c ON o.CART_id= c.id WHERE c.customer_id= ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        javax.sql.rowset.CachedRowSet crs = javax.sql.rowset.RowSetProvider.newFactory().createCachedRowSet();
        crs.populate(rs);

        rs.close();
        ps.close();
        con.close();

        return crs;
    }
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection established successfully.");
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}