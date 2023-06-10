package dos.Parser.Expressions;


import java.util.List;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.Types.CharExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Logic Parser Tests
public class VPTest extends TestCase  {

    public static void main(String[] args) {
        testmakeString();
        testErrorFunctions();
    }

    public static void testErrorFunctions(){
        assertError(
            "9 LOL",
            "P11"
        );
    }

    public static void testmakeString(){
        assertEq("9", new IntExpr(9));
        assertEq("9.4", new FloatExpr(9.4F));
        assertEq("'c'", new CharExpr('c'));
        assertEq("\"lalal\"", new StringExpr("lalal"));
        assertEq("rat(9, 4)", new FunctionExpr("rat", List.of(new IntExpr(9), new IntExpr(4))));
    }

    public static Test suite(){
        return new TestSuite(VPTest.class);
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
