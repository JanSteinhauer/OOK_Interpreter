package de.uniwue.jpp.ook;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Das Interface Util stellt nützliche Werkzeuge zur Verfügung, die benötigt werden, um die Hauptklassen zu
 * implementieren. Dieses Interface muss implementiert werden.
 */
public interface Util {

    static Util create() {
        UtilImpl util = new UtilImpl();
        return  util;
    }

    /**
     * @param first das vordere Symbol einer Anweisung
     * @param last das hintere Symbol einer Anweisung
     * @return die durch die Symbole definierte Anweisung
     * @throws NullPointerException falls mindestens eines der übergebenen Objekte null ist.
     * @throws IllegalArgumentException mit dem text "No instruction is for symbol "+first+" and "+last+" present!",
     * falls es zu den Symbolen keine passende Anweisung geben sollte.
     */
    Instruction forSymbols(Symbol first, Symbol last);

    /**
     *
     * @param token ein Text der ein Symbol beschreibt.
     * @return das zum Text passende Symbol
     * @throws NullPointerException falls das Argument null ist.
     * @throws IllegalArgumentException mit dem Text "No symbol is for token "+token+" present!",
     * falls es zu dem Text kein passendes Symbol gibt.
     */
     Symbol forToken(String token);


    /**
     * Diese Methode erzeugt ein Erzeuger-Objekt, welches bei der Ausführung ein Zeichen von dem Eingabefluss ließt
     * und zurück gibt. Vergleiche dazu die Java-Dokumentation.
     *
     * @param input der Eingabefluss
     * @return das Erzeuger-Objekt
     * @throws NullPointerException falls das Argument null ist.
     */
    Supplier<Integer> buildPipe(InputStream input);

    /**
     * Diese Methode erzeugt ein Verbraucher-Objekt, welches bei der Ausführung das Zeichen auf den Ausgabefluss schreibt.
     *
     * @param output der Ausgabefluss
     * @return das Erzeugerobjekt
     * @throws NullPointerException falls das Argument null ist.
     */
    Consumer<Integer> buildPipe(OutputStream output);

    /**
     *
     * @param instruction
     * @return Die Anweisung in der Form "Ook.Ook!", d.h. ohne Leerzeichen.
     * @throws NullPointerException falls das Argument null ist.
     */
    String getInstructionCode(Instruction instruction);

    /**
     *
     * @param code eine Anweisung der Form "Ook.Ook!", d.h. ohne Leerzeichen.
     * @return Ein Optional, welches für eine gültige Anweisung diese enthält und sonst leer ist.
     * @throws NullPointerException falls das Argument null ist.
     */
    Optional<Instruction> getInstruction(String code);
}
