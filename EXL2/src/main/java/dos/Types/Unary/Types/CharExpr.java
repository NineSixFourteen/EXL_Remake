package dos.Types.Unary.Types;

import dos.Types.Expression;

public class CharExpr implements Expression  {

    private char val; 

    public CharExpr(char c){
        val = c;
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }
}
