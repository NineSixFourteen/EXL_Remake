package dos.Util.Interaces;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import dos.EXL.Types.Expression;

public class VisitInterface {
    
    private MethodVisitor visitor; 

    public VisitInterface(MethodVisitor visitor){
        this.visitor = visitor;
    }

    public Label declareVariable(String name, String type, Label end, int index){
        Label start = new Label();
        visitor.visitLocalVariable(name, type, type, start, end, index);
        visitor.visitLabel(start);
        return start;
    }

    public void writeToVariable(int index, Expression e, String type, MethodInterface data){
        e.toASM(data);
    }
}
