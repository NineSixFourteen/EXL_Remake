package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import dos.Parser.Builders.CodeBlockBuilder;
import dos.Parser.Builders.FunctionBuilder;
import dos.Tokenizer.Tokenizer;
import dos.Types.Function;
import dos.Types.Tag;
import dos.Types.Binary.ObjectFieldExpr;
import dos.Types.Binary.ObjectFuncExpr;
import dos.Types.Binary.Boolean.AndExpr;
import dos.Types.Binary.Boolean.LThanExpr;
import dos.Types.Binary.Boolean.OrExpr;
import dos.Types.Binary.Maths.AddExpr;
import dos.Types.Binary.Maths.DivExpr;
import dos.Types.Unary.BracketExpr;
import dos.Types.Unary.FunctionExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.StringExpr;
import dos.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Function Parser Tests
public class FPTest extends TestCase{
    
    public static Test suite(){
        return new TestSuite(FPTest.class);
    }

    private static void assertEq(String msg, Function func) {
        var e = FunctionParser.getFunction(Tokenizer.convertToTokens(msg));
        if(e.hasError()){
            System.out.println(e.getError().getMessage());
            assertTrue(false);
        }
        if(!func.makeString().equals(e.getValue().makeString())){
            System.out.println(func.makeString());
            System.out.println(e.getValue().makeString());
        }
        assertTrue(func.makeString().equals(e.getValue().makeString()));
    }

    public static void main(String[] args) {
        testFunctions();
    }

    public static void testFunctions(){ 
        assertEq(
            "public static void main(String args){int i = 0;}",
            new FunctionBuilder()
                .addTag(Tag.Public).addTag(Tag.Static)
                .setName("main")
                .setType("void")
                .addParameter("String","args")
                .setBody(
                    new CodeBlockBuilder()
                    .addDeclare("i", "int", new IntExpr(0))
                    .build()
                )
                .build()    
        );
        assertEq(
            "private int Add(int a, int b){int c = a + b; return c;}",
            new FunctionBuilder()
                .addTag(Tag.Private)
                .setName("Add")
                .setType("int")
                .addParameter("int","a")
                .addParameter("int","b")
                .setBody(
                    new CodeBlockBuilder()
                    .addDeclare("c", "int", new AddExpr(new VarExpr("a"), new VarExpr("b")))
                    .addReturn(new VarExpr("c"))
                    .build()
                )
                .build()    
        ); 
        assertEq(
            "private int foo(int a, float b, char c, String s){if 9 / 3 + 2 < 10 + 2{ if a && (b || c) { System.out.println(\"Nice\");return 69;}}System.out.println(\"Not Nice\");return 10;}",
            new FunctionBuilder()
                .addTag(Tag.Private)
                .setName("foo")
                .setType("int")
                .addParameter("int","a")
                .addParameter("float","b")
                .addParameter("char","c")
                .addParameter("String","s")
                .setBody(
                    new CodeBlockBuilder()
                    .addIf(new LThanExpr(
                        new AddExpr(
                            new DivExpr(
                                new IntExpr(9), new IntExpr(3)), 
                                new IntExpr(2)),
                            new AddExpr(new IntExpr(10), new IntExpr(2))),
                        new CodeBlockBuilder()
                            .addIf(
                                new AndExpr(
                                    new VarExpr("a"), 
                                    new BracketExpr(new OrExpr(new VarExpr("b"), new VarExpr("c")))),
                            new CodeBlockBuilder()
                                .addExpr(new ObjectFuncExpr(
                                    new ObjectFieldExpr(new VarExpr("System"), "out"),
                                    new FunctionExpr("println", List.of(new StringExpr("Nice")))))
                                .addReturn(new IntExpr(69))
                                .build()
                            ).build()
                        ).addExpr(new ObjectFuncExpr(
                            new ObjectFieldExpr(new VarExpr("System"), "out"),
                            new FunctionExpr("println", List.of(new StringExpr("Not Nice")))))
                        .addReturn(new IntExpr(10))
                    .build()
                )
                .build()    
        );
    }
    
    

}
