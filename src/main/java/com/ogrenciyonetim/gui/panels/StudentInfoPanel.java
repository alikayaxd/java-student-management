package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.model.Student;

import javax.swing.*;
import java.awt.*;

/**
 * Öğrenci bilgileri paneli
 */
public class StudentInfoPanel extends JPanel {
    public StudentInfoPanel(Student ogrenci) {
        initComponents(ogrenci);
    }
    
    private void initComponents(Student ogrenci) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Bilgi paneli
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Öğrenci Bilgileri"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Bilgileri göster
        addInfoRow(infoPanel, gbc, "Numara:", String.valueOf(ogrenci.getNumara()), 0);
        addInfoRow(infoPanel, gbc, "Ad:", ogrenci.getAd(), 1);
        addInfoRow(infoPanel, gbc, "Soyad:", ogrenci.getSoyad(), 2);
        
        if (ogrenci.notlarGirildiMi()) {
            addInfoRow(infoPanel, gbc, "Vize Notu:", String.valueOf(ogrenci.getVizeNotu()), 3);
            addInfoRow(infoPanel, gbc, "Final Notu:", String.valueOf(ogrenci.getFinalNotu()), 4);
            addInfoRow(infoPanel, gbc, "Ortalama:", String.format("%.1f", ogrenci.hesaplaOrtalama()), 5);
            addInfoRow(infoPanel, gbc, "Devamsızlık:", String.valueOf(ogrenci.getDevamsizlik()), 6);
            addInfoRow(infoPanel, gbc, "Durum:", ogrenci.getDurum(), 7);
        } else {
            JLabel noInfoLabel = new JLabel("Not ve devamsızlık bilgileri henüz girilmemiş.");
            noInfoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noInfoLabel.setForeground(Color.GRAY);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            infoPanel.add(noInfoLabel, gbc);
        }
        
        add(infoPanel, BorderLayout.CENTER);
    }
    
    private void addInfoRow(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(labelComponent, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(valueComponent, gbc);
    }
}




