package dos.Validate.Line;

import java.util.List;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Filer.Builder.ClassDataBuilder;
import dos.EXL.Filer.Builder.DataInterfaceBuilder;
import dos.EXL.Filer.Builder.ImportsDataBuilder;
import dos.EXL.Filer.Builder.SelfDataBuilder;
import dos.EXL.Filer.Program.Function.FunctionData;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.ObjectFuncExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.ObjectDeclareExpr;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValSelTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValSelTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testError();
    }

    public static void testValid(){
        assertValid(
            LineFactory.Print(
                new FunctionExpr("Dummy", List.of(new IntExpr(10)))
            ),
            new DataInterfaceBuilder("")
                .addSelf(
                    new SelfDataBuilder("")
                        .addFunction(new FunctionData("Dummy","void","(I)V", List.of()))   
                        .build()
                )
                .build()
        );
        assertValid(
            LineFactory.returnL(
                new ObjectDeclareExpr("Dully", List.of(new IntExpr(10), new BooleanExpr(false)))
            ),
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Dully", "LJava.Lang.Dully;", 
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("","Dully;",
                                    "(IC)LJava.Lang.Dully;", 
                                    List.of()
                            )).build()
                        ).build()
                )
                .build()
        );
        assertValid(
            LineFactory.exprL(
                new ObjectFuncExpr(
                    new FunctionExpr("nully", List.of(new IntExpr(1), new IntExpr(90))),
                    new FunctionExpr("pull", List.of()))
            ), 
            new DataInterfaceBuilder("")
                .addSelf(
                    new SelfDataBuilder("")
                        .addFunction(new FunctionData("nully", "Dully","(II)LJava.Lang.Dully;", List.of()))   
                        .build()
                )
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Dully", "LJava.Lang.Dully;", 
                            new ClassDataBuilder()
                                .addFunction(new FunctionData("pull","void","()V", List.of()))
                                .build()    
                        ).build()
        ).build());
    }

    public static void testError(){

    }

    private static void assertValid(Line line, DataInterface FunctionVisitor){
        Maybe<MyError> errorMaybe = line.validate(FunctionVisitor,0);
        if(errorMaybe.hasValue()){
            assertTrue(false);
        }
    }

    public static void assertError(Line line, String errorcode, DataInterface FunctionVisitor){
        Maybe<MyError> errorMaybe = line.validate(FunctionVisitor,0);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
    
}
