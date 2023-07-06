package dos.Parser.Lines;

import dos.EXL.Parser.LineParser;
import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Maths.AddExpr;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.ForLine;
import dos.EXL.Types.Lines.VarOverwrite;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ForParserTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ForParserTest.class);
    }

    public static void main(String[] args) {
        testValid();
    }

    public static void testValid(){
        assertValid(
            "for int i =0; i< 2; i = i + 1{ print i;}",
            new ForLine(
                new DeclarLine("i", "int", new IntExpr(0)),
                new LThanExpr(new VarExpr("i"), new IntExpr(2)),
                new VarOverwrite("i", new AddExpr(new VarExpr("i"), new IntExpr(1))),
                new CodeBlockBuilder()  
                    .addPrint(new VarExpr("i"))
                    .build()
                )
        );
    }

    // Remove White Space
    private static String RWS(String s){
        return s.replace(" ","").replace("\t", "").replace("\n", "");
    }

    private static void assertValid(String msg, Line line) {
        String l = line.makeString(0);
        if(!RWS(l).equals(RWS(msg))){
            System.out.println(RWS(l));
            System.out.println(RWS(msg));
        }
        assertTrue(RWS(l).equals(RWS(msg)));
        var result = LineParser.getLine(Tokenizer.convertToTokens(msg));
        if(result.hasError()){
            assertFalse(true);
        }
        var val = result.getValue();
        if(!val.makeString(0).equals(line.makeString(0))){
            System.out.println(RWS(val.makeString(0)));
            System.out.println(RWS(line.makeString(0)));
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
