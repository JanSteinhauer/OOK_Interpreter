package de.uniwue.jpp.ook;

import java.io.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.io.IOException;

public class UtilImpl implements Util{

    @Override
    public Instruction forSymbols(Symbol first, Symbol last) {
        if(Objects.isNull(first) || Objects.isNull(last)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }
        try {
            if(first == Symbol.Point && last == Symbol.Point){
                return Instruction.Inc;
            }
            if(first == Symbol.Exclamation && last == Symbol.Exclamation){
                return Instruction.Dec;
            }
            else if(first == Symbol.Point && last == Symbol.Question){
                return Instruction.PtrInc;
            }
            else if(first == Symbol.Question && last == Symbol.Point){
                return Instruction.PtrDec;
            }
            else if(first == Symbol.Exclamation && last == Symbol.Question){
                return Instruction.Loop;
            }
            else if(first == Symbol.Question && last == Symbol.Exclamation){
                return Instruction.End;
            }
            else if(first == Symbol.Exclamation && last == Symbol.Point){
                return Instruction.Write;
            }else if(first == Symbol.Point && last == Symbol.Exclamation){
                return Instruction.Read;
            }
            else{
                throw new IllegalArgumentException("No instruction is for symbol "+first+" and "+last+" present!");
            }
        }
        // wenn es eine Illegal ArguemntException ist
        catch(Exception e){
            throw new IllegalArgumentException("No instruction is for symbol "+first+" and "+last+" present!");
        }
    }

    @Override
    public Symbol forToken(String token) {
        if(Objects.isNull(token)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }
        try {
            if(token.equals("Ook?")){
                return Symbol.Question;
            }
            if(token.equals("Ook!")){
                return Symbol.Exclamation;
            }
            if(token.equals("Ook.")){
                return Symbol.Point;
            }
            else{
                throw new IllegalArgumentException("No symbol is for token "+token+" present!");
            }
        }// wenn es eine Illegal ArguemntException ist
        catch(Exception e){
            throw new IllegalArgumentException("No symbol is for token "+token+" present!");
        }
    }

    @Override
    public Supplier<Integer> buildPipe(InputStream input) {
        if(Objects.isNull(input)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }
        else {
            try {

                Supplier<Integer> read = new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        try {
                            return input.read();

                        }catch (IOException e){
                            e.printStackTrace();
                            throw new IllegalArgumentException("this is an Illigal shit");
                        }

                    }
                };
                return read;
            }catch (Exception e){
                throw new IllegalArgumentException("Fehler aufgetreten beim Versuch InputStream in String umzuwandeln / diesen in einen Integer umzuwandeln.");
            }
        }
    }

    @Override
    public Consumer<Integer> buildPipe(OutputStream output) {
        if(Objects.isNull(output)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }
        else {
            try {

                Consumer<Integer> cons = new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        try {
                            output.write(integer);

                        }catch (IOException e){
                            e.printStackTrace();
                            throw new IllegalArgumentException("this is an Illigal shit");
                        }
                    }
                };


                return cons;

            }catch (Exception e){
                throw new IllegalArgumentException("Fehler aufgetreten beim Versuch Outputstream in String umzuwandeln / diesen in einen Integer umzuwandeln.");
            }
        }
    }

    @Override
    public String getInstructionCode(Instruction instruction) {
        if(Objects.isNull(instruction)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }
        else {
           switch (instruction){
               case Inc: return "Ook.Ook."; // hier kann break weggelassen werden, da nach return eh nichtsmehr ausgeführt wird
               case Dec: return "Ook!Ook!";
               case PtrInc: return "Ook.Ook?";
               case PtrDec: return "Ook?Ook.";
               case Loop: return "Ook!Ook?";
               case End: return "Ook?Ook!";
               case Write: return "Ook!Ook.";
               case Read: return "Ook.Ook!";
               default: throw new IllegalArgumentException("Keine gültige Instruktion für die Klasse. Instruction: "+instruction);

           }
        }

    }

    @Override
    public Optional<Instruction> getInstruction(String code) {
        if(Objects.isNull(code)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }
        else {
            Optional<Instruction> inst;
            switch (code){
                case "Ook.Ook.": return inst = Optional.of(Instruction.Inc);
                case "Ook!Ook!": return inst = Optional.of(Instruction.Dec);
                case "Ook.Ook?": return inst = Optional.of(Instruction.PtrInc);
                case "Ook?Ook.": return inst = Optional.of(Instruction.PtrDec);
                case "Ook!Ook?": return inst = Optional.of(Instruction.Loop);
                case "Ook?Ook!": return inst = Optional.of(Instruction.End);
                case "Ook!Ook.": return inst = Optional.of(Instruction.Write);
                case "Ook.Ook!": return inst = Optional.of(Instruction.Read);


                default: return Optional.empty(); // wenn es leer ist
                    //throw new IllegalArgumentException("Keine gültiger String für die Klasse getInstruction. Code: "+code);

            }
        }
    }
}
