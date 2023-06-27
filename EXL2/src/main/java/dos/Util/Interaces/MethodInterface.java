package dos.Util.Interaces;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Compiler.ASM.Util.Symbol;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Result;

import static org.objectweb.asm.Opcodes.*;


public class MethodInterface {

    DataInterface data;
    VisitInterface visitor; 
    int lineNumber;
    Label ScopeEnd;

    public MethodInterface(DataInterface data, VisitInterface visitor){
        this.data = data;
        this.visitor = visitor;
        lineNumber = 0;
    }

    public MethodVisitor getVisitor() {
        return visitor.getVisitor();
    }
    
    public DataInterface getData() {
        return data;
    }

    public void declareVariable(String name){
        Result<Variable> varM = data.getVar(name, lineNumber);
        Variable var = varM.getValue();
        visitor.declareVariable(name, var.getType(), ScopeEnd, var.getMemory());
    }

    public void writeToVariable(String name, Expression e){
        var va = data.getVar(name, lineNumber).getValue();
        visitor.writeToVariable(va.getMemory(), e, Primitives.getPrimitive(va.getType()), this);
    }

    public void doMath(Primitives type, Symbol sybmol){
        visitor.mathSymbol(type, sybmol);
    }

    public void push(Expression expr, Primitives type) {
        Primitives actual = Primitives.getPrimitive(expr.getType(data,lineNumber).getValue());
        expr.toASM(this, actual);
        if(actual != type){
            convertToType(actual, type);
        }
    }

    private void convertToType(Primitives actual, Primitives type) {
        switch(actual){
            case Int:
                convertInt(type);
                break;
            case Float:
                convertFloat(type);
                break;
            case Long:
                convertLong(type);
                break;
            case Double:
                convertDouble(type);
                break;
            default:
                break;
        }
    }

    private void convertDouble(Primitives type) {
        switch(type){
            case Float:
                visitor.getVisitor().visitInsn(D2F);
                break;
            case Long:
                visitor.getVisitor().visitInsn(D2L);
                break;
            case Int:
                visitor.getVisitor().visitInsn(D2I);
                break;
            default:
                break;   
        }
    }

    private void convertLong(Primitives type) {
        switch(type){
            case Float:
                visitor.getVisitor().visitInsn(L2F);
                break;
            case Double:
                visitor.getVisitor().visitInsn(L2D);
                break;
            case Int:
                visitor.getVisitor().visitInsn(L2I);
                break;
            default:
                break;   
        }
    }

    private void convertFloat(Primitives type) {
        switch(type){
            case Long:
                visitor.getVisitor().visitInsn(F2L);
                break;
            case Double:
                visitor.getVisitor().visitInsn(F2D);
                break;
            case Int:
                visitor.getVisitor().visitInsn(F2I);
                break;
            default:
                break;   
        }
    }

    private void convertInt(Primitives type) {
        switch(type){
            case Char:
                visitor.getVisitor().visitInsn(I2C);
                break;
            case Boolean:
                visitor.getVisitor().visitInsn(I2B);
                break;
            case Long:
                visitor.getVisitor().visitInsn(I2L);
                break;
            case Double:
                visitor.getVisitor().visitInsn(I2D);
                break;
            case Float:
                visitor.getVisitor().visitInsn(I2F);
                break;
            case Short:
                visitor.getVisitor().visitInsn(I2S);
                break;
            default:
                break;   
        }
    }

    public void end(){
        visitor.getVisitor().visitMaxs(0, 0);
        visitor.getVisitor().visitEnd();
    }

    public void doFunc(boolean isStatic, String owner, String name, String desc) {
        visitor.getVisitor()
            .visitMethodInsn(isStatic ? INVOKESTATIC : INVOKEVIRTUAL, owner, name, desc, false);//Todo Interface 
    }

    public void lineNumberInc(){
        lineNumber++;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public Label getScopeEnd() {
        return ScopeEnd;
    }

    public void setScopeEnd(Label scopeEnd) {
        ScopeEnd = scopeEnd;
    }

    public void compile(CodeBlock body) {
        ScopeEnd = new Label();
        for(Line l : body.getLines()){
            l.toASM(this);
        }
        visitor.getVisitor().visitLabel(ScopeEnd);
    }

    public void IfStatement(Label start, Label end, BoolExpr val, CodeBlock body) {
        val.pushInverse(this,end,null);
        visitor.getVisitor().visitLabel(start);
        lineNumberInc();
        this.compile(body);
        visitor.getVisitor().visitLabel(end);
    }

    public void print(Expression e, Primitives type) {
        visitor.getVisitor().visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); //put System.out to operand stack
        push(e, type);
        visitor.getVisitor().visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", printDesc(type), false);
    }

    private String printDesc(Primitives type) {
        switch(type){
            case Int:
                return "(I)V";
            case Float:
                return "(F)V";
            case Object:
                return "(LJava.Lang.Object;)V";
            case Long:
                return "(L)V";
            case Boolean:
                return "(Z)V";
            case Char:
                return "(C)V";
            case Double:
                return "(J)V";
            case Short:
                return "(S)V";
            default:
                return "(I)V";
        }
    }

    public void Return(Primitives type) {
        switch(type){
            case Short:
            case Char:
            case Boolean:
            case Int:
                visitor.getVisitor().visitInsn(IRETURN);
                break;
            case Double:
                visitor.getVisitor().visitInsn(DRETURN);
                break;
            case Float:
                visitor.getVisitor().visitInsn(FRETURN);
                break;
            case Long:
                visitor.getVisitor().visitInsn(LRETURN);
                break;
            case Object:
                visitor.getVisitor().visitInsn(ARETURN);
                break;
            default:
                break;

        }
    }

    public void pushBool(Expression left, Expression right, int opcode) {
        Label True = new Label();
        Label Skip = new Label();
        MethodVisitor visit = visitor.getVisitor();
        push(left,Primitives.Int);
        push(right,Primitives.Int);
        visit.visitJumpInsn(opcode, True);
        visit.visitInsn(Opcodes.ICONST_0);
        visit.visitJumpInsn(Opcodes.GOTO, Skip);
        visit.visitLabel(True);
        visit.visitInsn(Opcodes.ICONST_1);
        visit.visitLabel(Skip);
    }

    public void pushJump(Expression left, Expression right, Label end, int opcode) {
        MethodVisitor visit = visitor.getVisitor();
        push(left,Primitives.Int);
        push(right,Primitives.Int);
        visit.visitJumpInsn(opcode, end);
    }

    public void pushVar(String name) {
        Variable Var = getData().getVar(name, lineNumber).getValue();
        int memLocation = Var.getMemory();
        visitor.pushVar(memLocation,Primitives.getPrimitive(Var.getType()));
    }

    public void pushInt(int val, Primitives type) {
        visitor.pushInt(val);
        if(type != Primitives.Int);
        convertToType(Primitives.Int, type);
    }

    public void pushFloat(float val, Primitives type) {
        visitor.pushFloat(val);
        if(type != Primitives.Float);
        convertToType(Primitives.Float, type);
    }




    

    
}
