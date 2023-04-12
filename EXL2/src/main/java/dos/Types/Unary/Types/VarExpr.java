package dos.Types.Unary.Types;

import dos.Types.Expression;

public class VarExpr implements Expression{

    String name;

    public VarExpr(String str){
        name = str;
    }

    @Override
    public void accept() {

    }
    

}
