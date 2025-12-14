import javax.swing.*;
import java.awt.*;

public class CustomerDashboard {
    private JFrame dashboardFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String customerId;

    public CustomerDashboard(String customerId){
        this.customerId = customerId;

        //---FRAME SETUP---
        dashboardFrame = new JFrame("Cinnamon & Chapters - Customer Store");
        dashboardFrame.setSize(700, 700);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setLocationRelativeTo(null);

        //---LAYOUT SETUP---
        cardLayout = new CardLayout();
        mainPanel = new Style.BackgroundPanel();
        mainPanel.setLayout(cardLayout);

        //---ADD VIEWS---
        mainPanel.add(createHome(), "HOME");
        mainPanel.add(BrowseView.createView(cardLayout, mainPanel, customerId), "BROWSE");
        mainPanel.add(CartView.createView(cardLayout, mainPanel, customerId), "CART");
        mainPanel.add(HistoryView.createView(cardLayout, mainPanel, customerId), "HISTORY");

        dashboardFrame.setContentPane(mainPanel);
        dashboardFrame.setVisible(true);
    }

    //---BACKGROUND, STYLES, LAYOUT AND, OTHER UI COMPONENTS---
    private JPanel createHome() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = Style.createCardPanel();

        Style.addHeader(card, "👋", "Welcome Back!", "Ready to find your next adventure?");

        // Buttons
        JButton browseBtn = new JButton("📖  Search Books"); // Renamed from your code
        Style.styleDashboardButton(browseBtn);
        browseBtn.addActionListener(e -> cardLayout.show(mainPanel, "BROWSE"));

        JButton cartBtn = new JButton("🛒  Show Cart");
        Style.styleDashboardButton(cartBtn);
        cartBtn.addActionListener(e -> cardLayout.show(mainPanel, "CART"));

        JButton historyBtn = new JButton("📜  Show History");
        Style.styleDashboardButton(historyBtn);
        historyBtn.addActionListener(e -> cardLayout.show(mainPanel, "HISTORY"));

        JButton logoutBtn = new JButton("Log Out");
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setForeground(new Color(220, 38, 38));
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutBtn.addActionListener(e -> {
            dashboardFrame.dispose();
            new OpenFrame(); // Go back to login
        });

        card.add(browseBtn);
        card.add(Box.createVerticalStrut(15));
        card.add(cartBtn);
        card.add(Box.createVerticalStrut(15));
        card.add(historyBtn);
        card.add(Box.createVerticalStrut(25));
        card.add(logoutBtn);

        wrapper.add(card);
        return wrapper;
    }
}
