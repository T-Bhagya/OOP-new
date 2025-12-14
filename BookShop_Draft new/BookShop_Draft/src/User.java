import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User implements ActionListener{
    // --- UI Components ---
    private JFrame frame;
    private JTextField txtfname;
    private JTextField txtlname;
    private JPasswordField txtpassword;
    private JPasswordField txtconpass;
    private JTextField txtemail;
    private JTextField txttelno;
    private JTextField txtaddress;
    private JButton submitBtn;
    private JButton backBtn;

    // --- Data Fields ---
    private String fname;
    private String lname;
    private String password;
    private String conpass;
    private String email;
    private String address;
    private String telno;

    public User(){
        // --- Main Frame Setup ---
        frame = new JFrame("Register Form - Cinnamon & Chapters");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // --- Background Panel (Gradient)---
        JPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout()); // Centers the form card
        frame.setContentPane(backgroundPanel);

        // --- Form Card (White container)---
        JPanel formCard = Style.createCardPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(new Color(255, 255, 255, 235));
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(253, 230, 138), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));

        // --- Header Section ---
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(120, 53, 15)); // Amber-900
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel("Join us today");
        subTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subTitle.setForeground(new Color(180, 83, 9));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        formCard.add(titleLabel);
        formCard.add(subTitle);
        formCard.add(Box.createVerticalStrut(25));

        // --- Input Fields Section ---
        txtfname = addFormField(formCard, "First Name");
        txtlname = addFormField(formCard, "Last Name");

        // Password specific handling
        formCard.add(createLabel("Password"));
        txtpassword = new JPasswordField(20);
        styleField(txtpassword);
        formCard.add(txtpassword);
        formCard.add(Box.createVerticalStrut(15));

        txtemail = addFormField(formCard, "Email");
        txttelno = addFormField(formCard, "Telephone Number");
        txtaddress = addFormField(formCard, "Address");

        // --- Buttons Section ---
        formCard.add(Box.createVerticalStrut(20));

        submitBtn = new JButton("Submit Registration");
        stylePrimaryButton(submitBtn);
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.addActionListener(this);
        formCard.add(submitBtn);

        formCard.add(Box.createVerticalStrut(10));

        // Add card to background
        backgroundPanel.add(formCard);
        // --- Add Back Button Section ---
        backBtn = new JButton("← Back to Login");
        stylePrimaryButton(backBtn);
        backBtn.setBackground(new Color(120, 53, 15)); // Darker color for back button
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the registration frame
                new OpenFrame(); // Open the login page (make sure you have a LoginPage class)
            }
        });
        formCard.add(Box.createVerticalStrut(10));
        formCard.add(backBtn);

    }
    //get the user id
    protected String getFrameInput(JTextField field){
        return field.getText().trim();
    }
    //get the table name
    protected  String getTableName(){
        return "Table_Name";
    };



    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn) {
            validateAndSubmit();
        }
    }

    //---Validations---
    public void validateAndSubmit() {
        String fNameInput = txtfname.getText().trim();
        String lNameInput = txtlname.getText().trim();
        String passInput = new String(txtpassword.getPassword());
        String emailInput = txtemail.getText().trim();
        String telInput = txttelno.getText().trim();
        String addrInput = txtaddress.getText().trim();

        if (fNameInput.isEmpty() || fNameInput.length() > 15) {
            showError("First Name must be between 1-15 characters.");
            return;
        }

        if (lNameInput.isEmpty() || lNameInput.length() > 30) {
            showError("Last Name must be between 1-30 characters.");
            return;
        }

        if (passInput.length() <= 8 || passInput.length() > 20) {
            showError("Password must be between 9-20 characters.");
            return;
        }

        if (emailInput.isEmpty() || emailInput.length() > 50 || !emailInput.contains("@")) {
            showError("Please enter a valid Email address.");
            return;
        }
        try {
            if (DBConnection.isEmailExist(getTableName(), emailInput)) {
                showError("This email is already registered. Please use a different email.");
                return;
            }
        } catch (Exception e) {
            showError("Database error while checking email!");
            return;
        }

        if (telInput.length() != 10 || !telInput.matches("\\d+")) {
            showError("Telephone must be exactly 10 digits.");
            return;
        }

        if (addrInput.isEmpty() || addrInput.length() > 100) {
            showError("Address must be between 1-100 characters.");
            return;
        }

        //--if validated--
        setFname(fNameInput);
        setLname(lNameInput);
        setPassword(passInput);
        setEmail(emailInput);
        setTelno(telInput);
        setAddress(addrInput);

//        JOptionPane.showMessageDialog(
//                getFrame(),
//                "Registration Successful!\nYour Customer ID is: ",
//                "Success",
//                JOptionPane.INFORMATION_MESSAGE
//        );
    }
    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Validation Error", JOptionPane.WARNING_MESSAGE);
    }

    //---CHECKING THE LOGIN---
    public static boolean checkPassword(char[] enteredPassword,String id,String table){
        String epass=new String(enteredPassword);
        try {
            String existingPass = DBConnection.loginCheck(table, id);
            if (epass.equals(existingPass)) {
                return true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    // --- Helper UI Methods ---
    private JTextField addFormField(JPanel panel, String labelText) {
        panel.add(createLabel(labelText));
        JTextField field = new JTextField(20);
        styleField(field);
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(new Color(120, 53, 15)); // Amber-900
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(253, 230, 138)), // Amber-200
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(255, 251, 235)); // Amber-50
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setBackground(new Color(245, 158, 11)); // Amber-500
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    // --- Getters and Setters ---

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelno() { return telno; }
    public void setTelno(String telno) { this.telno = telno; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public JFrame getFrame() { return frame; }

    // --- Static Dashboard Method (kept from original) ---
    public static JFrame fr = new JFrame("Cinnamon and Chapters");

    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            Color color1 = new Color(255, 251, 235); // Amber-50
            Color color2 = new Color(254, 242, 242); // Red-50
            GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }
}