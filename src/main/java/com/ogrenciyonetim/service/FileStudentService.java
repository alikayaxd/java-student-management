package com.ogrenciyonetim.service;

import com.ogrenciyonetim.model.Student;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dosya tabanlı öğrenci veri yönetim servisi
 * Öğrenci bilgilerini dosya sisteminde saklar
 * Performans optimizasyonu için cache mekanizması içerir
 */
public class FileStudentService {
    private String klasorYolu;
    // Cache mekanizması - performans için
    private Map<Integer, Student> ogrenciCache;
    private List<Student> tumOgrencilerCache;
    private long cacheTimestamp;
    private static final long CACHE_TIMEOUT = 5000; // 5 saniye
    
    public FileStudentService(String klasorYolu) {
        this.klasorYolu = klasorYolu;
        this.ogrenciCache = new ConcurrentHashMap<>();
        this.tumOgrencilerCache = null;
        this.cacheTimestamp = 0;
        // Klasör yoksa oluştur
        try {
            Files.createDirectories(Paths.get(klasorYolu));
        } catch (IOException e) {
            System.err.println("Klasör oluşturulamadı: " + e.getMessage());
        }
    }
    
    /**
     * Cache'i temizle
     */
    private void clearCache() {
        ogrenciCache.clear();
        tumOgrencilerCache = null;
        cacheTimestamp = 0;
    }
    
    /**
     * Cache'in geçerli olup olmadığını kontrol et
     */
    private boolean isCacheValid() {
        return (System.currentTimeMillis() - cacheTimestamp) < CACHE_TIMEOUT;
    }
    
    /**
     * Öğrenci ekleme
     */
    public boolean ogrenciEkle(Student ogrenci) {
        String dosyaAdi = klasorYolu + File.separator + ogrenci.getNumara() + ".txt";
        
        // Dosya zaten varsa false döndür
        if (new File(dosyaAdi).exists()) {
            return false;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(dosyaAdi, true))) {
            writer.println("Numara: " + ogrenci.getNumara());
            writer.println("Ad: " + ogrenci.getAd());
            writer.println("Soyad: " + ogrenci.getSoyad());
            // Cache'i güncelle
            ogrenciCache.put(ogrenci.getNumara(), ogrenci);
            clearCache(); // Liste cache'ini temizle
            return true;
        } catch (IOException e) {
            System.err.println("Öğrenci eklenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Öğrenci silme
     */
    public boolean ogrenciSil(int numara) {
        String dosyaAdi = klasorYolu + File.separator + numara + ".txt";
        File dosya = new File(dosyaAdi);
        
        if (!dosya.exists()) {
            return false;
        }
        
        boolean deleted = dosya.delete();
        if (deleted) {
            // Cache'den de sil
            ogrenciCache.remove(numara);
            clearCache(); // Liste cache'ini temizle
        }
        return deleted;
    }
    
    /**
     * Tüm öğrencileri listele (cache'li)
     */
    public List<Student> tumOgrencileriGetir() {
        // Cache geçerliyse cache'den döndür
        if (tumOgrencilerCache != null && isCacheValid()) {
            return new ArrayList<>(tumOgrencilerCache);
        }
        
        List<Student> ogrenciler = new ArrayList<>();
        File klasor = new File(klasorYolu);
        
        if (!klasor.exists() || !klasor.isDirectory()) {
            return ogrenciler;
        }
        
        File[] dosyalar = klasor.listFiles((dir, name) -> name.endsWith(".txt"));
        
        if (dosyalar != null) {
            for (File dosya : dosyalar) {
                // Önce cache'den kontrol et
                String dosyaAdi = dosya.getName();
                try {
                    int numara = Integer.parseInt(dosyaAdi.substring(0, dosyaAdi.lastIndexOf('.')));
                    Student cachedOgrenci = ogrenciCache.get(numara);
                    if (cachedOgrenci != null && isCacheValid()) {
                        ogrenciler.add(cachedOgrenci);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    // Dosya adı numara değilse devam et
                }
                
                Student ogrenci = dosyadanOku(dosya.getAbsolutePath());
                if (ogrenci != null) {
                    ogrenciler.add(ogrenci);
                    // Cache'e ekle
                    ogrenciCache.put(ogrenci.getNumara(), ogrenci);
                }
            }
        }
        
        // Cache'i güncelle
        tumOgrencilerCache = new ArrayList<>(ogrenciler);
        cacheTimestamp = System.currentTimeMillis();
        
        return ogrenciler;
    }
    
    /**
     * Öğrenci bilgilerini numaraya göre getir (cache'li)
     */
    public Student ogrenciGetir(int numara) {
        // Önce cache'den kontrol et
        Student cachedOgrenci = ogrenciCache.get(numara);
        if (cachedOgrenci != null && isCacheValid()) {
            return cachedOgrenci;
        }
        
        String dosyaAdi = klasorYolu + File.separator + numara + ".txt";
        Student ogrenci = dosyadanOku(dosyaAdi);
        
        // Cache'e ekle
        if (ogrenci != null) {
            ogrenciCache.put(numara, ogrenci);
        }
        
        return ogrenci;
    }
    
    /**
     * Dosyadan öğrenci bilgilerini oku
     */
    private Student dosyadanOku(String dosyaAdi) {
        File dosya = new File(dosyaAdi);
        if (!dosya.exists()) {
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(dosya))) {
            Student ogrenci = new Student();
            String satir;
            
            while ((satir = reader.readLine()) != null) {
                if (satir.startsWith("Numara:")) {
                    String deger = satir.split(":")[1].trim();
                    ogrenci.setNumara(Integer.parseInt(deger));
                } else if (satir.startsWith("Ad:")) {
                    ogrenci.setAd(satir.split(":")[1].trim());
                } else if (satir.startsWith("Soyad:")) {
                    ogrenci.setSoyad(satir.split(":")[1].trim());
                } else if (satir.startsWith("Vize Notu:") || satir.startsWith("Vize:")) {
                    String deger = satir.split(":")[1].trim();
                    try {
                        ogrenci.setVizeNotu(Integer.parseInt(deger));
                    } catch (NumberFormatException e) {
                        ogrenci.setVizeNotu(0);
                    }
                } else if (satir.startsWith("Final Notu:") || satir.startsWith("Final:")) {
                    String deger = satir.split(":")[1].trim();
                    try {
                        ogrenci.setFinalNotu(Integer.parseInt(deger));
                    } catch (NumberFormatException e) {
                        ogrenci.setFinalNotu(0);
                    }
                } else if (satir.startsWith("Devamsızlık:") || satir.startsWith("Devamsizlik:")) {
                    String deger = satir.split(":")[1].trim();
                    try {
                        ogrenci.setDevamsizlik(Integer.parseInt(deger));
                    } catch (NumberFormatException e) {
                        ogrenci.setDevamsizlik(0);
                    }
                }
            }
            
            return ogrenci;
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Not girişi yap
     */
    public boolean notGirisi(int numara, int vizeNotu, int finalNotu) {
        Student ogrenci = ogrenciGetir(numara);
        if (ogrenci == null) {
            return false;
        }
        
        ogrenci.setVizeNotu(vizeNotu);
        ogrenci.setFinalNotu(finalNotu);
        
        return ogrenciGuncelle(ogrenci);
    }
    
    /**
     * Devamsızlık ekle
     */
    public boolean devamsizlikEkle(int numara, int devamsizlik) {
        Student ogrenci = ogrenciGetir(numara);
        if (ogrenci == null) {
            return false;
        }
        
        ogrenci.setDevamsizlik(devamsizlik);
        return ogrenciGuncelle(ogrenci);
    }
    
    /**
     * Öğrenci bilgilerini güncelle
     */
    private boolean ogrenciGuncelle(Student ogrenci) {
        String dosyaAdi = klasorYolu + File.separator + ogrenci.getNumara() + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(dosyaAdi))) {
            writer.println("Numara: " + ogrenci.getNumara());
            writer.println("Ad: " + ogrenci.getAd());
            writer.println("Soyad: " + ogrenci.getSoyad());
            writer.println("Vize Notu: " + ogrenci.getVizeNotu());
            writer.println("Final Notu: " + ogrenci.getFinalNotu());
            writer.println("Devamsızlık: " + ogrenci.getDevamsizlik());
            
            // Cache'i güncelle
            ogrenciCache.put(ogrenci.getNumara(), ogrenci);
            clearCache(); // Liste cache'ini temizle
            
            return true;
        } catch (IOException e) {
            System.err.println("Öğrenci güncellenirken hata: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Recursive olarak toplam öğrenci sayısını hesapla
     */
    public int toplamOgrenciSayisiRecursive(int index) {
        File klasor = new File(klasorYolu);
        File[] dosyalar = klasor.listFiles((dir, name) -> name.endsWith(".txt"));
        
        if (dosyalar == null || index >= dosyalar.length) {
            return 0;
        }
        
        return 1 + toplamOgrenciSayisiRecursive(index + 1);
    }
    
    /**
     * Sınıf ortalamasını hesapla
     */
    public double sinifOrtalamasi() {
        List<Student> ogrenciler = tumOgrencileriGetir();
        if (ogrenciler.isEmpty()) {
            return 0.0;
        }
        
        double toplam = 0;
        int sayac = 0;
        
        for (Student ogrenci : ogrenciler) {
            if (ogrenci.notlarGirildiMi()) {
                toplam += ogrenci.hesaplaOrtalama();
                sayac++;
            }
        }
        
        return sayac > 0 ? toplam / sayac : 0.0;
    }
    
    /**
     * En başarılı öğrenciyi bul
     */
    public Student enBasariliOgrenci() {
        List<Student> ogrenciler = tumOgrencileriGetir();
        Student enBasarili = null;
        double enYuksekOrtalama = 0;
        
        for (Student ogrenci : ogrenciler) {
            if (ogrenci.notlarGirildiMi()) {
                double ortalama = ogrenci.hesaplaOrtalama();
                if (ortalama > enYuksekOrtalama) {
                    enYuksekOrtalama = ortalama;
                    enBasarili = ogrenci;
                }
            }
        }
        
        return enBasarili;
    }
    
    /**
     * En başarısız öğrenciyi bul
     */
    public Student enBasarisizOgrenci() {
        List<Student> ogrenciler = tumOgrencileriGetir();
        Student enBasarisiz = null;
        double enDusukOrtalama = 100;
        
        for (Student ogrenci : ogrenciler) {
            if (ogrenci.notlarGirildiMi()) {
                double ortalama = ogrenci.hesaplaOrtalama();
                if (ortalama < enDusukOrtalama) {
                    enDusukOrtalama = ortalama;
                    enBasarisiz = ogrenci;
                }
            }
        }
        
        return enBasarisiz;
    }
}

