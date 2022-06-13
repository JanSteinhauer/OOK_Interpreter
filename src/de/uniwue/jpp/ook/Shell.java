package de.uniwue.jpp.ook;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;

public interface Shell {

    /**
     * Diese Methode erzeugt eine neue Ook-Shell.
     * Die Befehle und Daten werden von dem InputStream eingelesen. Dies kann System.in sein.
     * Die Ausgaben der Shell und des Programms werden auf dem OutputStream ausgegeben. Dies kann System.out sein.
     * Zur einfacheren Handhabe wird empfohlen den OutputStream in einen PrintStream umzuwandeln.
     *
     *
     * @param handle
     * @param input
     * @param output
     * @return
     * @throws NullPointerException falls ein Argument null ist.
     */
    static Shell create(Ook handle, InputStream input, OutputStream output) {
        if(Objects.isNull(handle) || Objects.isNull(input)|| Objects.isNull(output)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }else{
            ShellImpl sh = new ShellImpl(handle,input,output);
            return sh;

        }

    }

    /**
     * Diese Methode wandelt ene eingelesene Anweisung in eine Instruction um.
     *
     * @param line eine Anweisung der Form "Ook.Ook!", d.h. ohne Leerzeichen.
     * @return Ein Optional, welches für eine gültige Anweisung diese enthält und sonst leer ist.
     * @throws NullPointerException falls das Argument null ist.
     */
    Optional<Instruction> parseLine(String line);

    /**
     * Diese Methode wird ausgeführt, bevor ein Ook-Befehl interpretiert wird.
     * Falls der folgende Befehl eine 'Read'-Anweisung ist, soll zuerst "Reading: " ausgegeben werden.
     * Falls der folgende Befehl eine 'Write'-Anweisung ist, soll zuerst "Writing: " ausgegeben werden.
     * Sonst soll der Befehlsname samt Zeilenumbruch ausgegeben werden.
     *
     * @param instruction ist der folgende Befehl.
     */
    void prepareUpdate(Instruction instruction);

    /**
     * Diese Methode wird ausgeführt, nachdem eine Ook-Anweisung interpretiert wurde.
     * Falls der Befehl eine 'Write'-Anweisung war, soll ein Zeilenumbruch ausgegeben werden.
     * Falls der Befehl eine 'Read'-Anweisung war, soll das nächste Zeichen eingelesen werden.
     * Wenn dies kein Zeilenumbruch ist, soll "Illegal Input: Insert only one character!" ausgegeben werden.
     *
     * @param instruction ist der interpretierte Befehl.
     */
    void completeUpdate(Instruction instruction);

    /**
     * Diese Methode startet die interaktive Ook-Shell.
     * Dazu wird ein Interpreter erstellt, welcher als Eingabeobjekt den InputStream und als Ausgabeobjekt den
     * OutputStream benutzt. Hier sei in aller Deutlichkeit auf die 'buildPipe'-Methoden aus dem Utils-Modul verwiesen.
     * Zuerst soll Folgendes jeweils mit Zeilenumbruch ausgegeben werden:
     * "--------------------------"
     * "  Interactive Ook! Shell  "
     * "--------------------------"
     * Dann soll eine Zeile eingelesen werden.
     * a) Falls diese Zeile "Bananas" gleicht, soll der Interpreter "Yippee!" ausgeben und die nächste Zeile einlesen,
     * denn Affen mögen Bananen.
     * b) Falls diese Zeile "Monkey" gleicht, soll der Interpreter "Ouch!" ausgeben und beendet werden, denn Affen mögen
     * keine anderen Affen.
     * c) Andernfalls soll die Anweisung in eine Instruction umgewandelt werden.
     * Ist dies nicht möglich, soll '"Invalid instruction: "+Anweisung' ausgegeben und die nächste Zeile eingelesen werden.
     * Ist dies möglich, soll
     *  1. Die Instruction in den Interpreter geladen werden.
     *  2. Der Interpreter so oft aktualisiert werden, bis er keine Anweisungen mehr ausführen kann.
     *     Jede Aktualisierung soll mittels 'prepareUpdate' vor und mittels 'completeUpdate' nachbereitet werden.
     */
    void run();
}
