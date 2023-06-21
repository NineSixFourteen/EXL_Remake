package dos.Validate.Expressions;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Unary.NotExpr;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.DataInterface;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValNotTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValNotTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            new NotExpr(new BooleanExpr(false)),
            "boolean",
            null
        );
    }

    public static void testErrors(){
        assertError(
            new NotExpr(new IntExpr(2)), 
            "L3", 
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