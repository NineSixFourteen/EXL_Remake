package dos.EXL.Parser.Util;

import java.util.ArrayList;
import java.util.List;

import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;

public class Seperator {

    public static List<List<Token>> splitOnCommas(List<Token> tokens){
        return splitOnSymbol(tokens, TokenType.Comma);
    }

        public static List<List<Token>> splitOnAndKeepSemiColans(List<Token> tokens){
        var x = splitOnSymbol(tokens, TokenType.SemiColan);
        for(var y : x){
            y.add(new Token(TokenType.SemiColan));
        }
        return x;
    }

    public static List<List<Token>> splitOnSymbol(List<Token> tokens, TokenType symbol){
        List<List<Token>> res = new ArrayList<>();
        int point = 0;
        List<Token> temp = new ArrayList<>();
        while(point < tokens.size()){
            if(tokens.get(point).getType() == symbol){
                res.add(temp);
                temp = new ArrayList<>();
            } else {
                temp.add(tokens.get(point));
            }
            point++;
        }
        res.add(temp);
        return res;
    }
    
}
