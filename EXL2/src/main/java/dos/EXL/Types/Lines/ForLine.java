package dos.EXL.Types.Lines;

import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Line;
import dos.EXL.Validator.Misc.CodeBlockValid;
import dos.Util.IndentMaker;
import dos.Util.Maybe;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;


public class ForLine implements Line {

    public ForLine(DeclarLine d, BoolExpr b, Line l, CodeBlock ls){
        dec = d;
        bool = b;
        line = l;
        body = ls;
    }

    DeclarLine dec;
    BoolExpr bool;
    Line line;
    CodeBlock body;

    @Override
    public void accept() {
    }

    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "for( ";
        res += dec.makeString(0) + "; ";
        res += bool.makeString() + "; ";
        res += line.makeString(0) + "){\n";
        indent++;
        for(Line l : body.lines){
            res += l.makeString(indent); 
        }
        res += IndentMaker.indent(indent - 1) + "}\n";
        return res;
    } 
    
    @Override
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        var decV = dec.validate(visitor,l);
        if(decV.hasValue()){
            return decV;
        }
        var boolT = bool.getType(visitor,l);// get Type also validates it
        if(boolT.hasError()){
            return new Maybe<>(boolT.getError());
        }
        if(!boolT.getValue().equals("boolean")){
            return new Maybe<>(ErrorFactory.makeLogic("Must be a boolean expression in the second segment of a for," + bool.makeString() + " is of type " + boolT.getValue(),11));
        }
        var lineV = line.validate(visitor,l);
        if(lineV.hasValue()){
            return lineV;
        }
        var bodyV = CodeBlockValid.validate(body,visitor,l);
        if(bodyV.hasValue()){
            return bodyV;
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface pass) {

    }

    @Override
    public void addToData(DataInterface data) {
    }  

    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd) {
        int memory = data.getNextMemory();
        LaterInt newScope = new LaterInt();
        dec.fill(lineNumber, data, newScope);
        line.fill(lineNumber, data, newScope);
        ++lineNumber;
        for(Line l : body.getLines()){
            lineNumber =  l.fill(lineNumber, data, newScope);
        }
        newScope.setNum(lineNumber);
        data.setNextMemory(memory);
        return lineNumber;
    }  
    
    
}
