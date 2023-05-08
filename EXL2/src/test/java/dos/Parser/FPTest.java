package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import dos.Parser.Builders.CodeBlockBuilder;
import dos.Parser.Builders.FunctionBuilder;
import dos.Tokenizer.Tokenizer;
import dos.Types.Function;
import dos.Types.Tag;
import dos.Types.Unary.Types.IntExpr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Function Parser Tests
public class FPTest extends TestCase{
    
    public static Test suite(){
        return new TestSuite(FPTest.class);
    }

    private static void assertEq(String msg, Function func) {
        var e = FunctionParser.getFunction(Tokenizer.convertToTokens(msg));
        if(e.hasError()){
            System.out.println(e.getError().getMessage());
            assertTrue(false);
        }
        if(!func.makeString().equals(e.getValue().makeString())){
            System.out.println(func.makeString());
            System.out.println(e.getValue().makeString());
        }
        assertTrue(func.makeString().equals(e.getValue().makeString()));
    }

    public static void main(String[] args) {
        assertEq(
            "public static void main(String args){int i = 0;}",
            new FunctionBuilder()
                .addTag(Tag.Public).addTag(Tag.Static)
                .setName("main")
                .setType("void")
                .addParameter("String","args")
                .setBody(
                    new CodeBlockBuilder()
                    .addDeclare("i", "int", new IntExpr(0))
                    .build()
                )
                .build()    
        );
    }



}
