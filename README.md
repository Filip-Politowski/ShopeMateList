# ShopMateList
REST API do ułatwienia tworzenia codziennych list zakupowych.
Program został napisany w języku JAVA za pomocą framework Spring BOOT.

# Opis
Aplikacja umożliwia użytkownikom na tworzenie list zakupowych z artykułami spożywczymi.
Produkty podzielone są na kategorie w celu ułatwienia szukania ich docelowo na pułkach sklepowych.
Każda lista zakupowa ma przypisaną datę oraz sklep, w jakim chcielibyśmy ją zrobić, w celu ułatwienia przeszukiwania naszych historycznych list zakupowych.
Użytkownicy mają możliwość udostępnienia innej osobie swoich list zakupowych.
Udostępniona lista od innego użytkownika jest w pełni modyfikowalna przez osobę,  która ją otrzymała, jedyną różnicą jest, że lista ma ustawioną rubrykę   „Owner” na false.
Daje to możliwość oddzielenia własnych list zakupowych od udostępnionych przez innego użytkownika.
***
Aplikacja umożliwia również tworzenie przepisów kulinarnych z opisem, nazwą oraz składnikami, jakie są potrzebne.
Każdy użytkownik ma bazę własnych przepisów, które tworzy na przestrzeni czasu w miarę swoich potrzeb.
Aplikacja ma funkcję dodania wszystkich składników, jakie są w przepisie do wybranej listy zakupowej.
Umożliwia to szybsze robienie list zakupowych.
W przypadku gdy jakiś produkt z innego przepisu powtarza się na liście zakupowej, aktualizowana jest jego ilość o taką jak była w danym przepisie.
***
Aplikacja ma również opcję tworzenia tygodniowych planów jedzenia.
Posiłki w planie podzielone są na śniadania, obiad, kolacje.
Daje to możliwość zaplanowania jedzenia na cały tydzień.
Z takiego planu tygodniowego jest możliwość dodania wszystkich produktów, jakie są potrzebne do jego realizacji do wybranej listy zakupowej.
Umożliwia to w jednym ruchu stworzenie całej niezbędnej listy zakupowej, dodatkowo można dodać pojedyncze produkty do tak utworzonej listy zakupowej.
Podobnie jak w przypadku przepisów, jeśli jakiś produkt znajduje się już na liście zakupowej, zostanie zwiększona tylko jego ilość.

# Funkcje
* Wyświetlanie dostępnych produktów
* Wyświetlanie dostępnych marketów
* Tworzenie listy zakupowej
* Przechowywanie starych list zakupowych
* Udostępnianie list zakupowych innym użytkownikom
* Tworzenie przepisów kulinarnych
* Tworzenie tygodniowych planów jedzeniowych
* Dodawanie produktów do listy zakupowej na bazie przepisów
* Dodawanie produktów dlo listy zakupowej na bazie tygodniowego planu jedzenia
* Logowanie się do aplikacji
# Struktura projektu
W skład struktury projektu wchodzą:
* Klasy konfiguracyjne
* Encje
* Klasy transferowe DTO
* Customowe wyjątki
* Repozytoria Spring Data JPA
* Warstwy serwisów 
* Kontrolery
* Mappery
* Swagger
# Autoryzacja i uwierzytelnienie
API wykorzystuje autoryzacje przez nagłówek (Header) z użyciem JWT tokenów.
Każde zapytanie chronionego zasobu musi zawierać poprawnie sformułowany nagłówek 'Authorization', który zawiera JWT token.

Przykład nagłówka 'Authorization': 
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```
# Endpointy API
Aplikacja ma tylko dwa niezabezpieczone endpoointy, które służą do rejestracji oraz do logowania.
Endpointy te generują token autoryzacyjny umożliwiający dostęp do aplikacji.
* Endpoint rejestracji ```localhost:8080/api/auth/signup```
```JSON
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string"
}
```
* Endpoint logowania ```localhost:8080/api/auth/signin```
```JSON
{
  "email": "string",
  "password": "string"
}
```
Wszystkie inne endpointy muszą posiadać poprawnie sformułowany nagłówek 'Authorization' w celu uwierzytelnienia użytkownika.

Zbiór wszystkich endpointów po uruchomieniu aplikacji znajduje się w Open API pod linkiem:
http://localhost:8080/swagger-ui/index.html

# Instrukcja uruchomienia
W celu uruchomienia aplikacji należy:
* Zweryfikować posiadanie na komputerze Java Development Kit (JDK), Maven, MySQL.
* Skopiować repozytorium na komputer
* Skonfigurować bazę danych:
  * utworzyć bazę danych o nazwie ```shopping_list_database```
  * zaktualizować plik ```application.properties```
    * ustawić pola: 
    ```
     spring.datasource.username=(użytkownik na danym komputerze)
     spring.datasource.password=(hasło do tego użytkownika)
     ``` 
    * wgrać dane do bazy danych z plików z rozszerzeniem ```.sql```, pliki znajdują się w katalogu```resources```
* Uruchomić aplikacje ```shift + F10```
* Po uruchomieniu aplikacji należy przejść do ```Swagger-UI``` pod linkiem: http://localhost:8080/swagger-ui/index.html
* Następnie należy się zarejestrować oraz zalogować w celu uzyskania dostępu do wszystkich endpointów
* Endpointy można również obsługiwać za pomocą ```POSTMAN```, należy pamiętać by przy procesie rejestracji i logowania ustawić autoryzację na ```none```. Po zalogowaniu na pozostałych endpointach należy ustawić autoryzację na bearerauth i skopiować token uzyskany podczas logowania