package de.uniwue.jpp.ook;

import java.util.List;

/**
 * Diese Klasse validiert ein Programm im Textformat und konvertiert es erst in Symbole und dann in Anweisungen.
 * Tipp: Zur Implementation werden Methoden aus dem Util-Interface benötigt.
 * Deshalb wird zur Erzeugung ein Ook-Objekt mit namen "handle" übergeben. Durch Aufruf der Methode
 * handle.createUtil() kann das benötigte Util-Objekt erzeugt werden.
 */
public interface Parser {

    static Parser create(Ook handle) {
        ParserImpl pars = new ParserImpl(handle);
        return pars;
    }

    /**
     * Diese Miethode soll das übergebene Programm auf seine Validität überprüfen und in ene Liste von Anweisungen
     * übersetzen. Dazu sollten die weiteren Methoden dieser Klasse genutzt werden. Im Fehlerfall sollen die
     * Exceptions der Hilfsmethoden weitergeleitet werden.
     *
     * @param program das Programm als String
     * @return die Liste an Anweisungen
     */
    List<Instruction> parse(String program);

    /**
     * Diese Methode überprüft, ob das Programm gültig ist. Dies trifft unter den folgenden Umständen zu:
     * - Das Programm enthält außer den Tokens und Whitespaces keine weiteren Zeichen.
     * - Die Token sind in solcher Anzahl vorhanden, dass zwei Token immer eine Anweisung erzeugen können.
     * - Diese zwei Token sind eine gültige Kombination, sodass keine Tokenkombination vorhanden ist,
     *   für die es keine Anweisung gibt.
     *
     * @param program
     * @return true, wenn das Programm gültig ist, sonst false
     * @throws NullPointerException wenn das Argument null ist.
     */
    boolean isValid(String program);

    /**
     * Diese Methode konvertiert das Programm in eine Liste aus Symbolen.
     * Ein Symbol besteht immer aus einem Token.
     *
     * @throws NullPointerException wenn das Argument null ist.
     * @throws IllegalArgumentException mit der Nachricht "Program is not valid!", wenn aus dem Programmtext keine
     * Symbolliste erstellt werden kann, weil das Programm ungültig ist.
     */
    List<Symbol> asSymbols(String program);

    /**
     * Diese Methode konvertiert die Liste aus Symbolen in eine Liste aus Anweisungen.
     * Eine Anweisung besteht immer aus zwei Symbolen.
     * Falls ein Symbolpaar keiner Instruktion entspricht, soll eine IllegalArgumentException mit der gleichen Nachricht
     * wie bei Util.asInstruction geworfen werden.
     * Falls Symbol ohne Partnersymbol existiert, soll eine IllegalArgumentException mit der Nachricht
     * "List of symbols is malformed!" geworfen werden.
     *
     * @throws NullPointerException wenn das Argument null ist.
     * @throws IllegalArgumentException wenn aus den Symbolen keine passende Anweisung gebaut werden kann
     */
    List<Instruction> asInstructions(List<Symbol> symbols);

}
