package dos;

import java.util.List;

import dos.EXL.Parser.ProgramParser;
import dos.EXL.Tokenizer.Tokenizer;
import dos.EXL.Tokenizer.Types.Token;

public class App {
    
    public static void main( String[] args ){
        String s = """
                    public class Main{ 
                        public static void main(String args){
                            print \"Hello+World\" - 89 = 1 dAS+s+-s;
                        }
                    }
                    """;
        List<Token> tokens = Tokenizer.convertToTokens(s);
        var p = ProgramParser.toClass(tokens);
        if(p.hasError()){
            System.out.println(p.getError());
        } else {
            System.out.println(p.getValue().makeString());
        }
    }
}
