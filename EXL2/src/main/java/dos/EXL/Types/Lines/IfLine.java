package dos.EXL.Types.Lines;

import org.objectweb.asm.Label;

import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Validator.Misc.CodeBlockValid;
import dos.Util.IndentMaker;
import dos.Util.Maybe;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;


public class IfLine implements Line {

    public IfLine(BoolExpr e, CodeBlock ls){
        val = e;
        body = ls;
    }
    
    private BoolExpr val;
    private CodeBlock body;

    @Override
    public void accept() {
        
    }
    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "if ";
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
        pass.IfStatement(start, end, val, body);
        pass.setScopeEnd(eScope);
        
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
