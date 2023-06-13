package dos.Validate.Expressions;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.DataInterface;
import dos.Util.Data.Variable;
import dos.Util.Data.Builder.FunctionVisitorBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValExprTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValExprTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            new VarExpr("a"),
            "int",
            new FunctionVisitorBuilder()
                .addVar(new Variable("a", "int", 0, 0, 0))
                .build()
        );
        assertValid(
            new VarExpr("a"),
            "int",
            new FunctionVisitorBuilder()
                .addField("a", "int")
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            new VarExpr("a"),
            "L7",
            new FunctionVisitorBuilder()
                .build()
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

