# Google Docs Blog Publisher

Aplikacja w Pythonie do publikowania tekstów blogowych z Google Docs z zachowaniem formatowania.

## Funkcje

- Pobieranie treści z Google Docs przez API
- Zachowanie formatowania tekstu:
  - Nagłówki (H1-H6)
  - Pogrubienie (**bold**)
  - Kursywa (*italic*)
  - Podkreślenie
  - Przekreślenie
  - Linki
  - Listy punktowane i numerowane
  - Tabele
- Export do formatów: HTML, Markdown, lub czysty tekst

## Wymagania

- Python 3.7 lub nowszy
- Konto Google z dostępem do Google Docs API

## Instalacja

### 1. Sklonuj repozytorium

```bash
git clone <repository-url>
cd cla
```

### 2. Zainstaluj wymagane biblioteki

```bash
pip install -r requirements.txt
```

Lub z wykorzystaniem wirtualnego środowiska (zalecane):

```bash
python -m venv venv
source venv/bin/activate  # Linux/Mac
# lub
venv\Scripts\activate  # Windows

pip install -r requirements.txt
```

### 3. Skonfiguruj dostęp do Google Docs API

#### A. Stwórz projekt w Google Cloud Console

1. Przejdź do [Google Cloud Console](https://console.cloud.google.com/)
2. Stwórz nowy projekt lub wybierz istniejący
3. Włącz Google Docs API:
   - W menu nawigacji wybierz "APIs & Services" > "Library"
   - Wyszukaj "Google Docs API"
   - Kliknij "Enable"

#### B. Stwórz credentials

1. W "APIs & Services" wybierz "Credentials"
2. Kliknij "Create Credentials" > "OAuth client ID"
3. Wybierz "Desktop app" jako typ aplikacji
4. Pobierz plik JSON i zapisz jako `credentials.json` w głównym katalogu projektu

### 4. Skonfiguruj aplikację

Skopiuj przykładowy plik konfiguracyjny:

```bash
cp config.example.ini config.ini
```

Edytuj `config.ini` i ustaw:

```ini
[google]
# ID dokumentu Google Docs (z URL: https://docs.google.com/document/d/DOCUMENT_ID/edit)
document_id = TWOJ_DOCUMENT_ID

[output]
# Format wyjściowy: html, markdown, lub text
format = html
# Ścieżka do pliku wyjściowego
output_file = output/blog_post.html
```

## Użycie

### Podstawowe użycie

```bash
python blog_publisher.py
```

Aplikacja użyje ustawień z `config.ini`.

### Parametry wiersza poleceń

```bash
# Określ ID dokumentu
python blog_publisher.py --document-id "1abc123XYZ..."

# Określ format wyjściowy
python blog_publisher.py --format markdown

# Określ plik wyjściowy
python blog_publisher.py --output my_blog_post.html

# Użyj innego pliku konfiguracyjnego
python blog_publisher.py --config custom_config.ini

# Kombinacja parametrów
python blog_publisher.py --document-id "1abc123XYZ..." --format html --output my_post.html
```

### Pierwsze uruchomienie

Przy pierwszym uruchomieniu:
1. Aplikacja otworzy przeglądarkę
2. Zaloguj się na swoje konto Google
3. Zezwól aplikacji na dostęp do Google Docs
4. Token dostępu zostanie zapisany w `token.json` dla przyszłych użyć

## Jak uzyskać Document ID?

ID dokumentu znajduje się w URL Google Docs:

```
https://docs.google.com/document/d/1abc123XYZ-example-document-id/edit
                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                   To jest Document ID
```

## Formaty wyjściowe

### HTML
Pełny dokument HTML z podstawowymi stylami CSS, gotowy do publikacji.

### Markdown
Format Markdown z zachowanym formatowaniem, idealny do systemów CMS opartych na Markdown.

### Text
Czysty tekst bez formatowania.

## Struktura projektu

```
cla/
├── blog_publisher.py       # Główny skrypt aplikacji
├── google_docs_client.py   # Klient Google Docs API
├── document_formatter.py   # Formatowanie dokumentów
├── requirements.txt        # Zależności Python
├── config.example.ini      # Przykładowy plik konfiguracyjny
├── config.ini             # Plik konfiguracyjny (tworzony przez użytkownika)
├── credentials.json       # Credentials Google API (pobrane z Console)
├── token.json            # Token dostępu (generowany automatycznie)
├── .gitignore            # Pliki ignorowane przez Git
└── README.md             # Ta dokumentacja
```

## Rozwiązywanie problemów

### Błąd: "Credentials file not found"
Upewnij się, że pobrałeś `credentials.json` z Google Cloud Console i umieściłeś go w głównym katalogu projektu.

### Błąd: "Configuration file not found"
Stwórz plik `config.ini` bazując na `config.example.ini`.

### Błąd autoryzacji
Usuń plik `token.json` i uruchom aplikację ponownie, aby przejść przez proces autoryzacji od nowa.

## Licencja

MIT License

## Autor

Created with Python and Google Docs API
