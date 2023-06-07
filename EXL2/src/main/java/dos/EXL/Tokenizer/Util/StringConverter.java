package dos.EXL.Tokenizer.Util;

import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;

public class StringConverter {
    
    public static Token accept(String s){
        switch(s){
            case "print":return new Token(TokenType.Print);
            case "if":return new Token(TokenType.If);
            case "else":return new Token(TokenType.Else);
            case "for":return new Token(TokenType.For);
            case "switch":return new Token(TokenType.Switch);
            case "default":return new Token(TokenType.Default);
            case "while":return new Token(TokenType.While);
            case "case":return new Token(TokenType.Case);
            case "new":return new Token(TokenType.New);
            case "break":return new Token(TokenType.Break);
            case "try":return new Token(TokenType.Try);
            case "catch":return new Token(TokenType.Catch);
            case "return":return new Token(TokenType.Return);
            case "throws":return new Token(TokenType.Throws);
            case "public":return new Token(TokenType.Public);
            case "private":return new Token(TokenType.Private);
            case "static":return new Token(TokenType.Static);
            case "null":return new Token(TokenType.Null);
            case "int":return new Token(TokenType.Int);
            case "double":return new Token(TokenType.Double);
            case "float":return new Token(TokenType.Float);
            case "boolean":return new Token(TokenType.Boolean);
            case "String":return new Token(TokenType.String);
            case "void":return new Token(TokenType.Void);
            case "var":return new Token(TokenType.Var);
            case "char":return new Token(TokenType.Char);
            case "class":return new Token(TokenType.Class);
            case "as": return new Token(TokenType.as);
            case "import": return new Token(TokenType.Import);
            default: return getValue(s);
        }
    }

    public static Token getValue(String s){
        try{
            Integer.parseInt(s);
            return new Token(TokenType.ValueInt, s);
        } catch(Exception e){
            return new Token(TokenType.Value, s);
        }
    }

}
