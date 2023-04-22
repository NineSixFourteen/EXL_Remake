package dos.Types.Unary.Types;

import dos.Types.Expression;

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
        throw new UnsupportedOperationException("Unimplemented method 'makeString'");
    }

}
