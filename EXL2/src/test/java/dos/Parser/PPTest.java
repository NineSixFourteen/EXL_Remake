package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import dos.EXL.Parser.ProgramParser;
import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Builders.FunctionBuilder;
import dos.EXL.Parser.Builders.ProgramBuilder;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Program;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Binary.Boolean.EqExpr;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Program Parser Tests
public class PPTest extends TestCase{
    
    public static Test suite(){
        return new TestSuite(PPTest.class);
    }

    public static void main(String[] args) {
        testFunctions();
        testFunction();
        testErrorFunctions();
    }

    private static void assertEq(String msg, Program prog) {
        var e = ProgramParser.toClass(Tokenizer.convertToTokens(msg));
        if(e.hasError()){
            System.out.println(e.getError().getFullErrorCode());
            assertTrue(false);
        }
        if(!prog.makeString().equals(e.getValue().makeString())){
            System.out.println(prog.makeString());
            System.out.println(e.getValue().makeString());
        }
        assertTrue(prog.makeString().equals(e.getValue().makeString()));
    }

    private static void assertError(String msg, String errorcode){
        var e = ProgramParser.toClass(Tokenizer.convertToTokens(msg));
        if(!e.hasError()){
            System.out.println("Error missed code - " + errorcode);
            assertTrue(false);
        } else {
            var error = e.getError();
            assertTrue(errorcode.equals(error.getFullErrorCode()));
        }
    }

    public static void testErrorFunctions(){
        assertError(
            "public Test { public static boolean Show = true; private static int notMain(int a){ return 10;}}",
            "P2"
        );
        assertError(
            "public class boolean { public static boolean Show = true; private static int notMain(int a){ return 10;}}",
            "P2"
        );
        assertError(
            "public class test { public static boolean Show + true; private static int notMain(int a){ return 10;}}",
            "P10"
        );
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
    
    public static void testFunction(){ 
        assertEq(
            """
                public class Test {  
                public static int fib(int n){
                    int a =  0;
                    int b =  1;
                    int c =  1;
                    if(i == 0){
                        print 10;
                    }
                    return (b);
                    }}
            """,
            new ProgramBuilder()
                .addTag(Tag.Public)
                .setName("Test")
                .addFunction(
                    new FunctionBuilder()
                        .addTag(Tag.Public)
                        .addTag(Tag.Static)
                        .addParameter("int", "n")
                        .setName("fib")
                        .setType("int")
                        .setBody(
                            new CodeBlockBuilder()
                            .addDeclare("a", "int", new IntExpr(0))
                            .addDeclare("b", "int", new IntExpr(1))
                            .addDeclare("c", "int", new IntExpr(1))
                            .addIf(new BracketExpr( new EqExpr(new VarExpr("i"), new IntExpr(0))),
                                new CodeBlockBuilder()
                                .addPrint(new IntExpr(10))
                                .build())
                            .addReturn(new BracketExpr(new VarExpr("b")))
                            .build()
                        ).build()
                ).build()
            ); 
    }
    

}
