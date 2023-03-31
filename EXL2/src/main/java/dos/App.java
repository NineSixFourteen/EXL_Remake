package dos;

import java.util.List;

import dos.Tokenizer.Tokenizer;
import dos.Tokenizer.Types.Token;

public class App {
    
    public static void main( String[] args ){
        String s = """
                    public class Main{ 
                        public static void main(String[] args){
                            print \"Hello+World\" - 89 = 1 dAS+s+-s;
                        }
                    }
                    """;
        List<Token> tokens = Tokenizer.convertToTokens(s);
        System.out.println(tokens);
    }
}
