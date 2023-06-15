package dos.EXL.Types;

import java.util.List;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

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
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        if(elements.size() == 0 ){
            return new Maybe<>(ErrorFactory.makeLogic("Array declaration must contain atleast one element",8));
        }
        for(Expression e : elements){
            var x = e.validate(visitor, line);
            if(x.hasValue()){
                return x;
            }
        }
        return new Maybe<>();
    }


    @Override
    public Result<String> getType(DataInterface FunctionVisitor, int line) {
        return elements.get(0).getType(FunctionVisitor, line);
    }

    @Override
    public void toASM(MethodInterface visitor, Primitives type,int line) {

    }

}
