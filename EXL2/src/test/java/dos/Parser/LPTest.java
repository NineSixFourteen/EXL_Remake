package dos.Parser;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import dos.Parser.Builders.CodeBlockBuilder;
import dos.Tokenizer.Tokenizer;
import dos.Types.Expression;
import dos.Types.Line;
import dos.Types.Binary.Boolean.GThanExpr;
import dos.Types.Binary.Boolean.LThanExpr;
import dos.Types.Lines.CodeBlock;
import dos.Types.Lines.DeclarLine;
import dos.Types.Lines.IfLine;
import dos.Types.Unary.BracketExpr;
import dos.Types.Unary.Types.CharExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.StringExpr;
import dos.Util.Result;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Line Parser Tests
public class LPTest extends TestCase  {

    public static Test suite(){
        return new TestSuite(LPTest.class);
    }

    public static void main(String[] args) {
        //testDeclare();
        //testVarOverwrite();
        testIf();
    }

    static void testDeclare(){
        testDeclareHelper("int i = 0;","i", "int", "0");
        testDeclareHelper("float f = 0.2;","f", "float", "0.2");
        testDeclareHelper("char c = 'c';","c", "char", "'c'");
        testDeclareHelper("String s = \"lala\";","s", "string", "\"lala\"");
    }

    static void testDeclareHelper(String msg, String name, String type, String expected){
        Result<Triplet<String, String, Expression>, Error> result = LineParser.getDeclare(Tokenizer.convertToTokens(msg)); 
        if(result.hasError()){
            assertTrue(false);
        } else {
            var x = result.getValue();
            if(!type.equals(x.getValue0())){
                System.out.println(type);
                System.out.println(x.getValue0());
            }
            if(!name.equals(x.getValue1())){
                System.out.println(name);
                System.out.println(x.getValue1());
            }
            if(!x.getValue2().makeString().equals(expected)){
                System.out.println(expected);
                System.out.println(x.getValue2().makeString());
            }
        }
    }

    static void testIf(){
        testIfHelper("if 9 < 10 { int i = 0;}", new LThanExpr(new IntExpr(9), new IntExpr(10)), List.of(new DeclarLine("i","int", new IntExpr(0))));
        testIfHelper("if 9 < 10 { int i = 0;char c = 'c';}", new LThanExpr(new IntExpr(9), new IntExpr(10)), List.of(
            new DeclarLine("i","int", new IntExpr(0)),
            new DeclarLine("c","char", new CharExpr('c'))));
        var cbb = new CodeBlockBuilder();
        cbb.addDeclare("s", "string", new StringExpr("sassa"));
        testIfHelper("if 9 < 10 { int i = 0;char c = 'c';if(9 > 10){String s = \"sassa\";}}", new LThanExpr(new IntExpr(9), new IntExpr(10)), 
        List.of(
            new DeclarLine("i","int", new IntExpr(0)),
            new DeclarLine("c","char", new CharExpr('c')),
            new IfLine(
                new BracketExpr(new GThanExpr(new IntExpr(9), new IntExpr(10))),
                cbb.build()
            ))
        );

    }

    static void testIfHelper(String msg, Expression exp, List<Line> lines){
        Result<Pair<Expression, CodeBlock>, Error> result = LineParser.getIf(Tokenizer.convertToTokens(msg)); 
        if(result.hasError()){
            assertTrue(false);
        } else {
            var x = result.getValue();
            if(!exp.makeString().equals(x.getValue0().makeString())){
                System.out.println(exp.makeString());
                System.out.println(x.getValue0().makeString());
                assertTrue(false);
            }
            List<Line> cb = x.getValue1().getLines();
            assertTrue(lines.size() == cb.size());
            for(int i = 0; i < cb.size();i++){
                if(!lines.get(i).makeString(i).equals(cb.get(i).makeString(i))){
                    System.out.println(lines.get(i).makeString(i));
                    System.out.println(cb.get(i).makeString(i));
                    assertTrue(false);
                }
                
            }
        }
    }

    static void testVarOverwrite(){
        testVarOverwriteHelper("x = 10;", "x", new IntExpr(10));
        testVarOverwriteHelper("c = 'd';", "c", new CharExpr('d'));
        testVarOverwriteHelper("b = 9 < 10;", "b", new LThanExpr(new IntExpr(9), new IntExpr(10)));
    }

    static void testVarOverwriteHelper(String message, String name, Expression exptect){
        Result<Pair<String,Expression>, Error> result = LineParser.getVarOver(Tokenizer.convertToTokens(message)); 
        if(result.hasError()){
            assertTrue(false);
        } else {
            var x = result.getValue();
            if(!name.equals(x.getValue0())){
                System.out.println(name);
                System.out.println(x.getValue0());
            }
            if(!x.getValue1().makeString().equals(exptect.makeString())){
                System.out.println(exptect.makeString());
                System.out.println(x.getValue1().makeString());
            }
        }
    }
    
}
