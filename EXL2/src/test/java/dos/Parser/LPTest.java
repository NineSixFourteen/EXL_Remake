package dos.Parser;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import dos.Tokenizer.Tokenizer;
import dos.Types.Expression;

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
        testDeclare();
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
    
}
