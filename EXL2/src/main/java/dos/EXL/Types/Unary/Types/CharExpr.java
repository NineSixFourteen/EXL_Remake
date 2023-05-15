package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;

public class CharExpr implements Expression  {

    private char val; 

    public CharExpr(char c){
        val = c;
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public String makeString() {
        return "'" + val + "'";
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void toASM() {

    }
}
