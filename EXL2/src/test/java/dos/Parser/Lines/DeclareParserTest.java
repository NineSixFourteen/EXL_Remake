package dos.Parser.Lines;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.LineParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Line;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Unary.Types.CharExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DeclareParserTest extends TestCase {

    public static Test suite(){
        return new TestSuite(DeclareParserTest.class);

    }

    public static void main(String[] args) {
        testValid();
    }

    public static void testValid(){
        assertValid("int i = 0;\n", new DeclarLine("i","int", new IntExpr(0)));
        assertValid("float f = 0.2;\n", new DeclarLine("f","float", new FloatExpr(0.2F)));
        assertValid("char c = 'c';\n", new DeclarLine("c","char", new CharExpr('c')));
        assertValid("String s = \"lala\";\n", new DeclarLine("s","String", new StringExpr("lala")));
    }

    public static void testError(){
        assertError(
            "int i = 'c';",
            "L10"
        );
        assertError(
            "float i = 'c';",
            "L10"
        );
        assertError(
            "String i = 'c';",
            "L10"
        );
        assertError(
            "String s = 10;",
            "L10"
        );
    }

    private static void assertValid(String msg, Line line) {
        if(!line.makeString(0).equals(msg)){
            System.out.println(line.makeString(0));
            System.out.println(msg);
        }
        assertTrue(line.makeString(0).equals(msg));
        var result = LineParser.getLine(Tokenizer.convertToTokens(msg));
        if(result.hasError()){
            assertFalse(true);
        }
        var val = result.getValue();
        if(!val.makeString(0).equals(line.makeString(0))){
            System.out.println(val.makeString(0));
            System.out.println(line.makeString(0));
        }
        assertTrue(val.makeString(0).equals(line.makeString(0)));
    }
    
    private static void assertError(String msg, String errorCode){
        var line = LineParser.getLine(Tokenizer.convertToTokens(msg));
        if(!line.hasError()){
            System.out.println("Error missed code - " + errorCode);
            assertTrue(false);
        } else {
            assertTrue(errorCode.equals(line.getError().getFullErrorCode()));
        }
    }

}
