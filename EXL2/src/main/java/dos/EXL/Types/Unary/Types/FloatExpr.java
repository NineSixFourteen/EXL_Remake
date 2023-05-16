package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class FloatExpr  implements Expression  {

    private float val; 

    public FloatExpr(float c){
        val = c;
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public String makeString() {
        return "" + val;
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
        return null;
    }
}