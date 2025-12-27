package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.model.Student;
import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Öğrenci listesi paneli
 */
public class StudentListPanel extends JPanel {
    private FileStudentService studentService;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    
    public StudentListPanel(FileStudentService studentService) {
        this.studentService = studentService;
        initComponents();
        loadStudents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Tablo modeli
        String[] columnNames = {"Numara", "Ad", "Soyad"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Tablo
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.setRowHeight(25);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tüm Öğrenciler"));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> ogrenciler = studentService.tumOgrencileriGetir();
        
        for (Student ogrenci : ogrenciler) {
            Object[] row = {
                ogrenci.getNumara(),
                ogrenci.getAd(),
                ogrenci.getSoyad()
            };
            tableModel.addRow(row);
        }
    }
}




