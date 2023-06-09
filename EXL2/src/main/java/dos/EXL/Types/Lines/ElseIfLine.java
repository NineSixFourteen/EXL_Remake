package dos.EXL.Types.Lines;

import java.util.ArrayList;

import org.objectweb.asm.Label;

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

public class ElseIfLine implements Line  {

    private BoolExpr val;
    private CodeBlock body;

    public ElseIfLine(BoolExpr b, CodeBlock v){
        val = b;
        body = v;
    }

     @Override
    public void accept() {
        
    }
    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "else if ";
        res += val.makeString() + "{\n";
        indent++;
        for(Line l : body.lines){
            res += l.makeString(indent); 
        }
        res += IndentMaker.indent(indent - 1) + "}\n";
        return res;
    } 

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        var boolT = val.getType(visitor,l);// get Type also validates it
        if(boolT.hasError()){
            return new Maybe<>(boolT.getError());
        }
        if(!boolT.getValue().equals("boolean")){
            return new Maybe<>(ErrorFactory.makeLogic("Must be a boolean expression in if statement ," + val.makeString() + " is of type " + boolT.getValue(),11));
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
        pass.IfStatement(start, end, val, body, new ArrayList<>());
        pass.setScopeEnd(eScope);
    }

    @Override
    public void addToData(DataInterface data) {
    }  
    
    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd) {
        ++lineNumber;
        int memory = data.getNextMemory();
        LaterInt later = new LaterInt();
        for(Line l : body.getLines()){
            lineNumber =  l.fill(lineNumber, data, later);
        }
        later.setNum(lineNumber);
        data.setNextMemory(memory);
        return lineNumber;
    }  

}
