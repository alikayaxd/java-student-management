package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.model.Student;
import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * Modern öğrenci not grafiği paneli - Gerçek grafik çubukları ile
 */
public class GradeGraphPanel extends JPanel {
    private FileStudentService studentService;
    private JTable graphTable;
    private DefaultTableModel tableModel;
    
    public GradeGraphPanel(FileStudentService studentService) {
        this.studentService = studentService;
        initComponents();
        loadGraph();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Başlık
        JLabel titleLabel = new JLabel("Öğrenci Not Grafiği");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Tablo modeli
        String[] columnNames = {"Ad", "Soyad", "Grafik", "Ortalama"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 2) {
                    return BarGraphComponent.class;
                }
                return Object.class;
            }
        };
        
        // Tablo
        graphTable = new JTable(tableModel);
        graphTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        graphTable.setRowHeight(50);
        graphTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        graphTable.setGridColor(new Color(220, 220, 220));
        
        // Grafik sütunu için özel renderer
        graphTable.getColumnModel().getColumn(2).setCellRenderer(new BarGraphRenderer());
        
        // Sütun genişlikleri
        graphTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        graphTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        graphTable.getColumnModel().getColumn(2).setPreferredWidth(400);
        graphTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        // Tablo başlık stili
        graphTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        graphTable.getTableHeader().setBackground(new Color(52, 73, 94));
        graphTable.getTableHeader().setForeground(Color.WHITE);
        graphTable.getTableHeader().setPreferredSize(new Dimension(0, 35));
        
        JScrollPane scrollPane = new JScrollPane(graphTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadGraph() {
        tableModel.setRowCount(0);
        List<Student> ogrenciler = studentService.tumOgrencileriGetir();
        
        for (Student ogrenci : ogrenciler) {
            if (ogrenci.notlarGirildiMi()) {
                double ortalama = ogrenci.hesaplaOrtalama();
                
                Object[] row = {
                    ogrenci.getAd(),
                    ogrenci.getSoyad(),
                    new BarGraphComponent(ortalama),
                    String.format("%.1f", ortalama)
                };
                tableModel.addRow(row);
            } else {
                Object[] row = {
                    ogrenci.getAd(),
                    ogrenci.getSoyad(),
                    new BarGraphComponent(-1), // -1 = veri yok
                    "-"
                };
                tableModel.addRow(row);
            }
        }
    }
    
    /**
     * Grafik çubuk bileşeni
     */
    public static class BarGraphComponent {
        private double ortalama;
        
        public BarGraphComponent(double ortalama) {
            this.ortalama = ortalama;
        }
        
        public double getOrtalama() {
            return ortalama;
        }
    }
    
    /**
     * Grafik renderer sınıfı - Gerçek çubuk grafikleri çizer
     */
    private class BarGraphRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            if (value instanceof BarGraphComponent) {
                BarGraphComponent barGraph = (BarGraphComponent) value;
                return new BarGraphPanel(barGraph.getOrtalama());
            }
            
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    
    /**
     * Çubuk grafik paneli
     */
    private class BarGraphPanel extends JPanel {
        private double ortalama;
        
        public BarGraphPanel(double ortalama) {
            this.ortalama = ortalama;
            setPreferredSize(new Dimension(400, 45));
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (ortalama < 0) {
                // Veri yok
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 11));
                g2d.setColor(new Color(127, 140, 141));
                g2d.drawString("Not bilgisi yok", 10, getHeight() / 2 + 5);
                return;
            }
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            int width = getWidth() - 20;
            int height = getHeight() - 10;
            int barHeight = 25;
            int y = (height - barHeight) / 2;
            
            // Arka plan çizgisi
            g2d.setColor(new Color(240, 240, 240));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(10, y, width, barHeight);
            g2d.fillRect(10, y, width, barHeight);
            
            // Çubuk genişliği (ortalama / 100 * genişlik)
            int barWidth = (int) (width * ortalama / 100.0);
            
            // Renk belirleme
            Color barColor;
            if (ortalama >= 90) {
                barColor = new Color(46, 204, 113); // Yeşil
            } else if (ortalama >= 80) {
                barColor = new Color(52, 152, 219); // Mavi
            } else if (ortalama >= 70) {
                barColor = new Color(155, 89, 182); // Mor
            } else if (ortalama >= 60) {
                barColor = new Color(241, 196, 15); // Sarı
            } else if (ortalama >= 50) {
                barColor = new Color(230, 126, 34); // Turuncu
            } else {
                barColor = new Color(231, 76, 60); // Kırmızı
            }
            
            // Çubuk çiz
            g2d.setColor(barColor);
            g2d.fillRect(10, y, barWidth, barHeight);
            
            // Çubuk kenarlığı
            g2d.setColor(new Color(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), 200));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(10, y, barWidth, barHeight);
            
            // Değer etiketi
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2d.setColor(new Color(52, 73, 94));
            String label = String.format("%.1f", ortalama);
            FontMetrics fm = g2d.getFontMetrics();
            int labelX = Math.max(barWidth + 15, 10);
            int labelY = y + barHeight / 2 + fm.getAscent() / 2 - 2;
            g2d.drawString(label, labelX, labelY);
        }
    }
}
