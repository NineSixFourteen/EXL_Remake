package dos.Types.Unary.Types;

import dos.Types.Expression;
import dos.Util.Maybe;

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
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void compileASM() {

    }
}