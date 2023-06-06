package dos.Validate.Expressions;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Maths.AddExpr;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Binary.Maths.ModExpr;
import dos.EXL.Types.Binary.Maths.MulExpr;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.InfoClasses.ValueRecords;
import dos.Util.InfoClasses.Builder.ValueRecordsBuilder;
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
            new ValueRecordsBuilder()
                .addVar("a", "int")
                .build()
        );
        assertValid(
            new VarExpr("a"),
            "int",
            new ValueRecordsBuilder()
                .addField("a", "int")
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            new VarExpr("a"),
            "L7",
            new ValueRecordsBuilder()
                .build()
        );
    }

    private static void assertValid(Expression exp, String predicatedType, ValueRecords records){
        Result<String> type = exp.getType(records);
        if(type.hasError()){
            System.out.println(type.getError().getFullErrorCode());
            assertTrue(false);
        }
        assertTrue(type.getValue().equals(predicatedType));
    }

    public static void assertError(Expression exp, String errorcode, ValueRecords records){
        Maybe<MyError> errorMaybe = exp.validate(records);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
}

