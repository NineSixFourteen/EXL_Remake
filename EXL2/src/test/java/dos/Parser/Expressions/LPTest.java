package dos.Parser.Expressions;


import org.javatuples.Pair;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.Boolean.EqExpr;
import dos.EXL.Types.Binary.Boolean.GThanEqExpr;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Boolean.NotEqExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Result;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Logic Parser Tests
public class LPTest extends TestCase  {

    public static void main(String[] args) {
        testmakeString();
    }

    public static void testmakeString(){
        assertEq("9 < 4", new LThanExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 <= 4", new LThanEqExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 >= 4", new GThanEqExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 > 4", new GThanExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 == 4", new EqExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 != 4", new NotEqExpr(new IntExpr(9), new IntExpr(4)));
        String test = "9 > 4 && 3 <= 2 || 5 == 2 && 5";
        var result = ExpressionParser.parse(Tokenizer.convertToTokens(test));
        if(result.hasError()){
            assertTrue(false);
        } else {
            if(!result.getValue().makeString().equals(test)){
                System.out.println(result.getValue().makeString());
                System.out.println(test);
                assertTrue(false);
            }
        }
    }

    public static Test suite(){
        return new TestSuite(LPTest.class);
    }

    public static void assertErr(Result<Pair<Expression, Integer>> res){
        assertTrue(res.hasError());
    }

    public static void assertValue(Result<Pair<Expression, Integer>> res, Expression exp, int point){
        if(res.hasError()){
            assertFalse(true);
        }
        var val = res.getValue();
        assertEq(val.getValue0(), exp);
        assertTrue(val.getValue1() == point);
    }

    private static void assertEq(Expression val, Expression exp) {
        assertTrue(exp.makeString().equals(val.makeString()));
    }

    private static void assertEq(String msg, Expression exp) {
        if(!exp.makeString().equals(msg)){
            System.out.println(exp.makeString());
            System.out.print(msg);
        }
        assertTrue(exp.makeString().equals(msg));
    }
    
}
