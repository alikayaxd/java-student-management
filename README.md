# Öğrenci Yönetim Sistemi

Java Swing kullanılarak geliştirilmiş, Nesne Yönelimli Programlama (OOP) kavramlarını uygulamalı olarak pekiştirmeye yönelik Öğrenci Yönetim Sistemi.

## Özellikler

- ✅ Login ekranı (Kullanıcı adı: admin, Şifre: admin123)
- ✅ Öğrenci ekleme, silme ve listeleme
- ✅ Not girişi (Vize / Final)
- ✅ Devamsızlık ekleme
- ✅ Başarı raporu oluşturma
- ✅ En başarılı ve en başarısız öğrenciyi bulma
- ✅ Sınıf ortalaması hesaplama
- ✅ Not histogramı görüntüleme (gerçek grafik çubukları)
- ✅ Öğrenci not grafiği görüntüleme
- ✅ Recursive öğrenci sayısı hesaplama
- ✅ Modern GUI tasarımı
- ✅ Üniversite logosu desteği

## Proje Yapısı

src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── ogrenciyonetim/
│   │           ├── OgrenciYonetimApp.java
│   │           ├── model/
│   │           │   └── Student.java
│   │           ├── service/
│   │           │   └── FileStudentService.java
│   │           ├── util/
│   │           │   └── LogoUtil.java
│   │           └── gui/
│   │               ├── LoginFrame.java
│   │               ├── MainFrame.java
│   │               ├── dialogs/
│   │               └── panels/
│   └── resources/
│       └── logo.png


## Eclipse'de Açma

1. Eclipse'i açın  
2. File → Import → General → Existing Projects into Workspace  
3. "Select root directory" seçeneğini seçin  
4. Proje klasörünü seçin  
5. Finish'e tıklayın  

## Çalıştırma

### Eclipse Üzerinden
1. Projeyi Eclipse'te açın  
2. `OgrenciYonetimApp.java` dosyasına sağ tıklayın  
3. Run As → Java Application  

## Giriş Bilgileri

- **Kullanıcı Adı:** `admin`
- **Şifre:** `admin123`

## Veri Saklama Yapısı

- Öğrenci verileri dosya tabanlı olarak saklanır  
- Her öğrenci için `[numara].txt` dosyası oluşturulur  
- Dosyalar proje dizini altında otomatik olarak bulunan `ogrenciler/` klasöründe tutulur  
- Hem `Vize:` hem `Vize Notu:` formatları desteklenir  

## Kullanılan Teknolojiler

- Java 8+
- Java Swing (GUI)
- WindowBuilder uyumlu yapı
- Dosya tabanlı veri saklama
- Graphics2D ile grafik çizimi

## OOP Prensipleri

- **Encapsulation:** Getter / Setter kullanımı  
- **Separation of Concerns:** Model, GUI ve Service katmanları  
- **Single Responsibility Principle:** Her sınıf tek sorumluluğa sahiptir  
- **Dependency Injection:** Servis sınıfları constructor üzerinden enjekte edilir  

## GUI ve Performans Özellikleri

### Modern Arayüz
- Gradient arka planlar
- Hover efektleri
- Modern renk paleti
- Logo entegrasyonu

### Performans
- Cache mekanizması
- Lazy loading
- SwingWorker ile arka plan işlemleri
- Thread-safe veri yapıları

## Notlar

- Logo dosyası `src/main/resources/logo.png` konumunda bulunmalıdır  
- Tüm GUI bileşenleri WindowBuilder ile uyumludur  
- Uygulama giriş noktası `OgrenciYonetimApp.java` sınıfıdır  

## Geliştirici

**Muhammed Ali Kaya**
