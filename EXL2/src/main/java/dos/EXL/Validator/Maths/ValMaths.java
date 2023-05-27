package dos.EXL.Validator.Maths;



import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Validator.Util.TypeCombiner;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class ValMaths {

    public static Maybe<MyError> validateMaths(Expression left, Expression right, ValueRecords records){
        var leftType = left.getType(records);
        var rightType = left.getType(records);
        if(leftType.hasError()){
            return new Maybe<>(leftType.getError());
        }
        if(rightType.hasError()){
            return new Maybe<>(rightType.getError());
        }
        var type = TypeCombiner.MathsBinary(left, right, records);
        if(type.hasError()){
            return new Maybe<>(type.getError());
        }
        return new Maybe<>();
    }
    
}
