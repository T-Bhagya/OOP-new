import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistoryView {

    public static JPanel createView(CardLayout cardLayout, JPanel mainPanel, String customerId) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(20, 20, 20, 20);

        JPanel card = Style.createCardPanel();
        card.setLayout(new BorderLayout(0, 20));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        Style.addHeader(headerPanel, "📜", "History", "Your past purchases");
        card.add(headerPanel, BorderLayout.NORTH);

        // Placeholder Table
        String[] columns = {"Order ID", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(253, 230, 138));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setMaximumSize(new Dimension(500, 300));
        card.add(scrollPane, BorderLayout.CENTER);
        try{
            ResultSet rs=DBConnection.orderHistory(customerId);
            while (rs.next()) {
                Object[] row = {
                        rs.getString("id"),
                        rs.getDate("Date_of_order"),
                        rs.getInt("cart_status"),
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        // Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navPanel.setOpaque(false);
        JButton backBtn = new JButton("← Back to Dashboard");
        Style.styleSecondaryButton(backBtn);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        navPanel.add(backBtn);

        card.add(navPanel, BorderLayout.SOUTH);

        wrapper.add(card, gbc);
        return wrapper;
    }
}