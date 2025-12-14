import javax.swing.*;

public class Customer extends User {

    private static int id = 10000;   // Starting number

    public Customer() {
        super();
    }
    @Override
    protected String getTableName() {
        return "Customer";
    }


    // ------- ID GENERATOR (Always 5 digits) -------
    public static String idNumber() {
        String currentID = "";
        int num = 0;

        try {
            currentID = DBConnection.getID("Customer");

            // If DB returned empty, use default starting ID
            if (currentID.equals(" ")) {
                currentID = String.valueOf(id);
            }

            num = Integer.parseInt(currentID);
            num++;

        } catch (Exception e) {
            num = id;   // default fallback
        }

        // Return EXACT 5 digits (e.g., 00001 to 99999)
        return String.format("%05d", num);
    }

    // ------------ HANDLE SUBMIT BUTTON ------------
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

        // Run User validations (does NOT return a boolean)
        super.validateAndSubmit();

        // If validation failed, User fields remain null → stop Customer processing
        if (getFname() == null || getEmail() == null) {
            return;
        }

        // Generate the new 5-digit Customer ID
        String idno = idNumber();

        try {
            // Insert into database
            DBConnection.insertForRegister(
                    "Customer",
                    idno,
                    getFname(),
                    getLname(),
                    getPassword(),
                    getEmail(),
                    getTelno(),
                    getAddress()
            );

            // Show success message
            JOptionPane.showMessageDialog(
                    getFrame(),
                    "Registration Successful!\nYour Customer ID is: " + idno,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Close Registration Form
            getFrame().dispose();

            // Open Login Window
            new OpenFrame();   // Must be your Login class

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    getFrame(),
                    "Database Error!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}