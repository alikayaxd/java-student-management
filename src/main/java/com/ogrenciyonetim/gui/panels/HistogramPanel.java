package com.ogrenciyonetim.gui.panels;

import com.ogrenciyonetim.model.Student;
import com.ogrenciyonetim.service.FileStudentService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Modern not ortalaması histogramı paneli - Gerçek grafik çubukları ile
 */
public class HistogramPanel extends JPanel {
    private FileStudentService studentService;
    private int[] araliklar;
    private int maxValue;
    
    public HistogramPanel(FileStudentService studentService) {
        this.studentService = studentService;
        this.araliklar = calculateHistogram();
        this.maxValue = getMaxValue();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Başlık
        JLabel titleLabel = new JLabel("Not Ortalaması Histogramı");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Grafik paneli
        HistogramGraphPanel graphPanel = new HistogramGraphPanel();
        graphPanel.setBackground(Color.WHITE);
        graphPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JScrollPane scrollPane = new JScrollPane(graphPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private int[] calculateHistogram() {
        int[] araliklar = new int[6];
        List<Student> ogrenciler = studentService.tumOgrencileriGetir();
        
        for (Student ogrenci : ogrenciler) {
            if (ogrenci.notlarGirildiMi()) {
                double ort = ogrenci.hesaplaOrtalama();
                
                if (ort >= 90 && ort <= 100) {
                    araliklar[0]++;
                } else if (ort >= 80) {
                    araliklar[1]++;
                } else if (ort >= 70) {
                    araliklar[2]++;
                } else if (ort >= 60) {
                    araliklar[3]++;
                } else if (ort >= 50) {
                    araliklar[4]++;
                } else if (ort >= 0) {
                    araliklar[5]++;
                }
            }
        }
        
        return araliklar;
    }
    
    private int getMaxValue() {
        int max = 0;
        for (int value : araliklar) {
            if (value > max) {
                max = value;
            }
        }
        return max == 0 ? 1 : max; // Sıfıra bölmeyi önle
    }
    
    /**
     * Grafik çizim paneli
     */
    private class HistogramGraphPanel extends JPanel {
        private String[] labels = {
            "[ 90 - 100]",
            "[ 80 - 89 ]",
            "[ 70 - 79 ]",
            "[ 60 - 69 ]",
            "[ 50 - 59 ]",
            "[  0 - 49 ]"
        };
        
        private Color[] colors = {
            new Color(46, 204, 113),   // Yeşil
            new Color(52, 152, 219),  // Mavi
            new Color(155, 89, 182),   // Mor
            new Color(241, 196, 15),   // Sarı
            new Color(230, 126, 34),   // Turuncu
            new Color(231, 76, 60)     // Kırmızı
        };
        
        public HistogramGraphPanel() {
            setPreferredSize(new Dimension(700, 400));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            int width = getWidth();
            int height = getHeight();
            int barWidth = (width - 200) / 6; // Sol tarafta etiketler için 200px
            int maxBarHeight = height - 100; // Üst ve alt boşluklar için
            int startX = 180;
            int startY = 30;
            
            // Başlık
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g2d.setColor(new Color(52, 73, 94));
            FontMetrics fm = g2d.getFontMetrics();
            String title = "Öğrenci Sayısı";
            g2d.drawString(title, startX, startY - 10);
            
            // Y ekseni etiketi
            g2d.rotate(-Math.PI / 2);
            g2d.drawString("Öğrenci Sayısı", -height / 2 - fm.stringWidth("Öğrenci Sayısı") / 2, 15);
            g2d.rotate(Math.PI / 2);
            
            // X ekseni çizgisi
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(new Color(200, 200, 200));
            g2d.drawLine(startX, startY + maxBarHeight, width - 20, startY + maxBarHeight);
            
            // Y ekseni çizgisi
            g2d.drawLine(startX, startY, startX, startY + maxBarHeight);
            
            // Y ekseni değerleri
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            g2d.setColor(new Color(127, 140, 141));
            for (int i = 0; i <= 5; i++) {
                int yValue = maxValue * i / 5;
                int yPos = startY + maxBarHeight - (maxBarHeight * i / 5);
                String label = String.valueOf(yValue);
                g2d.drawString(label, startX - fm.stringWidth(label) - 10, yPos + 5);
                // Yatay çizgi
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5}, 0));
                g2d.drawLine(startX, yPos, width - 20, yPos);
            }
            
            // Çubukları çiz
            g2d.setStroke(new BasicStroke(1));
            for (int i = 0; i < araliklar.length; i++) {
                int barHeight = maxValue > 0 ? (araliklar[i] * maxBarHeight / maxValue) : 0;
                int x = startX + i * barWidth + 10;
                int y = startY + maxBarHeight - barHeight;
                
                // Çubuk
                g2d.setColor(colors[i]);
                g2d.fillRect(x, y, barWidth - 20, barHeight);
                
                // Çubuk kenarlığı
                g2d.setColor(new Color(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue(), 150));
                g2d.drawRect(x, y, barWidth - 20, barHeight);
                
                // Değer etiketi (çubuk üstünde)
                if (araliklar[i] > 0) {
                    g2d.setColor(new Color(52, 73, 94));
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    String valueLabel = String.valueOf(araliklar[i]);
                    int labelX = x + (barWidth - 20) / 2 - fm.stringWidth(valueLabel) / 2;
                    int labelY = y - 5;
                    g2d.drawString(valueLabel, labelX, labelY);
                }
                
                // X ekseni etiketi
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g2d.setColor(new Color(52, 73, 94));
                int labelX = x + (barWidth - 20) / 2 - fm.stringWidth(labels[i]) / 2;
                int labelY = startY + maxBarHeight + 20;
                g2d.drawString(labels[i], labelX, labelY);
            }
        }
    }
}
