package dos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.objectweb.asm.ClassWriter;

import dos.EXL.Compiler.ASM.Compiler;
import dos.EXL.Filer.Filer;
import dos.EXL.Parser.ProgramParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Errors.LogicError;
import dos.Util.Result;
import dos.Util.Results;

public class App {
    
    public static void main( String[] args ) throws IOException{
        String name = "Hello";
        var StringM = readFile(name);
        if(StringM.hasError()){
            System.out.println("Error reading file");
        }
        String s = StringM.getValue();
        List<Token> tokens = Tokenizer.convertToTokens(s);
        var p = ProgramParser.toClass(tokens);
        if(p.hasError())
            System.out.println("P " + p.getError().toErrorMessage());
        var f = Filer.fill(p.getValue());
        if(f.hasError())
            System.out.println("F " + f.getError().getFullErrorCode());
        Compiler c = new Compiler(f.getValue(), p.getValue());
        ClassWriter cw = c.compile();
        FileOutputStream out = new FileOutputStream("Exl2/src/Main/java/dos/ProgramExamples/out/" + name + ".class");
                        out.write(cw.toByteArray());
                        out.close();
    }

    private static Result<String> readFile(String fileName){
        try {
            File myObj = new File("Exl2/src/Main/java/dos/ProgramExamples/" + fileName+ ".exl");
            Scanner myReader = new Scanner(myObj);
            String data = "";
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
            return Results.makeResult(data);
            } catch (FileNotFoundException e) {
                 e.printStackTrace();
                return Results.makeError(new LogicError("Could not find file " + fileName, 0));
               
            }
        }
}
