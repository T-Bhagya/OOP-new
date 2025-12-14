import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Style {

    // Colors
    public static final Color PRIMARY_COLOR = new Color(245, 158, 11); // Orange
    public static final Color TEXT_COLOR = new Color(120, 53, 15);     // Dark Brown
    public static final Color BG_LIGHT = new Color(255, 251, 235);
    public static final Color BG_DARK = new Color(254, 242, 242);

    // Header Generator
    public static void addHeader(JPanel panel, String icon, String title, String sub) {
        JLabel lIcon = new JLabel(icon);
        lIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lTitle = new JLabel(title);
        lTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lTitle.setForeground(TEXT_COLOR);
        lTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lSub = new JLabel(sub);
        lSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lSub.setForeground(new Color(180, 83, 9));
        lSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lIcon);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lTitle);
        panel.add(lSub);
        panel.add(Box.createVerticalStrut(25));
    }

    // Button Styling
    public static void stylePrimaryButton(JButton btn) {
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
    }

    public static void styleSecondaryButton(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setForeground(TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
        btn.setOpaque(true);
    }

    public static void styleDashboardButton(JButton btn) {
        btn.setBackground(BG_LIGHT);
        btn.setForeground(TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                new EmptyBorder(10, 20, 10, 20)
        ));
        btn.setOpaque(true);
    }

    public static void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(253, 230, 138)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(BG_LIGHT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // Card Panel Container
    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255, 230));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(253, 230, 138), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));
        return card;
    }

    // Custom Background Panel Class
    public static class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, BG_LIGHT, w, h, BG_DARK);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }
}