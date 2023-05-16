package dos.EXL.Validator.Util;

import dos.EXL.Types.Expression;
import dos.Util.ValueRecords;

public class TypeCombiner {

    public static String MathsBinary(Expression left, Expression right, ValueRecords records){
        String leftType = left.getType(records);
        String rightType = left.getType(records);
        if(isNumType(leftType) && isNumType(rightType)){
            return highestNum(leftType, rightType);
        } else if(leftType == "String"){
            return "String";
        } else {
            return "Error";
        }
    }


    private static String highestNum(String leftType, String rightType) {
        int left = getNum(leftType);
        int right = getNum(rightType);
        return left < right ? rightType : leftType;
    }

    private static int getNum(String type) {
        switch(type){
            case "double":
                return 4; 
            case "long":
                return 3;
            case "float":
                return 2;
            case "int":
                return 1;
            default:
                return 0;
        }
    }

    private static boolean isNumType(String type){
        switch(type){
            case "int":
            case "long":
            case "float":
            case "double":
                return true;
            default:
                return false;
        }
    }

}
