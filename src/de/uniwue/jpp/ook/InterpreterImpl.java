package de.uniwue.jpp.ook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InterpreterImpl implements Interpreter{
    List<Instruction> hauptInstruction = new ArrayList<>();
    int Memory = 0;
    int Anweisungszeiger = 0;
    List<Integer> zellen = new ArrayList<>(); //TODO das ist der Zellspeicher, der in dem Interface gementioned wird
    //TODO gibt es einen Unterschied zw instruction Pointer und Memory pointer

    Supplier<Integer> onRead;
    Consumer<Integer> onWrite;

    public InterpreterImpl(){}

    public InterpreterImpl(Supplier<Integer> onRead, Consumer<Integer> onWrite){
        this.onRead = onRead;
        this.onWrite = onWrite;
        zellen.add(0);
    }


    @Override
    public void loadInstruction(Instruction instruction) {
        if (Objects.isNull(instruction)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }else {
            hauptInstruction.add(instruction);
        }
    }

    @Override
    public void loadInstructions(List<Instruction> instructions) {
        if (Objects.isNull(instructions)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }else {
            hauptInstruction.addAll(instructions);
        }
    }

    @Override
    public List<Instruction> getInstructions() {
        return Collections.unmodifiableList(hauptInstruction);
    }

    @Override
    public List<Integer> getMemory() {
        if (zellen.size() == 0){
            zellen.add(0);
            return Collections.unmodifiableList(zellen);
        }else{
            return Collections.unmodifiableList(zellen);
        }
    }
    @Override
    public int getMemoryPointer() {
        if (Memory < 0){
            throw new IllegalArgumentException("getMemoryPointer kann nicht kleiner 0 sein");
        }else{
            return Memory;
        }
    }

    @Override
    public int getInstructionPointer() {
        /*if (Anweisungszeiger < 0){
            throw new IllegalArgumentException("getInstructionPointer kann nicht kleiner 0 sein");
        }else if(Anweisungszeiger > hauptInstruction.size()){
            throw new IllegalArgumentException("getInstructionPointer kann nicht größer als Länge der List Hauptinstruktions sein");
        }else{
            return Anweisungszeiger;
        }*/
        return Anweisungszeiger;
    }

    @Override
    public boolean reachedEnd() {
        if(Anweisungszeiger >= hauptInstruction.size()){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void update() {
       if (reachedEnd()){
           throw new ExecutionException("Reached end of instruction list!");
       }else{
           switch (hauptInstruction.get(Anweisungszeiger)){
               case Inc: increment(); break;
               case Dec: decrement(); break;
               case PtrInc: pointerIncrement(); break;
               case PtrDec: pointerDecrement(); break;
               case Loop: loop(); break;
               case End: end(); break;
               case Write: write(); break;
               case Read: read(); break;
               default: throw new ExecutionException("Reached end of instruction list!");
           }
       }
    }

    @Override
    public void increment() {
        zellen.set(Memory, zellen.get(Memory) + 1);
        Anweisungszeiger += 1;
    }

    @Override
    public void decrement() {
        //Todo  wenn Zeiger kleiner 0 dann hinten anfangen
        /*if(zellen.size() == 1){
            Memory = 0;
        }*/

         /*if (Memory == 0){
            Memory = zellen.size();
            zellen.set(Memory, zellen.get(Memory) - 1);
        }else{
            zellen.set(Memory, zellen.get(Memory) - 1);
        }*/
        zellen.set(Memory, zellen.get(Memory) - 1);

        Anweisungszeiger += 1;
    }

    @Override
    public void pointerIncrement() {
        Memory += 1;
        Anweisungszeiger += 1;
        if (zellen.size() <= Memory){
            zellen.add(0);
        }
    }

    @Override
    public void pointerDecrement() {
        if(Memory -1 < 0){
            throw new ExecutionException("Requested invalid memory address!");
        }
        else{
            Memory = Memory - 1;
        }
        Anweisungszeiger += 1;
    }

    @Override
    public void read() {
        Anweisungszeiger += 1;
        //read ist der supplier
        Integer read = onRead.get(); // wird von Eingabeschnittstelle gelesen
        zellen.add(Memory, read); //in die aktuell betrachtete Speicherzelle geschrieben
        // Es wird ein Integer von der Eingabeschnittstelle gelesen und in die aktuell betrachtete Speicherzelle geschrieben.
        //     * Der Anweisungszeiger wird zudem um eins erhöht.

    }

    @Override
    public void write() {
        Anweisungszeiger += 1;
        onWrite.accept(zellen.get(Memory));
    }

    @Override
    public void loop() {
        Anweisungszeiger += 1;
    }

    @Override
    public void end() {
        if (zellen.get(Memory) == 0){
            Anweisungszeiger += 1;
        }else{
            int wievieleWerteZurück = 0; //diese var keeps track of wie viele Stellen die memory nach hinten verschoben werden muss
            for (int i = Anweisungszeiger -1; i >= 0; i--) { // mal schauen, hier ist bei pabs die Zahl 1 zu hoch
                if(hauptInstruction.get(i) == Instruction.Loop) {
                    wievieleWerteZurück += 1;

                    Anweisungszeiger = Anweisungszeiger-wievieleWerteZurück;

                }
                else if((hauptInstruction.get(i) == Instruction.End)){
                    wievieleWerteZurück -=1;
                }
                if(wievieleWerteZurück == 1){
                    // ein Loop mehr als n gefunden
                    Anweisungszeiger = i;
                    break;
                }
                else if (i == 0){

                    throw new ExecutionException("Closing loop without beginning!");
                }
            }
        }
    }
}
