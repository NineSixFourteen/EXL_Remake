package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import dos.Tokenizer.Tokenizer;
import dos.Types.Expression;
import dos.Types.Binary.ObjectFieldExpr;
import dos.Types.Binary.ObjectFuncExpr;
import dos.Types.Binary.Boolean.AndExpr;
import dos.Types.Binary.Boolean.EqExpr;
import dos.Types.Binary.Boolean.LThanEqExpr;
import dos.Types.Binary.Boolean.LThanExpr;
import dos.Types.Binary.Maths.AddExpr;
import dos.Types.Binary.Maths.DivExpr;
import dos.Types.Binary.Maths.MulExpr;
import dos.Types.Binary.Maths.SubExpr;
import dos.Types.Unary.BracketExpr;
import dos.Types.Unary.FunctionExpr;
import dos.Types.Unary.NotExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Expression Parser Tests
public class EPTest extends TestCase{
    
    public static Test suite(){
        return new TestSuite(EPTest.class);
    }

    private static void assertEq(String msg, Expression exp) {
        var e = ExpressionParser.parse(Tokenizer.convertToTokens(msg));
        if(e.hasError()){
            System.out.print(e.getError().getMessage());
            assertTrue(false);
        }
        if(!exp.makeString().equals(e.getValue().makeString())){
            System.out.println(exp.makeString());
            System.out.println(e.getValue().makeString());
        }
        assertTrue(exp.makeString().equals(e.getValue().makeString()));
    }

    public static void main(String[] args) {
        testExpressions();
    }

    public static void testExpressions(){
        assertEq("9 < 4 && 2 == 4", new AndExpr(new LThanExpr(new IntExpr(9), new IntExpr(4)), new EqExpr(new IntExpr(2), new IntExpr(4))));
        assertEq("9 - 4 * 2 / 4 - 2 * 4 ", new SubExpr(new SubExpr(new IntExpr(9), new MulExpr(new IntExpr(4), new DivExpr(new IntExpr(2), new IntExpr(4)))), new MulExpr(new IntExpr(2), new IntExpr(4))));
        assertEq("a.b.c.d.e(2).f",
         new ObjectFieldExpr(
            new ObjectFuncExpr(
                new ObjectFieldExpr(
                    new ObjectFieldExpr(
                        new ObjectFieldExpr(new VarExpr("a"), "b"), "c"),"d"),
                         new FunctionExpr("e", List.of(new IntExpr(2)))),
                            "f"
        ));
        assertEq("!(a)", new NotExpr(new BracketExpr(new VarExpr("a"))));
        assertEq("!(a.b(5))", new NotExpr(new BracketExpr(new ObjectFuncExpr(new VarExpr("a"), new FunctionExpr("b", List.of(new IntExpr(5)))))));
        assertEq("!(a.b(5) && 9 + 4 <= 13 - 2 / 1)", 
                new NotExpr(
                    new BracketExpr(
                        new AndExpr(
                            new ObjectFuncExpr(new VarExpr("a"), new FunctionExpr("b", List.of(new IntExpr(5)))),
                            new LThanEqExpr(
                                new AddExpr(new IntExpr(9), new IntExpr(4)), 
                                new SubExpr(new IntExpr(13), new DivExpr(new IntExpr(2), new IntExpr(1)))
                            )
                ))));
    }


}
