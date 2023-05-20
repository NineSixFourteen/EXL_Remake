package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class VarExpr implements Expression{

    String name;

    public VarExpr(String str){
        name = str;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString() {
        return name;
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
        return records.getVar(name).getValue1();
    }
    

}
