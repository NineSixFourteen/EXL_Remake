package dos.EXL.Types.Binary;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

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
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        var leftType = object.getType(visitor,line);
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
    public void toASM(MethodInterface visitor,Primitives type) {

    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        var val = validate(visitor,line);
        if(val.hasValue())
            return Results.makeError(val.getValue());
        var leftType = object.getType(visitor,line);
        if(leftType.hasError())
            return leftType;
        return visitor.getFieldType(leftType.getValue(),fieldCall);
    }

}
