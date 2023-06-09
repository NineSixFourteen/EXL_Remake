package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class ObjectFuncExpr implements Expression{

    Expression object;
    FunctionExpr func; 

    public ObjectFuncExpr(Expression left, FunctionExpr fe){
        object = left;
        func = fe;
    }
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return object.makeString() + "." + func.makeString();
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor) {
        var leftType = object.getType(visitor);
        if(leftType.hasError()){
            return new Maybe<>(leftType.getError());
        }
        var classData = visitor.getfuncType(leftType.getValue(), func);
        if(classData.hasError())
            return new Maybe<MyError>(classData.getError());
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visitor) {

    }

    @Override
    public Result<String> getType(DataInterface visitor) {
        var val = validate(visitor);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        var leftType = object.getType(visitor);
        if(leftType.hasError()){
            return leftType;
        }
        return visitor.getfuncType(leftType.getValue(), func);
       
    }
}
