package dos.Parser.Lines;

import java.util.List;

import dos.EXL.Parser.LineParser;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.ObjectFieldExpr;
import dos.EXL.Types.Binary.ObjectFuncExpr;
import dos.EXL.Types.Binary.Maths.AddExpr;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Binary.Maths.SubExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.ObjectDeclareExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SELParserTest extends TestCase {

    public static Test suite(){
        return new TestSuite(SELParserTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid(){
         assertValid(
            "return i + 4 / 3 - func(2);",
                LineFactory.returnL(
                    new SubExpr(
                        new AddExpr(
                            new VarExpr("i"),
                            new DivExpr(new IntExpr(4), new IntExpr(3))
                        ),
                        new FunctionExpr("func", List.of(new IntExpr(2)))
                    )
                )
            );
        assertValid(
            "print new Dummy(5, Luppy.lump);",
                LineFactory.Print(
                    new ObjectDeclareExpr(
                        "Dummy",
                        List.of(new IntExpr(5), new ObjectFieldExpr(new VarExpr("Luppy"),"lump"))
                    )
                )
            );
        assertValid(
            "new Dummy().Print();",
                LineFactory.exprL(
                    new ObjectFuncExpr(
                        new ObjectDeclareExpr("Dummy", List.of()),
                        new FunctionExpr("Print", List.of()))
                )
            );
        assertValid(
            "doThing(1,2,3,4,5,6,7,8,9);",
                LineFactory.exprL(
                        new FunctionExpr("doThing", List.of(
                            new IntExpr(1), new IntExpr(2), new IntExpr(3),
                            new IntExpr(4), new IntExpr(5), new IntExpr(6),
                            new IntExpr(7), new IntExpr(8), new IntExpr(9)
                        ))
                )
            );
    }

    public static void testErrors(){
        assertError(
            "print;",
            "P4"
        );
        assertError(";", "P10");
        assertError("9 + 4;", "P10");
    }
    
    private static String RWS(String s){
        return s.replace(" ","").replace("\t", "").replace("\n", "");
    }

    private static void assertValid(String msg, Line line) {
        String l = line.makeString(0);
        if(!RWS(l).equals(RWS(msg))){
            System.out.println(l);
            System.out.println(msg);
        }
        assertTrue(RWS(l).equals(RWS(msg)));
        var result = LineParser.getLine(Tokenizer.convertToTokens(msg));
        if(result.hasError()){
            assertFalse(true);
        }
        var val = result.getValue();
        if(!val.makeString(0).equals(line.makeString(0))){
            System.out.println(val.makeString(0));
            System.out.println(line.makeString(0));
        }
        assertTrue(RWS(val.makeString(0)).equals(RWS(line.makeString(0))));
    }
    
    private static void assertError(String msg, String errorCode){
        var line = LineParser.getLine(Tokenizer.convertToTokens(msg));
        if(!line.hasError()){
            System.out.println("Error missed code - " + errorCode);
            assertTrue(false);
        } else {
            assertTrue(errorCode.equals(line.getError().getFullErrorCode()));
        }
    }
}
