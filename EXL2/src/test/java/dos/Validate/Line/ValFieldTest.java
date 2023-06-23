package dos.Validate.Line;

import java.util.List;

import dos.EXL.Filer.Builder.ClassDataBuilder;
import dos.EXL.Filer.Builder.DataInterfaceBuilder;
import dos.EXL.Filer.Builder.ImportsDataBuilder;
import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Lines.Field;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Maybe;
import dos.Util.Interaces.DataInterface;
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
            new DataInterfaceBuilder()
                .addVar(new Variable("a", "String", 0, 0, 0))
                .build()
        );
        assertValid(
            new Field(List.of(),"Str", new VarExpr("a"),"Barry"),
            new DataInterfaceBuilder()
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
            new Field(List.of(),"i", new BooleanExpr(false), "int"),
            "L10",
            null
        );
        assertError(
            new Field(List.of(),"i", new VarExpr("a"),  "Barry"),
            "L7",
            new DataInterfaceBuilder()
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
