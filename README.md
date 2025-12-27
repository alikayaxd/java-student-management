# Öğrenci Yönetim Sistemi

Java Swing kullanılarak geliştirilmiş, Nesne Yönelimli Programlama (OOP) kavramlarını uygulamalı olarak pekiştirmeye yönelik Öğrenci Yönetim Sistemi.

## Özellikler

- ✅ Login ekranı (Kullanıcı adı: admin, Şifre: admin123)
- ✅ Öğrenci ekleme/silme/listeleme
- ✅ Not girişi (Vize/Final)
- ✅ Devamsızlık ekleme
- ✅ Başarı raporu oluşturma
- ✅ En başarılı/başarısız öğrenci bulma
- ✅ Sınıf ortalaması hesaplama
- ✅ Not histogramı görüntüleme (Gerçek grafik çubukları)
- ✅ Öğrenci not grafiği görüntüleme (Gerçek grafik çubukları)
- ✅ Recursive öğrenci sayısı hesaplama
- ✅ Modern GUI tasarımı
- ✅ Üniversite logosu desteği

## Proje Yapısı

```
OgrenciYonetimSistemi/
├── .project                    # Eclipse proje dosyası
├── .classpath                  # Eclipse classpath dosyası
├── .settings/                  # Eclipse ayarları
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── ogrenciyonetim/
│       │           ├── OgrenciYonetimApp.java    # Ana uygulama (Eclipse uyumlu)
│       │           ├── model/
│       │           │   └── Student.java          # Öğrenci model sınıfı
│       │           ├── service/
│       │           │   └── FileStudentService.java  # Dosya tabanlı veri yönetimi
│       │           ├── util/
│       │           │   └── LogoUtil.java        # Logo yardımcı sınıfı
│       │           └── gui/
│       │               ├── LoginFrame.java       # Login ekranı
│       │               ├── MainFrame.java        # Ana menü ekranı
│       │               ├── dialogs/              # Dialog pencereleri
│       │               └── panels/               # Panel bileşenleri
│       └── resources/
│           └── logo.png                     # Üniversite logosu
└── bin/                        # Derlenmiş sınıf dosyaları
```

## Eclipse'de Açma

1. Eclipse'i açın
2. File → Import → General → Existing Projects into Workspace
3. "Select root directory" seçeneğini seçin
4. Proje klasörünü seçin (`C:\Users\ali\Desktop\new`)
5. "Copy projects into workspace" seçeneğini işaretleyin (isteğe bağlı)
6. Finish'e tıklayın

## Çalıştırma

### Eclipse'de:
1. Projeyi Eclipse'de açın
2. `OgrenciYonetimApp.java` dosyasına sağ tıklayın
3. Run As → Java Application seçin

### Komut satırından:
```bash
javac -d bin -encoding UTF-8 -cp bin src/main/java/com/ogrenciyonetim/OgrenciYonetimApp.java src/main/java/com/ogrenciyonetim/model/*.java src/main/java/com/ogrenciyonetim/service/*.java src/main/java/com/ogrenciyonetim/gui/*.java src/main/java/com/ogrenciyonetim/gui/dialogs/*.java src/main/java/com/ogrenciyonetim/gui/panels/*.java src/main/java/com/ogrenciyonetim/util/*.java

java -cp bin com.ogrenciyonetim.OgrenciYonetimApp
```

## Giriş Bilgileri

- **Kullanıcı Adı:** `admin`
- **Şifre:** `admin123`

## Veri Saklama

Öğrenci bilgileri dosya sisteminde saklanır:
- **Konum:** `[Proje Klasörü]/ogrenciler/` (proje klasörü nerede olursa olsun otomatik bulunur)
- **Format:** Her öğrenci için `[numara].txt` dosyası
- **İçerik:** Numara, Ad, Soyad, Vize/Vize Notu, Final/Final Notu, Devamsızlık/Devamsizlik
- **Desteklenen formatlar:** Hem "Vize:" hem "Vize Notu:" formatları desteklenir

## Teknolojiler

- Java 8+
- Swing (GUI)
- WindowBuilder uyumlu kod yapısı
- Dosya tabanlı veri saklama
- Graphics2D ile grafik çizimi

## OOP Prensipleri

- **Encapsulation:** Model sınıflarında getter/setter metodları
- **Separation of Concerns:** Model-View-Service katmanları
- **Single Responsibility:** Her sınıf tek bir sorumluluğa sahip
- **Dependency Injection:** Service sınıfları constructor ile enjekte edilir

## Özellikler

### Modern GUI
- Gradient arka planlar
- Hover efektleri
- Modern renk paleti
- Segoe UI fontu
- Logo desteği

### Performans Optimizasyonları
- Cache mekanizması (5 saniye)
- Lazy loading
- SwingWorker ile arka plan işlemleri
- ConcurrentHashMap ile thread-safe cache

### Grafik Özellikleri
- Gerçek çubuk grafikleri (Graphics2D)
- Renk kodlu gösterimler
- Eksen ve etiketler
- Profesyonel görünüm

## Notlar

- Logo dosyası: `src/main/resources/logo.png` konumunda olmalıdır
- Öğrenci verileri proje klasöründeki `ogrenciler/` klasöründen otomatik olarak okunur
- Proje klasörü (new klasörü) nerede olursa olsun, otomatik olarak bulunur
- Tüm GUI bileşenleri WindowBuilder ile uyumludur
- Eclipse'de sorun yaşamamak için Main.java yerine OgrenciYonetimApp.java kullanılmıştır

## Geliştirici

Muhammed Ali KAYA
