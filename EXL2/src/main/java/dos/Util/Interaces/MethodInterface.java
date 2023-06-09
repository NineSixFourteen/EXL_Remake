package dos.Util.Interaces;


import org.objectweb.asm.Label;

import dos.EXL.Types.Expression;

public class MethodInterface {

    DataInterface data;
    VisitInterface visitor; 

    public MethodInterface(DataInterface data, VisitInterface visitor){
        this.data = data;
        this.visitor = visitor;
    }

    public void declareVariable(String name, String type, Label scopeEnd){
        int index = data.getNextMemort();
        Label label = visitor.declareVariable(name, type, scopeEnd, index);
        var x = data.addVariable(name, type, label);
    }

    public void writeToVariable(String name, Expression e){
        var vari = data.getVar(name);
        var va = vari.getValue();
        visitor.writeToVariable(va.getValue2(), e, va.getValue0(), this);
        
    }

    

    
}
