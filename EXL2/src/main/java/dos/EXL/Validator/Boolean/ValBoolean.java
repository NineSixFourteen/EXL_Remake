package dos.EXL.Validator.Boolean;



import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class ValBoolean {

    // Function for validating And and OR 
    public static Maybe<Error> validateExtend(Expression left, Expression right, ValueRecords records){
        Maybe<Error> leftIsError = left.validate(records);
        if(leftIsError.hasValue()){
            return leftIsError;
        }
        boolean leftIsBool = left.getType(records).equals("boolean");
        if(!leftIsBool){
            return new Maybe<Error>(new Error("Left side of AND is not a boolean " + left.makeString()));
        }
        Maybe<Error> rightIsError = right.validate(records);
        if(rightIsError.hasValue()){
            return rightIsError;
        }
        boolean rightIsBool = right.getType(records).equals("boolean");
        if(!rightIsBool){
            return new Maybe<Error>(new Error("Right side of AND is not a boolean " + right.makeString()));
        }
        return new Maybe<>();
    }

    // Function for validating Any comparisions i.e. !=, ==, >, < 
    public static Maybe<Error> validateCompare(Expression left, Expression right, ValueRecords records){
        Maybe<Error> leftIsError = left.validate(records);
        if(leftIsError.hasValue()){
            return leftIsError;
        }
        Maybe<Error> rightIsError = right.validate(records);
        if(rightIsError.hasValue()){
            return rightIsError;
        }
        var leftType = left.getType(records);
        var rightType = right.getType(records);
        if(leftType.hasError()){
            return new Maybe<Error>(leftType.getError());
        }
        if(rightType.hasError()){
            return new Maybe<Error>(rightType.getError());
        }
        if(!leftType.getValue().equals(rightType.getValue())){
            return new Maybe<Error>(new Error("left and right side are differnt type left : " + leftType.getValue() + " right : " + rightType.getValue()));
        }
        return new Maybe<>();
    }
    
}
