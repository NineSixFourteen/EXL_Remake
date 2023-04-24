package dos.Tokenizer;

import java.util.ArrayList;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import dos.Tokenizer.Util.CharConverter;
import dos.Tokenizer.Util.StringConverter;
import dos.Tokenizer.Util.StringGrabber;

public class Tokenizer {

    public static ArrayList<Token> convertToTokens(String message){
        ArrayList<Token> tokens = new ArrayList<>();
        int place = 0;
        while(place < message.length()){
            switch(message.charAt(place)){
                case '&':
                    if(message.charAt(place + 1) == '&'){
                        tokens.add(new Token(TokenType.And));
                        place++;
                    }
                    break;
                case '|':
                    if(message.charAt(place + 1) == '|'){
                        tokens.add(new Token(TokenType.Or));
                        place++;
                    }
                    break;
                case '\\':
                    break;
                case '/':
                    if(message.charAt(place + 1) == '/'){
                        tokens.add(new Token(TokenType.Comment));
                        place++;
                    } else {
                        tokens.add(CharConverter.accept(message.charAt(place)));
                    }
                    break;
                case '\"':
                    Pair<String, Integer> info = StringGrabber.getString(message, place);
                    tokens.add(new Token(TokenType.ValueString, info.getValue0()));
                    place = info.getValue1();
                    break;
                case '=':
                case '>':
                case '<':
                case '!':
                    if(message.charAt(place + 1) == '='){
                        tokens.add(getEq(message.charAt(place)));
                        place++;
                        break;
                    } else {
                        tokens.add(CharConverter.accept(message.charAt(place)));
                    }
                    break;
                case '+':
                case '-':
                case '*':
                case ')':
                case '(':
                case ']':
                case '[':
                case '}':
                case '{':
                case ',':
                case ';':
                case ':':
                case '%':
                    tokens.add(CharConverter.accept(message.charAt(place)));
                    break;
                case '\n':
                case ' ':
                    break;
                case '.':
                    if(tokens.get(tokens.size() -1 ).getType() == TokenType.ValueInt){
                        String firstPart = tokens.get(tokens.size() -1 ).getValue();
                        Pair<String, Integer> word = StringGrabber.getWord(message, place);
                        tokens.remove(tokens.size() -1);
                        place = word.getValue1();
                        tokens.add(new Token(TokenType.ValueFloat, firstPart + word.getValue0()));
                    } else {
                        tokens.add(new Token(TokenType.Dot));
                    }
                    break;
                case '\'':
                    Pair<String, Integer> charV = StringGrabber.getChar(message, place);
                    tokens.add(new Token(TokenType.ValueChar, charV.getValue0()));
                    place = charV.getValue1();
                    break;
                default:
                    Pair<String, Integer> word = StringGrabber.getWord(message, place);
                    if(word.getValue0().trim().length() != 0){
                        tokens.add(StringConverter.accept(word.getValue0()));
                        place = word.getValue1();
                    }
                    break;
            }
            place++;
        }
        return tokens;
    }

    private static Token getEq(char c) {
       switch(c){
        case '<':
            return new Token(TokenType.LThanEq);
        case '>':
            return new Token(TokenType.GThanEq);
        case '!':
            return new Token(TokenType.NotEqualTo);
        case '=':
            return new Token(TokenType.EqualTo);
        default: 
            return new Token(TokenType.Unknown);
       }
    }


    
}
