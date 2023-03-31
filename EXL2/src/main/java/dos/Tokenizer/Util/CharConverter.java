package dos.Tokenizer.Util;

import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;

public class CharConverter {

    public static Token accept(char c){
        switch(c){
            case '&':return new Token(TokenType.And);
            case '|':return new Token(TokenType.Or);
            case '+':return new Token(TokenType.Plus);
            case '-':return new Token(TokenType.Minus);
            case '*':return new Token(TokenType.Mul);
            case '/':return new Token(TokenType.Div);
            case '.':return new Token(TokenType.Dot);
            case ')':return new Token(TokenType.RBracket);
            case '(':return new Token(TokenType.LBracket);
            case ']':return new Token(TokenType.RSquare);
            case '[':return new Token(TokenType.LSquare);
            case '}':return new Token(TokenType.RBrace);
            case '{':return new Token(TokenType.LBrace);
            case ',':return new Token(TokenType.Comma);
            case ';':return new Token(TokenType.SemiColan);
            case ':':return new Token(TokenType.Colan);
            case '=':return new Token(TokenType.Equal);
            case '>':return new Token(TokenType.LThan);
            case '<':return new Token(TokenType.GThan);
            case '!':return new Token(TokenType.Not);
            case '%':return new Token(TokenType.Mod);
            default:
                System.out.println(c);
                return new Token(TokenType.Unknown);
        }
        
    }
    
}
