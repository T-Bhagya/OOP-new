import java.awt.*;
import javax.swing.*;

public class AdminDashBoard {
    private JFrame dashboardFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public AdminDashBoard() {
        //---MAIN FRAME SETUP--
        dashboardFrame = new JFrame("Admin Dashboard - Cinnamon & Chapters");
        dashboardFrame.setSize(700, 700);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setLocationRelativeTo(null);
        //---LAYOUT SETUP---
        cardLayout = new CardLayout();
        mainPanel = new Style.BackgroundPanel();
        mainPanel.setLayout(cardLayout);

        //---ADDING VIEWS FROM EXTERNAL CLASSES---
        mainPanel.add(createDashboardHome(), "DASHBOARD");
        mainPanel.add(BookViews.createBookMenuView(cardLayout, mainPanel), "BOOK_MENU");
        mainPanel.add(BookViews.createBookDetailsView(cardLayout, mainPanel, dashboardFrame), "BOOK_DETAILS");
        mainPanel.add(CustomerView.createView(cardLayout, mainPanel), "CUSTOMER_VIEW");
        mainPanel.add(viewOrders.createOrderView(cardLayout, mainPanel), "ORDER_VIEW");

        dashboardFrame.setContentPane(mainPanel);
        dashboardFrame.setVisible(true);
    }

    //---ADMIN DASHBOARD, BACKGROUND, LAYOUT AND UI COMPONENTS.---
    private JPanel createDashboardHome() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = Style.createCardPanel();

        Style.addHeader(card, "👨‍💼", "Admin Dashboard", "Manage your bookstore");

        // Buttons
        JButton addBtn = new JButton("📚  Manage Books");
        Style.styleDashboardButton(addBtn);
        addBtn.addActionListener(e -> cardLayout.show(mainPanel, "BOOK_MENU"));

        JButton viewOrdersBtn = new JButton("📦  View Orders");
        Style.styleDashboardButton(viewOrdersBtn);
        viewOrdersBtn.addActionListener(e -> cardLayout.show(mainPanel, "ORDER_VIEW"));

        JButton viewCusBtn = new JButton("👥  Customer Details");
        Style.styleDashboardButton(viewCusBtn);
        viewCusBtn.addActionListener(e -> cardLayout.show(mainPanel, "CUSTOMER_VIEW"));

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
            new OpenFrame();
        });

        card.add(addBtn);
        card.add(Box.createVerticalStrut(15));
        card.add(viewOrdersBtn);
        card.add(Box.createVerticalStrut(15));
        card.add(viewCusBtn);
        card.add(Box.createVerticalStrut(25));
        card.add(logoutBtn);

        wrapper.add(card);
        return wrapper;
    }
}