package dos.Parser.Lines;

import dos.EXL.Parser.LineParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Line;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class VarOverParserTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ForParserTest.class);
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
