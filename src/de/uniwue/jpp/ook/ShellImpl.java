package de.uniwue.jpp.ook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ShellImpl implements Shell{
    private Ook handle;
    private InputStream input;
    private OutputStream output;
    PrintStream ps;


    public ShellImpl(Ook handle, InputStream input, OutputStream output){
        this.handle = handle;
        this.input = input;
        this.output = output;
         ps = new PrintStream(output);
    }


    @Override
    public Optional<Instruction> parseLine(String line) {
        if(Objects.isNull(line)){
            throw new NullPointerException();
        }else{
            try{
                Util util = handle.createUtil();
                return util.getInstruction(line);
            }catch (Exception e){
                throw new IllegalArgumentException("bei parseLine ist ein Fehler aufgetreten");
            }

        }
    }

    @Override
    public void prepareUpdate(Instruction instruction) {
        switch (instruction){
            case Write:
                ps.print("Writing: "); break;
            case Read:
                ps.print("Reading: "); break;
            default:
                ps.println(instruction); //TODO hier was verändert für Prepare Update

        }
    }

    @Override
    public void completeUpdate(Instruction instruction) {


        switch (instruction){
            case Write:
                ps.println(); break;
            case Read:
                try {
                    Character x = (char) input.read();
                    if (x != '\n'){
                        ps.println("Illegal Input: Insert only one character!");
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

        }
    }

    @Override
    public void run() {

        Util util = handle.createUtil();
        Supplier<Integer> sup = util.buildPipe(input);
        Consumer<Integer> cons = util.buildPipe(output);
        InterpreterImpl interpret = new InterpreterImpl(sup, cons); // Dazu wird ein Interpreter erstellt, welcher als Eingabeobjekt den InputStream und als Ausgabeobjekt den

        ps.println("--------------------------");
        ps.println("  Interactive Ook! Shell  ");
        ps.println("--------------------------");
        Scanner sc = new Scanner(input);

        while(sc.hasNextLine()){
             String befehl = sc.nextLine();
            if(befehl.equals("Bananas")){
                ps.println("Yippee!");//die nächste Zeile einlesen,* denn Affen mögen Bananen
            }else if(befehl.equals("Monkey")){
                ps.println("Ouch!");
                break;
            }else{
                try {
                    interpret.loadInstruction(util.forSymbols(util.forToken(befehl.substring(0,3)), util.forToken(befehl.substring(4,7))));
                    prepareUpdate(util.forSymbols(util.forToken(befehl.substring(0,3)), util.forToken(befehl.substring(4,7))));
                    interpret.update();
                    completeUpdate(util.forSymbols(util.forToken(befehl.substring(0,3)), util.forToken(befehl.substring(4,7))));
                }catch (IllegalArgumentException e){
                    ps.print("Invalid instruction: "+ befehl);
                }
            }
        }
    }

}
