package dos.Util.Interaces;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Compiler.ASM.Util.Symbol;
import dos.EXL.Types.Expression;

public class MethodInterface {

    DataInterface data;
    VisitInterface visitor; 

    public MethodInterface(DataInterface data, VisitInterface visitor){
        this.data = data;
        this.visitor = visitor;
    }

    public MethodVisitor getVisitor() {
        return visitor.getVisitor();
    }
    
    public DataInterface getData() {
        return data;
    }

    public void declareVariable(String name,String type, Label scopeEnd){
        int index = data.getNextMemort();
        Label label = visitor.declareVariable(name, type, scopeEnd, index);
        var x = data.addVariable(name, type, label);
    }

    public void writeToVariable(String name, Expression e){
        var vari = data.getVar(name);
        var va = vari.getValue();
        visitor.writeToVariable(va.getValue2(), e, Primitives.getPrimitive(va.getValue1()), this);
    }

    public void mathSymbol(Primitives type, Symbol sybmol){
        visitor.mathSymbol(type, sybmol);
    }

    public void push(Expression expr, Primitives type) {
    }



    

    
}
