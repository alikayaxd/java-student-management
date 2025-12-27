package com.ogrenciyonetim;

import com.ogrenciyonetim.gui.LoginFrame;
import javax.swing.*;

/**
 * Ana uygulama sınıfı
 * Programın giriş noktası
 * Eclipse uyumluluğu için Main yerine OgrenciYonetimApp kullanıldı
 */
public class OgrenciYonetimApp {
    public static void main(String[] args) {
        // Swing bileşenlerini thread-safe şekilde başlat
        SwingUtilities.invokeLater(() -> {
            try {
                // Sistem görünümünü ayarla
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Look and feel ayarlanamadı: " + e.getMessage());
            }
            
            // Login ekranını göster
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}

