package dos.Parser.Builders;

import java.util.List;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Types.Function;
import dos.Types.Program;
import dos.Types.Tag;
import dos.Types.Lines.CodeBlock;
import dos.Types.Lines.Field;

public class ProgramBuilder {

    private Program p;

    public ProgramBuilder(){
        p = new Program();
    }

    public ProgramBuilder addField(String name, List<Tag> tags, Expression e ){
        p.addField(new Field(tags, name, e));
        return this;
    }

    public ProgramBuilder addField(Field f ){
        p.addField(f);
        return this;
    }

    public ProgramBuilder setName(String s){
        p.setName(s);
        return this;
    }

    public ProgramBuilder addFunction(Function f ){
        p.addFunction(f);
        return this;
    }

    public ProgramBuilder addFunction(String name, List<Tag> tags, List<Pair<String,String>> params, String type, CodeBlock body){
        p.addFunction(new Function(tags, type, body, params));
        return this;
    }

    public ProgramBuilder addTag(Tag t){
        p.addTags(t);
        return this;
    }

    public ProgramBuilder addTag(Token t){
        switch(t.getType()){
            case Public:
                p.addTags(Tag.Public);
                break;
            case Private:
                p.addTags(Tag.Private);
                break;
            default:
        }
        return this;
    }

    public Program build(){
        return p;
    }

}
