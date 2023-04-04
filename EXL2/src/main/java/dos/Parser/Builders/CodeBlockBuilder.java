package dos.Parser.Builders;

import dos.Types.Expression;
import dos.Types.Line;
import dos.Types.Lines.CodeBlock;
import dos.Types.Lines.DeclarLine;

public class CodeBlockBuilder {

    private CodeBlock body;

    CodeBlockBuilder(){
        body = new CodeBlock();
    }

    public CodeBlockBuilder addDeclare(String name, String type, Expression expr){
        body.addLine(LineFactory.IninitVariable(name, type, expr));
        return this;
    }

    public CodeBlockBuilder addIf(Expression bool, CodeBlock body){
        body.addLine(LineFactory.ifL(bool, body));
        return this;
    }

    public CodeBlockBuilder addFor(DeclarLine dec, Expression bool,Line l, CodeBlock body){
        body.addLine(LineFactory.forL(dec, bool,l, body));
        return this;
    }

    public CodeBlockBuilder addWhile(Expression bool, CodeBlock body){
        body.addLine(LineFactory.whileL(bool, body));
        return this;
    }

    public CodeBlockBuilder addSwitch(){
        return this;
    }

    public CodeBlockBuilder addReturn( Expression expr){
        body.addLine(LineFactory.returnL(expr));
        return this;
    }

    public CodeBlockBuilder addPrint(Expression expr){
        body.addLine(LineFactory.Print(expr));
        return this;
    }


    
}
