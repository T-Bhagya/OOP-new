import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class viewOrders {
    public static JPanel createOrderView(CardLayout cardLayout, JPanel mainPanel) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = Style.createCardPanel();
        Style.addHeader(card, "📦", "View Orders", "Manage customer orders");

        String[] columns = {"Order ID", "Cart ID", "ISBN", "Date of Order", "Quantity","Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setMaximumSize(new Dimension(500, 300));


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(255, 255, 255, 0));
        searchPanel.setMaximumSize(new Dimension(500, 40));

        JTextField searchField = new JTextField(20);
        Style.styleField(searchField);
        JButton searchBtn = new JButton("Search");
        Style.stylePrimaryButton(searchBtn);
        searchBtn.setPreferredSize(new Dimension(80, 35));

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        card.add(searchPanel);
        card.add(Box.createVerticalStrut(10));

        searchBtn.addActionListener(e -> {
            String id=searchField.getText();
            try {
                ResultSet rs = DBConnection.search(id,"AdminOrder");
                model.setRowCount(0);
                while(rs.next()) {
                    Object[] row = {
                            rs.getString("id"),
                            rs.getString("cart_id"),
                            rs.getString("ISBN"),
                            rs.getString("date_Of_order"),
                            rs.getInt("Qty"),
                            rs.getDouble("Line_Total")
                    };
                    model.addRow(row);
                }
            }catch(Exception ex){
                ex.getMessage();
            }
        });

        // Load data from database
        try {
            ResultSet rs = DBConnection.view("AdminOrder");
            while (rs.next()) {
                Object[] row = {
                        rs.getString("id"),
                        rs.getString("Cart_id"),
                        rs.getString("ISBN"),
                        rs.getString("Date_of_order"),
                        rs.getInt("qty"),
                        rs.getDouble("Line_Total")
                };
                model.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(card, "Database Error: " + ex.getMessage());
        }

        card.add(scrollPane);
        card.add(Box.createVerticalStrut(20));

        // Back Button
        JButton backBtn = new JButton("← Back to Dashboard");
        Style.styleSecondaryButton(backBtn);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        card.add(backBtn);
        wrapper.add(card);
        return wrapper;
    }

}