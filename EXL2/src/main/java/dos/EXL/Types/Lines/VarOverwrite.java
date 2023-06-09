package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.IndentMaker;
import dos.Util.Maybe;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class VarOverwrite implements Line {

    String name; 
    Expression newExpr;

    public VarOverwrite(String n, Expression nE){
        name = n;
        newExpr = nE;
    }

    @Override
    public void accept() {
    } 

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + name +  " = " + newExpr.makeString() + ";\n";
    }

    @Override
    public Maybe<MyError> validate(DataInterface FunctionVisitor) {
        var type = FunctionVisitor.getVar(name);
        if(type.hasError()){
            return new Maybe<>(type.getError());
        }
        var newType = newExpr.getType(FunctionVisitor);
        if(newType.hasError()){
            return new Maybe<>(newType.getError());
        }
        if(!type.getValue().getValue1().equals(newType.getValue())){
            return new Maybe<>(ErrorFactory.makeLogic("Expression" + newExpr.makeString() +  " doesn't match type of " + type, 10));
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface pass) {

    } 

    
}
