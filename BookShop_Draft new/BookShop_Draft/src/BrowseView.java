import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class BrowseView {
    public static JPanel createView(CardLayout cardLayout,JPanel mainPanel, String customerId){
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(20, 20, 20, 20);

        //---SETUP CARD---
        JPanel card = Style.createCardPanel();
        card.setLayout(new BorderLayout(0, 20));

        // --- TOP: Header ---
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        Style.addHeader(headerPanel, "📚", "Find your Book", "Browse our collection");
        card.add(headerPanel, BorderLayout.NORTH);

        // --- CENTER: Table ---
        String[] columns = {"ISBN", "Title", "Author", "Stock","Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(253, 230, 138));

        JScrollPane scrollPane = new JScrollPane(table);
        card.add(scrollPane, BorderLayout.CENTER);

        //---LOAD DATA---
        loadBooks(model);

        // --- BOTTOM: Controls ---
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        bottomPanel.setOpaque(false);

        // Qty & Add Button
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setOpaque(false);
        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        JButton addBtn = new JButton("Add to Cart");
        Style.stylePrimaryButton(addBtn);

        controlPanel.add(new JLabel("Qty:"));
        controlPanel.add(qtySpinner);
        controlPanel.add(addBtn);

        //---NAVIGATION---
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navPanel.setOpaque(false);
        JButton backBtn = new JButton("← Back to Dashboard");
        Style.styleSecondaryButton(backBtn);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        navPanel.add(backBtn);

        bottomPanel.add(controlPanel);
        bottomPanel.add(navPanel);
        card.add(bottomPanel, BorderLayout.SOUTH);

        //---LOGIC---
        addBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(mainPanel, "Select a book!");
                return;
            }
            String isbn = model.getValueAt(row, 0).toString();
            int qty = (int) qtySpinner.getValue();
            CartService.addToCart(customerId, isbn, qty);
        });

        wrapper.add(card, gbc);
        return wrapper;
    }

    private static void loadBooks(DefaultTableModel model) {
        try {
            ResultSet rs = DBConnection.view("Book");
            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getString("ISBN"), rs.getString("Title"), rs.getString("Author"), rs.getInt("No_of_Copies"),rs.getDouble("Price")
                });
            }
        } catch(Exception e) { e.printStackTrace(); }
    }
}
