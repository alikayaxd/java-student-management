package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.model.Student;
import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Başarı raporu paneli
 */
public class ReportPanel extends JPanel {
    private FileStudentService studentService;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    
    public ReportPanel(FileStudentService studentService) {
        this.studentService = studentService;
        initComponents();
        loadReport();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Tablo modeli
        String[] columnNames = {"Numara", "Ad Soyad", "Ortalama", "Devamsızlık", "Durum"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Tablo
        reportTable = new JTable(tableModel);
        reportTable.setFont(new Font("Arial", Font.PLAIN, 12));
        reportTable.setRowHeight(25);
        reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Sütun genişlikleri
        reportTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        reportTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        reportTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        reportTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        reportTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        
        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Başarı Raporu"));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadReport() {
        tableModel.setRowCount(0);
        List<Student> ogrenciler = studentService.tumOgrencileriGetir();
        
        for (Student ogrenci : ogrenciler) {
            if (ogrenci.notlarGirildiMi()) {
                Object[] row = {
                    ogrenci.getNumara(),
                    ogrenci.getAd() + " " + ogrenci.getSoyad(),
                    String.format("%.1f", ogrenci.hesaplaOrtalama()),
                    ogrenci.getDevamsizlik(),
                    ogrenci.getDurum()
                };
                tableModel.addRow(row);
            } else {
                Object[] row = {
                    ogrenci.getNumara(),
                    ogrenci.getAd() + " " + ogrenci.getSoyad(),
                    "Not girilmemiş",
                    "-",
                    "-"
                };
                tableModel.addRow(row);
            }
        }
    }
}




