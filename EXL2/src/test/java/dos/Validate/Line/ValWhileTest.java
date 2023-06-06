package dos.Validate.Line;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import dos.Util.InfoClasses.ValueRecords;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValWhileTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValWhileTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testError();
    }

    public static void testValid() {
        assertValid(
            LineFactory.whileL(
                new LThanEqExpr(new IntExpr(9), new IntExpr(10)),
                new CodeBlockBuilder()
                    .addWhile(
                        new BoolExpr(true), 
                        new CodeBlockBuilder().build())
                    .build()
            ));
        assertValid(
            LineFactory.whileL(
                new BoolExpr(false),
                new CodeBlockBuilder()
                    .addWhile(
                        new BoolExpr(false), 
                        new CodeBlockBuilder().build())
                    .build()
            ));
        assertValid(
            LineFactory.whileL(
                new BoolExpr(false),
                new CodeBlockBuilder()
                    .addWhile(
                        new BoolExpr(false), 
                        new CodeBlockBuilder().build())
                    .build()
            ));
    }

    public static void testError(){
        assertError(
            LineFactory.whileL(
                new IntExpr(10),
                new CodeBlockBuilder().build()),
            "L11"
        );
    }

    private static void assertValid(Line line){
        Maybe<MyError> errorMaybe = line.validate(new ValueRecords());
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
