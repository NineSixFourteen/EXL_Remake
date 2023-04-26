package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Parser.ExpressionParser;
import dos.Tokenizer.Tokenizer;
import dos.Tokenizer.Types.Token;
import dos.Types.ArrayExpr;
import dos.Types.Expression;
import dos.Types.Binary.Maths.AddExpr;
import dos.Types.Binary.Maths.DivExpr;
import dos.Types.Binary.Maths.ModExpr;
import dos.Types.Binary.Maths.MulExpr;
import dos.Types.Binary.Maths.SubExpr;
import dos.Types.Unary.BracketExpr;
import dos.Types.Unary.ObjectDeclareExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Util.Result;
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
    }

    public static void testmakeString(){
        assertEq("[9, 4, 3, 2, 1]", new ArrayExpr(List.of(new IntExpr(9),new IntExpr(4),new IntExpr(3),new IntExpr(2),new IntExpr(1))));
        assertEq("(9 - 4)", new BracketExpr(new SubExpr(new IntExpr(9), new IntExpr(4))));
        assertEq("new rat(9)", new ObjectDeclareExpr("rat", List.of(new IntExpr(9))));
        String test = "[new rat(9), (9 - 4)]";
        List<Token> tokens = Tokenizer.convertToTokens(test);
        var result = ExpressionParser.parse(Tokenizer.convertToTokens(test));
        if(result.hasError()){
            assertTrue(false);
        } else {
            if(!result.getValue().makeString().equals(test)){
                System.out.println(result.getValue().makeString());
                System.out.println(test);
            }
            assertTrue(result.getValue().makeString().equals(test));
        }
    }

    
    //Helpers
    public static void assertErr(Result<Pair<Expression, Integer>, Error> res){
        assertTrue(res.hasError());
    }

    public static void assertValue(Result<Pair<Expression, Integer>, Error> res, Expression exp, int point){
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
