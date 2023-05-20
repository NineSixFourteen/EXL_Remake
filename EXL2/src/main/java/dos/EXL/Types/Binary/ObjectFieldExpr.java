package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

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
    public String getType(ValueRecords records) {
        String leftType = object.getType(records);
        var x = records.getImportInfo(leftType);//TOdo
        var z =  x.getFieldType(fieldCall);
        return z.hasValue() ? z.getValue() : "Error";
    }

}
