package de.uniwue.jpp.ook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Das Interface OokInterpreter beschreibt den Zustand eines Ook!-Interpreters.
 * Es muss implementiert werden.
 *
 * Der Ook!-Interpreter besitzt eine Liste von Anweisungen und einen Zeiger auf die als nächstes auszuführende Anweisung.
 * Zudem wird ein Speicher benötigt. Dieser enthält Zellen, die einen Integer speichern können. Die aktuell betrachtete
 * Zelle wird auch gespeichert.
 *
 * Die Klasse ist veränderlich durch die angegeben Methoden.
 * Durch die loadInstruction/s-Methoden werden weitere Instructions in den Interpreter geladen.
 * Durch die update() Methode wird die aktuelle Anweisung ausgeführt und der Zustand erneuert.
 * Für jede Anweisung gibt es eine Methode, die diese realisiert.
 * Es ist davon auszugehen, dass die Anweisungsmethoden nicht nur durch update() Methode aufgerufen werden, sondern
 * auch von außen (durch die Tests), und somit möglicherweise Anweisungen ausführen, die sich nicht in der
 * Anweisungsliste befinden.
 *
 * Zudem gibt es noch Methoden um den internen Zustand (vor allem zu Testzwecken) abzufragen.
 * Dies Methoden geben nur unveränderliche Objekte zurück, da durch sie nicht der interne Zustand verändert werden darf.
 *
 * Der Ook!-Interpreter arbeitet mit einer Eingabe- und einer Ausgabeschnittstelle, welche bei der Erzeugung des
 * Interpreters definiert wird. Durch diese Schnittstelle interagiert das Ook!-Programm mit dem Benutzer.
 */
public interface Interpreter {

    /**
     * Diese statische Methode erzeugt eine neue Instanz des konkreten Ook!-Interpreters.
     * Der Instanz werden die Eingabe- und Ausgabeschnittstelle übergeben.
     *
     * @param onRead die Schnittstelle zur Ook!-Programm Eingabe.
     * @param onWrite die Schnittstelle zur Ook!-Programm Ausgabe.
     * @return die neu erzeugte Instanz
     * @throws NullPointerException, falls eines der Argumente null ist
     */
    static Interpreter create(Supplier<Integer> onRead, Consumer<Integer> onWrite){
        if (Objects.isNull(onRead) || Objects.isNull(onWrite)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }else{
            InterpreterImpl in = new InterpreterImpl(onRead, onWrite);
            return  in;
        }

    }


    /*
     * Die nachfolgenden Methoden müssen in der konkreten Implementation von diesem Interface implementiert werden.
     */

    /**
     * Es wird eine Anweisung ans Ende der gespeicherten Liste von Anweisungen hinzugefügt.
     * Bei null als Argument soll eine NullPointerException geworfen werden.
     *
     * @param instruction die Anweisung
     */
    void loadInstruction(Instruction instruction);

    /**
     * Es werden die angegeben Anweisungen geordnet ans Ende der gespeicherten Liste von Anweisungen hinzugefügt.
     * Bei null als Argument soll eine NullPointerException geworfen werden.
     *
     * @param instructions
     */
    void loadInstructions(List<Instruction> instructions);

    /**
     * Diese Methode gibt eine für den Betrachter unveränderliche Ansicht auf die vollständige Liste an geladenen
     * Anweisungen zurück. D.h. die Liste kann nicht von außen verändert werden, wird aber durch die
     * loadInstruction/s-Methoden verändert.
     */
    List<Instruction> getInstructions();

    /**
     * Diese Methode gibt eine für den Betrachter unveränderliche Ansicht auf den SPeicher zurück. D.h. die Liste kann
     * nicht von außen verändert werden, wird aber durch die Aktualisierung des Zustands des Interpreters verändert.
     * Zu Beginn enthält die Liste ein Element mit dem Wert 0, welches die einzige Speicherzelle representiert.
     * Durch eine PointerInkrement-Anweisung werden eventuell weitere Speicherzellen hinzugefügt.
     * Die Liste darf also höchstens so lang sein, wie oft PointerInkrement-Anweisungen ausgeführt worden sind.
     */
    List<Integer> getMemory();

    /**
     * Diese Methode gibt die aktuell betrachtete Speicherzelle zurück.
     * Die Position der Speicherzelle kann niemals kleiner 0 sein.
     */
    int getMemoryPointer();

    /**
     * Diese Methode gibt die Position der als nächstes betrachteten Anweisung in der Anweisungsliste zurück.
     * Sie kann niemals kleiner 0 sein oder größer als die Länge der Anweisungsliste plus eins.
     */
    int getInstructionPointer();

    /**
     * Diese Methode gibt true zurück, wenn die letzte Anweisung ausgeführt worden ist.
     */
    boolean reachedEnd();


    /**
     * Diese Methode aktualisiert den Zustand des Interpreters.
     * D.h. es wird die nächste Anweisung ausgeführt und die Anweisungsposition so verändert,
     * dass auf die als nächstes auszuführende Anweisung gezeigt wird.
     * Dies muss nicht zwangsläufig die nachfolgende Anweisung in der Anweisungsliste sein, da es auch Schleifen gibt.
     *
     * Falls das Ende der Anweisungsliste bereits erreicht wurde, soll eine ExecutionException mit der Nachricht
     * "Reached end of instruction list!" geworfen werden.
     * Sollte eine der verwendeten Methoden eine ExecutionException werfen, so soll diese weitergeleitet werden.
     */
    void update();

    /**
     * Der Wert in der gerade betrachteten Speicherzelle wird um eins erhöht.
     * Der Anweisungszeiger wird zudem um eins erhöht.
     */
    void increment();

    /**
     * Der Wert in der gerade betrachteten Speicherzelle wird um eins reduziert.
     * Der Anweisungszeiger wird zudem um eins erhöht.
     */
    void decrement();

    /**
     * Der Zeiger auf die aktuell betrachteten Speicherzelle wird um eins erhöht.
     * Ggf. wird der Speicher um eine Speicherzelle erweitert, wenn der Zeiger sonst ins Leere zeigen würde.
     * Neue Speicherzellen bekommen immer den Standardwert 0.
     * Der Anweisungszeiger wird zudem um eins erhöht.
     */
    void pointerIncrement();

    /**
     * Der Zeiger auf die aktuell betrachtete Speicherzelle wird um eins reduziert.
     * Falls der Zeiger ins Negative zeigen würde, wird stattdessen eine ExecutionException mit der Nachricht
     * "Requested invalid memory address!" geworfen.
     * Der Anweisungszeiger wird zudem um eins erhöht.
     */
    void pointerDecrement();

    /**
     * Es wird ein Integer von der Eingabeschnittstelle gelesen und in die aktuell betrachtete Speicherzelle geschrieben.
     * Der Anweisungszeiger wird zudem um eins erhöht.
     */
    void read();

    /**
     * Es wird der Wert der aktuell betrachteten Speicherzelle in die Ausgabeschnittstelle geschrieben.
     * Der Anweisungszeiger wird zudem um eins erhöht.
     */
    void write();

    /**
     * Diese Anweisung beschreibt den Anfang einer Schleife. Es muss hier nichts getan werden, also wird lediglich der
     * Anweisungszeiger um eins erhöht.
     */
    void loop();

    /**
     * Falls der Wert der aktuell betrachteten Speicherzelle gleich 0 ist, soll lediglich der Anweisungszeiger erhöht
     * werden, da aus der Schleife hinaus gesprungen wird.
     * Sollte jedoch die Speicherzelle einen Wert ungleich 0 enthalten, so wird der Anweisungszeiger auf den
     * Schleifenanfang gesetzt. Dabei soll bei verschachtelten Schleifen nicht der nächste, sondern der dazugehörige
     * Schleifenanfang verwendet werden.
     * Z.B.:
     * Die aktuelle Anweisung ist mit (*) markiert:
     * Ausgangsituation: Loop,Inc,Loop,End,End(*)
     * Richtig: Loop(*),Inc,Loop,End,End
     * Falsch: Loop,Inc,Loop(*),End,End
     *
     * Sollte kein passender Schleifenanfang gefunden werden, so soll eine ExecutionException geworfen werden, die den
     * Text "Closing loop without beginning!" enthält. Die Position des Anweisungszeigers in diesem Fall ist nicht
     * vorgegeben.
     */
    void end();
}
