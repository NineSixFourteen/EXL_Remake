package dos.Validate.Expressions;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.AndExpr;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Boolean.OrExpr;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.DataInterface;
import dos.Util.Data.Builder.FunctionVisitorBuilder;
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
            new FunctionVisitorBuilder()
                .addVar("a", "int")
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            new AndExpr(new LThanExpr(new BoolExpr(false), new VarExpr("a")), new VarExpr("a")),
            "L2",
            new FunctionVisitorBuilder()
                .addVar("a", "boolean").build()
        );
        assertError(
            new AndExpr(new IntExpr(3), new BoolExpr(false)),
            "L4",
                new FunctionVisitorBuilder().build()
        );
    }

    private static void assertValid(Expression exp, String predicatedType, DataInterface visitor){
        Result<String> type = exp.getType(visitor);
        if(type.hasError()){
            System.out.println(type.getError().getFullErrorCode());
            assertTrue(false);
        }
        assertTrue(type.getValue().equals(predicatedType));
    }

    public static void assertError(Expression exp, String errorcode, DataInterface FunctionVisitor){
        Maybe<MyError> errorMaybe = exp.validate(FunctionVisitor);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
}