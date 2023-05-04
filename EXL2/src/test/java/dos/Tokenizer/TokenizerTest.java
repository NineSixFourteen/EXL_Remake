package dos.Tokenizer;

import java.util.List;

import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TokenizerTest extends TestCase {

    static boolean debug = false;

    public static void main(String[] args) {
        testSymbols();
        testLine();
        testChar();
        testDot();
    }

    public static void testSymbols(){
        var expected = new Token[]{
            new Token(TokenType.ValueInt, "9"), new Token(TokenType.Plus), new Token(TokenType.ValueInt, "10"), new Token(TokenType.Div), 
            new Token(TokenType.ValueInt, "3"),new Token(TokenType.Mul), new Token(TokenType.ValueInt, "8"), new Token(TokenType.Div), 
            new Token(TokenType.ValueInt, "2"), new Token(TokenType.Value, "Bars"), new Token(TokenType.Comma), new Token(TokenType.Value, "Aru"),
            new Token(TokenType.ValueFloat, "2.2"), new Token(TokenType.ValueString, "lala")
        };
        assertEqual("9 + 10 / 3 *8/2 Bars,Aru 2.2 \"lala\" ",expected );
    }

    public static void testLine(){
        var expected2 = new Token[]{
            new Token(TokenType.Print), new Token(TokenType.Value, "i"), new Token(TokenType.LThan), new Token(TokenType.ValueInt, "9"), new Token(TokenType.SemiColan)
        };
        assertEqual("print i < 9;", expected2);
    }

    public static void testChar(){
        var expected3 = new Token[]{
            new Token(TokenType.Char), new Token(TokenType.Value, "c"), new Token(TokenType.Equal), new Token(TokenType.ValueChar, "c"), new Token(TokenType.SemiColan)
        };
        assertEqual("char c = 'c';", expected3);
    }
    public static void testDot(){
        var expected4 = new Token[]{
            new Token(TokenType.Value, "Das"), new Token(TokenType.Dot), new Token(TokenType.Value, "boos"), new Token(TokenType.LBracket), 
            new Token(TokenType.ValueString, "Lol"), new Token(TokenType.Comma), new Token(TokenType.ValueFloat, "2.4"), new Token(TokenType.Comma), new Token(TokenType.ValueChar, "s"), new Token(TokenType.RBracket),
            new Token(TokenType.SemiColan)
        };
        assertEqual("Das.boos(\"Lol\", 2.4, 's');", expected4);
    }

    // Helpers 
        
    public static Test suite(){
        return new TestSuite(TokenizerTest.class );
    }

    public static void assertEqual(String message, Token[] tokens){
        assertTrue(arrayEquality(tokens, Tokenizer.convertToTokens(message)));
    }

    public static boolean arrayEquality(Token[] tokens, List<Token> toks){
        if(tokens.length != toks.size()){
            return false;
        }
        for(int i = 0; i < tokens.length;i++){
            if(!tokenEq(tokens[i],toks.get(i))){
                if(debug){
                    System.out.println(tokens[i]);
                    System.out.println(toks.get(i));
                }
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

}
