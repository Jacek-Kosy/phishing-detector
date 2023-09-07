Dodatkowe założenia:
- Wiadomości pobieramy z topicu `sms-source`
- W wiadomości może się znajdować więcej niż jeden link
- Blokujemy wiadomość, jeżeli dla przynajmniej jednego rodzaju zagrożenia (ThreatType) jest ono wyższe lub równe ustalonemu próg poziomu ufności (ConfidenceLevel) - domyślnie HIGHER
- Zablokowane wiadomości odfiltrowujemy, a pozostałe puszczamy dalej na topic `filtered-sms-sink`
- Rejestracja do oraz rezygnacja z usługi odbywa się poprzez wysłanie SMS, o treści START lub STOP, na numer 6789. Zarówno numer, jak i wymagana treść wiadomości, mogą być zmienione poprzez przekazanie odpowiednich zmiennych środowiskowych.