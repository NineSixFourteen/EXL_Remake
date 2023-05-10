package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import dos.Parser.Builders.CodeBlockBuilder;
import dos.Parser.Builders.FunctionBuilder;
import dos.Parser.Builders.ProgramBuilder;
import dos.Tokenizer.Tokenizer;
import dos.Tokenizer.Types.Token;
import dos.Types.Program;
import dos.Types.Tag;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Program Parser Tests
public class PPTest extends TestCase{
    
    public static Test suite(){
        return new TestSuite(PPTest.class);
    }

    private static void assertEq(String msg, Program prog) {
        var e = ProgramParser.toClass(Tokenizer.convertToTokens(msg));
        if(e.hasError()){
            System.out.println(e.getError().getMessage());
            assertTrue(false);
        }
        if(!prog.makeString().equals(e.getValue().makeString())){
            System.out.println(prog.makeString());
            System.out.println(e.getValue().makeString());
        }
        assertTrue(prog.makeString().equals(e.getValue().makeString()));
    }

    public static void testFunctions(){ 
        assertEq(
            "public class Test { public static boolean Show = true; private static int notMain(int a){ return 10;}}",
            new ProgramBuilder()
                .addTag(Tag.Public)
                .setName("Test")
                .addField("Show", List.of(Tag.Public, Tag.Static), new VarExpr("true"), "boolean")
                .addFunction(
                    new FunctionBuilder()
                        .addTag(Tag.Private)
                        .addTag(Tag.Static)
                        .addParameter("int", "a")
                        .setName("notMain")
                        .setType("int")
                        .setBody(
                            new CodeBlockBuilder()
                            .addReturn(new IntExpr(10))
                            .build()
                        ).build()
                ).build()
            ); 
    }
    
    

}
