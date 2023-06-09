package dos.Parser.Lines;

import dos.EXL.Parser.LineParser;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Line;
import dos.EXL.Types.Unary.Types.CharExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class VarOverParserTest extends TestCase {

    public static Test suite(){
        return new TestSuite(VarOverParserTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testError();
    }

    public static void testValid(){
        assertValid(
            "i = 0;",
            LineFactory.varO("i", new IntExpr(0))
        );
        assertValid(
            "i = 0.2;",
            LineFactory.varO("i", new FloatExpr(0.2F))
        );
        assertValid(
            "i = 'c';",
            LineFactory.varO("i", new CharExpr('c'))
        );
        assertValid(
            "i = \"ss\";",
            LineFactory.varO("i", new StringExpr("ss"))
        );
    }

    public static void testError(){
        assertError(
            "i = ;",
            "P4"
        );
    }
    
    private static String RWS(String s){
        return s.replace(" ","").replace("\t", "").replace("\n", "");
    }

    private static void assertValid(String msg, Line line) {
        String l = line.makeString(0);
        if(!RWS(l).equals(RWS(msg))){
            System.out.println(l);
            System.out.println(msg);
        }
        assertTrue(RWS(l).equals(RWS(msg)));
        var result = LineParser.getLine(Tokenizer.convertToTokens(msg));
        if(result.hasError()){
            assertFalse(true);
        }
        var val = result.getValue();
        if(!val.makeString(0).equals(line.makeString(0))){
            System.out.println(val.makeString(0));
            System.out.println(line.makeString(0));
        }
        assertTrue(RWS(val.makeString(0)).equals(RWS(line.makeString(0))));
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
