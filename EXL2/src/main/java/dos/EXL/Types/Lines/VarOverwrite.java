package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
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
        var type = visitor.getVarsFromName(name);
        if(type.size() == 0){
            
        }
        var newType = newExpr.getType(visitor,l);
        if(newType.hasError()){
            return new Maybe<>(newType.getError());
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface pass) {

    }

    @Override
    public void addToData(DataInterface data) {
    }  

    
}
