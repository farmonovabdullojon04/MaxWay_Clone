# 📱 MaxWay Clone

![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-4285F4?logo=jetpackcompose&logoColor=white)
![Min SDK](https://img.shields.io/badge/minSdk-24-brightgreen)
![Target SDK](https://img.shields.io/badge/targetSdk-36-brightgreen)
![License](https://img.shields.io/badge/License-Educational-lightgrey)

**MaxWay** fast-food tarmog'ining mobil ilovasi klonini Android'da **Kotlin** va **Jetpack Compose** yordamida qayta yaratish loyihasi. **Clean Architecture** (MVVM/MVI) tamoyillariga asoslangan, to'liq funksional food-delivery ilova.

---


## ✨ Asosiy funksiyalar

- 🔐 Ro'yxatdan o'tish va telefon raqami orqali autentifikatsiya (SMS-kod tasdiqlash)
- 🍔 Mahsulotlar katalogi, kategoriyalar bo'yicha filtrlash va qidiruv
- 🛒 Savatcha (korzina) va buyurtma berish jarayoni
- 📦 Joriy va tarixiy buyurtmalarni kuzatish
- 📍 Filiallar ro'yxati va tafsilotlari
- 👤 Profilni tahrirlash, sozlamalar, hisobni o'chirish
- 🌐 Ko'p tillilik (Rus / O'zbek)
- 📢 Reklama bannerlari va "Stories" formatidagi kontent

---

## 🛠 Texnologiyalar

| Kategoriya | Texnologiya |
|---|---|
| Til | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Arxitektura | Clean Architecture (data / domain / presentation) |
| Dependency Injection | Hilt |
| Navigatsiya | Voyager |
| State management | Orbit MVI |
| Tarmoq | Retrofit, OkHttp (logging-interceptor) |
| Serializatsiya | Kotlinx Serialization, Gson |
| Rasm yuklash | Coil |
| Loglash | Timber |
| Network debugging | Chucker (faqat debug build) |
| Splash screen | AndroidX Core SplashScreen |
| Custom UI komponent | WheelPickerCompose |
| Test | JUnit, Espresso, Compose UI Test |

---

## 🏗 Loyiha arxitekturasi

Loyiha **Clean Architecture** tamoyillariga asosan uchta asosiy qatlamga bo'lingan:

```
app/src/main/java/com/abdullojon/maxwayclone/
├── data/               # Ma'lumotlar qatlami
│   ├── repository/     # Repository implementatsiyasi
│   ├── source/
│   │   ├── remote/     # API interfeyslari, DTO'lar
│   │   └── local/      # Lokal saqlash (preferences)
│   └── mapper/         # DTO -> UI model mapperlari
│
├── domain/             # Biznes logika qatlami
│   ├── model/          # UI/domain modellari
│   ├── repository/     # Repository interfeyslari
│   └── usecase/        # Use case'lar (auth, order, profile va h.k.)
│
├── presentation/        # UI qatlami (Jetpack Compose)
│   ├── main/            # Bosh sahifa, mahsulot detali
│   ├── basket/          # Savatcha
│   ├── order/           # Checkout va buyurtmalar
│   ├── profile/         # Profil, filiallar, sozlamalar
│   ├── register/        # Ro'yxatdan o'tish/kirish
│   └── components/      # Qayta ishlatiladigan Compose komponentlar
│
├── navigation/          # Navigatsiya dispatcher va handlerlar
├── di/                  # Hilt modullari
└── util/                # Yordamchi klasslar (masalan, LocaleHelper)
```

## 🖼 video
<img width="240" height="531" alt="Image" src="https://github.com/user-attachments/assets/da3069d2-66e2-4b4e-934d-7eafbab78229" />
---

## 🖼 Ekran suratlari
<img width="360" height="780" alt="image" src="https://github.com/user-attachments/assets/b840db5e-30a9-4484-9a14-ea1620ba664f" />
<img width="360" height="780" alt="image" src="https://github.com/user-attachments/assets/be0506e5-ca47-4f2a-af08-e19d76f1ef99" />
<img width="360" height="780" alt="image" src="https://github.com/user-attachments/assets/898effc8-71f0-4443-b2a5-86e4d8808e3c" />
<img width="360" height="780" alt="image" src="https://github.com/user-attachments/assets/3008e0b3-0445-4481-a83c-373792fc79e1" />
<img width="360" height="780" alt="image" src="https://github.com/user-attachments/assets/f6ca7190-3d2b-40fc-b8a4-af0f6569ff9b" />
<img width="360" height="780" alt="image" src="https://github.com/user-attachments/assets/891db204-16e1-40d6-bf82-dcf6c03310dc" />

---

## 👤 Muallif

**Abdullojon Farmonov**

**farmonovabdullojon04@gmail.com**

## ⚠️ Eslatma

Bu loyiha faqat **o'quv (ta'lim) maqsadida** yaratilgan bo'lib, MaxWay kompaniyasi bilan hech qanday rasmiy aloqasi yo'q. Barcha huquqlar tegishli egalariga tegishli.
