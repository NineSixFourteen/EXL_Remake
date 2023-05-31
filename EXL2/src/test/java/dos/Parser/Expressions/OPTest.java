package dos.Parser.Expressions;

import java.util.List;


import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.ObjectFieldExpr;
import dos.EXL.Types.Binary.ObjectFuncExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Maths Parser Tests
public class OPTest extends TestCase {

    public static Test suite(){
        return new TestSuite(OPTest.class );
    }

    public static void main(String[] args) {
        testmakeString();
        testErrorFunctions();
    }

    public static void testErrorFunctions(){
        assertError(
            "Lol.",
            "P4"
        );
        assertError(
            "Lol.bas(",
            "P3"
        );
    }

    public static void testmakeString(){
        assertEq("bas.barry.Los(9)", new ObjectFuncExpr(new ObjectFieldExpr(new VarExpr("bas"), "barry"), new FunctionExpr("Los", List.of( new IntExpr(9)))));
        assertEq("bas.Los(9)", new ObjectFuncExpr(new VarExpr("bas"), new FunctionExpr("Los", List.of( new IntExpr(9)))));
        assertEq("bas.barry.barry", new ObjectFieldExpr(new ObjectFieldExpr(new VarExpr("bas"), "barry"), "barry"));
    }

    private static void assertEq(String msg, Expression exp) {
        if(!exp.makeString().equals(msg)){
            System.out.println(exp.makeString());
            System.out.print(msg);
        }
        assertTrue(exp.makeString().equals(msg));
        var result = ExpressionParser.parse(Tokenizer.convertToTokens(msg));
        if(result.hasError()){
            assertFalse(true);
        }
        var val = result.getValue();
        assertTrue(val.makeString().equals(exp.makeString()));
    }

    private static void assertError(String message, String errorcode){
        var e = ExpressionParser.parse(Tokenizer.convertToTokens(message));
        if(!e.hasError()){
            System.out.println("Error missed code - " + errorcode);
            assertTrue(false);
        } else {
            assertTrue(errorcode.equals(e.getError().getFullErrorCode()));
        }
    }
}
