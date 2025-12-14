import javax.swing.*;
import java.awt.*;

public class OpenFrame extends JFrame{
    public OpenFrame(){
        //---WINDOW SETTINGS---
        setTitle("Cinnamon & Chapters - System");
        setSize(600,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //Background color
        getContentPane().setBackground(new Color(255,245,230));
        setLayout(null);

        //---MAIN PANEL---
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(90,70,420,500);
        panel.setBorder(BorderFactory.createLineBorder(new Color(240,220,200),2));
        add(panel);

        //---LOGO---
        JLabel logo =new JLabel("\u2615", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI Emoji",Font.PLAIN,50));
        logo.setBounds(0,20,420,60);
        panel.add(logo);

        //---WELCOME TEXT---
        JLabel welcome =new JLabel("Welcome Back",SwingConstants.CENTER);
        welcome.setFont(new Font("Serif",Font.BOLD,28));
        welcome.setBounds(0,90,420,40);
        panel.add(welcome);

        JLabel subtitle =new JLabel("Please log in to continue",SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI",Font.PLAIN,14));
        subtitle.setBounds(0,120,420,30);
        panel.add(subtitle);

        //---ROLE SELECTION---
        JLabel roleLabel = new JLabel("Select Role");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setBounds(90, 160, 200, 20);
        panel.add(roleLabel);

        String[] roles = {"Admin", "Customer"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setBounds(90, 182, 240, 28);
        panel.add(roleBox);

        //---USER ID---
        JLabel userLabel = new JLabel("User ID");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setBounds(90, 225, 200, 20);
        panel.add(userLabel);

        JTextField userIDField = new JTextField();
        userIDField.setBounds(90, 247, 240, 28);
        panel.add(userIDField);

        //---PASSWORD---
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setBounds(90, 288, 200, 20);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(90, 310, 240, 28);
        panel.add(passField);

        //---LOGIN BUTTON---
        JButton loginBtn = new JButton("Log In");
        loginBtn.setBounds(90, 360, 240, 35);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(new Color(255, 140, 0));
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginBtn.setFocusPainted(false);
        panel.add(loginBtn);
        //ACTION LISTENER FOR LOGIN
        loginBtn.addActionListener(e -> {
            String selectedRole = roleBox.getSelectedItem().toString();
            if(selectedRole.equals("Admin")){
                boolean match=Admin_Staff.checkPassword(passField.getPassword(),userIDField.getText(),"Admin_staff");
                if(match){
                    JOptionPane.showMessageDialog(null,"Login successful!");
                    new AdminDashBoard();
                }else{
                    JOptionPane.showMessageDialog(null,"Invalid credentials, Try again");
                }
            }else {
                if (selectedRole.equals("Customer")) {
                    boolean match = Customer.checkPassword(passField.getPassword(), userIDField.getText(), "Customer");
                    if (match) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        new CustomerDashboard(userIDField.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials, Try again");
                    }
                }
            }
        });

        //---OR LABEL---
        JLabel orLabel = new JLabel("- OR -", SwingConstants.CENTER);
        orLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orLabel.setBounds(0, 400, 420, 30);
        orLabel.setForeground(Color.GRAY);
        panel.add(orLabel);

        //---CREATE ACCOUNT BUTTON---
        JButton createAccBtn = new JButton("Create New Account");
        createAccBtn.setBounds(90, 440, 240, 30);
        createAccBtn.setFocusPainted(false);
        createAccBtn.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0)));
        createAccBtn.setForeground(new Color(255, 140, 0));
        createAccBtn.setBackground(new Color(255, 245, 230));
        createAccBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(createAccBtn);

        //ACTION LISTENER FOR THE CREATE AN ACCOUNT
        createAccBtn.addActionListener(ex -> {
            String selectedRole = roleBox.getSelectedItem().toString();
            if(selectedRole.equals("Admin")){
                new Admin_Staff();
            }else{
                new Customer();
            }
        });
    }
}
