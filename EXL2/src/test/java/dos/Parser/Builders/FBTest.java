package dos.Parser.Builders;

import java.util.List;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Builders.FunctionBuilder;
import dos.EXL.Types.Line;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Binary.ObjectFieldExpr;
import dos.EXL.Types.Binary.ObjectFuncExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Binary.Maths.MulExpr;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Parser.EPTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Function Builder Tests
public class FBTest extends TestCase {
    

    public static Test suite(){
        return new TestSuite(FBTest.class);
    }

    public static void main(String[] args) {
        testfunctionBlock();
    }

    public static void testfunctionBlock(){
        functionBlockHelper("public static void main(String arg){\n\tprint 95;\n\tprint 92;\n\tprint \"lala\";\n\tabs.cbs.as(9);\n}",
            new FunctionBuilder()
                .setName("main")
                .setType("void")
                .addTag(Tag.Public)
                .addTag(Tag.Static)
                .addParameter("String","arg")
                .setBody(
                    new CodeBlockBuilder()
                        .addPrint(new IntExpr(95))
                        .addPrint(new IntExpr(92))
                        .addPrint(new StringExpr("lala"))
                        .addExpr(new ObjectFuncExpr(new ObjectFieldExpr(new VarExpr("abs"), "cbs"), new FunctionExpr("as", List.of(new IntExpr(9)))))
                        .build()
                )
        );
        functionBlockHelper("private int Lopa(int a, float b){\n\treturn a / b * a * 3;\n}",
            new FunctionBuilder()
                .addTag(Tag.Private)
                .setType("int")
                .setName("Lopa")
                .addParameter("int", "a")
                .addParameter("float", "b")
                .setBody(
                    new CodeBlockBuilder()
                    .addReturn(new MulExpr(new MulExpr(new DivExpr(new VarExpr("a"), new VarExpr("b")), new VarExpr("a")), new IntExpr(3)))
                    .build()
                )
        );
    }


    public static void functionBlockHelper(String code, FunctionBuilder fb){
        if(!code.equals(fb.build().makeString())){
            System.out.println(code);
            System.out.println(fb.build().makeString());
            assertTrue(false);
        }
       
    }

}
