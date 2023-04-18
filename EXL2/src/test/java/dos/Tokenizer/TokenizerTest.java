package dos.Tokenizer;

import java.util.List;

import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TokenizerTest extends TestCase {

    public static void main(String[] args) {
        testTokens();
    }
    
    public static Test suite(){
        return new TestSuite(TokenizerTest.class );
    }

    public void testApp(){
        assertTrue( true );
    }

    public static boolean assertEqual(String message, Token[] tokens){
        return arrayEquality(tokens, Tokenizer.convertToTokens(message));
    }

    public static boolean arrayEquality(Token[] tokens, List<Token> toks){
        if(tokens.length != toks.size()){
            return false;
        }
        for(int i = 0; i < tokens.length;i++){
            if(!tokenEq(tokens[i],toks.get(i))){
                System.out.println(tokens[i]);
                System.out.println(toks.get(i));
                return false;
            }
        }
        return true;
    }

    public static boolean tokenEq(Token t1, Token t2){
        if(t1.getType() != t2.getType()){
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

    public static void testTokens(){
        var expected = new Token[]{
            new Token(TokenType.ValueInt, "9"), new Token(TokenType.Plus), new Token(TokenType.ValueInt, "10"), new Token(TokenType.Div), 
            new Token(TokenType.ValueInt, "3"),new Token(TokenType.Mul), new Token(TokenType.ValueInt, "8"), new Token(TokenType.Div), 
            new Token(TokenType.ValueInt, "2"), new Token(TokenType.Value, "Bars"), new Token(TokenType.Comma), new Token(TokenType.Value, "Aru"),
            new Token(TokenType.ValueFloat, "2.2"), new Token(TokenType.ValueString, "lala")
        };
        assertTrue(assertEqual("9 + 10 / 3 *8/2 Bars,Aru 2.2 \"lala\" ",expected ));
        var expected2 = new Token[]{
            new Token(TokenType.Print), new Token(TokenType.Value, "i"), new Token(TokenType.LThan), new Token(TokenType.ValueInt, "9"), new Token(TokenType.SemiColan)
        };
        assertTrue(assertEqual("print i < 9;", expected2));
        var expected3 = new Token[]{
            new Token(TokenType.Char), new Token(TokenType.Value, "c"), new Token(TokenType.Equal), new Token(TokenType.ValueChar, "c"), new Token(TokenType.SemiColan)
        };
        assertTrue(assertEqual("char c = 'c';", expected3));
        var expected4 = new Token[]{
            new Token(TokenType.Value, "Das"), new Token(TokenType.Dot), new Token(TokenType.Value, "boos"), new Token(TokenType.LBracket), 
            new Token(TokenType.ValueString, "Lol"), new Token(TokenType.Comma), new Token(TokenType.ValueInt, "2"), new Token(TokenType.RBracket),
            new Token(TokenType.SemiColan)
        };
        assertTrue(assertEqual("Das.boos(\"Lol\", 2);", expected4));
    }

}
