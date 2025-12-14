import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class CartService {
    //---GENERATES ID---
    private static String generateID(String prefix) {
        return prefix + System.currentTimeMillis() / 1000000;
    }

    public static void addToCart(String customerId, String isbn, int quantity) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();

            // ---CHECKING FOR AN ACTIVE CART---
            String cartId = null;
            String checkCart = "SELECT id FROM Cart WHERE Customer_id = ? AND Cart_Status = 0";
            PreparedStatement pstCheck = con.prepareStatement(checkCart);
            pstCheck.setString(1, customerId);
            ResultSet rs = pstCheck.executeQuery();

            if (rs.next()) {
                cartId = rs.getString("id");
            } else {
                // ---CREATING CART AND DELIVERY---
                String delId = generateID("DEL");
                String insertDel = "INSERT INTO Delivery (id, Del_Status, Del_Date) VALUES (?, 0, NULL)";
                PreparedStatement pstDel = con.prepareStatement(insertDel);
                pstDel.setString(1, delId);
                pstDel.executeUpdate();

                cartId = generateID("CRT");
                String insertCart = "INSERT INTO Cart (id, Cart_Status, No_of_Items, Del_id, Customer_id) VALUES (?, 0, 0, ?, ?)";
                PreparedStatement pstCart = con.prepareStatement(insertCart);
                pstCart.setString(1, cartId);
                pstCart.setString(2, delId);
                pstCart.setString(3, customerId);
                pstCart.executeUpdate();
            }

            // ---ADDING ITEMS TO THE ORDER TABLE---
            String orderId = generateID("ORD");
            String insertOrder = "INSERT INTO Order_Table (id, Cart_id, ISBN, Date_of_order, Qty) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstOrder = con.prepareStatement(insertOrder);
            pstOrder.setString(1, orderId);
            pstOrder.setString(2, cartId);
            pstOrder.setString(3, isbn);
            pstOrder.setDate(4, Date.valueOf(LocalDate.now()));
            pstOrder.setInt(5, quantity);
            pstOrder.executeUpdate();

            //---UPDATING THE QUANTITY---
            String updateCount = "UPDATE Cart SET No_of_Items = No_of_Items + 1 WHERE id = ?";
            PreparedStatement pstCount = con.prepareStatement(updateCount);
            pstCount.setString(1, cartId);
            pstCount.executeUpdate();

            JOptionPane.showMessageDialog(null, "Added to Cart successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}