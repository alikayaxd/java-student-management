package com.ogrenciyonetim.model;

/**
 * Öğrenci model sınıfı
 * Öğrenci bilgilerini tutar
 */
public class Student {
    private int numara;
    private String ad;
    private String soyad;
    private int vizeNotu;
    private int finalNotu;
    private int devamsizlik;
    
    // Varsayılan constructor
    public Student() {
        this.vizeNotu = 0;
        this.finalNotu = 0;
        this.devamsizlik = 0;
    }
    
    // Tam constructor
    public Student(int numara, String ad, String soyad) {
        this.numara = numara;
        this.ad = ad;
        this.soyad = soyad;
        this.vizeNotu = 0;
        this.finalNotu = 0;
        this.devamsizlik = 0;
    }
    
    // Ortalama hesaplama metodu
    public double hesaplaOrtalama() {
        return (vizeNotu * 0.4) + (finalNotu * 0.6);
    }
    
    // Geçti/Kaldı durumu hesaplama
    public String getDurum() {
        double ortalama = hesaplaOrtalama();
        
        if (ortalama >= 60 && devamsizlik < 4) {
            return "Geçti";
        } else if (ortalama >= 60 && devamsizlik >= 4) {
            return "Devamsızlıktan Kaldı";
        } else if (ortalama < 60 && devamsizlik >= 4) {
            return "Devamsızlıktan Kaldı";
        } else if (ortalama < 60) {
            return "Kaldı";
        }
        return "Belirsiz";
    }
    
    // Notların girilip girilmediğini kontrol et
    public boolean notlarGirildiMi() {
        return vizeNotu > 0 || finalNotu > 0;
    }
    
    // Getter ve Setter metodları
    public int getNumara() {
        return numara;
    }
    
    public void setNumara(int numara) {
        this.numara = numara;
    }
    
    public String getAd() {
        return ad;
    }
    
    public void setAd(String ad) {
        this.ad = ad;
    }
    
    public String getSoyad() {
        return soyad;
    }
    
    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
    
    public int getVizeNotu() {
        return vizeNotu;
    }
    
    public void setVizeNotu(int vizeNotu) {
        this.vizeNotu = vizeNotu;
    }
    
    public int getFinalNotu() {
        return finalNotu;
    }
    
    public void setFinalNotu(int finalNotu) {
        this.finalNotu = finalNotu;
    }
    
    public int getDevamsizlik() {
        return devamsizlik;
    }
    
    public void setDevamsizlik(int devamsizlik) {
        this.devamsizlik = devamsizlik;
    }
    
    @Override
    public String toString() {
        return numara + " - " + ad + " " + soyad;
    }
}

