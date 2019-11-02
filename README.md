# SchachTurnierBastler

SchachTurnierBastler dient zur Verwaltung von Schach Turnieren Aehnlich dem Schweizer System.
Wichtig: Die Software ist noch im fruehen Stadium der Entwicklung.
Wichtig: Sie sollte nur fuer vereinsinterne Turniere mit hoechstens 50 Teilnehmern genutzt werden.
Im ersten Schritt gibt man die teilnehmenden Spieler ein und legt die Anzahl zu Spielende Runden fest.
Danach startet man das Turnier. 
Ab dann ist keine Aenderung mehr an den teilnehmenden Spielern und der Anzahl Runden mehr moeglich. 
Ledglich jeweils eine neue Runde kann hinzugefuegt werden. Jeweils ein Spieler kann entfernt werden.
Die erste Runde wird sofort festgelegt. 
Nachdem die Ergebnisse einer Runde eingetragen wurden, kann die naechste Runde generiert werden. 
Das Generieren einer Runde dauert eine gewisse Zeit, da hier sehr viele Runden zufaellig erzeugt und bewertet werden. 
Eine neue Runde, fuer die noch keine Ergebnisse eingetragen wurden, kann storniert werden. 
Ausnahme: Die erste Runde darf nicht storniert werden. 
Man kann jederzeit eine Auswertung bis zur dargestellten Runde bekommen. 
Es ist jederzeit moeglich eine oder mehrere bereits erzeugte Runden anzusehen. 
Ein Backup des Turniers oder der Spieler ist immer moeglich und sollte auch ausgiebig genutzt werden. 
Vom SchachTurnierBastler werden automatisch Backups des Turniers nach dem Erzeugen einer neuen Runde gemacht.

KO Turniere sind noch nicht vollstaendig implementiert.

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
