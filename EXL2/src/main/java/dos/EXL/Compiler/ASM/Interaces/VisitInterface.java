package dos.EXL.Compiler.ASM.Interaces;

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

    public void writeToVariable(int index, Expression e, Primitives type, MethodInterface data){
        push(data,e, type);
        int[] codes = new int[]{ISTORE,FSTORE,DSTORE,LSTORE,ASTORE,AASTORE,ISTORE};
        data.visitor.visitor.visitVarInsn(codes[type.ordinal()], index);
        
    }

    private void push(MethodInterface MI, Expression e, Primitives type) {
        e.toASM(MI,type);
    }

    public void mathSymbol(Primitives type, Symbol sybmol){
        int[][] opCodes = new int[][]{
            {IADD,ISUB,IDIV,IMUL},
            {FADD,FSUB,FDIV,FMUL},
            {DADD,DSUB,DDIV,DMUL},
        };
        visitor.visitInsn(opCodes[type.ordinal()][sybmol.ordinal()]);
    }

    public void pushVar(int memLocation, Primitives type) {
        int code;
        switch(type){
            case Char:
            case Int:
            case Short:
            case Boolean:
                code = ILOAD;
                break;
            case Double:
                code = DLOAD;
                break;
            case Float:
                code = FLOAD;
                break;
            case Long:
                code = LLOAD;
                break;
            case Object: 
            default:
                code = ALOAD;
                break;
        }
        visitor.visitVarInsn(code, memLocation);
    }

    public void pushInt(int val) {
        if ((val < 0) && (val > -128))
            visitor.visitIntInsn(BIPUSH , 256 - (val * -1));
        else if ((val < 0) && (val > - 32768))
            visitor.visitIntInsn(SIPUSH , 65536 - (val * -1));
        else if (val < 6)
            switch (val) {
                case 0: visitor.visitInsn(ICONST_0);break;
                case 1: visitor.visitInsn(ICONST_1);break;
                case 2: visitor.visitInsn(ICONST_2);break;
                case 3: visitor.visitInsn(ICONST_3);break;
                case 4: visitor.visitInsn(ICONST_4);break;
                case 5: visitor.visitInsn(ICONST_5);break;
            }
        else if (val < 128) 
            visitor.visitIntInsn(BIPUSH, val);
        else if (val < 32767)
            visitor.visitIntInsn(SIPUSH, val);
        else 
            visitor.visitLdcInsn(val);
    }

    public void pushFloat(float val) {
        visitor.visitLdcInsn(val);
    }


}
