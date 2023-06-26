package dos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.objectweb.asm.ClassWriter;

import dos.EXL.Compiler.ASM.Compiler;
import dos.EXL.Filer.Filer;
import dos.EXL.Parser.ProgramParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Tokenizer.Types.Token;

public class App {
    
    public static void main( String[] args ) throws IOException{
        String s = "public class Test { public Test(int a){print 10;} private static int notMain(int a){print 10;}}";
        List<Token> tokens = Tokenizer.convertToTokens(s);
        var p = ProgramParser.toClass(tokens);
        if(p.hasError())
            System.out.println("P " + p.getError().toErrorMessage());
        var f = Filer.fill(p.getValue());
        if(f.hasError())
            System.out.println("F " + f.getError().getFullErrorCode());
        Compiler c = new Compiler(f.getValue(), p.getValue());
        ClassWriter cw = c.compile();
        FileOutputStream out = new FileOutputStream("Test.class");
                        out.write(cw.toByteArray());
                        out.close();
    }
}
