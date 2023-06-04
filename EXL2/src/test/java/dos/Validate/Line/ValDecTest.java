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
import dos.Util.InfoClasses.ImportsData;
import dos.Util.InfoClasses.ValueRecords;
import dos.Util.InfoClasses.Builder.ClassDataBuilder;
import dos.Util.InfoClasses.Builder.ImportsDataBuilder;
import dos.Util.InfoClasses.Builder.ValueRecordsBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValDecTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValDecTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            LineFactory.IninitVariable("i", "int ", new IntExpr(0)),
            new ValueRecords()
        );
        assertValid(
            LineFactory.IninitVariable("il", "int", new DivExpr(new IntExpr(2),new IntExpr(3))),
            new ValueRecords()
        );
        assertValid(
            LineFactory.IninitVariable("f","float", new FloatExpr(.24F)),
            new ValueRecords()
        );
        assertValid(
            LineFactory.IninitVariable("f","float", new FloatExpr(.24F)),
            new ValueRecords()
        );
        assertValid(
            LineFactory.IninitVariable("Str","String", new VarExpr("a")),
            new ValueRecordsBuilder()
                .addVar("a", "String")
                .build()
        );
        assertValid(
            LineFactory.IninitVariable("Str","Barry", new VarExpr("a")),
            new ValueRecordsBuilder()
                .addImports(
                    new ImportsDataBuilder()
                    .addImports("Barry", "LJava.Lang.Barry;",
                        new ClassDataBuilder()
                            .build()    
                    ).build()
                )
                .addVar("a", "Barry")
                .build()
        );
    }

    public static void testErrors(){
        assertError(
            LineFactory.IninitVariable("i", "int", new IntExpr(2)),
            "L22",
            new ValueRecordsBuilder()
                .addVar("i","int")
                .build()
        );
        assertError(
            LineFactory.IninitVariable("i", "int", new BoolExpr(false)),
            "L10",
            new ValueRecords()
        );
        assertError(
            LineFactory.IninitVariable("i", "Barry", new VarExpr("a")),
            "L7",
            new ValueRecordsBuilder()
                .addImports(new ImportsData())
                .addVar("a", "Barry")
                .build()
        );
    }

    private static void assertValid(Line line, ValueRecords records){
        Maybe<MyError> errorMaybe = line.validate(records);
        if(errorMaybe.hasValue()){
            assertTrue(false);
        }
    }

    public static void assertError(Line line, String errorcode, ValueRecords records){
        Maybe<MyError> errorMaybe = line.validate(records);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
    
}
