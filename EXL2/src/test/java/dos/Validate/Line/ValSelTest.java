package dos.Validate.Line;

import java.util.List;

import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.ObjectFuncExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.ObjectDeclareExpr;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import dos.Util.InfoClasses.FunctionData;
import dos.Util.InfoClasses.ValueRecords;
import dos.Util.InfoClasses.Builder.ClassDataBuilder;
import dos.Util.InfoClasses.Builder.ImportsDataBuilder;
import dos.Util.InfoClasses.Builder.ValueRecordsBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValSelTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValWhileTest.class);
    }

    public static void main(String[] args) {
        testValid();
    }

    public static void testValid(){
        assertValid(
            LineFactory.Print(
                new FunctionExpr("Dummy", List.of(new IntExpr(10)))
            ),
            new ValueRecordsBuilder()
                .addFunction("Dummy", "V(I)")
                .build()
        );
        assertValid(
            LineFactory.returnL(
                new ObjectDeclareExpr("Dully", List.of(new IntExpr(10), new BoolExpr(false)))
            ),
            new ValueRecordsBuilder()
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Dully", "LJava.Lang.Dully;", 
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData(
                                    "(IC)LJava.Lang.Dully;", 
                                    List.of()
                            )).build()
                        ).build()
                )
                .addFunction("Dummy", "V(I)")
                .build()
        );
        assertValid(
            LineFactory.exprL(
                new ObjectFuncExpr(
                    new FunctionExpr("nully", List.of(new IntExpr(1), new IntExpr(90))),
                    new FunctionExpr("pull", List.of()))
            ), 
            new ValueRecordsBuilder()
                .addFunction("nully", "LJava.Lang.Dully;(II)")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Dully", "LJava.Lang.Dully;", 
                            new ClassDataBuilder()
                                .addFunction("pull", new FunctionData("V()", List.of()))
                                .build()    
                        ).build()
        ).build());
    }

    public static void testError(){

    }

    private static void assertValid(Line line, ValueRecords records){
        Maybe<MyError> errorMaybe = line.validate(records);
        if(errorMaybe.hasValue()){
            assertTrue(false);
        }
    }

    public static void assertError(Line line, String errorcode){
        Maybe<MyError> errorMaybe = line.validate(new ValueRecords());
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
    
}
