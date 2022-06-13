package de.uniwue.jpp.ook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExcecutorImpl implements Executor{
    Ook handle;

    public ExcecutorImpl(Ook handle){
        this.handle = handle;
    }

/*
    @Override
    public void execute(Path program, Path input, Path output) throws IOException {
        if (Objects.isNull(program)|| Objects.isNull(input)||Objects.isNull(output)){
            throw new NullPointerException();
        }else {
            String strstd = String.valueOf(program);
            String strinpu= String.valueOf(input);
            String strout = String.valueOf(output);

            InputStream inputS = new ByteArrayInputStream( strinpu.getBytes(StandardCharsets.UTF_8) );
            OutputStream outputS = new FileOutputStream(strout);

            Supplier<Integer> sup = handle.createUtil().buildPipe(inputS);
            Consumer<Integer> cons = handle.createUtil().buildPipe(outputS);
            InterpreterImpl interp = new InterpreterImpl(sup, cons); //hier noch einen supplier und consumer reinkriegen
            try{
                ParserImpl par = new ParserImpl(handle);
                List<Instruction> instrucList = new ArrayList<>();
                if(par.isValid(String.valueOf(program))){
                    instrucList.addAll(par.parse(strstd)); //hier werden der erste String in eine Liste von Instructions umgewandelt

                    for (int i = 0; i < instrucList.size(); i++) {
                        if(instrucList.get(i) == Instruction.Read){

                            interp.read();
                        } else if (instrucList.get(i) == Instruction.Write){
                            interp.write();

                        }else {
                            interp.loadInstruction(instrucList.get(i));
                        }
                    }
                }else{
                    interp.update();
                }
            }catch (Exception e){
                interp.update();

            }


        }
    }
*/

    @Override
    public void execute(Path program, Path input, Path output) throws IOException {
        if (Objects.isNull(program)|| Objects.isNull(input)||Objects.isNull(output)){
            throw new NullPointerException();
        }else {
            String strstd = Files.readString(program);
            String strinpu= String.valueOf(input);
            String strout = String.valueOf(output);

            InputStream inputS = new FileInputStream(strinpu);
            OutputStream outputS = new FileOutputStream(strout);

            Supplier<Integer> sup = handle.createUtil().buildPipe(inputS);
            Consumer<Integer> cons = handle.createUtil().buildPipe(outputS);
            InterpreterImpl interp = new InterpreterImpl(sup, cons); //hier noch einen supplier und consumer reinkriegen
            ParserImpl par = new ParserImpl(handle);
            List<Instruction> instrucList = new ArrayList<>();
            instrucList.addAll(par.parse(strstd)); //hier werden der erste String in eine Liste von Instructions umgewandelt

            for (int i = 0; i < instrucList.size(); i++) {
                interp.loadInstruction(instrucList.get(i));
                interp.update();
            }
        }
    }

    @Override
    public void execute(String[] args) throws IOException {
        if (Objects.isNull(args)){
            throw new NullPointerException();
            // wenn eines der Objects = null ist
        }else {
            Path path = Paths.get(args[0]);
            Path pathinput = Paths.get(args[1]);
            Path pathoutput = Paths.get(args[2]);

            execute(path, pathinput, pathoutput);

            /*StringBuilder sb = new StringBuilder();

            String standartString = Files.readString(Paths.get(args[0]), StandardCharsets.US_ASCII);
            String inputString = Files.readString(Paths.get(args[1]), StandardCharsets.US_ASCII);
            String outputString = Files.readString(Paths.get(args[2]), StandardCharsets.US_ASCII);

            sb.append(standartString);
            sb.append("lol");
            sb.append(inputString);
            sb.append("lol");
            sb.append(outputString);*/
            /*try {
                StringBuilder sb = new StringBuilder();
                StringBuilder sd = new StringBuilder();

                String standartmittxt = args[0].substring(0, args[0].length()-3);
                sd.append(standartmittxt);
                sd.append("txt");

                Path path = Paths.get(sd.toString());
                Path pathinput = Paths.get(args[1]);
                Path pathoutput = Paths.get(args[2]);
              *//*  Files.readString(program, StandardCharsets.US_ASCII);
                String pathstrstd = new String(Files.readAllBytes(path));
                String pathstrinput = new String(Files.readAllBytes(pathinput));
                String pathstroutput = new String(Files.readAllBytes(pathoutput));*//*
                String pathstrstd = Files.readString(path, StandardCharsets.US_ASCII);
                String pathstrinput = Files.readString(pathinput, StandardCharsets.US_ASCII);
                String pathstroutput = Files.readString(pathoutput, StandardCharsets.US_ASCII);

                sb.append(pathstrstd);
                sb.append("lol");
                sb.append(pathstrinput);
                sb.append("lol");
                sb.append(pathstroutput);



                if ( 5 == 5){
                    throw new IllegalArgumentException(sb.toString());
                }

            }catch (Exception e){
                StringBuilder sd = new StringBuilder();

                String standartmittxt = args[0].substring(0, args[0].length()-3);
                sd.append(standartmittxt);
                sd.append("txt");
                throw new IllegalArgumentException("bis hier in versagt" + Arrays.toString(args) + "lol" + sd.toString());
            }

            try {

                Path path = Paths.get(args[0]);
                Path pathinput = Paths.get(args[1]);
                Path pathoutput = Paths.get(args[2]);

                String pathstrstd = new String(Files.readAllBytes(path));
                String pathstrinput = new String(Files.readAllBytes(pathinput));
                String pathstroutput = new String(Files.readAllBytes(pathoutput));




                InputStream input = new ByteArrayInputStream( pathstrinput.getBytes() );
                OutputStream output = new FileOutputStream(pathstroutput);

                Supplier<Integer> sup = handle.createUtil().buildPipe(input);
                Consumer<Integer> cons = handle.createUtil().buildPipe(output);
                InterpreterImpl interp = new InterpreterImpl(sup, cons); //hier noch einen supplier und consumer reinkriegen
                ParserImpl par = new ParserImpl(handle);
                List<Instruction> instrucList = new ArrayList<>();
                instrucList.addAll(par.parse(pathstrstd)); //hier werden der erste String in eine Liste von Instructions umgewandelt
                *//*List<Instruction> readList = new ArrayList<>();
                readList.addAll(par.parse(args[1]));
                List<Instruction> writeList = new ArrayList<>();
                writeList.addAll(par.parse(args[2]));*//*
                for (int i = 0; i < instrucList.size(); i++) {
                    if(instrucList.get(i) == Instruction.Read){

                        interp.read();
                    } else if (instrucList.get(i) == Instruction.Write){
                        interp.write();

                    }else {
                        interp.loadInstruction(instrucList.get(i));
                    }
                }
            }catch (Exception e){
                throw new IllegalArgumentException("Problem mit der execute Formel" );
            }*/
        }
    }
}
