package dos.Parser.Builders;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Code Block Builder Tests
public class CBBTest extends TestCase {
    

    public static Test suite(){
        return new TestSuite(CBBTest.class);
    }

    public static void main(String[] args) {
        testCodeBlockBuilder();
    }

    public static void testCodeBlockBuilder(){
        CodeBlockBuildHelp("int i = 0;\ni = 4;\nreturn 3;\nprint 5;\n",
          new CodeBlockBuilder()
          .addDeclare("i", "int", new IntExpr(0))
          .addVarO("i", new IntExpr(4))
          .addReturn(new IntExpr(3))
          .addPrint(new IntExpr(5))
        );
        CodeBlockBuildHelp("if (i < 10){\n\tprint i;\n}\n",
        new CodeBlockBuilder()
        .addIf(new BracketExpr(new LThanExpr(new VarExpr("i"), new IntExpr(10))), 
            new CodeBlockBuilder()
            .addPrint(new VarExpr("i"))
            .build()
        )
      );
    }


    public static void CodeBlockBuildHelp(String code, CodeBlockBuilder cbb){
        StringBuilder sb = new StringBuilder();
        for(Line l : cbb.build().getLines()){
            sb.append(l.makeString(0));
        }
        if(!code.equals(sb.toString())){
            System.out.println(code);
            System.out.println(sb.toString());
        }
        assertTrue(code.equals(sb.toString()));
    }

}
