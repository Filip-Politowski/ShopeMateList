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

# Struktura projektu

# Instrukcja uruchomienia