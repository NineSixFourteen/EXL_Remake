package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;

public class StringExpr implements Expression {

    private String val; 

    public StringExpr(String s){
        val = s;
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public String makeString() {
        return "\"" + val + "\"";
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void toASM() {

    }

}
