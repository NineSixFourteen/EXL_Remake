package dos.Parser.Lines;

import java.util.List;

import dos.EXL.Parser.Lines.FieldParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Line;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Lines.Field;
import dos.EXL.Types.Unary.Types.CharExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FieldParserTest extends TestCase {

    public static Test suite(){
        return new TestSuite(FieldParserTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid(){
        assertValid(
            "public int i = 0;",
            new Field(List.of(Tag.Public),"i", new IntExpr(0), "int")
        );
        assertValid(
            "static char c = 'c';",
            new Field(List.of(Tag.Static),"c", new CharExpr('c'), "char")
        );
        assertValid(
            "private static float f = 0.2;",
            new Field(List.of(Tag.Private, Tag.Static),"f", new FloatExpr(0.2F), "float")
        );
        assertValid(
            "String s = \"la\";",
            new Field(List.of(),"s", new StringExpr("la"), "String")
        );
    }

    public static void testErrors(){
        assertError(
            "private = 0",
            "P2"
        );
        assertError(
            "private i = 0",
            "P2"
        );
        assertError(
            "private string = 0",
            "P2"
        );
        assertError(
            "String s = ",
            "P4"
        );
        assertError(
            "private int int = 0",
            "P2"
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
        var result = FieldParser.parse(Tokenizer.convertToTokens(msg));
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
        var line = FieldParser.parse(Tokenizer.convertToTokens(msg));
        if(!line.hasError()){
            System.out.println("Error missed code - " + errorCode);
            assertTrue(false);
        } else {
            assertTrue(errorCode.equals(line.getError().getFullErrorCode()));
        }
    }
}
