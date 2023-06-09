package dos.EXL.Types.Lines;

import java.util.ArrayList;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Validator.Misc.CodeBlockValid;
import dos.Util.IndentMaker;
import dos.Util.Maybe;

public class WhileLine implements Line {

    public WhileLine(BoolExpr b, CodeBlock ls){
        bool = b;
        body = ls;
    }

    BoolExpr bool;
    CodeBlock body;

    @Override
    public void accept() {

    } 
    
    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "while ";
        res += bool.makeString() + "{\n";
        indent++;
        for(Line l : body.lines){
            res += l.makeString(indent); 
        }
        res += IndentMaker.indent(indent - 1) + "}\n";
        return res;
    } 

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        var boolT = bool.getType(visitor,l);// get Type also validates it
        if(boolT.hasError()){
            return new Maybe<>(boolT.getError());
        }
        if(!boolT.getValue().equals("boolean")){
            return new Maybe<>(ErrorFactory.makeLogic("Must be a boolean expression in while statement ," + bool.makeString() + " is of type " + boolT.getValue(),11));
        }
        var bodyV = CodeBlockValid.validate(body,visitor,l);
        if(bodyV.hasValue()){
            return bodyV;
        }
        return new Maybe<MyError>();
    }

    @Override
    public void toASM(MethodInterface pass) {
        Label start = new Label();
        Label end = new Label();
        Label eScope = pass.getScopeEnd();
        pass.setScopeEnd(end);
        bool.pushInverse(pass,end,null,false);
        pass.getVisitor().visitLabel(start);
        pass.compile(body,new ArrayList<>());
        pass.getVisitor().visitJumpInsn(Opcodes.GOTO, start);
        pass.getVisitor().visitLabel(end);
        pass.setScopeEnd(eScope);
        pass.lineNumberInc();
    }

    @Override
    public void addToData(DataInterface data) {
    } 

    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd) {
        ++lineNumber;
        int memory = data.getNextMemory();
        for(Line l : body.getLines()){
            lineNumber =  l.fill(lineNumber, data, scopeEnd);
        }
        scopeEnd.setNum(lineNumber);
        data.setNextMemory(memory);
        return lineNumber;
    }  

    
}