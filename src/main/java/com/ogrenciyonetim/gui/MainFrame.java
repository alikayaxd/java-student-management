package com.ogrenciyonetim.gui;

import com.ogrenciyonetim.service.FileStudentService;
import com.ogrenciyonetim.util.LogoUtil;
import com.ogrenciyonetim.gui.dialogs.StudentDialog;
import com.ogrenciyonetim.gui.panels.StudentListPanel;
import com.ogrenciyonetim.gui.panels.ReportPanel;
import com.ogrenciyonetim.gui.panels.HistogramPanel;
import com.ogrenciyonetim.gui.panels.GradeGraphPanel;
import com.ogrenciyonetim.gui.panels.DeleteStudentPanel;
import com.ogrenciyonetim.gui.panels.StudentInfoInputPanel;
import com.ogrenciyonetim.gui.panels.StudentCountPanel;
import com.ogrenciyonetim.gui.panels.BestWorstStudentPanel;
import com.ogrenciyonetim.gui.panels.ClassAveragePanel;
import com.ogrenciyonetim.gui.panels.GradeInputPanel;
import com.ogrenciyonetim.gui.panels.AttendanceInputPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Ana menü ekranı
 * Tüm işlevlere erişim sağlar
 */
public class MainFrame extends JFrame {
    private FileStudentService studentService;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    public MainFrame() {
        // Dosya yolu - proje klasörüne göre relative olarak ogrenciler klasörünü kullan
        String projeKokDizini = getProjeKokDizini();
        String klasorYolu = projeKokDizini + File.separator + "ogrenciler";
        studentService = new FileStudentService(klasorYolu);
        
        initComponents();
    }
    
    /**
     * Proje kök dizinini bulur
     * new klasörü nerede olursa olsun, o klasörü döndürür
     */
    private String getProjeKokDizini() {
        // Önce user.dir'den kontrol et (çalışma dizini)
        String userDir = System.getProperty("user.dir");
        File ogrencilerKlasoru = new File(userDir, "ogrenciler");
        if (ogrencilerKlasoru.exists() && ogrencilerKlasoru.isDirectory()) {
            return userDir;
        }
        
        // Class dosyasının bulunduğu klasörden proje kök dizinini bul
        try {
            // Class dosyasının bulunduğu klasörü al
            java.net.URL classUrl = MainFrame.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation();
            
            if (classUrl != null) {
                String classPath = classUrl.getPath();
                
                // Windows için path düzeltmesi
                if (classPath.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("win")) {
                    classPath = classPath.substring(1);
                }
                
                // URL encoding'i düzelt
                try {
                    classPath = java.net.URLDecoder.decode(classPath, "UTF-8");
                } catch (java.io.UnsupportedEncodingException e) {
                    // UTF-8 desteklenmiyorsa olduğu gibi kullan
                }
                
                File classFile = new File(classPath);
                
                // Eğer JAR dosyasıysa, JAR'ın bulunduğu klasörü al
                if (classFile.isFile() && classFile.getName().endsWith(".jar")) {
                    String jarKlasoru = classFile.getParent();
                    File ogrencilerKlasoru2 = new File(jarKlasoru, "ogrenciler");
                    if (ogrencilerKlasoru2.exists() && ogrencilerKlasoru2.isDirectory()) {
                        return jarKlasoru;
                    }
                }
                
                // Class dosyası bin klasöründeyse, bir üst dizine çık
                if (classFile.isDirectory()) {
                    // bin/com/ogrenciyonetim/gui/... yapısından proje kök dizinine çık
                    File currentDir = classFile;
                    for (int i = 0; i < 5; i++) {
                        File ogrencilerKlasoru3 = new File(currentDir, "ogrenciler");
                        if (ogrencilerKlasoru3.exists() && ogrencilerKlasoru3.isDirectory()) {
                            return currentDir.getAbsolutePath();
                        }
                        currentDir = currentDir.getParentFile();
                        if (currentDir == null) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Proje kök dizini bulunamadı, user.dir kullanılıyor: " + e.getMessage());
        }
        
        // Son çare olarak user.dir'yi döndür
        return userDir;
    }
    
    /**
     * WindowBuilder ile uyumlu bileşenleri başlat
     */
    private void initComponents() {
        setTitle("Öğrenci Yönetim Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Ana düzen
        setLayout(new BorderLayout());
        
        // Üst başlık paneli - logo ile
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Sol menü paneli - daha estetik
        JPanel menuPanel = createMenuPanel();
        
        // Menü butonları
        addMenuButton(menuPanel, "1. Öğrenci Ekle", "add");
        addMenuButton(menuPanel, "2. Öğrenci Sil", "delete");
        addMenuButton(menuPanel, "3. Tüm Öğrencileri Listele", "list");
        addMenuButton(menuPanel, "4. Öğrenci Bilgilerini Göster", "show");
        addMenuButton(menuPanel, "5. Not Girişi", "grade");
        addMenuButton(menuPanel, "6. Devamsızlık Ekle", "attendance");
        addMenuButton(menuPanel, "7. Başarı Raporu", "report");
        addMenuButton(menuPanel, "8. En Başarılı Öğrenci", "best");
        addMenuButton(menuPanel, "9. En Başarısız Öğrenci", "worst");
        addMenuButton(menuPanel, "10. Sınıf Ortalaması", "average");
        addMenuButton(menuPanel, "11. Not Histogramı", "histogram");
        addMenuButton(menuPanel, "12. Not Grafiği", "graph");
        addMenuButton(menuPanel, "13. Toplam Öğrenci Sayısı", "count");
        
        menuPanel.add(Box.createVerticalGlue());
        
        // Çıkış butonu
        JButton exitButton = new JButton("Çıkış");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setPreferredSize(new Dimension(180, 40));
        exitButton.setMaximumSize(new Dimension(180, 40));
        exitButton.setBackground(new Color(231, 76, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                MainFrame.this,
                "Programdan çıkmak istediğinize emin misiniz?",
                "Çıkış",
                JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        menuPanel.add(exitButton);
        
        add(menuPanel, BorderLayout.WEST);
        
        // İçerik paneli (CardLayout ile)
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);
        
        // Başlangıç ekranı
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.WHITE);
        JLabel welcomeLabel = new JLabel(
            "<html><div style='text-align: center;'><h1>Hoş Geldiniz!</h1>" +
            "<p>Sol menüden bir işlem seçiniz.</p></div></html>",
            SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        
        contentPanel.add(welcomePanel, "welcome");
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Menü butonu ekle - daha estetik
     */
    private void addMenuButton(JPanel panel, String text, String action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 38));
        button.setMaximumSize(new Dimension(200, 38));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addActionListener(new MenuActionListener(action));
        
        // Hover efekti
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219));
            }
        });
        
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
    }
    
    /**
     * Menü aksiyon dinleyicisi
     */
    private class MenuActionListener implements ActionListener {
        private String action;
        
        public MenuActionListener(String action) {
            this.action = action;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action) {
                case "add":
                    showAddStudentDialog();
                    break;
                case "delete":
                    showDeleteStudentDialog();
                    break;
                case "list":
                    showStudentList();
                    break;
                case "show":
                    showStudentInfo();
                    break;
                case "grade":
                    showGradeInput();
                    break;
                case "attendance":
                    showAttendanceInput();
                    break;
                case "report":
                    showSuccessReport();
                    break;
                case "best":
                    showBestStudent();
                    break;
                case "worst":
                    showWorstStudent();
                    break;
                case "average":
                    showClassAverage();
                    break;
                case "histogram":
                    showHistogram();
                    break;
                case "graph":
                    showGradeGraph();
                    break;
                case "count":
                    showStudentCount();
                    break;
            }
        }
    }
    
    // İşlev metodları
    private void showAddStudentDialog() {
        StudentDialog dialog = new StudentDialog(this, studentService, null);
        dialog.setVisible(true);
    }
    
    private void showDeleteStudentDialog() {
        DeleteStudentPanel panel = new DeleteStudentPanel(studentService);
        showPanel(panel, "Öğrenci Sil");
    }
    
    private void showStudentList() {
        StudentListPanel panel = new StudentListPanel(studentService);
        showPanel(panel, "Öğrenci Listesi");
    }
    
    private void showStudentInfo() {
        StudentInfoInputPanel panel = new StudentInfoInputPanel(studentService);
        showPanel(panel, "Öğrenci Bilgileri");
    }
    
    private void showGradeInput() {
        GradeInputPanel panel = new GradeInputPanel(studentService);
        showPanel(panel, "Not Girişi");
    }
    
    private void showAttendanceInput() {
        AttendanceInputPanel panel = new AttendanceInputPanel(studentService);
        showPanel(panel, "Devamsızlık Ekle");
    }
    
    private void showSuccessReport() {
        ReportPanel panel = new ReportPanel(studentService);
        showPanel(panel, "Başarı Raporu");
    }
    
    private void showBestStudent() {
        BestWorstStudentPanel panel = new BestWorstStudentPanel(studentService, true);
        showPanel(panel, "En Başarılı Öğrenci");
    }
    
    private void showWorstStudent() {
        BestWorstStudentPanel panel = new BestWorstStudentPanel(studentService, false);
        showPanel(panel, "En Başarısız Öğrenci");
    }
    
    private void showClassAverage() {
        ClassAveragePanel panel = new ClassAveragePanel(studentService);
        showPanel(panel, "Sınıf Ortalaması");
    }
    
    private void showHistogram() {
        HistogramPanel panel = new HistogramPanel(studentService);
        showPanel(panel, "Not Ortalaması Histogramı");
    }
    
    private void showGradeGraph() {
        GradeGraphPanel panel = new GradeGraphPanel(studentService);
        showPanel(panel, "Öğrenci Not Grafiği");
    }
    
    private void showStudentCount() {
        StudentCountPanel panel = new StudentCountPanel(studentService);
        showPanel(panel, "Öğrenci Sayısı");
    }
    
    /**
     * Başlık paneli oluştur - logo ile
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        // Sol taraf - Logo ve başlık
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);
        
        // Logo
        JLabel logoLabel = LogoUtil.createLogoLabel(40);
        leftPanel.add(logoLabel);
        
        JLabel titleLabel = new JLabel("Öğrenci Yönetim Sistemi");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        leftPanel.add(titleLabel);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        
        // Sağ taraf - Üniversite bilgisi
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        
        JLabel uniLabel = new JLabel("Bolu Abant İzzet Baysal Üniversitesi");
        uniLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        uniLabel.setForeground(new Color(200, 200, 200));
        rightPanel.add(uniLabel);
        
        JLabel authorLabel = new JLabel("Muhammed Ali KAYA");
        authorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        authorLabel.setForeground(new Color(180, 180, 180));
        rightPanel.add(authorLabel);
        
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    
    /**
     * Menü paneli oluştur - daha estetik
     */
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(44, 62, 80));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        menuPanel.setPreferredSize(new Dimension(220, 0));
        
        // Menü başlığı
        JLabel menuTitle = new JLabel("MENÜ");
        menuTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuTitle.setForeground(new Color(236, 240, 241));
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        menuPanel.add(menuTitle);
        
        // Menü butonları
        addMenuButton(menuPanel, "Öğrenci Ekle", "add");
        addMenuButton(menuPanel, "Öğrenci Sil", "delete");
        addMenuButton(menuPanel, "Tüm Öğrenciler", "list");
        addMenuButton(menuPanel, "Öğrenci Bilgileri", "show");
        addMenuButton(menuPanel, "Not Girişi", "grade");
        addMenuButton(menuPanel, "Devamsızlık Ekle", "attendance");
        addMenuButton(menuPanel, "Başarı Raporu", "report");
        addMenuButton(menuPanel, "En Başarılı", "best");
        addMenuButton(menuPanel, "En Başarısız", "worst");
        addMenuButton(menuPanel, "Sınıf Ortalaması", "average");
        addMenuButton(menuPanel, "Not Histogramı", "histogram");
        addMenuButton(menuPanel, "Not Grafiği", "graph");
        addMenuButton(menuPanel, "Öğrenci Sayısı", "count");
        
        menuPanel.add(Box.createVerticalGlue());
        
        // Çıkış butonu
        JButton exitButton = new JButton("Çıkış");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        exitButton.setPreferredSize(new Dimension(200, 42));
        exitButton.setMaximumSize(new Dimension(200, 42));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setBackground(new Color(231, 76, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                MainFrame.this,
                "Programdan çıkmak istediğinize emin misiniz?",
                "Çıkış",
                JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        // Hover efekti
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(192, 57, 43));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(231, 76, 60));
            }
        });
        
        menuPanel.add(exitButton);
        
        return menuPanel;
    }
    
    /**
     * Panel göster - lazy loading ile performans optimizasyonu
     */
    private void showPanel(JPanel panel, String title) {
        // SwingWorker ile arka planda yükle
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Panel hazırlanıyor
                return null;
            }
            
            @Override
            protected void done() {
                contentPanel.removeAll();
                contentPanel.add(panel, "content");
                cardLayout.show(contentPanel, "content");
                setTitle("Öğrenci Yönetim Sistemi - " + title);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        };
        worker.execute();
    }
}

