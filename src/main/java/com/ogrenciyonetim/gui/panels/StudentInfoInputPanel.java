package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Öğrenci bilgileri giriş paneli - Ana ekranda gösterilir
 */
public class StudentInfoInputPanel extends JPanel {
    private FileStudentService studentService;
    private JTextField numaraField;
    private JPanel resultPanel;
    
    public StudentInfoInputPanel(FileStudentService studentService) {
        this.studentService = studentService;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Başlık
        JLabel titleLabel = new JLabel("Öğrenci Bilgileri");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Form paneli
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Öğrenci numarası
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel numaraLabel = new JLabel("Öğrenci Numarası:");
        numaraLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        numaraLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(numaraLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        numaraField = new JTextField();
        numaraField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        numaraField.setPreferredSize(new Dimension(300, 42));
        numaraField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        formPanel.add(numaraField, gbc);
        
        // Buton
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton showButton = createButton("Göster", new Color(52, 152, 219), new Color(41, 128, 185));
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numara = Integer.parseInt(numaraField.getText().trim());
                    com.ogrenciyonetim.model.Student ogrenci = studentService.ogrenciGetir(numara);
                    
                    if (ogrenci != null) {
                        showStudentInfo(ogrenci);
                    } else {
                        JOptionPane.showMessageDialog(
                            StudentInfoInputPanel.this,
                            "Bu numaraya ait öğrenci bulunamadı.",
                            "Hata",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        StudentInfoInputPanel.this,
                        "Geçersiz öğrenci numarası.",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        formPanel.add(showButton, gbc);
        
        add(formPanel, BorderLayout.NORTH);
        
        // Sonuç paneli
        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBackground(Color.WHITE);
        add(resultPanel, BorderLayout.CENTER);
    }
    
    private void showStudentInfo(com.ogrenciyonetim.model.Student ogrenci) {
        resultPanel.removeAll();
        StudentInfoPanel infoPanel = new StudentInfoPanel(ogrenci);
        resultPanel.add(infoPanel, BorderLayout.CENTER);
        resultPanel.revalidate();
        resultPanel.repaint();
    }
    
    private JButton createButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 42));
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
}



