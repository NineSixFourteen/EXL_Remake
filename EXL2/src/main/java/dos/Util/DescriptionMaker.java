package dos.Util;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Interaces.DataInterface;
import dos.Util.Data.ImportsData;

public class DescriptionMaker {

    public static Result<String> makeFuncASM(String type, List<Pair<String, String>> params, ImportsData imports) {
        StringBuilder sb =new StringBuilder("(");
        for(Pair<String,String> param : params){
            var toASMMay = toASM(param.getValue0(), imports);
            if(toASMMay.hasError()){
                return toASMMay;
            }
            sb.append(toASMMay.getValue());
        }
        sb.append(")");
        var toASMMay = toASM(type, imports);
        if(toASMMay.hasError()){
            return toASMMay;
        }
        return Results.makeResult(sb.append(toASMMay.getValue()).toString());
    }

    public static Result<String> toASM(String type, ImportsData imports){
        switch(type){
            case "int":
                return Results.makeResult("I");
            case "double":
                return Results.makeResult("D");
            case "float":
                return Results.makeResult("F");
            case "long":
                return Results.makeResult("J");
            case "boolean":
                return Results.makeResult("C");
            case "string":
                return Results.makeResult("Ljava/lang/String");
            case "char":
                return Results.makeResult("C");
            default:
                var x = imports.getLongPath(type);
                if(x.hasValue()){
                    return Results.makeResult(x.getValue());
                } else {
                    return Results.makeError(ErrorFactory.makeLogic("No such import - "+ type,8));
                }
        }
    }

    //Function to make description of functions from partial info i.e when return type in unknown 
    public static Result<String> partial(List<Expression> params, DataInterface visitor){
        StringBuilder sb = new StringBuilder("(");
        for(Expression e : params){
            var type = e.getType(visitor);
            if(type.hasError()){return type;}
            var x = toASM(type.getValue(), visitor.getImports());
            if(x.hasError()){return x;}
            sb.append(x.getValue());
        }
        return Results.makeResult(sb.append(")").toString());
    }

    public static Result<String> fromASM(String type, ImportsData imports) {
        switch(type){
            case "I":
                return Results.makeResult("int");
            case "D":
                return Results.makeResult("double");
            case "F":
                return Results.makeResult("float");
            case "J":
                return Results.makeResult("long");
            case "Z":
                return Results.makeResult("boolean");
            case "C":
                return Results.makeResult("char");
            case "V":
                return Results.makeResult("void");
            case "Ljava/lang/String;":
                return Results.makeResult("String");
            default:
                return imports.getShortPath(type);
        }
    }
    
}
