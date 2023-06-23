package dos.EXL.Types.Lines;

import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
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
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        var type = visitor.getVar(name,l);
        if(type.hasError())
            return new Maybe<MyError>(type.getError());
        var newType = newExpr.getType(visitor,l);
        if(newType.hasError())
            return new Maybe<>(newType.getError());
        if(!type.getValue().getType().equals(newType.getValue()))
            return new Maybe<MyError>(ErrorFactory.makeLogic("Variable " + name +  " is of type " + type.getValue().getType() + " your expression is an incompatible type of " + newType.getValue(), 10));
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface pass) {
        pass.writeToVariable(name, newExpr);
        pass.lineNumberInc();
    }

    @Override
    public void addToData(DataInterface data) {
    }  

    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd) {
        return ++lineNumber;
    }  

    
}
