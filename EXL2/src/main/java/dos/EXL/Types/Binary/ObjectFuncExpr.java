package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.Maybe;
import dos.Util.InfoClasses.FunctionVisitor;
import dos.Util.Result;
import dos.Util.Results;

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
    public Maybe<MyError> validate(FunctionVisitor visitor) {
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
    public void toASM() {

    }

    @Override
    public Result<String> getType(FunctionVisitor visitor) {
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
