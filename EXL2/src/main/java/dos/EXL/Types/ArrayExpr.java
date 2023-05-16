package dos.EXL.Types;

import java.util.List;

import dos.EXL.Validator.Array.ValArrayMake;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

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

    @Override
    public Maybe<Error> validate(ValueRecords records) {
        for(Expression e : elements){
            var x = e.validate(records);
            if(x.hasValue()){
                return x;
            }
        }
        return new Maybe<>();
    }


    @Override
    public void toASM() {

    }

    @Override
    public String getType(ValueRecords records) {
        return null;
    }

}
