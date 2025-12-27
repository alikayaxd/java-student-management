package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Öğrenci silme paneli - Ana ekranda gösterilir
 */
public class DeleteStudentPanel extends JPanel {
    private FileStudentService studentService;
    private JTextField numaraField;
    
    public DeleteStudentPanel(FileStudentService studentService) {
        this.studentService = studentService;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Başlık
        JLabel titleLabel = new JLabel("Öğrenci Sil");
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
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
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
        
        // Uyarı mesajı
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel warningLabel = new JLabel("<html><div style='color: #e74c3c; font-size: 12px;'>" +
            "⚠ Bu işlem geri alınamaz! Öğrenci bilgileri kalıcı olarak silinecektir.</div></html>");
        warningLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(warningLabel, gbc);
        
        // Buton paneli
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        JButton deleteButton = createButton("Sil", new Color(231, 76, 60), new Color(192, 57, 43));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numara = Integer.parseInt(numaraField.getText().trim());
                    
                    if (studentService.ogrenciGetir(numara) == null) {
                        JOptionPane.showMessageDialog(
                            DeleteStudentPanel.this,
                            "Bu numaraya ait öğrenci bulunamadı.",
                            "Hata",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    
                    int option = JOptionPane.showConfirmDialog(
                        DeleteStudentPanel.this,
                        "<html><div style='text-align: center; padding: 10px;'>" +
                        "<b>Bu öğrenciyi silmek istediğinize emin misiniz?</b><br><br>" +
                        "Öğrenci No: " + numara + "<br>" +
                        "Bu işlem geri alınamaz!</div></html>",
                        "Onay",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    if (option == JOptionPane.YES_OPTION) {
                        if (studentService.ogrenciSil(numara)) {
                            JOptionPane.showMessageDialog(
                                DeleteStudentPanel.this,
                                "Öğrenci başarıyla silindi.",
                                "Başarılı",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            numaraField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(
                                DeleteStudentPanel.this,
                                "Öğrenci silinirken bir hata oluştu.",
                                "Hata",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        DeleteStudentPanel.this,
                        "Geçersiz öğrenci numarası.",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
        buttonPanel.add(deleteButton);
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 42));
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



