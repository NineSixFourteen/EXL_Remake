package dos.EXL.Types.Lines;

import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.Util.IndentMaker;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class DeclarLine implements Line {

    public DeclarLine(String n, String t, Expression e){
        name = n;
        value = e;
        type = t;
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
    public Maybe<MyError> validate(ValueRecords records) {
        var valueType = value.getType(records);
        if(valueType.hasError()){
            return new Maybe<>(valueType.getError());
        }
        if(!type.equals(valueType.getValue())){// TODO Check nums to be converted
            return new Maybe<>(new MyError("Expression doesn't match type"));
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(ASMPass pass) {

    } 
    
    
}
