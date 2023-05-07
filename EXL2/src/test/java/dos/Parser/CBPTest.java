package dos.Parser;

import java.util.List;

import dos.Parser.Builders.CodeBlockBuilder;
import dos.Tokenizer.Tokenizer;
import dos.Types.Line;
import dos.Types.Binary.ObjectFuncExpr;
import dos.Types.Binary.Boolean.AndExpr;
import dos.Types.Binary.Boolean.LThanEqExpr;
import dos.Types.Binary.Boolean.LThanExpr;
import dos.Types.Binary.Boolean.NotEqExpr;
import dos.Types.Binary.Boolean.OrExpr;
import dos.Types.Binary.Maths.AddExpr;
import dos.Types.Binary.Maths.DivExpr;
import dos.Types.Binary.Maths.SubExpr;
import dos.Types.Lines.DeclarLine;
import dos.Types.Lines.ExpressionLine;
import dos.Types.Lines.IfLine;
import dos.Types.Lines.PrintLine;
import dos.Types.Lines.ReturnLine;
import dos.Types.Lines.VarOverwrite;
import dos.Types.Unary.BracketExpr;
import dos.Types.Unary.FunctionExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Code BlocK Parser Tests
public class CBPTest  extends TestCase{
    
    public static Test suite(){
        return new TestSuite(CBPTest.class);
    }

    public static void main(String[] args) {  
        testBlocks();
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

    public static void assertEq(String textBlock, List<Line> lines){
        var expr = CodeBlockParser.getCodeBlock(Tokenizer.convertToTokens(textBlock));
        if(expr.hasError()){
            System.out.println(expr.getError().getMessage());
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
