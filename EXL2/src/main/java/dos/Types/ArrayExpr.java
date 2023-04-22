package dos.Types;

import java.util.List;

public class ArrayExpr implements Expression {

    List<Expression> elements;

    public ArrayExpr(List<Expression> elems){
        elements = elems;
    }

    @Override
    public void accept() {
    }

    @Override
    public String makeString() {
        String res = "[";
        for(Expression exp : elements){
            res += exp.makeString() + ", ";
        }
        res = res.substring(0, res.length() -2);
        res += "]";
        return res;
    }

}
