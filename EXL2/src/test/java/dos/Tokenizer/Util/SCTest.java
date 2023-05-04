package dos.Tokenizer.Util;

import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// String Converter Tests
public class SCTest extends TestCase{

    static boolean debug = false;

    public static void main(String[] args) {
        testValues();
    }
    

    public static Test suite(){
        return new TestSuite(SCTest.class );
    }

    public static void testValues(){
        assertEq("221313", new Token(TokenType.ValueInt, "221313"));
        assertEq("323A", new Token(TokenType.Value, "323A"));
        assertEq("IF", new Token(TokenType.Value, "IF"));
        assertEq("if", new Token(TokenType.If));
        assertEq("print", new Token(TokenType.Print));
    }

    public static void assertEq(String msg, Token t){
        assertTrue(tokenEq(StringConverter.accept(msg),t));
    }

    public static boolean tokenEq(Token t1, Token t2){
        if(t1.getType() != t2.getType()){
            if(debug){
                System.out.println(t1);
                System.out.println(t2);
            }
            return false;
        }
        switch(t1.getType()){
            case Value:
            case ValueChar:
            case ValueFloat:
            case ValueInt:
            case ValueString:
                return t1.getValue().equals(t2.getValue());
            default: 
                return true;
        }
    }


    

}
