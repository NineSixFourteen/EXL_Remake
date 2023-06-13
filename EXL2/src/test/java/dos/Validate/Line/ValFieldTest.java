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
import dos.Util.Interaces.DataInterface;
import dos.Util.Data.ImportsData;
import dos.Util.Data.Variable;
import dos.Util.Data.Builder.ClassDataBuilder;
import dos.Util.Data.Builder.FunctionVisitorBuilder;
import dos.Util.Data.Builder.ImportsDataBuilder;
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
            new DataInterface("")
        );
        assertValid(
            new Field(List.of(),"i", new DivExpr(new IntExpr(2),new IntExpr(3)), "int"),
            new DataInterface("")
        );
        assertValid(
            new Field(List.of(),"f", new FloatExpr(.24F), "float"),
            new DataInterface("")
        );
        assertValid(
            new Field(List.of(),"Str", new VarExpr("a"),"String"),
            new FunctionVisitorBuilder()
                .addVar(new Variable("a", "String", 0, 0, 0))
                .build()
        );
        assertValid(
            new Field(List.of(),"Str", new VarExpr("a"),"Barry"),
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

    public static void testErrors(){
        assertError(
            new Field(List.of(),"i", new IntExpr(2), "int"),
            "L22",
            new FunctionVisitorBuilder()
                .addField("i","int")
                .build()
        );
        assertError(
            new Field(List.of(),"i", new BoolExpr(false), "int"),
            "L10",
            null
        );
        assertError(
            new Field(List.of(),"i", new VarExpr("a"),  "Barry"),
            "L7",
            new FunctionVisitorBuilder()
                .addImports(new ImportsData())
                .addField("d", "Barry")
                .build()
        );
    }

    private static void assertValid(Field field, DataInterface FunctionVisitor){
        Maybe<MyError> errorMaybe = field.validate(FunctionVisitor,0);
        if(errorMaybe.hasValue()){
            assertTrue(false);
        }
    }

    public static void assertError(Field field, String errorcode, DataInterface FunctionVisitor){
        Maybe<MyError> errorMaybe = field.validate(FunctionVisitor,0);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
    
}
