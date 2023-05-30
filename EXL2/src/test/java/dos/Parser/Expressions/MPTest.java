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
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
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
        assertEq("9 + 4 - 3 / 2 * 5 + 2 / 5",
            new AddExpr(
                new SubExpr(
                    new AddExpr(new IntExpr(9), new IntExpr(4)),
                    new DivExpr(
                        new IntExpr(3),
                        new MulExpr(new IntExpr(2), new IntExpr(5))
                    )
                ),
                new DivExpr(new IntExpr(2), new IntExpr(5))
            ));
        assertEq("9 + 4 * (3 + 2) / 7 * -3 + 2 - a + -9", 
        (new AddExpr(
                new SubExpr(
                    new AddExpr(
                        new AddExpr(
                            new IntExpr(9),
                            new MulExpr(
                                new IntExpr(4),
                                new MulExpr(
                                    new DivExpr(
                                        new BracketExpr(new AddExpr(
                                            new IntExpr(3),
                                            new IntExpr(2)
                                        )),
                                        new IntExpr(7)
                                    ),
                                    new IntExpr(-3)
                                )   
                            )
                        ),
                        new IntExpr(2)
                    ),
                    new VarExpr("a")
                ),
                new IntExpr(-9))
        ));
    }

    private static void assertEq(String msg, Expression exp) {
        if(!exp.makeString().equals(msg)){
            System.out.println(exp.makeString());
            System.out.print(msg);
        }
        var toks = Tokenizer.convertToTokens(msg);
        var result = ExpressionParser.parse(toks);
        assertTrue(exp.makeString().equals(msg));
        if(result.hasError()){
            assertTrue(false);
        } else {
            if(!result.getValue().makeString().equals(msg)){
                System.out.println("OG     " + msg);
                System.out.println("Parsed " + result.getValue().makeString());
            }
            assertTrue(result.getValue().makeString().equals(msg));
        }
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
