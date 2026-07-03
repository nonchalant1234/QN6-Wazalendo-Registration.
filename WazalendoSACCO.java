package com.sacco.wazalendosacco;

import com.sacco.wazalendosacco.database.DatabaseConnector;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WazalendoSACCO extends JFrame {
    
    // GUI Components
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JPanel statusPanel;
    
    // Labels
    private JLabel lblMemberId;
    private JLabel lblFullName;
    private JLabel lblNIN;
    private JLabel lblPhone;
    private JLabel lblInitialDeposit;
    
    // Text Fields
    private JTextField txtMemberId;
    private JTextField txtFullName;
    private JTextField txtNIN;
    private JTextField txtPhone;
    private JTextField txtInitialDeposit;
    
    // Buttons
    private JButton btnRegister;
    private JButton btnClear;
    private JButton btnExit;
    
    // Status Components
    private JTextArea statusArea;
    private JScrollPane scrollPane;
    private JLabel lblStatus;
    
    // Database Connection
    private Connection dbConnection;
    
    // Constructor
    public WazalendoSACCO() {
        initComponents();
        setupFrame();
        connectToDatabase();
        addStatusMessage("=== SYSTEM STARTED ===");
        addStatusMessage("Wazalendo SACCO Member Registration System");
        addStatusMessage("Ready to register new members...");
        addStatusMessage("=".repeat(50));
    }
    
    /**
     * Initialize all GUI components
     */
  private void initComponents() {
    // Main Panel
    mainPanel = new JPanel(new BorderLayout(5, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 20, 20));
    mainPanel.setBackground(new Color(240, 248, 255));

    createInputPanel();
    createButtonPanel();
    createStatusPanel();

    JPanel topPanel = new JPanel(new BorderLayout(10, 10));
    topPanel.setBackground(new Color(240, 248, 255));

    topPanel.add(inputPanel, BorderLayout.CENTER);
    topPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Add to main panel
    mainPanel.add(topPanel, BorderLayout.CENTER);
    mainPanel.add(statusPanel, BorderLayout.SOUTH);

    add(mainPanel);

    // Register event handlers
    registerEventHandlers();
}
    
  
    private void createInputPanel() {
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
            "Member Registration Details",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(0, 0, 139)
        ));
        inputPanel.setBackground(new Color(240, 248, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Arial", Font.BOLD, 13);
        Font fieldFont = new Font("Arial", Font.PLAIN, 12);
        
        // Create labels
        lblMemberId = new JLabel("Member ID:");
        lblMemberId.setFont(labelFont);
        lblMemberId.setForeground(new Color(0, 0, 139));
        
        lblFullName = new JLabel("Full Name:");
        lblFullName.setFont(labelFont);
        lblFullName.setForeground(new Color(0, 0, 139));
        
        lblNIN = new JLabel("National ID (NIN):");
        lblNIN.setFont(labelFont);
        lblNIN.setForeground(new Color(0, 0, 139));
        
        lblPhone = new JLabel("Phone Number:");
        lblPhone.setFont(labelFont);
        lblPhone.setForeground(new Color(0, 0, 139));
        
        lblInitialDeposit = new JLabel("Initial Deposit (UGX):");
        lblInitialDeposit.setFont(labelFont);
        lblInitialDeposit.setForeground(new Color(0, 0, 139));
        
        // Create text fields
        txtMemberId = new JTextField(15);
        txtMemberId.setFont(fieldFont);
        txtMemberId.setToolTipText("Enter Member ID (e.g., M001)");
        txtMemberId.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        txtFullName = new JTextField(20);
        txtFullName.setFont(fieldFont);
        txtFullName.setToolTipText("Enter full name (e.g., John Mukasa)");
        txtFullName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        txtNIN = new JTextField(14);
        txtNIN.setFont(fieldFont);
        txtNIN.setToolTipText("Enter 14-character National ID");
        txtNIN.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        txtPhone = new JTextField(10);
        txtPhone.setFont(fieldFont);
        txtPhone.setToolTipText("Enter 10-digit phone number");
        txtPhone.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        txtInitialDeposit = new JTextField(10);
        txtInitialDeposit.setFont(fieldFont);
        txtInitialDeposit.setToolTipText("Enter positive deposit amount");
        txtInitialDeposit.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Add components to panel
        // Row 1: Member ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 0.2;
        inputPanel.add(lblMemberId, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 0.8;
        inputPanel.add(txtMemberId, gbc);
        gbc.gridwidth = 1;
        
        // Row 2: Full Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.2;
        inputPanel.add(lblFullName, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weightx = 0.8;
        inputPanel.add(txtFullName, gbc);
        gbc.gridwidth = 1;
        
        // Row 3: NIN
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0.2;
        inputPanel.add(lblNIN, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0.6;
        inputPanel.add(txtNIN, gbc);
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0.2;
        JLabel ninHint = new JLabel("(14 chars)");
        ninHint.setFont(new Font("Arial", Font.ITALIC, 10));
        ninHint.setForeground(Color.GRAY);
        inputPanel.add(ninHint, gbc);
        
        // Row 4: Phone
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weightx = 0.2;
        inputPanel.add(lblPhone, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weightx = 0.6;
        inputPanel.add(txtPhone, gbc);
        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weightx = 0.2;
        JLabel phoneHint = new JLabel("(10 digits)");
        phoneHint.setFont(new Font("Arial", Font.ITALIC, 10));
        phoneHint.setForeground(Color.GRAY);
        inputPanel.add(phoneHint, gbc);
        
        // Row 5: Initial Deposit
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.weightx = 0.2;
        inputPanel.add(lblInitialDeposit, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weightx = 0.8;
        inputPanel.add(txtInitialDeposit, gbc);
        gbc.gridwidth = 1;
    }
    
    /**
     * Creates the button panel
     */
    private void createButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        // Register Button
        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(46, 139, 87));
        btnRegister.setForeground(Color.black);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.setPreferredSize(new Dimension(130, 42));
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setMnemonic('R');
        btnRegister.setToolTipText("Register the member (Alt+R)");
        
        // Clear Button
        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(255, 140, 0));
        btnClear.setForeground(Color.black);
        btnClear.setFont(new Font("Arial", Font.BOLD, 14));
        btnClear.setPreferredSize(new Dimension(130, 42));
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.setMnemonic('C');
        btnClear.setToolTipText("Clear all fields (Alt+C)");
        
        // Exit Button
        btnExit = new JButton("Exit");
        btnExit.setBackground(new Color(178, 34, 34));
        btnExit.setForeground(Color.black);
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setPreferredSize(new Dimension(130, 42));
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.setMnemonic('X');
        btnExit.setToolTipText("Exit application (Alt+X)");
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);
    }
    
    /**
     * Creates the status panel with text area
     */
   private void createStatusPanel() {
    statusPanel = new JPanel(new BorderLayout());

    statusPanel.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 0, 0, 0), // space above
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Registration Log",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12)
            )
        )
    );

    statusPanel.setPreferredSize(new Dimension(0, 180));
    statusPanel.setBackground(Color.WHITE);

    statusArea = new JTextArea(10, 50);
    statusArea.setEditable(false);
    statusArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    statusArea.setBackground(new Color(255, 255, 240));
    statusArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    scrollPane = new JScrollPane(statusArea);
    scrollPane.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    statusPanel.add(scrollPane, BorderLayout.CENTER);
}
    
    /**
     * Connect to database
     */
    private void connectToDatabase() {
        try {
            dbConnection = DatabaseConnector.getConnection();
            DatabaseConnector.createTable();
            DatabaseConnector.testConnection();
            lblStatus = new JLabel("Database: Connected");
            lblStatus.setForeground(Color.GREEN);
        } catch (Exception e) {
            lblStatus = new JLabel("Database: Disconnected");
            lblStatus.setForeground(Color.RED);
            addStatusMessage("WARNING: Database connection failed!");
        }
    }
    
    /**
     * Register event handlers
     */
    private void registerEventHandlers() {
        // Register Button
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerMember();
            }
        });
        
        // Clear Button
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        // Exit Button
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExit();
            }
        });
        
        // Enter key triggers Register
        getRootPane().setDefaultButton(btnRegister);
        
        // Window close handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
    }
    
    /**
     * Setup frame properties
     */
    private void setupFrame() {
        setTitle("Wazalendo SACCO - Member Registration System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     setSize(950, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    // =============================================================
    // PART (d): REGISTER MEMBER WITH VALIDATION
    // =============================================================
    
    /**
     * Main registration method with complete validation
     */
    private void registerMember() {
        addStatusMessage("\n--- Processing Registration ---");
        
        // Step 1: Get field values
        String memberId = txtMemberId.getText().trim();
        String fullName = txtFullName.getText().trim();
        String nin = txtNIN.getText().trim();
        String phone = txtPhone.getText().trim();
        String depositStr = txtInitialDeposit.getText().trim();
        
        // Step 2: Validate all fields
        String validationError = validateFields(memberId, fullName, nin, phone, depositStr);
        
        if (validationError != null) {
            // Validation failed
            addStatusMessage("❌ VALIDATION FAILED: " + validationError);
            showErrorDialog("Validation Error", "Please correct the following:\n\n" + validationError);
            return;
        }
        
        // Step 3: Parse deposit amount (validation already confirmed it's numeric)
        double deposit = Double.parseDouble(depositStr);
        
        // Step 4: Insert into database
        try {
            if (insertMemberIntoDatabase(memberId, fullName, nin, phone, deposit)) {
                // Success
                String successMsg = "✅ Registration SUCCESSFUL!\n" +
                                   "   Member ID: " + memberId + "\n" +
                                   "   Name: " + fullName + "\n" +
                                   "   NIN: " + nin + "\n" +
                                   "   Phone: " + phone + "\n" +
                                   "   Initial Deposit: UGX " + String.format("%,.2f", deposit) + "\n" +
                                   "   Registered: " + new Date();
                addStatusMessage(successMsg);
                addStatusMessage("-".repeat(50));
                
                JOptionPane.showMessageDialog(this,
                    "Member Registered Successfully!\n\n" +
                    "Member ID: " + memberId + "\n" +
                    "Name: " + fullName + "\n" +
                    "Initial Deposit: UGX " + String.format("%,.2f", deposit),
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields for next registration
                clearFields();
                
            } else {
                // Database insertion failed
                addStatusMessage("Database insertion failed!");
                showErrorDialog("Database Error", 
                    "Failed to insert member into database.\n" +
                    "Please check if the member ID already exists.");
            }
        } catch (SQLException e) {
            addStatusMessage(" Database Error: " + e.getMessage());
            showErrorDialog("Database Error", 
                "Error saving member:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Validates all input fields
     * @return null if valid, error message if invalid
     */
    private String validateFields(String memberId, String fullName, String nin, 
                                   String phone, String depositStr) {
        StringBuilder errors = new StringBuilder();
        
        // 1. Check empty fields
        if (memberId.isEmpty()) {
            errors.append("• Member ID is required\n");
        }
        if (fullName.isEmpty()) {
            errors.append("• Full Name is required\n");
        }
        if (nin.isEmpty()) {
            errors.append("• National ID (NIN) is required\n");
        }
        if (phone.isEmpty()) {
            errors.append("• Phone Number is required\n");
        }
        if (depositStr.isEmpty()) {
            errors.append("• Initial Deposit is required\n");
        }
        
        // If any field is empty, return immediately
        if (errors.length() > 0) {
            return errors.toString();
        }
        
        // 2. Validate NIN - exactly 14 characters
        if (nin.length() != 14) {
            errors.append("• NIN must be exactly 14 characters (currently " + nin.length() + ")\n");
        }
        
        // 3. Validate Phone - numeric and 10 digits
        if (!phone.matches("\\d{10}")) {
            errors.append("• Phone Number must be exactly 10 digits (0-9 only)\n");
        }
        
        // 4. Validate Initial Deposit - positive number
        try {
            double deposit = Double.parseDouble(depositStr);
            if (deposit <= 0) {
                errors.append("• Initial Deposit must be a positive number greater than 0\n");
            }
        } catch (NumberFormatException e) {
            errors.append("• Initial Deposit must be a valid number (e.g., 100000)\n");
        }
        
        // 5. Additional validation: Member ID format (optional - M001, M002, etc.)
        if (!memberId.matches("M\\d{3,}")) {
            errors.append("• Member ID should follow format: M001, M002, etc.\n");
        }
        
        // 6. Additional validation: Full Name - at least 3 characters
        if (fullName.length() < 3) {
            errors.append("• Full Name must be at least 3 characters\n");
        }
        
        return errors.length() > 0 ? errors.toString() : null;
    }
    
    /**
     * Inserts a member into the database
     * @return true if successful, false otherwise
     */
    private boolean insertMemberIntoDatabase(String memberId, String fullName, 
                                             String nin, String phone, 
                                             double deposit) throws SQLException {
        String insertSQL = "INSERT INTO Members (MemberID, FullName, NIN, Phone, InitialDeposit) " +
                          "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = dbConnection.prepareStatement(insertSQL)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, fullName);
            pstmt.setString(3, nin);
            pstmt.setString(4, phone);
            pstmt.setDouble(5, deposit);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate") || e.getMessage().contains("unique")) {
                throw new SQLException("Member ID or NIN already exists in the database.");
            }
            throw e;
        }
    }
    
    /**
     * Clears all input fields
     */
    private void clearFields() {
        txtMemberId.setText("");
        txtFullName.setText("");
        txtNIN.setText("");
        txtPhone.setText("");
        txtInitialDeposit.setText("");
        
        // Set focus to first field
        txtMemberId.requestFocus();
        
        addStatusMessage("Fields cleared. Ready for next registration.");
    }
    
    /**
     * Adds a message to the status area
     */
    private void addStatusMessage(String message) {
        statusArea.append(message + "\n");
        // Auto-scroll to bottom
        statusArea.setCaretPosition(statusArea.getDocument().getLength());
    }
    
    /**
     * Shows an error dialog
     */
    private void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Confirms exit with user
     */
    private void confirmExit() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to exit?\n\n" +
            "Any unsaved data will be lost.",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Close database connection
            DatabaseConnector.closeConnection();
            System.exit(0);
        }
    }
    
    /**
     * Main method - Entry point of application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new WazalendoSACCO();
            }
        });
    }
}
