package de.uniwue.jpp.ook;

import java.io.IOException;
import java.nio.file.Path;

public interface Executor {

    static Executor create(Ook handle){
        ExcecutorImpl ex = new ExcecutorImpl(handle);
        return ex;
    }

    /**
     * Diese Methbis zum Ende aus.
     * Falls die Read-Anweisung vorkommt, soll aus der input-Datei gelesen werdeode ließt das Programm aus dem ersten Pfad ein und führt es n.
     * Falls die Write-Anweisung vorkommt, soll in die output-Datei geschrieben werden.
     *      * Sollte eine der verwendeten Methoden eine ExecutionException werfen, so soll diese weitergeleitet werden.
     *
     * @param program
     * @param input
     * @param output
     * @throws IOException
     * @throws NullPointerException falls das Argument null ist.
     */
    void execute(Path program, Path input, Path output) throws IOException;

    /**
     * Macht das selbe wie execute(Path program, Path input, Path output).
     * Die Pfade sind in einem Array aus drei Strings in der selben Ordnung angegeben.
     *
     * @param args
     * @throws IOException
     * @throws NullPointerException falls das Argument null ist.
     */
    void execute(String[] args) throws IOException;

}
