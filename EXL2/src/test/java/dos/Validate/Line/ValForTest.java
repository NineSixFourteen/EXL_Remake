package dos.Validate.Line;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValForTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValForTest.class);
    }

    public static void main(String[] args) {
        testValid();
    }

    public static void testValid() {
        assertValid(
            LineFactory.forL(
                new DeclarLine("i","int",new IntExpr(10)),
                new BooleanExpr(false),
                LineFactory.exprL(new IntExpr(10)),
                    new CodeBlockBuilder()
                        .addFor(new DeclarLine("l","int", new IntExpr(0)), 
                                new BooleanExpr(false), LineFactory.exprL(new IntExpr(10)) , 
                                new CodeBlockBuilder().build())
                        .build()
            ));
    }

    private static void assertValid(Line line){
        Maybe<MyError> errorMaybe = line.validate(null,0);
        if(!errorMaybe.hasValue()){
            System.out.println(errorMaybe.getValue().getFullErrorCode());
            assertTrue(false);
        }
    }
    
}
