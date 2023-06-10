package dos.EXL.Validator.Maths;



import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Validator.Util.TypeCombiner;
import dos.Util.Maybe;
import dos.Util.Interaces.DataInterface;
import static org.objectweb.asm.Opcodes.*;
public class ValMaths {

    public static Maybe<MyError> validateMaths(Expression left, Expression right, DataInterface visitor){
        var leftType = left.getType(visitor);
        var rightType = left.getType(visitor);
        if(leftType.hasError()){
            return new Maybe<>(leftType.getError());
        }
        if(rightType.hasError()){
            return new Maybe<>(rightType.getError());
        }
        var type = TypeCombiner.MathsBinary(left, right, visitor);
        if(type.hasError()){
            return new Maybe<>(type.getError());
        }
        return new Maybe<>();
    }
    
}
