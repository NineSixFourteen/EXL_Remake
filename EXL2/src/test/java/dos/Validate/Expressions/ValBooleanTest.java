package dos.Validate.Expressions;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.AndExpr;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Boolean.OrExpr;
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

public class ValBooleanTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValBooleanTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            new AndExpr(
                new LThanExpr(
                    new IntExpr(2), 
                    new IntExpr(3)),
                new OrExpr(
                    new GThanExpr(new IntExpr(2), new VarExpr("a")), 
                    new BoolExpr(false))
            ),
            "boolean",
            new ValueRecordsBuilder()
                .addVar("a", "int")
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            new AndExpr(new LThanExpr(new BoolExpr(false), new VarExpr("a")), new VarExpr("a")),
            "L2",
            new ValueRecordsBuilder()
                .addVar("a", "boolean")
                .build()
        );
        assertError(
            new AndExpr(new IntExpr(3), new BoolExpr(false)),
            "L4",
            new ValueRecords()
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