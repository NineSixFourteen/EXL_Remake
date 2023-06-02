package dos.EXL.Validator.Util;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.ValueRecords;

public class TypeCombiner {

    public static Result<String> MathsBinary(Expression left, Expression right, ValueRecords records){
        var leftType = left.getType(records);
        var rightType = left.getType(records);
        if(leftType.hasError()){
            return leftType;
        }
        if(rightType.hasError()){
            return rightType;
        }
        if(isNumType(leftType.getValue()) && isNumType(rightType.getValue())){
            return Results.makeResult(highestNum(leftType.getValue(), rightType.getValue()));
        } else if(leftType.getValue() == "String"){
            return Results.makeResult("String");
        } else {
            return Results.makeError(ErrorFactory.makeLogic("Unable to determine a mutal type for " + left.makeString() + "  and  " + right.makeString(),30));
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
