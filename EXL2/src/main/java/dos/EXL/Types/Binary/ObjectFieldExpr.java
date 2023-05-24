package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;
import dos.Util.Result;
import dos.Util.Results;

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
    public Maybe<Error> validate(ValueRecords records) {
        return null;
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String,Error> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        var leftType = object.getType(records);
        if(leftType.hasError()){
            return leftType;
        }
        var x = records.getImportInfo(leftType.getValue());//TOdo
        var z =  x.getFieldType(fieldCall);
        return z.hasValue() ? Results.makeResult(z.getValue()) : Results.makeError(new Error("Could not find type for field " + fieldCall + " in " + leftType.getValue()));
    }

}
