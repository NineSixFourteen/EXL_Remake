package dos.Validate.Line;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValIfTest extends TestCase {
    
    public static Test suite(){
        return new TestSuite(ValIfTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testError();
    }

    public static void testValid() {
        assertValid(
            LineFactory.ifL(
                new LThanEqExpr(new IntExpr(9), new IntExpr(10)),
                new CodeBlockBuilder()
                    .addWhile(
                        new BooleanExpr(true), 
                        new CodeBlockBuilder().build())
                .build()
        ));
        assertValid(
                LineFactory.ifL(
                new BooleanExpr(false),
                new CodeBlockBuilder()
                    .addWhile(
                        new BooleanExpr(true), 
                        new CodeBlockBuilder().build())
                .build()
            ));
        assertValid(
            LineFactory.ifL(
                new BooleanExpr(false),
                new CodeBlockBuilder()
                    .addWhile(
                        new BooleanExpr(false), 
                        new CodeBlockBuilder().build())
                .build()
            ));
    }

    public static void testError(){
        assertError(
            LineFactory.ifL(
                new BracketExpr(new IntExpr(0)),
                new CodeBlockBuilder().build()),
            "L11"
        );
    }

    private static void assertValid(Line line){
        Maybe<MyError> errorMaybe = line.validate(null,0);
        if(errorMaybe.hasValue()){
            assertTrue(false);
        }
    }

    public static void assertError(Line line, String errorcode){
        Maybe<MyError> errorMaybe = line.validate(null,0);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }

    
}
