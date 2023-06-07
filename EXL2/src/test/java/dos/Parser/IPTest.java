package dos.Parser;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.FunctionParser;
import dos.EXL.Parser.ImportParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Tokenizer.Types.Token;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IPTest extends TestCase{
    
    public static Test suite(){
        return new TestSuite(IPTest.class);
    }
    
    public static void main(String[] args) {
        testFunctions();
    }

    public static void testFunctions(){
        assertValid(
            "import DooDoo.Barry as Bars; import LooLoo.Barry;",
            List.of(new Pair<>("Bars","DooDoo.Barry"), new Pair<>("Barry", "LooLoo.Barry"))
        );
        assertValid(
            "import Barry as Bars; import ree.eee.eee.eee.eee.LooLoo.Barry;",
            List.of(new Pair<>("Bars","Barry"), new Pair<>("Barry", "ree.eee.eee.eee.eee.LooLoo.Barry"))
        );
    }

    public static void testErrors(){
        assertError(
            "import int as double",
            "P2"
        );
        assertError(
            "import barry as larry",
            "P2"
        );
        assertError(
            "import lee.. as double",
            "P2"
        );
        assertError(
            "import r",
            "P2"
        );
        assertError(
            "import r",
            "P2"
        );
        assertError(
            "import ",
            "P2"
        );
        assertError(
            "import r as ;",
            "P2"
        );
        assertError(
            "import r le",
            "P2"
        );
    }

    private static void assertValid(String msg, List<Pair<String,String>> imports) {
        List<Token> tokens = Tokenizer.convertToTokens(msg);
        var e = ImportParser.parse(tokens);
        if(e.hasError()){
            System.out.println(e.getError().getFullErrorCode());
            assertTrue(false);
        }
        var importsM = e.getValue();
        assertTrue(importsM.size() == imports.size());
        for(int i =0; i < imports.size(); i++){
            assertTrue(importsM.get(i).getValue0().equals(imports.get(i).getValue0()));
            assertTrue(importsM.get(i).getValue1().equals(imports.get(i).getValue1()));
        }
    }


    private static void assertError(String message, String errorcode){
        var e = FunctionParser.getFunction(Tokenizer.convertToTokens(message));
        if(!e.hasError()){
            System.out.println("Error missed code - " + errorcode);
            assertTrue(false);
        } else {
            assertTrue(errorcode.equals(e.getError().getFullErrorCode()));
        }
    }
    
}
