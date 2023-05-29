package dos.Parser;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import dos.EXL.Parser.LineParser;
import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.Field;
import dos.EXL.Types.Lines.IfLine;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.CharExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Result;
import dos.Util.Results;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Line Parser Tests
public class LPTest extends TestCase  {

    public static Test suite(){
        return new TestSuite(LPTest.class);
    }

    public static void testDeclare(){
        DeclareHelper("int i = 0;","i", "int", "0");
        DeclareHelper("float f = 0.2;","f", "float", "0.2");
        DeclareHelper("char c = 'c';","c", "char", "'c'");
        DeclareHelper("String s = \"lala\";","s", "string", "\"lala\"");
    }

    public static void DeclareHelper(String msg, String name, String type, String expected){
        Result<Triplet<String, String, Expression>> result = LineParser.getDeclare(Tokenizer.convertToTokens(msg)); 
        if(result.hasError()){
            System.out.println(result.getError());
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

    public static void testIf(){
        IfHelper("if 9 < 10 { int i = 0;}", new LThanExpr(new IntExpr(9), new IntExpr(10)), List.of(new DeclarLine("i","int", new IntExpr(0))));
        IfHelper("if 9 < 10 { int i = 0;char c = 'c';}", new LThanExpr(new IntExpr(9), new IntExpr(10)), List.of(
            new DeclarLine("i","int", new IntExpr(0)),
            new DeclarLine("c","char", new CharExpr('c'))));
        var cbb = new CodeBlockBuilder();
        cbb.addDeclare("s", "string", new StringExpr("sassa"));
        IfHelper("if 9 < 10 { int i = 0;char c = 'c';if(9 > 10){String s = \"sassa\";}}", new LThanExpr(new IntExpr(9), new IntExpr(10)), 
        List.of(
            new DeclarLine("i","int", new IntExpr(0)),
            new DeclarLine("c","char", new CharExpr('c')),
            new IfLine(
                new BracketExpr(new GThanExpr(new IntExpr(9), new IntExpr(10))),
                cbb.build()
            ))
        );

    }

    public static void IfHelper(String msg, Expression exp, List<Line> lines){
        Result<Pair<Expression, CodeBlock>> result = LineParser.getIf(Tokenizer.convertToTokens(msg)); 
        if(result.hasError()){
            System.out.println(result.getError().getFullErrorCode());
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

    private static void assertError(Result<Line> result, String errorcode){
        if(!result.hasError()){
            System.out.println("Error missed code - " + errorcode);
            assertTrue(false);
        } else {
            var error = result.getError();
            assertTrue(errorcode.equals(error.getFullErrorCode()));
        }
    }

    public static void main(String[] args) {
        testErrorFunctions();
    }

    public static void testErrorFunctions(){
        var feildInfo = LineParser.getField(Tokenizer.convertToTokens("private int double = 10;"));
        assertError(
            feildInfo.hasError() ? Results.makeError(feildInfo.getError()) : Results.makeResult(feildInfo.getValue())//WOW this fells dumb convert from Result<Feild> to Result<Line>
            ,"P2"
        );
        var declareInfo = LineParser.getDeclare(Tokenizer.convertToTokens("int double = 10;"));
        var quick = declareInfo.getValue();
        assertError(
            declareInfo.hasError() ? Results.makeError(declareInfo.getError()) : 
                    Results.makeResult(LineFactory.IninitVariable(quick.getValue0(), quick.getValue1(), quick.getValue2()))
            ,"P2"
        );
        var ifInfo = LineParser.getIf(Tokenizer.convertToTokens("if(9 < 10 {return 8;}"));
        var quick2 = ifInfo.getValue();
        assertError(
            ifInfo.hasError() ? Results.makeError(ifInfo.getError()) : 
                    Results.makeResult(LineFactory.ifL(quick2.getValue0(), quick2.getValue1()))
            ,"P2"
        );
    }

    public static void testFieldHelper(){
        FieldHelper("private boolean bees = false", new Field(List.of(Tag.Private), "bees", new VarExpr("false"), "boolean"));
        FieldHelper("public int inner = 5", new Field(List.of(Tag.Public), "inner", new IntExpr(5), "int"));
        FieldHelper("static String sock = \"lala\"", new Field(List.of(Tag.Static), "sock", new StringExpr("lala"), "string"));
        FieldHelper("public static float oats = 5.0", new Field(List.of(Tag.Public, Tag.Static), "oats", new FloatExpr(5), "float"));
        FieldHelper("private static char cull = 'c'", new Field(List.of(Tag.Private, Tag.Static), "cull", new CharExpr('c'), "char"));
    }

    public static void FieldHelper(String message, Field field){
        Result<Field> result = LineParser.getField(Tokenizer.convertToTokens(message)); 
        if(result.hasError()){
            System.out.println(result.getError().getFullErrorCode());
            assertTrue(false);
        } else {
            if(!result.getValue().makeString(0).equals(field.makeString(0))){
                System.out.println(field.makeString(0));
                System.out.println(result.getValue().makeString(0));
                assertTrue(false);
            }
        }
    }

    public static void testVarOverwrite(){
        VarOverwriteHelper("x = 10;", "x", new IntExpr(10));
        VarOverwriteHelper("c = 'd';", "c", new CharExpr('d'));
        VarOverwriteHelper("b = 9 < 10;", "b", new LThanExpr(new IntExpr(9), new IntExpr(10)));
    }

    public static void VarOverwriteHelper(String message, String name, Expression exptect){
        Result<Pair<String,Expression>> result = LineParser.getVarOver(Tokenizer.convertToTokens(message)); 
        if(result.hasError()){
            System.out.println(result.getError().getFullErrorCode());
            assertTrue(false);
        } else {
            var x = result.getValue();
            if(!name.equals(x.getValue0())){
                System.out.println(name);
                System.out.println(x.getValue0());
                assertTrue(false);
            }
            if(!x.getValue1().makeString().equals(exptect.makeString())){
                System.out.println(exptect.makeString());
                System.out.println(x.getValue1().makeString());
                assertTrue(false);
            }
        }
    }
    
}
