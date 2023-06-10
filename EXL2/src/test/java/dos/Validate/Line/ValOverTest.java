package dos.Validate.Line;

import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Maybe;
import dos.Util.Interaces.DataInterface;
import dos.Util.Data.ImportsData;
import dos.Util.Data.Builder.ClassDataBuilder;
import dos.Util.Data.Builder.FunctionVisitorBuilder;
import dos.Util.Data.Builder.ImportsDataBuilder;
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
            new FunctionVisitorBuilder()
                .addVar("i", "int")
                .build()
        );
        assertValid(
            LineFactory.varO("il",new DivExpr(new IntExpr(2),new IntExpr(3))),
            new FunctionVisitorBuilder()
                .addVar("il", "int")
                .build()
        );
        assertValid(
            LineFactory.varO("f", new FloatExpr(.24F)),
            new FunctionVisitorBuilder()
                .addVar("f", "float")
                .build()
        );
        assertValid(
            LineFactory.varO("Str", new VarExpr("a")),
            new FunctionVisitorBuilder()
                .addVar("Str","String")
                .addVar("a", "String")
                .build()
        );
        assertValid(
            LineFactory.varO("Str", new VarExpr("a")),
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                    .addImports("Barry", "LJava.Lang.Barry;",
                        new ClassDataBuilder()
                            .build()    
                    ).build()
                )
                .addVar("Str", "Barry")
                .addVar("a", "Barry")
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            LineFactory.varO("i", new BoolExpr(false)),
            "L10",
            new FunctionVisitorBuilder()
                .addImports(new ImportsData())
                .addVar("i", "int")
                .build()
        );
        assertError(
            LineFactory.varO("i", new VarExpr("a")),
            "L7",
            new FunctionVisitorBuilder()
                .addImports(new ImportsData())
                .addVar("i", "int")
                .addVar("a", "Barry")
                .build()
        );
    }

    private static void assertValid(Line line, DataInterface FunctionVisitor){
        Maybe<MyError> errorMaybe = line.validate(FunctionVisitor);
        if(errorMaybe.hasValue()){
            assertTrue(false);
        }
    }

    public static void assertError(Line line, String errorcode, DataInterface FunctionVisitor){
        Maybe<MyError> errorMaybe = line.validate(FunctionVisitor);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
    
}
