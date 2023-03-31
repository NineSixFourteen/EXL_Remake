package dos.Tokenizer.Util;

import org.javatuples.Pair;

public class StringGrabber {
    
    public static Pair<String, Integer> getString(String message, int place) {
        place++;
        int start = place;
        while(message.charAt(place) != '"'){
            place++;
            if(place == message.length()){
                return new Pair<String,Integer>("ss", 0); //TODO Throw Error
            }
        }
        return new Pair<String,Integer>(message.substring(start, place), place);
    }
    
    public static Pair<String, Integer> getWord(String message, int place) {
        int start = place;
        place++;
        boolean b = true;
        while(b){
            if(place == message.length()){
                return new Pair<String,Integer>(message.substring(start, place), place-1);
            }
            switch(message.charAt(place)){
                case ' ':case';':case':':case',':case'.':case'&':case'|':
                case '+': case '-':case '*': case'/':case'%':
                case '{':case'}':case '[':case']':case '(':case')': 
                case '<':case '>':case '=':case '!':
                b = false;
            }
            place++;
        }
        return new Pair<String,Integer>(message.substring(start, place - 1), place -2);
    }
    
}
