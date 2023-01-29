# Aplikacja kinowa

Graficzna desktopowa aplikacja kinowa do rezerwowania miejsc na wybrany film.

## Funkcjonalności
* Wybór daty
>  * Możliwość wyboru daty do 6 dni na przód. Domyślna wartość to bieżący dzień.

* Wybór filmu
>  * Pod oknem z datą jest lista przycisków z nazwami dostępnych filmów -> przenoszą nas one do widoku z dostępnymi miejscami na wybrany film w wybranym dniu. Jeżeli wszystkie miejsca będą już zajęte otrzymamy alert z informacją o braku dostępnych miejsc i nie zostaniemy przeniesieni (oprócz sytuacji, w której posiadamy rezerwacje wtedy będziemy mieli możliwość podglądu).

* Podgląd miejsc
> * Widok w którym widzimy wszystkie miejsca na wybrany seans. Są to przyciski podpisane numerem rzędu oraz miejscem.
> * Miejsca posiadają kolory: Zielony-> wolne, Czerwony -> zajęte, Zółty -> miejsce zarezerwowane przez nas, Szary -> wybrane przez nas miejsce, które chcemy zarezerować
> * Jeżeli klinikemy na zielone miejsce wtedy zmieni się ono w szare i z prawej strony pojawi się przycisk "Zarezerwuj" - po kliknięciu dostaniemy alert z informacją o zarezerwowaniu miejsca na daną date i szczegóły dotyczące rezerwacji takie jak nazwa filmu, miejsce oraz rząd.
