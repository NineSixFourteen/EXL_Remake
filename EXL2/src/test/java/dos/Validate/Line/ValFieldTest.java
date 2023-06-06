package dos.Validate.Line;

import java.util.List;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Lines.Field;
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

public class ValFieldTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValFieldTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            new Field(List.of(),"i", new IntExpr(0), "int"),
            new ValueRecords()
        );
        assertValid(
            new Field(List.of(),"i", new DivExpr(new IntExpr(2),new IntExpr(3)), "int"),
            new ValueRecords()
        );
        assertValid(
            new Field(List.of(),"f", new FloatExpr(.24F), "float"),
            new ValueRecords()
        );
        assertValid(
            new Field(List.of(),"Str", new VarExpr("a"),"String"),
            new ValueRecordsBuilder()
                .addVar("a", "String")
                .build()
        );
        assertValid(
            new Field(List.of(),"Str", new VarExpr("a"),"Barry"),
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
            new Field(List.of(),"i", new IntExpr(2), "int"),
            "L22",
            new ValueRecordsBuilder()
                .addField("i","int")
                .build()
        );
        assertError(
            new Field(List.of(),"i", new BoolExpr(false), "int"),
            "L10",
            new ValueRecords()
        );
        assertError(
            new Field(List.of(),"i", new VarExpr("a"),  "Barry"),
            "L7",
            new ValueRecordsBuilder()
                .addImports(new ImportsData())
                .addField("a", "Barry")
                .build()
        );
    }

    private static void assertValid(Field field, ValueRecords records){
        Maybe<MyError> errorMaybe = field.validate(records);
        if(errorMaybe.hasValue()){
            assertTrue(false);
        }
    }

    public static void assertError(Field field, String errorcode, ValueRecords records){
        Maybe<MyError> errorMaybe = field.validate(records);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
    
}
