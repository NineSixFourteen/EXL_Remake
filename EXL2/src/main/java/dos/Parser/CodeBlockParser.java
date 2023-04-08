package dos.Parser;

import java.util.List;

import dos.Parser.Builders.CodeBlockBuilder;
import dos.Tokenizer.Types.Token;
import dos.Types.Lines.CodeBlock;
import dos.Util.Result;

public class CodeBlockParser {

    public static Result<CodeBlock,Error> getCodeBlock(List<Token> tokens){
        CodeBlockBuilder cbb = new CodeBlockBuilder();
        Result<CodeBlock,Error> res = new Result<>();
        int point = 0; 
        while(point < tokens.size()){
            switch(tokens.get(point).getType()){
                case Print:
                    break;
                case Return:
                    break;
                case Int:
                    break;
                case Float:
                    break;
                case Double:
                    break;
                case Long:
                    break;
                case Boolean:
                    break;
                case String:
                    break;
                case For:
                    break;
                case If:
                    break;
                case While:
                    break;
                case Switch:
                    break;
                case Value:
                    break;
                default: 
                    res.setError(new Error("Unknown way of starting line"));
            }
        }
        return res;
    }
    
}
