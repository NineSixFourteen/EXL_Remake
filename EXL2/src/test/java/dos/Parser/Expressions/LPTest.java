package dos.Parser.Expressions;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.Boolean.AndExpr;
import dos.EXL.Types.Binary.Boolean.EqExpr;
import dos.EXL.Types.Binary.Boolean.GThanEqExpr;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Boolean.NotEqExpr;
import dos.EXL.Types.Binary.Boolean.OrExpr;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Logic Parser Tests
public class LPTest extends TestCase  {

    public static void main(String[] args) {
        testParser();
        testErrorFunctions();
    }

    public static void testParser(){
        
        assertEq("9 < 4", new LThanExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 <= 4", new LThanEqExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 >= 4", new GThanEqExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 > 4", new GThanExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 == 4", new EqExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 != 4", new NotEqExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 != 4 && true", new AndExpr(new NotEqExpr(new IntExpr(9), new IntExpr(4)), new VarExpr("true")));
        
        assertEq("9 != 4 && b || (c && d)", 
            new OrExpr(
                new AndExpr(new NotEqExpr(new IntExpr(9), new IntExpr(4)), new VarExpr("b")),
                new BracketExpr(new AndExpr(new VarExpr("c"), new VarExpr("d")))
            )
        );
        
        assertEq("9 != 4 && b || c && d || e", 
        new OrExpr(
            new OrExpr(
                new AndExpr(new NotEqExpr(new IntExpr(9), new IntExpr(4)), new VarExpr("b")),
                new AndExpr(new VarExpr("c"), new VarExpr("d"))
            ),
            new VarExpr("e"))
        );

    }

    public static void testErrorFunctions(){
        assertError(
            "9 && !",
            "P2"
        );
    }

    public static Test suite(){
        return new TestSuite(LPTest.class);
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

}
