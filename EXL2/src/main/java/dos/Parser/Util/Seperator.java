package dos.Parser.Util;

import java.util.ArrayList;
import java.util.List;

import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;

public class Seperator {

    public static List<List<Token>> splitOnCommas(List<Token> tokens){
        List<List<Token>> res = new ArrayList<>();
        int point = 0;
        List<Token> temp = new ArrayList<>();
        while(point < tokens.size()){
            if(tokens.get(point).getType() == TokenType.Comma){
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
