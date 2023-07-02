package dos.EXL.Types.Lines;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.IndentMaker;
import dos.Util.Maybe;

public class DeclarLine implements Line {

    public DeclarLine(String n, String t, Expression e){
        name = n;
        value = e;
        type = t.replace(" ","");
    }

    String name;
    String type;
    Expression value;

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + type + " "+ name + " = " + value.makeString() + ";\n";
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        var valueType = value.getType(visitor,l);
        if(valueType.hasError())
            return new Maybe<>(valueType.getError());
        if(!type.equals(valueType.getValue()))
            return new Maybe<>(ErrorFactory.makeLogic("Expression " + value.makeString() +  " is of type " + valueType.getValue() + " is should be of type " + type, 10));
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface pass) {
        pass.declareVariable(name);
        pass.writeToVariable(name, value);
        pass.lineNumberInc();
    }

    @Override
    public void addToData(DataInterface data) {
    }

    @Override
    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd) {
        data.add(new Variable(name, type, lineNumber, scopeEnd, data.getNextMemory()));
        return ++lineNumber;
    }  
    
    
}
