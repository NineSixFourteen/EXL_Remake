package dos.EXL.Validator.Boolean;



import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class ValBoolean {

    // Function for validating And and OR 
    public static Maybe<Error> validateExtend(Expression left, Expression right, ValueRecords records){
        var leftIsBool = left.getType(records);
        if(leftIsBool.hasError()){
            return new Maybe<Error>(leftIsBool.getError());
        } 
        if(!leftIsBool.getValue().equals("boolean")){
            return new Maybe<Error>(new Error("left side of an extend(&&,||) has to be a boolean " + left.makeString() + " is of type" + leftIsBool.getValue()));
        }
        var rightIsBool = right.getType(records);
        if(rightIsBool.hasError()){
            return new Maybe<Error>(rightIsBool.getError());
        } 
        if(!rightIsBool.getValue().equals("boolean")){
            return new Maybe<Error>(new Error("right side of an extend(&&,||) has to be a boolean " + right.makeString() + " is of type" + rightIsBool.getValue()));
        }
        return new Maybe<>();
    }

    // Function for validating Any comparisions i.e. !=, ==, >, < 
    public static Maybe<Error> validateCompare(Expression left, Expression right, ValueRecords records){
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