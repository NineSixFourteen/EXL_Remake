package dos.Util;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Types.Expression;

public class DescriptionMaker {

    public static Result<String,Error> makeFuncASM(String type, List<Pair<String, String>> params, ValueRecords base) {
        Result<String,Error> res = new Result<>();
        StringBuilder sb =new StringBuilder("(");
        for(Pair<String,String> param : params){
            var toASMMay = toASM(param.getValue0(), base);
            if(toASMMay.hasError()){
                return toASMMay;
            }
            sb.append(toASMMay.getValue());
        }
        sb.append(")");
        var toASMMay = toASM(type, base);
        if(toASMMay.hasError()){
            return toASMMay;
        }
        res.setValue(sb.append(toASMMay.getValue()).toString());
        return res;
    }

    private static Result<String,Error> makeValid(String msg){
        Result<String,Error> res = new Result<>();
        res.setValue(msg);
        return res;
    }

    private static Result<String,Error> makeError(String error){
        Result<String,Error> res = new Result<>();
        res.setError(new Error(error));
        return res;
    }

    public static Result<String,Error> toASM(String type, ValueRecords records){
        switch(type){
            case "int":
                return makeValid("I");
            case "double":
                return makeValid("D");
            case "float":
                return makeValid("F");
            case "long":
                return makeValid("J");
            case "boolean":
                return makeValid("C");
            case "string":
                return makeValid("Ljava/lang/String");
            case "char":
                return makeValid("C");
            default:
                var x = records.getFullImport(type);
                if(x.hasValue()){
                    return makeValid(x.getValue());
                } else {
                    return makeError("Error");
                }
        }
    }

    //Function to make description of functions from partial info i.e when return type in unknown 
    public static Result<String,Error> partial(String name, List<Expression> params, ValueRecords records){
        StringBuilder sb = new StringBuilder("(");
        for(Expression e : params){
            var type = e.getType(records);
            if(type.hasError()){return type;}
            var x = toASM(type.getValue(), records);
            if(x.hasError()){return x;}
            sb.append(x.getValue());
        }
        return Results.makeResult(sb.append(")").toString());
    }

    public static String fromASM(String type, ValueRecords records) {
        switch(type){
            case "I":
                return "int";
            case "D":
                return "double";
            case "F":
                return "float";
            case "J":
                return "long";
            case "Z":
                return "boolean";
            case "C":
                return "char";
            case "Ljava/lang/String;":
                return "String";
            default:
                var x = records.getShortImport(type);
                return x.hasError() ? x.getError().getMessage() : x.getValue();
        }
    }
    
}
