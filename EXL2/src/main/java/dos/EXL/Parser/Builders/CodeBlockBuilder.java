package dos.EXL.Parser.Builders;

import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;

public class CodeBlockBuilder {

    private CodeBlock body;

    public CodeBlockBuilder(){
        body = new CodeBlock();
    }

    public CodeBlockBuilder addDeclare(String name, String type, Expression expr){
        this.body.addLine(LineFactory.IninitVariable(name, type, expr));
        return this;
    }

    public CodeBlockBuilder addIf(Expression bool, CodeBlock body){
        this.body.addLine(LineFactory.ifL(bool, body));
        return this;
    }

    public CodeBlockBuilder addFor(DeclarLine dec, Expression bool,Line l, CodeBlock body){
        this.body.addLine(LineFactory.forL(dec, bool,l, body));
        return this;
    }

    public CodeBlockBuilder addWhile(Expression bool, CodeBlock body){
        this.body.addLine(LineFactory.whileL(bool, body));
        return this;
    }

    public CodeBlockBuilder addSwitch(){
        return this;
    }

    public CodeBlockBuilder addReturn( Expression expr){
        this.body.addLine(LineFactory.returnL(expr));
        return this;
    }

    public CodeBlockBuilder addPrint(Expression expr){
        this.body.addLine(LineFactory.Print(expr));
        return this;
    }

    public CodeBlockBuilder addExpr(Expression expr){
        this.body.addLine(LineFactory.exprL(expr));
        return this;
    }

    public CodeBlockBuilder addVarO(String name, Expression expr){
        this.body.addLine(LineFactory.varO(name, expr));
        return this;
    }

    public CodeBlock build(){
        return body;
    }


    
}
