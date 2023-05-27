package dos.EXL.Parser;

import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class CodeBlockParser {

    public static Result<CodeBlock> getCodeBlock(List<Token> tokens){
        CodeBlockBuilder cbb = new CodeBlockBuilder();
        int point = 0; 
        Maybe<MyError> lineRes;
        while(point < tokens.size()){
            var nextLine = Grabber.grabNextLine(tokens, point);
            if(nextLine.hasError())  return Results.makeError(nextLine.getError());
            point = nextLine.getValue().getValue1();
            lineRes = addLine(nextLine.getValue().getValue0(), cbb);
            if(lineRes.hasValue())  return Results.makeError(lineRes.getValue());
        }
        return Results.makeResult(cbb.build());
    }

    private static Maybe<MyError> addLine(List<Token> tokens, CodeBlockBuilder cbb) {
        switch(tokens.get(0).getType()){
            case Int:case Float:case Long:case Short:case Boolean:case String:case Char:
                var declareMaybe = LineParser.getDeclare(tokens);
                if(declareMaybe.hasError()) return new Maybe<>(declareMaybe.getError());
                Triplet<String, String, Expression> declareParts = declareMaybe.getValue();
                cbb.addDeclare(declareParts.getValue1(),declareParts.getValue0(), declareParts.getValue2());
                break;
            case Print:
                var printMaybe = ExpressionParser.parse(tokens.subList(1, tokens.size() - 1));
                if(printMaybe.hasError()) return new Maybe<>(printMaybe.getError());
                cbb.addPrint(printMaybe.getValue());
                break;
            case Return:
                var returnMaybe = ExpressionParser.parse(tokens.subList(1, tokens.size() - 1));
                if(returnMaybe.hasError()){return new Maybe<>(returnMaybe.getError());}
                cbb.addReturn(returnMaybe.getValue());
                break;
            case If:
                var ifMaybe = LineParser.getIf(tokens);
                if(ifMaybe.hasError()) return new Maybe<>(ifMaybe.getError());
                Pair<Expression,CodeBlock> ifParts = ifMaybe.getValue();
                cbb.addIf(ifParts.getValue0(), ifParts.getValue1());
                break;
            case For:
                var forMaybe = LineParser.getFor(tokens);
                if(forMaybe.hasError()) return new Maybe<>(forMaybe.getError());
                var forParts = forMaybe.getValue();
                cbb.addFor(forParts.getValue0(), forParts.getValue1(), forParts.getValue2(), forParts.getValue3());
                break;
            case Switch:
                break;
            case Value:
                switch(tokens.get(1).getType()){
                    case Dot:
                    case LBracket:
                        var exprMaybe = ExpressionParser.parse(tokens.subList(0, tokens.size() - 1));
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
                        return new Maybe<>(new MyError("Unknow line" + tokens));
                }
            case ValueString:
                break;
            case New:
                var exprMaybe = ExpressionParser.parse(tokens);
                if(exprMaybe.hasError()){return new Maybe<>(exprMaybe.getError());}
                cbb.addExpr(exprMaybe.getValue());
                break; 
            default:
                return new Maybe<>(new MyError("Unknown line start " + tokens.get(0)));
        }
        return new Maybe<>();
    }
    
}
