package dos.Parser.Expressions;

import java.util.List;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.ArrayExpr;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.Maths.SubExpr;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.ObjectDeclareExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Maths Parser Tests
public class SPTest extends TestCase {

    public static Test suite(){
        return new TestSuite(SPTest.class );
    }

    public static void main(String[] args) {
        testmakeString();
        testErrorFunctions();
    }

    public static void testmakeString(){
        assertEq("[9, 4, 3, 2, 1]", 
            new ArrayExpr(
                List.of(
                    new IntExpr(9), new IntExpr(4),
                    new IntExpr(3), new IntExpr(2),
                    new IntExpr(1)
            )));
        assertEq("(9 - 4)", new BracketExpr(new SubExpr(new IntExpr(9), new IntExpr(4))));
        assertEq("new rat(9)", new ObjectDeclareExpr("rat", List.of(new IntExpr(9))));
        assertEq("[new rat(9), (9 - 4)]",
            new ArrayExpr(
                List.of(
                    new ObjectDeclareExpr("rat", List.of(new IntExpr(9))),
                    new BracketExpr(
                        new SubExpr(new IntExpr(9), new IntExpr(4))
            ))));
    }

    public static void testErrorFunctions(){
        assertError(
            "new Lab",
            "P12"
        );
        assertError(
            "new ",
            "P12"
        );
        assertError(
            "new int",
            "P2"
        );
        assertError(
            "new lab{",
            "P2"
        );

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
