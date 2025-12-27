package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Not girişi paneli - Ana ekranda gösterilir
 */
public class GradeInputPanel extends JPanel {
    private FileStudentService studentService;
    private JTextField numaraField;
    private JSpinner vizeSpinner;
    private JSpinner finalSpinner;
    
    public GradeInputPanel(FileStudentService studentService) {
        this.studentService = studentService;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Başlık
        JLabel titleLabel = new JLabel("Not Girişi");
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
        JLabel numaraLabel = createLabel("Öğrenci Numarası:");
        formPanel.add(numaraLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        numaraField = createTextField();
        numaraField.setPreferredSize(new Dimension(300, 42));
        formPanel.add(numaraField, gbc);
        
        // Vize notu
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel vizeLabel = createLabel("Vize Notu:");
        formPanel.add(vizeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        vizeSpinner = createSpinner();
        formPanel.add(vizeSpinner, gbc);
        
        // Final notu
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel finalLabel = createLabel("Final Notu:");
        formPanel.add(finalLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        finalSpinner = createSpinner();
        formPanel.add(finalSpinner, gbc);
        
        // Buton paneli
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        JButton saveButton = createButton("Kaydet", new Color(52, 152, 219), new Color(41, 128, 185));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numara = Integer.parseInt(numaraField.getText().trim());
                    int vize = (Integer) vizeSpinner.getValue();
                    int finalNot = (Integer) finalSpinner.getValue();
                    
                    if (studentService.notGirisi(numara, vize, finalNot)) {
                        JOptionPane.showMessageDialog(
                            GradeInputPanel.this,
                            "Notlar başarıyla eklendi.",
                            "Başarılı",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        numaraField.setText("");
                        vizeSpinner.setValue(0);
                        finalSpinner.setValue(0);
                    } else {
                        JOptionPane.showMessageDialog(
                            GradeInputPanel.this,
                            "Bu numaraya ait öğrenci bulunamadı.",
                            "Hata",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        GradeInputPanel.this,
                        "Geçersiz öğrenci numarası.",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
        buttonPanel.add(saveButton);
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return field;
    }
    
    private JSpinner createSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.setPreferredSize(new Dimension(300, 42));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
        }
        return spinner;
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

