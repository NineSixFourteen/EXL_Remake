package dos.Types.Unary.Types;

import dos.Types.Expression;

public class FloatExpr  implements Expression  {

    private float val; 

    public FloatExpr(float c){
        val = c;
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }
}