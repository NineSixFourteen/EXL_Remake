package dos.Util;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Compiler.ASM.Util.ValueRecords;

public class DescriptionMaker {

    public static String makeFuncASM(String type, List<Pair<String, String>> params, ValueRecords base) {
        StringBuilder sb =new StringBuilder("(");
        for(Pair<String,String> param : params){
            sb.append(toASM(param.getValue0(), base));
        }
        sb.append(")").append(toASM(type, base));
        return sb.toString();
    }

    public static String toASM(String type, ValueRecords base){
        switch(type){
            case "int":
                return "I";
            case "double":
                return "I";
            case "float":
                return "I";
            case "long":
                return "I";
            case "boolean":
                return "I";
            case "string":
                return "Ljava/lang/String";
            default:
                return base.getFullImport(type);
        }
    }
    
}
