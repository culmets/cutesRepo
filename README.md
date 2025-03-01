# cutesRepo mit Schachspiel
Mit diesem Projekt soll ein CLI Schach ermöglicht werden.

Es können zwei Menschen gegeneinander Spielen. Dabei werden die Gewinne, der verschiedenen Spieler, gespeichert und können als Leaderboard abgefragt werden.

Zum Ende von jedem Spiel wird der Spielverlauf in einem GameRecord gespeichert, diese Files befinden sich im ordner "game_records".

Ein laufendes Spiel kann unterbrochen werden, indem bei dre Abfrage nach der Startposition "speichern" eingegeben wird. Das Spiel wird als GameState gespeichert und kann zu einem beliebigen späteren Zeitpunkt weitergespielt werden.
Es werden die Namen der Spieler, die Farben der Spieler, der Spieler der aktuell am Zu ist und die Boardfigurenaufstellung gespeichert. 
Die GameState Files befinden sich im Ordner "game_state_". 

Um einen Spielstand weiterzuspielen muss beim Start (GameInitializer) eine 3 eingegeben werden.
Dann werden die Dateinamen aller gespeicherten Spielstände angezeigt und es muss ein Spielstand ausgewählt werden.
Der ausgewählte Spielstand wird geladen.

Zu beachten: 
- Wenn ein Bauer das andere Ende des Bretts erreicht wird er automatisch zur Dame.
- En Passant schlagen in möglich, Rochade aber nicht.
- Der Figuren haben ihre englischen Anfangsbuchstaben als Symbol auf dem Brett, nur für den Knight (Springer) wird statt K ein S verwendet