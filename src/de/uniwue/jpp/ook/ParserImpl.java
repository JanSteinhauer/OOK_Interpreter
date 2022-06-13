package de.uniwue.jpp.ook;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserImpl implements Parser{
    public Ook handle;


    public ParserImpl(Ook handle){
        this.handle = handle;
    }

    public static void main(String[] args) {
        Ook k = null;
        ParserImpl pi = new ParserImpl(k);
        pi.parse("Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook. Ook. Ook! Ook? Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook? Ook! Ook! Ook? Ook! Ook? Ook.\n" +
                "Ook! Ook. Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook! Ook? Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook?\n" +
                "Ook! Ook! Ook? Ook! Ook? Ook. Ook. Ook. Ook! Ook. Ook. Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook. Ook! Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook! Ook. Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook? Ook? Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook? Ook! Ook! Ook? Ook! Ook? Ook. Ook! Ook.\n" +
                "Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook? Ook? Ook. Ook. Ook.\n" +
                "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook. Ook? Ook! Ook! Ook? Ook! Ook? Ook. Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook.\n" +
                "Ook? Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook! Ook. Ook. Ook. Ook. Ook. Ook. Ook.\n" +
                "Ook! Ook. Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook.\n" +
                "Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook!\n" +
                "Ook! Ook. Ook. Ook? Ook. Ook? Ook. Ook. Ook! Ook.");
    }


    @Override
    public List<Instruction> parse(String program) {
        List<Instruction> myList = new ArrayList<Instruction>();
        //String[] programarray = program.split(("ook\\.|ook\\?|ook!| "));
        String[] programarrayToken = program.split((" "));
        if (isValid(program)){
            String ohneLeerzeichen = program.replace("\s", "");
            Pattern pattern = Pattern.compile("[\\s]");
            Matcher matcher = pattern.matcher(program);
            String Stringfertig = matcher.replaceAll("");
            /*Ook handle;
            Util util = handle.createUtil();*/

            List<String> strings = new ArrayList<String>();
            //List<String> jeder8te = usingSplitMethod(ohneLeerzeichen,8);
            int index = 0;
            while (index < Stringfertig.length()) {
                strings.add(Stringfertig.substring(index, Math.min(index + 8,Stringfertig.length())));
                index += 8;
            }
            strings.remove(strings.size()-1);
            strings.add(Stringfertig.substring(Stringfertig.length()-8, Stringfertig.length()));


            for (int i = 0; i < strings.size(); i++) {//hier wird sich jeder Token angeschaut und übersetzt
                switch (strings.get(i)){
                    case "Ook.Ook.": myList.add(i, Instruction.Inc); break;
                    case "Ook!Ook!": myList.add(i, Instruction.Dec); break;
                    case "Ook.Ook?": myList.add(i, Instruction.PtrInc); break;
                    case "Ook?Ook.": myList.add(i, Instruction.PtrDec); break;
                    case "Ook!Ook?": myList.add(i, Instruction.Loop); break;
                    case "Ook?Ook!": myList.add(i, Instruction.End); break;
                    case "Ook!Ook.": myList.add(i, Instruction.Write); break;
                    case "Ook.Ook!": myList.add(i, Instruction.Read); break;
                    default: throw new IllegalArgumentException("Program is not valid!");
                }
            }
            return myList;
        } else{
            throw new IllegalArgumentException("Program is not valid!");

        }

    }
    public  List<String> usingSplitMethod(String text, int n) {
        String[] results = text.split("(?<=\\G.{" + n + "})");

        return Arrays.asList(results);
    }

    @Override
    public boolean isValid(String program) {
        if(Objects.isNull(program)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }else{
            String ohneLeerzeichen = program.replace("\s", "");
            Pattern pattern = Pattern.compile("[\\s]");
            Matcher matcher = pattern.matcher(program);
            String Stringfertig = matcher.replaceAll("");

            List<String> strings = new ArrayList<String>();
            //List<String> jeder8te = usingSplitMethod(ohneLeerzeichen,8);
            int index = 0;
            while (index < Stringfertig.length()) {
                strings.add(Stringfertig.substring(index, Math.min(index + 8,Stringfertig.length())));
                index += 8;
            }
            strings.remove(strings.size()-1);
            strings.add(Stringfertig.substring(Stringfertig.length()-8, Stringfertig.length()));
            /*StringBuilder sb = new StringBuilder();
            sb.append(strings);
            sb.delete(strings.size()-1,strings.size());

            sb.append(program.substring(program.length()-8, program.length()));*/
            List<Symbol> alleSymbole = new ArrayList<>();
            for (int i = 0; i < strings.size(); i++) {
                switch (strings.get(i)){
                    case "Ook.Ook.": break;
                    case "Ook!Ook!": break;
                    case "Ook.Ook?": break;
                    case "Ook?Ook.": break;
                    case "Ook!Ook?": break;
                    case "Ook?Ook!": break;
                    case "Ook!Ook.": break;
                    case "Ook.Ook!": break;
                    default:
                        return false;

                }

            }

            return true;
        }

    }

    @Override
    public List<Symbol> asSymbols(String program) {
        if(Objects.isNull(program)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }else{
            String ohneLeerzeichen = program.replace("\s", "");
            Pattern pattern = Pattern.compile("[\\s]");
            Matcher matcher = pattern.matcher(program);
            String Stringfertig = matcher.replaceAll("");

            List<String> strings = new ArrayList<String>();
            //List<String> jeder8te = usingSplitMethod(ohneLeerzeichen,8);
            int index = 0;
            while (index < Stringfertig.length()) {
                strings.add(Stringfertig.substring(index, Math.min(index + 4,Stringfertig.length())));
                index += 4;
            }
            strings.remove(strings.size()-1);
            strings.add(Stringfertig.substring(Stringfertig.length()-4, Stringfertig.length()));
            List<Symbol> alleSymbole = new ArrayList<>();
            for (int i = 0; i < strings.size(); i++) {
                switch (strings.get(i)){
                    case "Ook?": alleSymbole.add(Symbol.Question); break;
                    case "Ook!": alleSymbole.add(Symbol.Exclamation); break;
                    case "Ook.": alleSymbole.add(Symbol.Point); break;
                    case "OOk?": alleSymbole.add(Symbol.Question); break;
                    case "OOk!": alleSymbole.add(Symbol.Exclamation); break;
                    case "OOk.": alleSymbole.add(Symbol.Point); break;
                    default: throw new IllegalArgumentException("Program is not valid!");
                }
            }

               /* String[] programarrayToken = program.split((" "));
                List<String> einzelBefehle = new ArrayList<>();

                *//*for (int i = 0; i < programarrayToken.length; i++) {// der zweier Befehl wird zu einen gemacht z.B. ook!okk. -> ook! + ook.
                    String[] results = programarrayToken[i].split("(?<=\\G.{" + 4 + "})");
                    for (int j = 0; j < results.length; j++) {
                        einzelBefehle.add(results[i]);
                    }
                }
                for (int i = 0; i < einzelBefehle.size(); i++) {
                    switch (einzelBefehle.get(i)){
                        case "Ook? ": alleSymbole.add(Symbol.Question); break;
                        case "Ook! ": alleSymbole.add(Symbol.Exclamation); break;
                        case "Ook. ": alleSymbole.add(Symbol.Point); break;
                        default: throw new IllegalArgumentException("Program is not valid!");
                    }

                }*//*
                for (int i = 0; i < programarrayToken.length; i++) {
                    switch (programarrayToken[i]){
                        case "Ook?": alleSymbole.add(Symbol.Question); break;
                        case "Ook!": alleSymbole.add(Symbol.Exclamation); break;
                        case "Ook.": alleSymbole.add(Symbol.Point); break;
                        default: throw new IllegalArgumentException("Program is not valid!");
                    }
                }*/
                return alleSymbole;
            /*}catch (Exception e){
                throw new IllegalArgumentException("Program is not valid!");
            }*/
        }
    }
    @Override
    public List<Instruction> asInstructions(List<Symbol> symbols) {
        if(Objects.isNull(symbols)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }
        else {
           /* if (symbols.size() % 2 == 0){
                throw new IllegalArgumentException("List of symbols is malformed!");
            } else{
                Util util = handle.createUtil();

                List<Instruction> myList = new ArrayList<Instruction>();
                for (int i = 0; i < symbols.size(); i = i + 2) {
                    myList.add(util.forSymbols(symbols.get(i), symbols.get(i + 1)));
                }
                System.out.println("Hello");
                return myList;
            }*/
            if (symbols.size() % 2 != 0){
                throw new IllegalArgumentException("List of symbols is malformed!");
            }
                Util util = handle.createUtil();

                List<Instruction> myList = new ArrayList<Instruction>();
                for (int i = 0; i < symbols.size(); i = i + 2) {
                    if((symbols.get(i) == Symbol.Question && symbols.get(i + 1) == Symbol.Question)){
                        throw new IllegalArgumentException("No instruction is for symbol Question and Question present!");
                    }
                    myList.add(util.forSymbols(symbols.get(i), symbols.get(i + 1)));
                }

                return myList;
                

            
        }
    }
}
