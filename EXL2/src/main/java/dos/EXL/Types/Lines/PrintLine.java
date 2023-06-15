package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.Util.Maybe;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;
import dos.EXL.Types.MyError;

public class PrintLine implements Line {


    public Expression val; 

    public PrintLine(Expression e){
        val = e;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + "print " +  val.makeString() + ";\n";
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        return val.validate(visitor,l);
    }

    @Override
    public void toASM(MethodInterface pass) {

    }

    @Override
    public void addToData(DataInterface data) {
    }  

}
