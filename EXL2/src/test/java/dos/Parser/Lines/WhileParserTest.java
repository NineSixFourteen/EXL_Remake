package dos.Parser.Lines;

import java.util.List;

import dos.EXL.Parser.LineParser;
import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.IfLine;
import dos.EXL.Types.Lines.WhileLine;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.CharExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WhileParserTest extends TestCase {

    public static Test suite(){
        return new TestSuite(WhileParserTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrorFunctions();
    }

    public static void testValid(){
        assertValid(
            "while 9 < 10{ int i = 0; }",
            new WhileLine(
                new LThanExpr(new IntExpr(9), new IntExpr(10)), 
                toCB(List.of(new DeclarLine("i","int", new IntExpr(0))))
            ));
        assertValid(
            "while 9 < 10{ int i = 0; char c = 'c';}", 
            new WhileLine(
                new LThanExpr(new IntExpr(9), new IntExpr(10)),
                toCB(List.of(
                    new DeclarLine("i","int", new IntExpr(0)),
                    new DeclarLine("c","char", new CharExpr('c'))))
            ));
        assertValid(
            "while 9 < 10 { int i = 0; char c = 'c'; if(9 > 10){ String s = \"sassa\";}}",
            new WhileLine(
                new LThanExpr(new IntExpr(9), new IntExpr(10)), 
                toCB(List.of(
                    new DeclarLine("i","int", new IntExpr(0)),
                    new DeclarLine("c","char", new CharExpr('c')),
                    new IfLine(
                        new BracketExpr(new GThanExpr(new IntExpr(9), new IntExpr(10))),
                        new CodeBlockBuilder()
                            .addDeclare("s","String",new StringExpr("sassa"))
                            .build())))
        ));
    }

    public static void testErrorFunctions(){
        assertError(
            "while {print 11;}",
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

    private static CodeBlock toCB(List<Line> lines){
        CodeBlockBuilder cbb = new CodeBlockBuilder();
        for(Line l : lines){
            cbb.addLine(l);
        }
        return cbb.build();
    }
}
