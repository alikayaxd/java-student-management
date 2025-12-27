package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import java.awt.*;

/**
 * Öğrenci sayısı paneli - Ana ekranda gösterilir
 */
public class StudentCountPanel extends JPanel {
    private FileStudentService studentService;
    
    public StudentCountPanel(FileStudentService studentService) {
        this.studentService = studentService;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Başlık
        JLabel titleLabel = new JLabel("Toplam Öğrenci Sayısı");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // İçerik paneli
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(60, 60, 60, 60)
        ));
        
        int count = studentService.toplamOgrenciSayisiRecursive(0);
        
        // Büyük sayı gösterimi
        JLabel countLabel = new JLabel(String.valueOf(count));
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 96));
        countLabel.setForeground(new Color(52, 152, 219));
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Açıklama
        JLabel descLabel = new JLabel("Toplam Öğrenci");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        descLabel.setForeground(new Color(127, 140, 141));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(countLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(descLabel);
        centerPanel.add(Box.createVerticalGlue());
        
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
    }
}



