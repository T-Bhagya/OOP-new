import javax.swing.*;
import java.awt.*;

public class BookViews {

    //---VIEW 1: BOOK MENU---
    public static JPanel createBookMenuView(CardLayout cardLayout, JPanel mainPanel) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = Style.createCardPanel();
        Style.addHeader(card, "📚", "Books Menu", "Inventory Management");

        JButton updateBtn = new JButton("Update Book Details");
        Style.stylePrimaryButton(updateBtn);
        updateBtn.addActionListener(e -> cardLayout.show(mainPanel, "BOOK_DETAILS"));

        JButton backBtn = new JButton("← Back to Dashboard");
        Style.styleSecondaryButton(backBtn);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        card.add(updateBtn);
        card.add(Box.createVerticalStrut(15));
        card.add(backBtn);

        wrapper.add(card);
        return wrapper;
    }

    //---VIEW 2: BOOK DETAILS FORM---
    public static JPanel createBookDetailsView(CardLayout cardLayout, JPanel mainPanel, JFrame parentFrame) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = Style.createCardPanel();
        Style.addHeader(card, "✏️", "Update Book", "Edit details below");

        JTextField txtIsbn = addFormField(card, "ISBN:");
        JTextField txtTitle = addFormField(card, "Book Title:");
        JTextField txtAuthor = addFormField(card, "Author:");
        JTextField txtCopy = addFormField(card, "No. of Copies:");
        JTextField txtPrice = addFormField(card, "Price");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(new Color(255, 255, 255, 0));

        JButton saveBtn = new JButton("Update");
        Style.stylePrimaryButton(saveBtn);

        JButton backBtn = new JButton("Back");
        Style.styleSecondaryButton(backBtn);

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);
        card.add(Box.createVerticalStrut(20));
        card.add(btnPanel);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "BOOK_MENU"));

        saveBtn.addActionListener(e -> {
            if(txtTitle.getText().isEmpty() || txtPrice.getText().isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Please fill in required fields.");
                return;
            }
            try {
                int copies = Integer.parseInt(txtCopy.getText());
                double price = Double.parseDouble(txtPrice.getText());
                DBConnection.addBooks("book", txtIsbn.getText(), copies, txtAuthor.getText(), txtTitle.getText(),price);
                JOptionPane.showMessageDialog(parentFrame, "Updated Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Error: " + ex.getMessage());
            }
        });

        wrapper.add(card);
        return wrapper;
    }

    //---SUPPORTING FUNCTION---
    private static JTextField addFormField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(Style.TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        JTextField field = new JTextField(20);
        Style.styleField(field);
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
        return field;
    }
}