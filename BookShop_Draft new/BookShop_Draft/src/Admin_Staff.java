import javax.swing.*;
import java.awt.*;

public class Admin_Staff extends User {

    private static int id = 10000; // starting ID

    public Admin_Staff() {
        super();
    }

    // ---------------- ID Generator (exact 5 digits) ----------------
    public static String idNumber() {
        String currentID = "";
        int num = 0;

        try {
            currentID = DBConnection.getID("Admin_Staff");

            if (currentID.equals(" ")) {
                currentID = String.valueOf(id);
            }

            num = Integer.parseInt(currentID);
            num++;

        } catch (Exception e) {
            num = id; // fallback
        }

        // Return exactly 5 digits
        return String.format("%05d", num);
    }
    @Override
    protected String getTableName() {
        return "Admin_Staff";
    }

    // ---------------- HANDLE SUBMIT ----------------
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

        // Step 1: Run User validation
        super.validateAndSubmit();

        // Step 2: Stop if validation failed
        if (getFname() == null || getEmail() == null) {
            return;
        }

        // Step 3: Generate new ID
        String idno = idNumber();

        try {
            // Insert into database
            DBConnection.insertForRegister(
                    "Admin_Staff",
                    idno,
                    getFname(),
                    getLname(),
                    getPassword(),
                    getEmail(),
                    getTelno(),
                    getAddress()
            );

            // Step 4: Show success message with ID
            JOptionPane.showMessageDialog(
                    getFrame(),
                    "Registration Successful!\nYour Admin ID is: " + idno,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Step 5: Close registration frame
            getFrame().dispose();

            // Step 6: Open Login frame
            new OpenFrame();  // Make sure OpenFrame is your login panel class

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