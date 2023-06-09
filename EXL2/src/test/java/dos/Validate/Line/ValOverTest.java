package dos.Validate.Line;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Filer.Builder.ClassDataBuilder;
import dos.EXL.Filer.Builder.DataInterfaceBuilder;
import dos.EXL.Filer.Builder.ImportsDataBuilder;
import dos.EXL.Filer.Imports.ClassData;
import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Maybe;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValOverTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValOverTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            LineFactory.varO("i", new IntExpr(0)),
            new DataInterfaceBuilder("")
                .addVar(new Variable("i", "int", 0, 0, 0))
                .build()
        );
        assertValid(
            LineFactory.varO("il",new DivExpr(new IntExpr(2),new IntExpr(3))),
            new DataInterfaceBuilder("")
                .addVar(new Variable("il", "int", 0, 0, 0))
                .build()
        );
        assertValid(
            LineFactory.varO("f", new FloatExpr(.24F)),
            new DataInterfaceBuilder("")
                .addVar(new Variable("f", "float", 0, 0, 0))
                .build()
        );
        assertValid(
            LineFactory.varO("Str", new VarExpr("a")),
            new DataInterfaceBuilder("")
                .addVar(new Variable("Str", "String", 0, 0, 0))
                .addVar(new Variable("a", "String", 0, 0, 0))
                .build()
        );
        assertValid(
            LineFactory.varO("Str", new VarExpr("a")),
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                    .addImports("Barry", "LJava.Lang.Barry;",
                        new ClassDataBuilder()
                            .build()    
                    ).build()
                )
                .addVar(new Variable("Str", "Barry", 0, 0, 0))
                .addVar(new Variable("a", "Barry", 0, 0, 0))
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            LineFactory.varO("i", new BooleanExpr(false)),
            "L10",
            new DataInterfaceBuilder("")
                .addImports(new ImportsData())
                .addVar(new Variable("i", "int", 0, 0, 0))
                .build()
        );
        assertError(
            LineFactory.varO("i", new VarExpr("a")),
            "L10",
            new DataInterfaceBuilder("")
                .addImports(new ImportsDataBuilder().addImports("Barry", "LaLa.Barry", new ClassData()).build())
                .addVar(new Variable("i", "int", 0, 0, 0))
                .addVar(new Variable("a", "Barry", 0, 0, 0))
                .build()
        );
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
