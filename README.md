# SchachTurnierBastler

SchachTurnierBastler dient zur Verwaltung von Schach Turnieren im KO System oder aehnlich dem Schweizer System.
Wichtig: Die Software ist noch im fruehen Stadium der Entwicklung.
Wichtig: Sie sollte nur fuer vereinsinterne Turniere mit hoechstens 64 Teilnehmern genutzt werden.
Im ersten Schritt gibt man die teilnehmenden Spieler ein und legt -beim schweizer System - die Anzahl zu Spielende Runden fest.
Danach startet man das Turnier (KO oder schweizer). 
Beim schweizer wird die erste Runde sofort festgelegt.
Beim KO koennen noch Spieler gesetzt werden.
Nachdem die Ergebnisse einer Runde eingetragen wurden, kann die naechste Runde generiert werden.
Folgendes gilt für das schweizer:
Das Generieren einer Runde dauert eine gewisse Zeit, da hier sehr viele Runden zufaellig erzeugt und bewertet werden. 
Eine neue Runde, fuer die noch keine Ergebnisse eingetragen wurden, kann storniert werden. 
Ausnahme: Die erste Runde darf nicht storniert werden. 
Man kann jederzeit eine Auswertung bis zur dargestellten Runde bekommen. 
Fuer beide gilt:
Es ist jederzeit moeglich eine oder mehrere bereits erzeugte Runden anzusehen. 
Ein Backup des Turniers oder der Spieler ist immer moeglich und sollte auch ausgiebig genutzt werden. 
Vom SchachTurnierBastler werden automatisch Backups des Turniers nach dem Erzeugen einer neuen Runde gemacht.
Auch beim Verlassen des Programms wird ein Backup gemacht.

Sollten Sie Kommentare, Anregungen oder Verbesserungsvorschläge haben, 
benachrichtigen Sie mich doch einfach unter christian@myoggradio.org.

Die Software besteht aus vielen Interface im Packet org.myoggradio.stb bzw org.myoggradio.stbko  und implementierenden Klassen in org.myoggradio.stb.impl bzw org.myoggradio.stbko.impl.
Die Start Klasse ist org.myoggradio.stb.Main.
Es gibt drei Haupt GUI: 
org.myoggradio.stb.MainMenu dient dem Eintragen von Spielern und setzen der Rundenzahl
org.myoggradio.stb.TurnierMenu dient der Steuerung des schweizer Turniers
org.myoggradio.stbko.TurnierMenu2 dient der Steuerung des KO Turniers.
Wichtige Interfaces sind:
org.myoggradio.stb.(KO)Turnier ist die Haupt-Datenstruktur eines Turniers
org.myoggradio.stb.(KO)TurnierManager hier wird eine neue Runde generiert
