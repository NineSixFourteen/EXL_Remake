package dos.EXL.Types.Unary;

import java.util.List;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;

public class ObjectDeclareExpr implements Expression {

    String objName; 
    List<Expression> params; 

    public ObjectDeclareExpr(String name, List<Expression> exprs){
        objName = name;
        params = exprs;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString() {
        String res = "new " + objName + "(";
        for(Expression exp : params){
            res += exp.makeString() + ", ";
        }
        res = res.substring(0, res.length() -2);
        res += ")";
        return res;
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void compileASM() {

    }

}
