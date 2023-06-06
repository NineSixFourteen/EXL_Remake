package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.Util.IndentMaker;
import dos.Util.Maybe;
import dos.Util.InfoClasses.FunctionVisitor;
import dos.Util.InfoClasses.FunctionVisitor;

public class ReturnLine implements Line {

    public Expression val; 

    public ReturnLine(Expression e){
        val = e;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + "return " +  val.makeString() + ";\n";
    }

    public Maybe<MyError> validate(FunctionVisitor visitor) {
        return val.validate(visitor);
    }
    @Override
    public void toASM(FunctionVisitor pass) {

    } 

}

