package dos.Parser.Expressions;


import java.util.List;

import org.javatuples.Pair;

import dos.Parser.ExpressionParser;
import dos.Tokenizer.Tokenizer;
import dos.Types.Expression;
import dos.Types.Unary.FunctionExpr;
import dos.Types.Unary.Types.CharExpr;
import dos.Types.Unary.Types.FloatExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.StringExpr;
import dos.Types.Unary.Types.VarExpr;
import dos.Util.Result;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Logic Parser Tests
public class VPTest extends TestCase  {

    public static void main(String[] args) {
        testmakeString();
    }

    public static void testmakeString(){
        assertEq("9", new IntExpr(9));
        assertEq("9.4", new FloatExpr(9.4F));
        assertEq("'c'", new CharExpr('c'));
        assertEq("\"lalal\"", new StringExpr("lalal"));
        assertEq("rat(9, 4)", new FunctionExpr("rat", List.of(new IntExpr(9), new IntExpr(4))));
        assertParse("9", new IntExpr(9));
        assertParse("9.4", new FloatExpr(9.4F));
        assertParse("'c'", new CharExpr('c'));
        assertParse("\"lalal\"", new StringExpr("lalal"));
        assertParse("b", new VarExpr("b"));
        assertParse("rat(9, 4)", new FunctionExpr("rat", List.of(new IntExpr(9), new IntExpr(4))));
    }

    public static Test suite(){
        return new TestSuite(VPTest.class);
    }

    public static void assertErr(Result<Pair<Expression, Integer>, Error> res){
        assertTrue(res.hasError());
    }

    public static void assertParse(String test, Expression expect){
        var result = ExpressionParser.parse(Tokenizer.convertToTokens(test));
        if(result.hasError()){
            System.out.println("Err - " + result.getError());
            assertTrue(false);
        } else {
            assertTrue(result.getValue().makeString().equals(test));
        }
    }

    public static void assertValue(Result<Pair<Expression, Integer>, Error> res, Expression exp, int point){
        if(res.hasError()){
            assertFalse(true);
        }
        var val = res.getValue();
        assertEq(val.getValue0(), exp);
        assertTrue(val.getValue1() == point);
    }

    private static void assertEq(Expression val, Expression exp){
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
