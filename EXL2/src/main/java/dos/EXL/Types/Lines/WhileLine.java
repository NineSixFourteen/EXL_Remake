package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Validator.Misc.CodeBlockValid;
import dos.Util.IndentMaker;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class WhileLine implements Line {

    public WhileLine(Expression b, CodeBlock ls){
        bool = b;
        body = ls;
    }

    Expression bool;
    CodeBlock body;

    @Override
    public void accept() {

    } 
    
    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "while( ";
        res += bool.makeString() + "){\n";
        indent++;
        for(Line l : body.lines){
            res += l.makeString(indent); 
        }
        res += IndentMaker.indent(indent - 1) + "}\n";
        return res;
    } 

    @Override
    public Maybe<Error> validate(ValueRecords records) {
        var boolT = bool.getType(records);// get Type also validates it
        if(boolT.hasError()){
            return new Maybe<Error>(boolT.getError());
        }
        if(!boolT.getValue().equals("boolean")){
            return new Maybe<Error>(new Error("Must be a boolean expression in for second segment , not " + boolT.getValue()));
        }
        var bodyV = CodeBlockValid.validate(body);
        if(bodyV.hasValue()){
            return bodyV;
        }
        return new Maybe<Error>();
    }

    @Override
    public void toASM(ASMPass pass) {

    } 
    
}