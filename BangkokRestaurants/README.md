# Bangkok Restaurants - Wyszukiwarka Restauracji

Prosta aplikacja Android do wyszukiwania restauracji w Bangkoku po frazie kluczowej.

---

## Spis treści
1. [Co robi aplikacja](#co-robi-aplikacja)
2. [Wymagania](#wymagania)
3. [Instalacja Android Studio](#instalacja-android-studio)
4. [Otwieranie projektu](#otwieranie-projektu)
5. [Budowanie aplikacji](#budowanie-aplikacji)
6. [Instalacja na telefonie](#instalacja-na-telefonie)
7. [Rozwiązywanie problemów](#rozwiązywanie-problemów)

---

## Co robi aplikacja

- Wyszukujesz restauracje wpisując frazę (np. "pad thai", "sushi", "BBQ")
- Aplikacja przeszukuje nazwę, opis, typ kuchni, dzielnicę i adres
- Klikasz na restaurację, żeby zobaczyć szczegóły
- Możesz otworzyć lokalizację w Google Maps

---

## Wymagania

- **Komputer**: Windows 10/11, macOS lub Linux
- **RAM**: minimum 8 GB (zalecane 16 GB)
- **Dysk**: ~10 GB wolnego miejsca
- **Telefon Android**: wersja 7.0 lub nowsza (API 24+)
- **Kabel USB** do podłączenia telefonu

---

## Instalacja Android Studio

### Krok 1: Pobierz Android Studio

1. Wejdź na stronę: https://developer.android.com/studio
2. Kliknij duży zielony przycisk **"Download Android Studio"**
3. Zaakceptuj warunki licencji
4. Pobierz plik instalacyjny (~1 GB)

### Krok 2: Zainstaluj Android Studio

#### Windows:
1. Uruchom pobrany plik `.exe`
2. Klikaj **"Next"** na każdym ekranie
3. Zostaw domyślne opcje zaznaczone
4. Kliknij **"Install"**
5. Po instalacji kliknij **"Finish"**

#### macOS:
1. Otwórz pobrany plik `.dmg`
2. Przeciągnij ikonę Android Studio do folderu **Applications**
3. Otwórz Android Studio z folderu Applications

#### Linux:
1. Rozpakuj pobrany plik `.tar.gz`
2. Wejdź do folderu `android-studio/bin`
3. Uruchom `./studio.sh`

### Krok 3: Pierwsze uruchomienie Android Studio

1. Uruchom Android Studio
2. Wybierz **"Do not import settings"** (jeśli zapyta)
3. Kreator pobierze dodatkowe komponenty (~2-3 GB) - **poczekaj cierpliwie!**
4. Wybierz **"Standard"** installation type
5. Wybierz motyw (jasny/ciemny) - obojętne
6. Kliknij **"Finish"** i poczekaj na pobranie SDK

**To może potrwać 15-30 minut przy pierwszym uruchomieniu!**

---

## Otwieranie projektu

### Krok 1: Pobierz projekt

Jeśli jeszcze nie masz projektu na komputerze:

```bash
git clone https://github.com/qwer29911/cla.git
cd cla/BangkokRestaurants
```

### Krok 2: Otwórz projekt w Android Studio

1. W Android Studio kliknij **"Open"** (lub File → Open)
2. Znajdź folder `BangkokRestaurants` na dysku
3. Zaznacz folder `BangkokRestaurants` (nie wchodź do środka!)
4. Kliknij **"OK"**

### Krok 3: Poczekaj na synchronizację

1. Android Studio zacznie pobierać zależności (Gradle sync)
2. Na dole ekranu zobaczysz pasek postępu
3. **Poczekaj aż zniknie** - może to potrwać 5-10 minut przy pierwszym otwarciu
4. Jeśli pojawi się pytanie o aktualizację Gradle - kliknij **"Update"**

**Gdy zobaczysz na dole "BUILD SUCCESSFUL" - projekt jest gotowy!**

---

## Budowanie aplikacji

### Metoda 1: Z Android Studio (łatwiejsza)

1. W górnym menu kliknij: **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Poczekaj na zakończenie budowania (1-3 minuty)
3. Pojawi się powiadomienie "Build completed" z linkiem **"locate"**
4. Kliknij **"locate"** - otworzy się folder z plikiem APK

Plik APK znajdziesz w:
```
BangkokRestaurants/app/build/outputs/apk/debug/app-debug.apk
```

### Metoda 2: Z terminala

```bash
cd BangkokRestaurants

# Windows:
gradlew.bat assembleDebug

# macOS/Linux:
./gradlew assembleDebug
```

---

## Instalacja na telefonie

### Metoda A: Przez kabel USB (zalecana)

#### Krok 1: Włącz tryb programisty na telefonie

1. Wejdź w **Ustawienia** telefonu
2. Znajdź **"Informacje o telefonie"** (czasem w "O telefonie")
3. Znajdź **"Numer kompilacji"** lub **"Build number"**
4. **Kliknij w niego 7 razy** szybko
5. Pojawi się komunikat "Jesteś teraz programistą!"

#### Krok 2: Włącz debugowanie USB

1. Wróć do głównych **Ustawień**
2. Znajdź **"Opcje programisty"** (pojawił się nowy punkt!)
3. Włącz **"Debugowanie USB"** (USB debugging)
4. Potwierdź ostrzeżenie

#### Krok 3: Podłącz telefon do komputera

1. Podłącz telefon kablem USB
2. Na telefonie pojawi się pytanie "Zezwolić na debugowanie USB?"
3. Zaznacz **"Zawsze zezwalaj z tego komputera"**
4. Kliknij **"Zezwól"** / **"OK"**

#### Krok 4: Uruchom aplikację z Android Studio

1. W Android Studio na górze zobaczysz listę rozwijaną z urządzeniami
2. Wybierz swój telefon z listy (powinien się pojawić)
3. Kliknij zielony przycisk **▶ Run** (trójkąt)
4. Aplikacja zainstaluje się i uruchomi na telefonie!

---

### Metoda B: Przez plik APK (bez kabla)

#### Krok 1: Przenieś plik APK na telefon

Opcja 1 - Email:
- Wyślij plik `app-debug.apk` na swój email
- Otwórz email na telefonie i pobierz załącznik

Opcja 2 - Dysk Google:
- Wrzuć plik APK na Google Drive
- Otwórz Google Drive na telefonie i pobierz plik

Opcja 3 - Kabel USB:
- Podłącz telefon kablem USB
- Wybierz "Transfer plików" na telefonie
- Skopiuj plik APK do folderu "Download" na telefonie

#### Krok 2: Zezwól na instalację z nieznanych źródeł

1. Wejdź w **Ustawienia → Bezpieczeństwo**
2. Znajdź **"Nieznane źródła"** lub **"Instaluj nieznane aplikacje"**
3. Zezwól dla aplikacji, z której będziesz instalować (np. "Pliki", "Chrome", "Gmail")

#### Krok 3: Zainstaluj aplikację

1. Otwórz menedżer plików na telefonie
2. Znajdź pobrany plik `app-debug.apk`
3. Kliknij na niego
4. Kliknij **"Zainstaluj"**
5. Po instalacji kliknij **"Otwórz"**

---

## Jak używać aplikacji

1. **Uruchom aplikację** - ikona "Bangkok Restaurants"
2. **Wpisz frazę** w pole wyszukiwania, np.:
   - `pad thai` - znajdzie restauracje z pad thai
   - `sushi` - znajdzie restauracje z sushi
   - `BBQ` - znajdzie restauracje z grillem
   - `Sukhumvit` - znajdzie restauracje w tej dzielnicy
3. **Kliknij na restaurację** żeby zobaczyć szczegóły
4. **Kliknij "Otwórz w Google Maps"** żeby zobaczyć lokalizację

---

## Rozwiązywanie problemów

### "Gradle sync failed"
- Sprawdź połączenie z internetem
- Kliknij **File → Sync Project with Gradle Files**
- Jeśli nie pomoże: **File → Invalidate Caches → Invalidate and Restart**

### Telefon nie jest widoczny w Android Studio
- Sprawdź czy kabel USB działa (spróbuj innego)
- Upewnij się, że włączyłeś "Debugowanie USB"
- Na telefonie zmień tryb USB na "Transfer plików" (nie "Tylko ładowanie")
- Zainstaluj sterowniki USB dla swojego telefonu (Windows)

### "App not installed" przy instalacji APK
- Odinstaluj starą wersję aplikacji (jeśli była)
- Sprawdź czy masz dość miejsca na telefonie
- Upewnij się, że zezwoliłeś na instalację z nieznanych źródeł

### Aplikacja się zawiesza
- Sprawdź czy plik `restauracje-all.csv` jest w folderze `app/src/main/assets/`
- Przebuduj aplikację: **Build → Clean Project**, potem **Build → Rebuild Project**

### Brak restauracji w aplikacji
- Upewnij się, że plik CSV jest w `app/src/main/assets/restauracje-all.csv`
- Sprawdź czy plik CSV ma poprawny format (przecinki jako separator)

---

## Aktualizacja danych restauracji

Jeśli chcesz zaktualizować listę restauracji:

1. Zastąp plik `app/src/main/assets/restauracje-all.csv` nową wersją
2. Przebuduj aplikację (**Build → Build APK**)
3. Zainstaluj nową wersję na telefonie

---

## Struktura projektu

```
BangkokRestaurants/
├── app/
│   ├── src/main/
│   │   ├── assets/
│   │   │   └── restauracje-all.csv    ← Dane restauracji
│   │   ├── java/.../
│   │   │   ├── data/
│   │   │   │   ├── Restaurant.kt       ← Model danych
│   │   │   │   └── RestaurantRepository.kt
│   │   │   ├── ui/
│   │   │   │   ├── MainActivity.kt     ← Główny ekran
│   │   │   │   ├── RestaurantAdapter.kt
│   │   │   │   ├── RestaurantDetailActivity.kt
│   │   │   │   └── RestaurantViewModel.kt
│   │   │   └── utils/
│   │   │       └── CsvParser.kt        ← Parser CSV
│   │   └── res/
│   │       ├── layout/                 ← Widoki XML
│   │       └── values/                 ← Kolory, teksty
│   └── build.gradle
├── build.gradle
└── settings.gradle
```

---

## Kontakt

Masz pytania? Utwórz issue na GitHub lub skontaktuj się z autorem.
