package dos.Validate.Expressions;

import java.util.List;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Filer.Builder.DataInterfaceBuilder;
import dos.EXL.Filer.Builder.SelfDataBuilder;
import dos.EXL.Filer.Program.Function.FunctionData;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import dos.Util.Result;
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
            new DataInterfaceBuilder("")
            .addSelf(
                new SelfDataBuilder("")
                .addFunction(new FunctionData("Babab","float","()F", List.of()))
                .build()
            )
                .build()
        );
        assertValid(
            new FunctionExpr("Babab", List.of(new IntExpr(2), new FunctionExpr("ll", List.of()))),
            "float",
            new DataInterfaceBuilder("")
                .addSelf(
                    new SelfDataBuilder("")
                    .addFunction(new FunctionData("Babab","float","(II)F", List.of()))
                    .addFunction(new FunctionData("ll","int","()I", List.of()))
                    .build()
                )
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            new FunctionExpr("Babab", List.of(new IntExpr(2), new FunctionExpr("ll", List.of()))),
            "L12",
            new DataInterfaceBuilder("").build()
        );
        assertError(
            new FunctionExpr("ll", List.of(new IntExpr(2), new FunctionExpr("ll", List.of()))),
            "L20",
            new DataInterfaceBuilder("")
                .addSelf(
                    new SelfDataBuilder("")
                        .addFunction(new FunctionData("ll", "int", "(II)I", List.of()))
                        .build()
                )
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
