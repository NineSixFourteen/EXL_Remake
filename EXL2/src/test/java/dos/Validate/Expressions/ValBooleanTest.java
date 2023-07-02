package dos.Validate.Expressions;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Filer.Builder.DataInterfaceBuilder;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.AndExpr;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Boolean.OrExpr;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Maybe;
import dos.Util.Result;
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
                    new BooleanExpr(false))
            ),
            "boolean",
            new DataInterfaceBuilder("")
                .addVar(new Variable("a", "int", 0, 0, 0))
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            new AndExpr(new LThanExpr(new BooleanExpr(false), new VarExpr("a")), new VarExpr("a")),
            "L2",
            new DataInterfaceBuilder("")
                .addVar(new Variable("a", "boolean", 0, 0, 0))
                .build()
        );
        assertError(
            new AndExpr(new BracketExpr(new IntExpr(3)), new BooleanExpr(false)),
            "L4",
                new DataInterfaceBuilder("").build()
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

    public static void assertError(Expression exp, String errorcode, DataInterface FunctionVisitor){
        Maybe<MyError> errorMaybe = exp.validate(FunctionVisitor,0);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
}