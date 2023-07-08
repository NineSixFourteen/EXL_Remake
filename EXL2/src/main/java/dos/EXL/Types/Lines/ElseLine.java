package dos.EXL.Types.Lines;

import java.util.ArrayList;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Validator.Misc.CodeBlockValid;
import dos.Util.IndentMaker;
import dos.Util.Maybe;

public class ElseLine implements Line  {

    private CodeBlock body;

    public ElseLine(CodeBlock b){
        body = b;
    }

    @Override
    public void accept() {
        
    }
    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "else ";
        res +=  "{\n";
        indent++;
        for(Line l : body.lines){
            res += l.makeString(indent); 
        }
        res += IndentMaker.indent(indent - 1) + "}\n";
        return res;
    } 

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        var bodyV = CodeBlockValid.validate(body,visitor,l);
        if(bodyV.hasValue()){
            return bodyV;
        }
        return new Maybe<MyError>();
    }

    @Override
    public void toASM(MethodInterface pass) {
        pass.compile(body, new ArrayList<>());
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
