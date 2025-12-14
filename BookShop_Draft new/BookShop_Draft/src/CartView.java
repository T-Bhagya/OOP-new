import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartView {

    public static JPanel createView(CardLayout cardLayout,JPanel mainPanel,String customerId){
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(20, 20, 20, 20);

        JPanel card = Style.createCardPanel();
        card.setLayout(new BorderLayout(0, 20));

        //---HEADER---
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        Style.addHeader(headerPanel, "🛒", "Your Cart", "Items ready for checkout");
        card.add(headerPanel, BorderLayout.NORTH);

        //---CART TABLE---
        String[] columns = {"Book Title", "Qty","Price", "Date Added"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(253, 230, 138));

        JScrollPane scrollPane = new JScrollPane(table);
        card.add(scrollPane, BorderLayout.CENTER);

        //---LOAD CART ITEMS---
        loadCartItems(model, customerId);
        // --- Total Label in bottom-right corner ---
        JLabel totalLabel = new JLabel("Total: 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setForeground(new Color(34, 139, 34)); // green highlight
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        updateTotal(model,totalLabel);

        //---NAVIGATION----
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navPanel.setOpaque(false);
        JButton backBtn = new JButton("← Back to Dashboard");
        Style.styleSecondaryButton(backBtn);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        navPanel.add(backBtn);
        navPanel.setLayout(new BorderLayout());
        navPanel.add(backBtn, BorderLayout.WEST);
        navPanel.add(totalLabel, BorderLayout.EAST);


        card.add(navPanel, BorderLayout.SOUTH);

        wrapper.add(card, gbc);
        return wrapper;
    }

    private static void loadCartItems(DefaultTableModel model, String customerId) {
        String sql = "SELECT Title, o.qty,o.date_of_order, b.price*o.qty AS line_total FROM BOOK B INNER JOIN order_table o ON\n" +
                "b.ISBN=o.ISBN INNER JOIN Cart c ON o.CART_id= c.id\n" +
                "                        WHERE CART_status=0 AND CUSTOMER_ID= ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                 Object[] row={
                        rs.getString("Title"), rs.getInt("Qty"), rs.getDouble("Line_total"),rs.getDate("Date_of_order")
                };
                 model.addRow(row);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void updateTotal(DefaultTableModel model, JLabel totalLabel) {
        double total = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += (double) model.getValueAt(i, 2); // column index 2 = Price/Line_Total
        }
        totalLabel.setText("Total: " + String.format("%.2f", total));
    }

}
