package dos.Parser.Builders;

import java.util.List;

import dos.Types.Tag;
import dos.Types.Binary.ObjectFieldExpr;
import dos.Types.Binary.ObjectFuncExpr;
import dos.Types.Lines.Field;
import dos.Types.Unary.FunctionExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.StringExpr;
import dos.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Program Builder Tests
public class PBTest extends TestCase {
    

    public static Test suite(){
        return new TestSuite(PBTest.class);
    }

    public static void main(String[] args) {
        testProgramBuilder();
    }

    public static void testProgramBuilder(){
        programBuilerHelper("public class Dan{\npublic static int a = 10;\npublic static void main(String arg){\n\tprint 95;\n\tprint 92;\n\tprint \"lala\";\n\tabs.cbs.as(9);\n}}",
            new ProgramBuilder()
                .setName("Dan")
                .addTag(Tag.Public)
                .addField(new Field(List.of(Tag.Public, Tag.Static), "a", new IntExpr(10), "int"))
                .addFunction(
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
                    ).build()
                )
        );

    }


    public static void programBuilerHelper(String code, ProgramBuilder pb){
        if(!code.equals(pb.build().makeString())){
            System.out.println(code);
            System.out.println(pb.build().makeString());
            assertTrue(false);
        }
       
    }

}
