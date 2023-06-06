package dos.Validate.Expressions;

import java.util.List;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Trechery.LogicExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.InfoClasses.ValueRecords;
import dos.Util.InfoClasses.Builder.ValueRecordsBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValFuncExprTest  extends TestCase {

    public static Test suite(){
        return new TestSuite(ValFuncExprTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            new FunctionExpr("Babab", List.of()),
            "float",
            new ValueRecordsBuilder()
                .addFunction("Babab", "()F")
                .build()
        );
        assertValid(
            new FunctionExpr("Babab", List.of(new IntExpr(2), new FunctionExpr("ll", List.of()))),
            "float",
            new ValueRecordsBuilder()
                .addFunction("Babab", "(II)F")
                .addFunction("ll","()I")
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            new FunctionExpr("Babab", List.of(new IntExpr(2), new FunctionExpr("ll", List.of()))),
            "L12",
            new ValueRecords()
        );
        assertError(
            new FunctionExpr("ll", List.of(new IntExpr(2), new FunctionExpr("ll", List.of()))),
            "L20",
            new ValueRecordsBuilder()
                .addFunction("ll", "(II)I")
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
