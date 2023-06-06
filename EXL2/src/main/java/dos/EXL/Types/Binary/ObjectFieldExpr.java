package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.FunctionVisitor;

public class ObjectFieldExpr implements Expression  {
    
    Expression object;
    String fieldCall; 

    public ObjectFieldExpr(Expression left, String fieldName){
        object = left;
        fieldCall = fieldName; 
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return object.makeString() + "." + fieldCall;
    }

    @Override
    public Maybe<MyError> validate(FunctionVisitor visitor) {
        var leftType = object.getType(visitor);
        if(leftType.hasError()){
            return new Maybe<>(leftType.getError());
        }
        var x = visitor.getFieldType(leftType.getValue(),fieldCall);
        if(x.hasError()){
            return new Maybe<>(x.getError());
        }
        return new Maybe<>();
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(FunctionVisitor visitor) {
        var val = validate(visitor);
        if(val.hasValue())
            return Results.makeError(val.getValue());
        var leftType = object.getType(visitor);
        if(leftType.hasError())
            return leftType;
        return visitor.getFieldType(leftType.getValue(),fieldCall);
    }

}
