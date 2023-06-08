package dos.EXL.Validator.Boolean;



import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;
import dos.Util.Interaces.DataInterface;

public class ValBoolean {

    // Function for validating And and OR 
    public static Maybe<MyError> validateExtend(Expression left, Expression right, DataInterface visitor){
        var leftIsBool = left.getType(visitor);
        if(leftIsBool.hasError())
            return new Maybe<MyError>(leftIsBool.getError());
        if(!leftIsBool.getValue().equals("boolean"))
            return new Maybe<>(ErrorFactory.makeLogic("left side of an extend(&&,||) has to be a boolean " + left.makeString() + " is of type " + leftIsBool.getValue(),4));
        var rightIsBool = right.getType(visitor);
        if(rightIsBool.hasError())
            return new Maybe<MyError>(rightIsBool.getError());
        if(!rightIsBool.getValue().equals("boolean"))
            return new Maybe<>(ErrorFactory.makeLogic("right side of an extend(&&,||) has to be a boolean " + right.makeString() + " is of type " + rightIsBool.getValue(),4));
        
        return new Maybe<>();
    }

    // Function for validating Any comparisions i.e. !=, ==, >, < 
    public static Maybe<MyError> validateCompare(Expression left, Expression right, DataInterface visitor){
        var leftType = left.getType(visitor);
        var rightType = right.getType(visitor);
        if(leftType.hasError())
            return new Maybe<>(leftType.getError());
        if(rightType.hasError())
            return new Maybe<>(rightType.getError());
        if(!(isNum(leftType.getValue()) && isNum(rightType.getValue())))
            return new Maybe<>(ErrorFactory.makeLogic("left and right side both have number type left is " + leftType.getValue() + " and right is " + rightType.getValue(),2));
        return new Maybe<>();
    }

    private static boolean isNum(String value) {
        switch(value){
            case "int":
            case "double":
            case "float":
            case "long":
            case "short":
                return true;
            default:
                return false;
        }
    }
    
}
