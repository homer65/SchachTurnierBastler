# SchachTurnierBastler

SchachTurnierBastler dient zur Verwaltung von Schach Turnieren Ähnlich dem Schweizer System.
Im ersten Schritt gibt man die teilnehmenden Spieler ein und legt die Anzahl zu Spielende Runden fest.
Danach startet man das Turnier. 
Ab dann ist keine Änderung mehr an den teilnehmenden Spielern und der Anzahl Runden mehr möglich. 
Die erste Runde wird sofort festgelegt. 
Nachdem die Ergebnisse einer Runde eingetragen wurden, kann die nächste Runde generiert werden. 
Das Generieren einer Runde dauert eine gewisse Zeit, da hier sehr viele Runden zufällig erzeugt und bewertet werden. 
Eine neue Runde, für die noch keine Ergebnisse eingetragen wurden, kann storniert werden. 
Ausnahme: Die erste Runde darf nicht storniert werden. 
Man kann jederzeit eine Auswertung bis zur dargestellten Runde bekommen. 
Es ist jederzeit möglich eine oder mehrere bereits erzeugte Runden vor oder zurück zu gehen. 
Ein Backup des Turniers oder der Spieler ist immer möglich und sollte auch ausgiebig genutzt werden. 
Vom SchachTurnierBastler werden automatisch Backups des Turniers nach dem Erzeugen einer neuen Runde gemacht.

Sollten Sie Kommentare, Anregungen oder Verbesserungsvorschläge haben, 
benachrichtigen Sie mich doch einfach unter christian@myoggradio.org.

Die Software besteht aus vielen Interface im Packet org.myoggradio.stb und implementierenden Klassen in org.myoggradio.stb.impl.
Die Start Klasse ist org.myoggradio.stb.Main.
Es gibt zwei Haupt GUI: 
org.myoggradio.stb.MainMenu dient dem Eintragen von Spielern und setzen der Rundenzahl
org.myoggradio.stb.TurnierMenu dient der Steuerung des Turniers
Wichtige Interfaces sind:
org.myoggradio.stb.Turnier ist die Haupt-Datenstruktur eines Turniers
org.myoggradio.stb.TurnierManager hier wird eine neue Runde generiert
