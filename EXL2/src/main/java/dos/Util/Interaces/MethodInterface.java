package dos.Util.Interaces;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Compiler.ASM.Util.Symbol;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Lines.CodeBlock;


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
        Variable var = data.getVar(name, lineNumber).getValue();
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
        val.pushInverse(end);
        this.compile(body);

    }




    

    
}
