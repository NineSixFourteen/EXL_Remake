package dos.EXL.Tokenizer.Types;

public class Token {
    TokenType Type;
    String Value; 

    public Token(TokenType t, String val ) {
        this.Type = t ;
        this.Value = val;
    }

    public Token(TokenType t ) {
        this.Type = t ;
        this.Value = null;
    }

    public TokenType getType() {
        return Type;
    }

    public String getValue(){
        return Value;
    }

    public String toString() {
        return Type + (Value == null ? "" : " " + Value) ;
    }
}
