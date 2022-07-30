# SchachTurnierBastler

SchachTurnierBastler dient zur Verwaltung von Schach Turnieren im KO System, Jeden gegen Jeden oder aehnlich dem Schweizer System.
Wichtig: Die Software ist noch im fruehen Stadium der Entwicklung.
Wichtig: Sie sollte nur fuer vereinsinterne Turniere mit hoechstens 64 Teilnehmern genutzt werden (schweizer System).
Im ersten Schritt gibt man die teilnehmenden Spieler ein.
Danach startet man das Turnier (KO,JGJ oder schweizer). 
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

Hinweise zur Installation
Es wird Java in mindestens der Version 11 benötigt.
SchachTurnierBastler wird nicht installiert, sondern nur entpackt.
Es gibt danach mehrere Möglichkeiten ihn zu starten.
(1) Durch doppelklicken auf SchachTurnierBastler.jar im lib Unterverzeichnis
(2) Durch ausführen des PowerShell Skriptes SchachTurnierBastler.ps1 (Windows) bzw SchachTurnierBastler.sh (Linux)
(3) Mit dem Befehl "java -jar SchachTurnierBastler.jar" im lib Unterverzeichnis
Nach dem ersten Start des Programms sollte man zuerst im Menu "Einstellungen" Das "AutoSave-Directory" festlegen. 
Dort werden automatische Sicherungen abgespeichert.
