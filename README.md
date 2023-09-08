Dodatkowe założenia:
- Wiadomości pobieramy z topicu `sms-source`
- W wiadomości może się znajdować więcej niż jeden link
- Nie walidujemy MSISDN — SMS może przyjść z numeru skróconego lub nadpisu alfanumerycznego.
- Blokujemy wiadomość, jeżeli dla przynajmniej jednego rodzaju zagrożenia (ThreatType) jest ono wyższe lub równe ustalonemu próg poziomu ufności (ConfidenceLevel) - domyślnie HIGHER
- Zablokowane wiadomości odfiltrowujemy, a pozostałe puszczamy dalej na topic `filtered-sms-sink`
- Rejestracja do oraz rezygnacja z usługi odbywa się poprzez wysłanie SMS, o treści START lub STOP, na numer 6789. Zarówno numer, jak i wymagana treść wiadomości, mogą być zmienione poprzez przekazanie odpowiednich zmiennych środowiskowych.

Do wyciągania URL z wiadomości wykorzystałem regex z bloga: https://daringfireball.net/2010/07/improved_regex_for_matching_urls

Komendy do uruchomienia:
- `docker pull jacek2212/phishing-detector:latest`
- `docker run -d -p 8080:8080 --mount destination=/data jacekk/phishing-detector`

Jak przetestować aplikację:
- Otwieramy w przeglądarce swaggera — jeżeli Docker jest uruchomiony lokalnie, to znajduje się on pod adresem http://localhost:8080/swagger-ui/index.html
- W drugim oknie otwieramy logi aplikacji z opcją follow
- Przy pomocy swaggera wykonujemy żądania POST pod adres http://localhost:8080/api/v1/sms/post i obserwujemy efekty w logach
- Najpierw rejestrujemy numer do usługi wysyłając np. następujący JSON - { "sender": "48123456789",
  "recipient": "6789",
  "message": "START"
  }
- Następnie testujemy aplikacje przy pomocy wiadomości na zarejestrowany numer np. - {
  "sender": "48987654321",
  "recipient": "48123456789",
  "message": "https://www.m-bonk.pl.ng/personal-data"
  }

Jak można byłoby bardziej przystosować aplikację do wdrożenia produkcyjnego:
- Przełączyć poprzez zmienne środowiskowe i ewentualne rozszerzenie konfiguracji w `application.yml` Kafkę z embedded na standalone.
- Podobnie postąpić z bazą danych, H2 w pliku zamienić na serwer, np. PostgreSQL, MariaDB
- Zmienić adres zewnętrznego API na docelowy, dodać obsługę autoryzacji
- Usunąć paczkę `mock`