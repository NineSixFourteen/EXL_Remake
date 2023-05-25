package dos.EXL.Types;

import java.util.List;
import dos.Util.Maybe;
import dos.Util.Result;
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
        if(elements.size() == 0 ){
            return new Maybe<Error>(new Error("Array declaration must contain atleast one element"));
        }
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
    public Result<String,Error> getType(ValueRecords records) {
        return elements.get(0).getType(records);
    }

}
