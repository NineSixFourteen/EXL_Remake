package dos.Util.Interaces;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Compiler.ASM.Util.Symbol;
import dos.EXL.Types.Expression;

public class VisitInterface {
    
    private MethodVisitor visitor; 

    public VisitInterface(MethodVisitor visitor){
        this.visitor = visitor;
    }

    public MethodVisitor getVisitor() {
        return visitor;
    }

    public Label declareVariable(String name, String type, Label end, int index){
        Label start = new Label();
        visitor.visitLocalVariable(name, type, type, start, end, index);
        visitor.visitLabel(start);
        return start;
    }

    public void writeToVariable(int index, Expression e, Primitives type, MethodInterface data, int line){
        push(data,e, type,line);
        int[] codes = new int[]{ISTORE,FSTORE,DSTORE,LSTORE,ASTORE,AASTORE};
        data.visitor.visitor.visitVarInsn(codes[type.ordinal()], index);
        
    }

    private void push(MethodInterface MI, Expression e, Primitives type, int line) {
        e.toASM(MI,type,line);
    }

    public void mathSymbol(Primitives type, Symbol sybmol){
        int[][] opCodes = new int[][]{
            {IADD,ISUB,IDIV,IMUL},
            {FADD,FSUB,FDIV,FMUL},
            {DADD,DSUB,DDIV,DMUL},
        };
        visitor.visitInsn(opCodes[type.ordinal()][sybmol.ordinal()]);
    }

}
