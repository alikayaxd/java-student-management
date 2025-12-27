package com.ogrenciyonetim.gui.dialogs;

import com.ogrenciyonetim.model.Student;
import com.ogrenciyonetim.service.FileStudentService;
import com.ogrenciyonetim.util.LogoUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Modern öğrenci ekleme/düzenleme dialogu
 * WindowBuilder ile uyumlu
 */
public class StudentDialog extends JDialog {
    private FileStudentService studentService;
    private Student student;
    
    private JTextField numaraField;
    private JTextField adField;
    private JTextField soyadField;
    private JButton saveButton;
    private JButton cancelButton;
    
    public StudentDialog(JFrame parent, FileStudentService studentService, Student student) {
        super(parent, true);
        this.studentService = studentService;
        this.student = student;
        
        initComponents();
        
        if (student != null) {
            fillStudentData();
        }
    }
    
    /**
     * WindowBuilder ile uyumlu bileşenleri başlat
     */
    private void initComponents() {
        setTitle(student == null ? "Yeni Öğrenci Ekle" : "Öğrenci Düzenle");
        setSize(480, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        // Ana panel - gradient arka plan
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Header paneli - logo ile
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Form paneli
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Buton paneli
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        getRootPane().setDefaultButton(saveButton);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Logo
        JLabel logoLabel = LogoUtil.createLogoLabel(35);
        headerPanel.add(logoLabel, BorderLayout.WEST);
        
        // Başlık
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(student == null ? "Yeni Öğrenci Ekle" : "Öğrenci Düzenle");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Öğrenci bilgilerini giriniz");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Öğrenci numarası
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel numaraLabel = createLabel("Öğrenci No:");
        formPanel.add(numaraLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        numaraField = createTextField();
        numaraField.setEnabled(student == null); // Düzenleme modunda devre dışı
        numaraField.setPreferredSize(new Dimension(250, 42));
        formPanel.add(numaraField, gbc);
        
        // Ad
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel adLabel = createLabel("Ad:");
        formPanel.add(adLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        adField = createTextField();
        adField.setPreferredSize(new Dimension(250, 42));
        formPanel.add(adField, gbc);
        
        // Soyad
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel soyadLabel = createLabel("Soyad:");
        formPanel.add(soyadLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        soyadField = createTextField();
        soyadField.setPreferredSize(new Dimension(250, 42));
        formPanel.add(soyadField, gbc);
        
        return formPanel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        
        saveButton = createButton("Kaydet", new Color(52, 152, 219), new Color(41, 128, 185));
        saveButton.addActionListener(new SaveButtonListener());
        
        cancelButton = createButton("İptal", new Color(149, 165, 166), new Color(127, 140, 141));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        return buttonPanel;
    }
    
    private JButton createButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(130, 42));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    /**
     * Mevcut öğrenci verilerini doldur
     */
    private void fillStudentData() {
        numaraField.setText(String.valueOf(student.getNumara()));
        adField.setText(student.getAd());
        soyadField.setText(student.getSoyad());
    }
    
    /**
     * Kaydet butonu dinleyicisi
     */
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String numaraStr = numaraField.getText().trim();
            String ad = adField.getText().trim();
            String soyad = soyadField.getText().trim();
            
            if (numaraStr.isEmpty() || ad.isEmpty() || soyad.isEmpty()) {
                JOptionPane.showMessageDialog(
                    StudentDialog.this,
                    "Lütfen tüm alanları doldurunuz.",
                    "Uyarı",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            try {
                int numara = Integer.parseInt(numaraStr);
                
                if (student == null) {
                    // Yeni öğrenci ekle
                    Student yeniOgrenci = new Student(numara, ad, soyad);
                    if (studentService.ogrenciEkle(yeniOgrenci)) {
                        JOptionPane.showMessageDialog(
                            StudentDialog.this,
                            "Öğrenci başarıyla eklendi.",
                            "Başarılı",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                            StudentDialog.this,
                            "Bu numaraya ait bir öğrenci zaten var.",
                            "Hata",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    // Öğrenci güncelleme (şimdilik sadece ad/soyad)
                    // Not: Dosya yapısı nedeniyle tam güncelleme için dosyayı yeniden yazmak gerekir
                    JOptionPane.showMessageDialog(
                        StudentDialog.this,
                        "Öğrenci güncelleme özelliği henüz tamamlanmadı.",
                        "Bilgi",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    StudentDialog.this,
                    "Geçersiz öğrenci numarası.",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
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
}
