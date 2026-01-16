# ✈️ UzAirWays (airLane)

UzAirWays — bu real aviakompaniya tizimiga o‘xshash tarzda ishlab chiqilgan onlayn aviabilet bron qilish tizimi bo‘lib,  
foydalanuvchilarga parvoz yo‘nalishlari bo‘yicha chipta tanlash, bron qilish va bekor qilish imkonini beradi.  
Project real serverga joylashtirilgan va 1 oy davomida ish holatida bo‘lgan.

---

## 🎯 Asosiy imkoniyatlar
- Onlayn chipta bron qilish
- Parvozdan 24 soat oldin bronni bekor qilish
- Bekor qilingan chiptalar uchun to‘lovni qaytarish
- Parvozlar va to‘lovlar tarixini yuritish
- Foydalanuvchi shikoyat va takliflarini boshqarish

---

## 👥 Rollar va vakolatlar tizimda 3 ta asosiy rol mavjud

### 👤 User
- Parvozlarni ko‘rish va chipta bron qilish
- Parvozdan 24 soat oldin bronni bekor qilish
- Shaxsiy pasport yoki ID karta ma’lumotlarini kiritish
- Onlayn to‘lov qilish
- O‘z bron va to‘lovlar tarixini ko‘rish

### 🛠 Admin
- Parvozlarni boshqarish va kuzatish
- Ob-havo yoki texnik sabablarga ko‘ra:
  - parvozni kechiktirish
  - parvozni bekor qilish
- Viloyatlar bo‘yicha yangi parvozlar yaratish
- Foydalanuvchilardan kelgan shikoyat va takliflarni ko‘rib chiqish

### 👑 Owner
- Barcha: userlar, adminlar, parvozlar, to‘lovlar tarixini ko‘rish
- Admin qo‘shish va o‘chirish
- Tizim ustidan to‘liq nazorat

---

## 🧩 Arxitektura (Monolith)
Loyiha quyidagi servislar asosida ishlab chiqilgan:
- User Service – foydalanuvchilar va autentifikatsiya
- Flight Service – parvozlar va yo‘nalishlar
- Booking Service – bron qilish logikasi
- Payment Service – to‘lovlar va refund jarayonlari
- Notification Service – xabarnomalar
- Config Service – markazlashgan konfiguratsiya

---

## 🛠 Ishlatilgan texnologiyalar
Java 17, Spring Boot, Spring Cloud (Gateway, Eureka, Config), Spring Data JPA & Hibernate, PostgreSQL,  
Kafka, Gradle, REST API, Swagger UI, JWT Authentication
