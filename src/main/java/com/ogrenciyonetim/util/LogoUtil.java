package com.ogrenciyonetim.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Logo yardımcı sınıfı
 * Üniversite logosunu yükler ve gösterir
 */
public class LogoUtil {
    
    /**
     * Logo ikonu oluştur
     * @param size Logo boyutu
     * @return ImageIcon
     */
    public static ImageIcon createLogoIcon(int size) {
        // Önce dosyadan yüklemeyi dene
        try {
            ImageIcon logoIcon = new ImageIcon(LogoUtil.class.getResource("/logo.png"));
            if (logoIcon.getIconWidth() > 0) {
                Image img = logoIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            // Logo dosyası yoksa devam et
        }
        
        // Programatik logo oluştur - basit versiyon
        return createSimpleLogo(size);
    }
    
    /**
     * Basit programatik logo oluştur
     */
    private static ImageIcon createSimpleLogo(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Beyaz arka plan
        g2d.setColor(Color.WHITE);
        g2d.fillOval(0, 0, size, size);
        
        // Mavi çerçeve
        g2d.setColor(new Color(44, 62, 80));
        g2d.setStroke(new BasicStroke(size / 20));
        g2d.drawOval(size / 20, size / 20, size - size / 10, size - size / 10);
        
        // M harfi
        g2d.setFont(new Font("Arial", Font.BOLD, size / 2));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "M";
        int x = (size - fm.stringWidth(text)) / 2;
        int y = (size + fm.getAscent()) / 2;
        g2d.setColor(new Color(44, 62, 80));
        g2d.drawString(text, x, y);
        
        // Yıl
        g2d.setFont(new Font("Arial", Font.PLAIN, size / 8));
        fm = g2d.getFontMetrics();
        String year = "1997";
        x = (size - fm.stringWidth(year)) / 2;
        y = size - size / 8;
        g2d.drawString(year, x, y);
        
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    /**
     * Logo label'ı oluştur
     */
    public static JLabel createLogoLabel(int size) {
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(createLogoIcon(size));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        return logoLabel;
    }
}
