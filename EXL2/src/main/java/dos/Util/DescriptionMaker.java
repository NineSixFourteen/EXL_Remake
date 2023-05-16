package dos.Util;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Types.Expression;

public class DescriptionMaker {

    public static String makeFuncASM(String type, List<Pair<String, String>> params, ValueRecords base) {
        StringBuilder sb =new StringBuilder("(");
        for(Pair<String,String> param : params){
            sb.append(toASM(param.getValue0(), base));
        }
        sb.append(")").append(toASM(type, base));
        return sb.toString();
    }

    public static String toASM(String type, ValueRecords records){
        switch(type){
            case "int":
                return "I";
            case "double":
                return "D";
            case "float":
                return "F";
            case "long":
                return "J";
            case "boolean":
                return "Z";
            case "string":
                return "Ljava/lang/String";
            case "char":
                return "C";
            default:
                var x = records.getFullImport(type);
                if(x.hasValue()){
                    return x.getValue();
                } else {
                    return "Error";
                }
        }
    }

    //Function to make description of functions from partial info i.e when return type in unknown 
    public static String partial(String name, List<Expression> params, ValueRecords records){
        StringBuilder sb = new StringBuilder("(");
        for(Expression e : params){
            sb.append(toASM(e.getType(records), records));
        }
        return sb.append(")").toString();
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
                return records.getShortImport(type);
        }
    }
    
}
