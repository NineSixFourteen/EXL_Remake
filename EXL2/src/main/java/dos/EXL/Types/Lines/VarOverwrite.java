package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

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
    public Maybe<Error> validate(ValueRecords records) {
        var type = records.getVar(name);
        if(type.hasError()){
            return new Maybe<Error>(type.getError());
        }
        var newType = newExpr.getType(records);
        if(newType.hasError()){
            return new Maybe<Error>(newType.getError());
        }
        if(!type.getValue().getValue1().equals(newType.getValue())){
            return new Maybe<Error>(new Error("Var type and expression missmatch "));//TODO error msg and check for type compat
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(ASMPass pass) {

    } 

    
}
