package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.model.Student;
import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import java.awt.*;

/**
 * En başarılı/başarısız öğrenci paneli - Ana ekranda gösterilir
 * Düzeltilmiş görünüm
 */
public class BestWorstStudentPanel extends JPanel {
    private FileStudentService studentService;
    private boolean isBest; // true = en başarılı, false = en başarısız
    
    public BestWorstStudentPanel(FileStudentService studentService, boolean isBest) {
        this.studentService = studentService;
        this.isBest = isBest;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Başlık
        JLabel titleLabel = new JLabel(isBest ? "En Başarılı Öğrenci" : "En Başarısız Öğrenci");
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
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        
        Student ogrenci = isBest ? 
            studentService.enBasariliOgrenci() : 
            studentService.enBasarisizOgrenci();
        
        if (ogrenci == null) {
            JLabel noDataLabel = new JLabel("<html><div style='text-align: center; padding: 40px;'>" +
                "<h2 style='color: #7f8c8d;'>Veri Bulunamadı</h2>" +
                "<p style='color: #95a5a6; font-size: 14px;'>Not girişi yapılmış öğrenci bulunamadı.</p></div></html>");
            noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noDataLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            contentPanel.add(noDataLabel, BorderLayout.CENTER);
        } else {
            // Ana bilgi paneli
            JPanel mainInfoPanel = new JPanel();
            mainInfoPanel.setLayout(new BorderLayout(30, 30));
            mainInfoPanel.setOpaque(false);
            
            // Sol panel - Ortalama ve temel bilgiler
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Ortalama (büyük ve vurgulu)
            JLabel averageLabel = new JLabel(String.format("%.1f", ogrenci.hesaplaOrtalama()));
            averageLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
            Color avgColor = isBest ? new Color(46, 204, 113) : new Color(231, 76, 60);
            averageLabel.setForeground(avgColor);
            averageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            averageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            
            // Ortalama etiketi
            JLabel avgLabel = new JLabel("Ortalama");
            avgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            avgLabel.setForeground(new Color(127, 140, 141));
            avgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            avgLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
            
            // Ad Soyad
            JLabel nameLabel = new JLabel(ogrenci.getAd() + " " + ogrenci.getSoyad());
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            nameLabel.setForeground(new Color(52, 73, 94));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            
            // Öğrenci No
            JLabel numLabel = new JLabel("Öğrenci No: " + ogrenci.getNumara());
            numLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            numLabel.setForeground(new Color(127, 140, 141));
            numLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            leftPanel.add(Box.createVerticalGlue());
            leftPanel.add(averageLabel);
            leftPanel.add(avgLabel);
            leftPanel.add(nameLabel);
            leftPanel.add(numLabel);
            leftPanel.add(Box.createVerticalGlue());
            
            // Sağ panel - Detaylı bilgiler
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new GridLayout(2, 2, 20, 20));
            rightPanel.setOpaque(false);
            rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            addDetailCard(rightPanel, "Vize Notu", String.valueOf(ogrenci.getVizeNotu()), 
                new Color(52, 152, 219));
            addDetailCard(rightPanel, "Final Notu", String.valueOf(ogrenci.getFinalNotu()), 
                new Color(155, 89, 182));
            addDetailCard(rightPanel, "Devamsızlık", String.valueOf(ogrenci.getDevamsizlik()) + " gün", 
                new Color(230, 126, 34));
            
            // Durum kartı - renk kodlu
            Color durumColor = ogrenci.getDurum().equals("Geçti") ? 
                new Color(46, 204, 113) : new Color(231, 76, 60);
            addDetailCard(rightPanel, "Durum", ogrenci.getDurum(), durumColor);
            
            mainInfoPanel.add(leftPanel, BorderLayout.WEST);
            mainInfoPanel.add(rightPanel, BorderLayout.CENTER);
            
            contentPanel.add(mainInfoPanel, BorderLayout.CENTER);
        }
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void addDetailCard(JPanel panel, String label, String value, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, accentColor),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            )
        ));
        
        // Etiket
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelComp.setForeground(new Color(127, 140, 141));
        labelComp.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelComp.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Değer
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueComp.setForeground(accentColor);
        valueComp.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(Box.createVerticalGlue());
        card.add(labelComp);
        card.add(valueComp);
        card.add(Box.createVerticalGlue());
        
        panel.add(card);
    }
}
