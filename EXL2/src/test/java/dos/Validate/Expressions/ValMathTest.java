package dos.Validate.Expressions;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Maths.AddExpr;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Binary.Maths.ModExpr;
import dos.EXL.Types.Binary.Maths.MulExpr;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.DataInterface;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValMathTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValMathTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            new AddExpr(
                new DivExpr(
                    new IntExpr(2), 
                    new BracketExpr(
                        new ModExpr(
                            new MulExpr(
                                new IntExpr(2),
                                new IntExpr(2)),
                             new IntExpr(2))
                    )),
                    new IntExpr(2)),
            "int",
            null
        );
    }

    public static void testErrors(){
        assertError(
            new AddExpr(new BooleanExpr(false), new BooleanExpr(false)), 
            "L30", 
            null
        );
    }

    private static void assertValid(Expression exp, String predicatedType, DataInterface visitor){
        Result<String> type = exp.getType(visitor,0);
        if(type.hasError()){
            System.out.println(type.getError().getFullErrorCode());
            assertTrue(false);
        }
        assertTrue(type.getValue().equals(predicatedType));
    }

    public static void assertError(Expression exp, String errorcode, DataInterface visitor){
        Maybe<MyError> errorMaybe = exp.validate(visitor,0);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
}
