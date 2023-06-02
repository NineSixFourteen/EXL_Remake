package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.IndentMaker;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;
import dos.Util.InfoClasses.ValueRecords;

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
    public Maybe<MyError> validate(ValueRecords records) {
        var type = records.getVar(name);
        if(type.hasError()){
            return new Maybe<>(type.getError());
        }
        var newType = newExpr.getType(records);
        if(newType.hasError()){
            return new Maybe<>(newType.getError());
        }
        if(!type.getValue().getValue1().equals(newType.getValue())){
            return new Maybe<>(ErrorFactory.makeLogic("Expression" + newExpr.makeString() +  " doesn't match type of " + type, 10));
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(ASMPass pass) {

    } 

    
}
