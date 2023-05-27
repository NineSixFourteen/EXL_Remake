package dos.Parser.Expressions;

import org.javatuples.Pair;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.Maths.AddExpr;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Binary.Maths.ModExpr;
import dos.EXL.Types.Binary.Maths.MulExpr;
import dos.EXL.Types.Binary.Maths.SubExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Result;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Maths Parser Tests
public class MPTest extends TestCase {

    public static Test suite(){
        return new TestSuite(MPTest.class );
    }

    public static void main(String[] args) {
        testmakeString();
    }

    public static void testmakeString(){
        assertEq("9 + 4", new AddExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 / 4", new DivExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 - 4", new SubExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 * 4", new MulExpr(new IntExpr(9), new IntExpr(4)));
        assertEq("9 % 4", new ModExpr(new IntExpr(9), new IntExpr(4)));
        var result = ExpressionParser.parse(Tokenizer.convertToTokens("9 + 4 - 3 / 2 * 5 + 2 / 5"));// WRONG
        if(result.hasError()){
            assertTrue(false);
        } else {
            assertTrue(result.getValue().makeString().equals("9 + 4 - 3 / 2 * 5 + 2 / 5"));
        }
    }

    
    //Helpers
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
