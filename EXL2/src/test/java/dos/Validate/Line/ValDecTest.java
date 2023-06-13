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
import dos.Util.Data.Variable;
import dos.Util.Data.Builder.ClassDataBuilder;
import dos.Util.Data.Builder.FunctionVisitorBuilder;
import dos.Util.Data.Builder.ImportsDataBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValDecTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValDecTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testError();
    }

    public static void testValid() {
        assertValid(
            LineFactory.IninitVariable("i", "int ", new IntExpr(0)),
            new DataInterface("")
        );
        assertValid(
            LineFactory.IninitVariable("il", "int", new DivExpr(new IntExpr(2),new IntExpr(3))),
            new DataInterface("")
        );
        assertValid(
            LineFactory.IninitVariable("f","float", new FloatExpr(.24F)),
            new DataInterface("") 
        );
        assertValid(
            LineFactory.IninitVariable("f","float", new FloatExpr(.24F)),
            new DataInterface("")
        );
        assertValid(
            LineFactory.IninitVariable("Str","String", new VarExpr("a")),
            new FunctionVisitorBuilder()
                .addVar(new Variable("a", "String", 0, 0, 0))
                .build()
        );
        assertValid(
            LineFactory.IninitVariable("Str","Barry", new VarExpr("a")),
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                    .addImports("Barry", "LJava.Lang.Barry;",
                        new ClassDataBuilder()
                            .build()    
                    ).build()
                )
                .addVar(new Variable("a", "Barry", 0, 0, 0))
                .build()
        );
    }

    public static void testError(){
        assertError(
            LineFactory.IninitVariable("i", "int", new IntExpr(2)),
            "L22",
            new FunctionVisitorBuilder()
                .addVar(new Variable("i", "int", 0, 0, 0))
                .build()
        );
        assertError(
            LineFactory.IninitVariable("i", "int", new BoolExpr(false)),
            "L10",
            null
        );
        assertError(
            LineFactory.IninitVariable("i", "Barry", new VarExpr("a")),
            "L7",
            new FunctionVisitorBuilder()
                .addImports(new ImportsData())
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
