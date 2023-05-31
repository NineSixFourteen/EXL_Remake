package dos.Validate.Line;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import dos.Util.ValueRecords;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValIfTest extends TestCase {
    
    public static Test suite(){
        return new TestSuite(ValWhileTest.class);
    }

    public static void testValid() {
        assertValid(
            LineFactory.ifL(
                new LThanEqExpr(new IntExpr(9), new IntExpr(10)),
                new CodeBlockBuilder()
                    .addWhile(
                        new BoolExpr(true), 
                        new CodeBlockBuilder().build())
                .build()
        ));
        assertValid(
                LineFactory.ifL(
                new IntExpr(10),
                new CodeBlockBuilder()
                    .addWhile(
                        new BoolExpr(true), 
                        new CodeBlockBuilder().build())
                .build()
            ));
        assertValid(
            LineFactory.ifL(
                new BoolExpr(false),
                new CodeBlockBuilder()
                    .addWhile(
                        new IntExpr(10), 
                        new CodeBlockBuilder().build())
                .build()
            ));
    }

    private static void assertValid(Line line){
        Maybe<MyError> errorMaybe = line.validate(new ValueRecords());
        if(!errorMaybe.hasValue()){
            assertTrue(false);
        }
    }

    
}
