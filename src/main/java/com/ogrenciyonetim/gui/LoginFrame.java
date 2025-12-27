package com.ogrenciyonetim.gui;

import com.ogrenciyonetim.util.LogoUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login ekranı
 * Kullanıcı girişi için WindowBuilder ile uyumlu Swing arayüzü
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    
    // Basit kullanıcı doğrulama için (gerçek uygulamada veritabanından kontrol edilmeli)
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin123";
    
    public LoginFrame() {
        initComponents();
    }
    
    /**
     * WindowBuilder ile uyumlu bileşenleri başlat
     */
    private void initComponents() {
        setTitle("Öğrenci Yönetim Sistemi - Giriş");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Ana panel - gradient arka plan için özel panel
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Logo ve başlık paneli
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Merkez paneli - form ve logo
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(30, 30));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Logo paneli
        JPanel logoPanel = createLogoPanel();
        centerPanel.add(logoPanel, BorderLayout.NORTH);
        
        // Form paneli
        JPanel formPanel = createFormPanel();
        centerPanel.add(formPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Buton paneli
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Enter tuşu ile giriş
        getRootPane().setDefaultButton(loginButton);
        
        pack();
        setSize(500, 650);
    }
    
    /**
     * Başlık paneli oluştur
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("Öğrenci Yönetim Sistemi");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JLabel uniLabel = new JLabel("Bolu Abant İzzet Baysal Üniversitesi");
        uniLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        uniLabel.setForeground(new Color(200, 200, 200));
        headerPanel.add(uniLabel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    /**
     * Logo paneli oluştur
     */
    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BorderLayout());
        logoPanel.setOpaque(false);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        // Logo container
        JPanel logoContainer = new JPanel();
        logoContainer.setOpaque(false);
        logoContainer.setLayout(new BorderLayout());
        logoContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        // Üniversite logosu
        JLabel logoLabel = LogoUtil.createLogoLabel(120);
        logoContainer.add(logoLabel, BorderLayout.CENTER);
        logoPanel.add(logoContainer, BorderLayout.CENTER);
        
        return logoPanel;
    }
    
    
    /**
     * Form paneli oluştur
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(35, 35, 35, 35)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Kullanıcı adı etiketi
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(usernameLabel, gbc);
        
        // Kullanıcı adı alanı
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameField.setPreferredSize(new Dimension(220, 38));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(usernameField, gbc);
        
        // Şifre etiketi
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(passwordLabel, gbc);
        
        // Şifre alanı
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordField.setPreferredSize(new Dimension(220, 38));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(passwordField, gbc);
        
        return formPanel;
    }
    
    /**
     * Buton paneli oluştur
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        loginButton = new JButton("Giriş Yap");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(140, 42));
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new LoginButtonListener());
        
        // Hover efekti
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(52, 152, 219));
            }
        });
        
        cancelButton = new JButton("İptal");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cancelButton.setPreferredSize(new Dimension(140, 42));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> System.exit(0));
        
        // Hover efekti
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelButton.setBackground(new Color(127, 140, 141));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelButton.setBackground(new Color(149, 165, 166));
            }
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        return buttonPanel;
    }
    
    /**
     * Gradient arka plan paneli
     */
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            Color color1 = new Color(236, 240, 241);
            Color color2 = new Color(189, 195, 199);
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }
    
    /**
     * Giriş butonu dinleyicisi
     */
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                    LoginFrame.this,
                    "Lütfen kullanıcı adı ve şifre giriniz.",
                    "Uyarı",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            // Kullanıcı doğrulama
            if (username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD)) {
                // Ana ekrana geç - performans için ayrı thread'de
                SwingUtilities.invokeLater(() -> {
                    LoginFrame.this.dispose();
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(
                    LoginFrame.this,
                    "Kullanıcı adı veya şifre hatalı!\nVarsayılan: admin / admin123",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE
                );
                passwordField.setText("");
            }
        }
    }
}
