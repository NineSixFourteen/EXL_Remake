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
                    }
                    break;
                case '\"':
                    Pair<String, Integer> info = StringGrabber.getString(message, place);
                    System.out.println("sds");
                    tokens.add(new Token(TokenType.ValueString, info.getValue0()));
                    place = info.getValue1();
                    break;
                case '+':
                case '-':
                case '*':
                case '.':
                case ')':
                case '(':
                case ']':
                case '[':
                case '}':
                case '{':
                case ',':
                case ';':
                case ':':
                case '=':
                case '>':
                case '<':
                case '!':
                case '%':
                    tokens.add(CharConverter.accept(message.charAt(place)));
                    break;
                case '\n':
                case ' ':
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


    
}
