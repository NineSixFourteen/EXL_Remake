package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.ValueRecords;

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
    public Maybe<MyError> validate(ValueRecords records) {
        var leftType = object.getType(records);
        if(leftType.hasError()){
            return new Maybe<>(leftType.getError());
        }
        var x = records.getImportInfo(leftType.getValue());
        if(x.hasError()){
            return new Maybe<>(x.getError());
        }
        var z =  x.getValue().getFieldType(fieldCall);
        if(z.hasError()){
            return new Maybe<>(z.getError());
        }
        return new Maybe<>();
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue())
            return Results.makeError(val.getValue());
        var leftType = object.getType(records);
        if(leftType.hasError())
            return leftType;
        var x = records.getImportInfo(leftType.getValue());
        if(x.hasError())
            return Results.makeError(x.getError());
        var z = x.getValue().getFieldType(fieldCall);
        return z;
    }

}
