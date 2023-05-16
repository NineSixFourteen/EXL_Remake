package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

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
    public Maybe<Error> validate(ValueRecords records) {
        return null;
    }

    @Override
    public void toASM() {

    }

    @Override
    public String getType(ValueRecords records) {
        String leftType = object.getType(records);
        records.getImportInfo(leftType);//TOdo
        return null;
    }
}
