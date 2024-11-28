package com.ca.ui.panels;

import com.ca.db.model.LoginUser;
import com.ca.db.service.LoginUserServiceImpl;
import com.gt.common.ResourceManager;
import com.gt.common.constants.StrConstants;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.AppFrame;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends AbstractFunctionPanel {
    private JPanel innerPanel;
    private JTextField userName;
    private JTextField passWord;

    public LoginPanel() {
        add(getLoginPanel());
        init();
    }

    public static void main(String[] args) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                LoginPanel panel = new LoginPanel();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private JPanel getLoginPanel() {
        JPanel fullPanel = new JPanel();
        fullPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fullPanel.setBackground(new Color(191, 194, 194)); 
        
        innerPanel = new JPanel();
        innerPanel.setBounds(41, 96, 336, 144);
        innerPanel.setBackground(new Color(191, 194, 194));
        fullPanel.add(innerPanel);
        innerPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(18dlu;default)"),
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(21dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(61dlu;default):grow"),
                FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(65dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(65dlu;default)"),}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                RowSpec.decode("max(57dlu;default):grow"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(20dlu;default)"),
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default)"),
                FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
                RowSpec.decode("max(14dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,}));

        JLabel lblImg = new JLabel("");
       // lblImg.setIcon(ResourceManager.getImageIcon("logo2.png"));
        
        ImageIcon originalIcon = ResourceManager.getImageIcon("logo2.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Set desired width and height
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        lblImg.setIcon(resizedIcon);
        innerPanel.add(lblImg, "2, 8, 7, 1");
        
        

        JLabel lblTitile = new JLabel(ResourceManager.getString(StrConstants.COMPANY_NAME));
        lblTitile.setFont(new Font("Tahoma", Font.BOLD, 16));
        innerPanel.add(lblTitile, "6, 8, 7, 1, left, default");

        JLabel lblDept = new JLabel(ResourceManager.getString(StrConstants.DEPARTMENT));
        lblDept.setFont(new Font("Tahoma", Font.BOLD, 13));
        innerPanel.add(lblDept, "6, 10, 7, 1, left, default");

        JLabel lblInventoryManagementSystem = new JLabel(ResourceManager.getString(StrConstants.APP_TITLE));
        lblInventoryManagementSystem.setFont(new Font("Tahoma", Font.BOLD, 13));
        innerPanel.add(lblInventoryManagementSystem, "6, 12, 7, 1, left, default");

        JLabel lblNewLabel = new JLabel("UserName :");
        lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        innerPanel.add(lblNewLabel, "4, 16, left, default");

        userName = new JTextField();
        userName.setFont(new Font("Courier New", Font.PLAIN, 16)); // Set font for the text field
        userName.setPreferredSize(new Dimension(200, 30));
        innerPanel.add(userName, "8, 16, 3, 1, fill, fill");
        userName.addActionListener(e -> doLogin());

        JLabel lblPassword = new JLabel("Password :");
        lblPassword.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        innerPanel.add(lblPassword, "4, 18, left, default");

        passWord = new JPasswordField();
        passWord.addActionListener(e -> doLogin());
        passWord.setFont(new Font("Courier New", Font.PLAIN, 16));
        passWord.setPreferredSize(new Dimension(200,30));
        passWord.addActionListener(e -> doLogin());

        innerPanel.add(passWord, "8, 18, 3, 1, fill, fill");

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        loginButton.setForeground(Color.BLACK); // Set text color
        loginButton.setBackground(Color.MAGENTA); // Set background color
        loginButton.setPreferredSize(new Dimension(150, 40));
        innerPanel.add(loginButton, "8, 20, fill, default");
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.BLUE);
                loginButton.setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.MAGENTA);
                loginButton.setForeground(Color.BLACK);
            }
        });
        loginButton.addActionListener(e -> doLogin());
        JButton restPassword = new JButton("Reset");
        restPassword.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        restPassword.setForeground(Color.BLACK); // Set text color
        //restPassword.setBackground(Color.YELLOW); // Set background color
        restPassword.setBackground(new Color(121, 227, 235));
        restPassword.setPreferredSize(new Dimension(150, 40));
        restPassword.addActionListener(e -> clearAll());
        innerPanel.add(restPassword, "10, 20");
        restPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                restPassword.setBackground(new Color(183, 249, 255));
                restPassword.setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                restPassword.setBackground(new Color(183, 249, 255));
                restPassword.setForeground(Color.BLACK);
            }
        });
        return fullPanel;
    }

    private void clearAll() {
        UIUtils.clearAllFields(innerPanel);

    }

    private void doLogin() {
        LoginUserServiceImpl lus;
        try {
            lus = new LoginUserServiceImpl();
            LoginUser user = LoginUserServiceImpl.getLoginUser(userName.getText().trim(), passWord.getText().trim());
            if (user != null) {
                AppFrame.loginSuccess();
            } else {
                JOptionPane.showMessageDialog(null, "Username password error!");
                passWord.setText("");
                userName.requestFocus();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "DB Connection Error!");
        }

    }

    @Override
    public final String getFunctionName() {
        return "Login";
    }

    @Override
    public void handleSaveAction() {

    }

    @Override
    public final void init() {
        super.init();
        userName.requestFocus();
        isReadyToClose = true;
    }

    @Override
    public void enableDisableComponents() {

    }

}