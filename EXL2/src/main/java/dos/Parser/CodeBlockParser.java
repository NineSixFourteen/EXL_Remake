package dos.Parser;

import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import dos.Parser.Builders.CodeBlockBuilder;
import dos.Parser.Util.Grabber;
import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Types.Lines.CodeBlock;
import dos.Util.Maybe;
import dos.Util.Result;

public class CodeBlockParser {

    public static Result<CodeBlock,Error> getCodeBlock(List<Token> tokens){
        CodeBlockBuilder cbb = new CodeBlockBuilder();
        Result<CodeBlock,Error> res = new Result<>();
        int point = 0; 
        Maybe<Error> lineRes;
        while(point < tokens.size()){
            var nextLine = Grabber.grabNextLine(tokens, point);
            if(nextLine.hasError()){res.setError(nextLine.getError());return res;}
            point = nextLine.getValue().getValue1();
            lineRes = addLine(nextLine.getValue().getValue0(), cbb);
            if(lineRes.hasValue()){res.setError(lineRes.getValue());return res;}
        }
        return res;
    }

    private static Maybe<Error> addLine(List<Token> tokens, CodeBlockBuilder cbb) {
        switch(tokens.get(0).getType()){
            case Int:case Float:case Long:case Short:case Boolean:case String:case Char:
                var declareMaybe = LineParser.getDeclare(tokens);
                if(declareMaybe.hasError()){return new Maybe<Error>(declareMaybe.getError());}
                Triplet<String, String, Expression> declareParts = declareMaybe.getValue();
                cbb.addDeclare(declareParts.getValue0(),declareParts.getValue1(), declareParts.getValue2());
                break;
            case Print:
                var printMaybe = ExpressionParser.parse(tokens.subList(1, tokens.size()));
                if(printMaybe.hasError()){return new Maybe<Error>(printMaybe.getError());}
                cbb.addReturn(printMaybe.getValue());
                break;
            case Return:
                var returnMaybe = ExpressionParser.parse(tokens.subList(1, tokens.size()));
                if(returnMaybe.hasError()){return new Maybe<Error>(returnMaybe.getError());}
                cbb.addReturn(returnMaybe.getValue());
                break;
            case If:
                var ifMaybe = LineParser.getIf(tokens);
                if(ifMaybe.hasError()){return new Maybe<>(ifMaybe.getError());}
                Pair<Expression,CodeBlock> ifParts = ifMaybe.getValue();
                cbb.addIf(ifParts.getValue0(), ifParts.getValue1());
                break;
            case For:
                var forMaybe = LineParser.getFor(tokens);
                if(forMaybe.hasError()){return new Maybe<>(forMaybe.getError());}
                var forParts = forMaybe.getValue();
                cbb.addFor(forParts.getValue0(), forParts.getValue1(), forParts.getValue2(), forParts.getValue3());
                break;
            case Switch:
                break;
            case Value:
                switch(tokens.get(1).getType()){
                    case Dot:
                    case LBracket:
                        var exprMaybe = ExpressionParser.parse(tokens);
                        if(exprMaybe.hasError()){return new Maybe<>(exprMaybe.getError());}
                        cbb.addExpr(exprMaybe.getValue());
                        break; 
                    case Equal:
                        var varOMaybe = LineParser.getVarOver(tokens); 
                        if(varOMaybe.hasError()){return new Maybe<>(varOMaybe.getError());}
                        var varOParts = varOMaybe.getValue();
                        cbb.addVarO(varOParts.getValue0(), varOParts.getValue1());
                        break;
                    default:
                        return new Maybe<>(new Error("Unknow line" + tokens));
                }
            case ValueString:
                break;
            case New:
                var exprMaybe = ExpressionParser.parse(tokens);
                if(exprMaybe.hasError()){return new Maybe<>(exprMaybe.getError());}
                cbb.addExpr(exprMaybe.getValue());
                break; 
            default:
                return new Maybe<>(new Error("Unknown line start " + tokens.get(0)));
        }
        return new Maybe<>();
    }
    
}
