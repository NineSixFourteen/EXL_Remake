package dos.Parser;

import java.util.List;

import dos.EXL.Parser.CodeBlockParser;
import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.ObjectFuncExpr;
import dos.EXL.Types.Binary.Boolean.AndExpr;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Boolean.NotEqExpr;
import dos.EXL.Types.Binary.Boolean.OrExpr;
import dos.EXL.Types.Binary.Maths.AddExpr;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Binary.Maths.SubExpr;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.ExpressionLine;
import dos.EXL.Types.Lines.IfLine;
import dos.EXL.Types.Lines.PrintLine;
import dos.EXL.Types.Lines.ReturnLine;
import dos.EXL.Types.Lines.VarOverwrite;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Code BlocK Parser Tests
public class CBPTest  extends TestCase{
    
    public static Test suite(){
        return new TestSuite(CBPTest.class);
    }

 

    public static void testBlocks(){
        assertEq("int i = 0; i = 4; a.b(19,2); return 10; print 9 < 10;",      
           List.of(
            new DeclarLine("i","int", new IntExpr(0)),
            new VarOverwrite("i", new IntExpr(4)),
            new ExpressionLine(new ObjectFuncExpr(new VarExpr("a"), new FunctionExpr("b", List.of(new IntExpr(19), new IntExpr(2))))),
            new ReturnLine(new IntExpr(10)),
            new PrintLine(new LThanExpr(new IntExpr(9), new IntExpr(10)))
            ));
        assertEq("if(9 - 3 <= 10 + 2 && b != c || false){ int i = 10; print 9;} int i = 0; i = 2; return 4 /2 + 3;",      
            List.of(
                new IfLine(
                    new BracketExpr(
                        new OrExpr(
                            new AndExpr(
                                new LThanEqExpr(
                                    new SubExpr(new IntExpr(9), new IntExpr(3)), 
                                    new AddExpr(new IntExpr(10), new IntExpr(2))),
                                new NotEqExpr(new VarExpr("b"), new VarExpr("c"))),
                            new VarExpr("false"))
                    ),
                    new CodeBlockBuilder()
                        .addDeclare("i", "int", new IntExpr(10))
                        .addPrint(new IntExpr(9))
                        .build()
                    ),
                new DeclarLine("i", "int", new IntExpr(0)),
                new VarOverwrite("i", new IntExpr(2)),
                new ReturnLine(new AddExpr(new DivExpr(new IntExpr(4), new IntExpr(2)), new IntExpr(3)))
             ));
    }


    public static void main(String[] args) {  
        testErrorFunctions();
    }

    public static void testErrorFunctions(){
        assertError(
            "var e + 14; return 12; ",
            "P10"
        );
        assertError(
            "+ e = 14; return 12; ",
            "P10"
        );
    }

    private static void assertError(String message, String errorcode){
        var e = CodeBlockParser.getCodeBlock(Tokenizer.convertToTokens(message));
        if(!e.hasError()){
            System.out.println("Error missed code - " + errorcode);
            assertTrue(false);
        } else {
            assertTrue(errorcode.equals(e.getError().getFullErrorCode()));
        }
    }

    public static void assertEq(String textBlock, List<Line> lines){
        var expr = CodeBlockParser.getCodeBlock(Tokenizer.convertToTokens(textBlock));
        if(expr.hasError()){
            System.out.println(expr.getError().getFullErrorCode());
            assertTrue(false);
        }
        var nLines = expr.getValue().getLines();
        if(nLines.size() != lines.size()){
            assertTrue(false);
        }
        for(int i = 0; i < nLines.size();i++){
            if(!nLines.get(i).makeString(0).equals(lines.get(i).makeString(0))){
                System.out.println(nLines.get(i).makeString(0));
                System.out.println(lines.get(i).makeString(0));
                assertTrue(false);
            }
        } 
    }
}
