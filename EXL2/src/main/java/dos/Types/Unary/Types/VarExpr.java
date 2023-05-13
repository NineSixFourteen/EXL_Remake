package dos.Types.Unary.Types;

import dos.Types.Expression;
import dos.Util.Maybe;

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
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void compileASM() {

    }
    

}
