package dos.Types.Unary.Types;

import dos.Types.Expression;

public class IntExpr  implements Expression  {

    private float val; 

    public IntExpr(int c){
        val = c;
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }
}