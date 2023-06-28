package dos.EXL.Parser.Builders;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Function;
import dos.EXL.Types.Program;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.Field;
import dos.Util.Maybe;

public class ProgramBuilder {

    private Program p;

    public ProgramBuilder(){
        p = new Program();
    }

    public ProgramBuilder addField(String name, List<Tag> tags, Expression e,String type){
        p.addField(new Field(tags, name, e, type));
        return this;
    }

    public ProgramBuilder addField(Field f ){
        p.addField(f);
        return this;
    }

    public ProgramBuilder setMain(Maybe<Function> main){
        p.setMain(main);
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

    public ProgramBuilder addFunction(String name, List<Tag> tags, List<Pair<String,String>> params,String type, CodeBlock body){
        p.addFunction(new Function(name, tags, type, body, params));
        return this;
    }

    public ProgramBuilder addTag(Tag t){
        p.addTags(t);
        return this;
    }

    public ProgramBuilder addTags(List<Tag> tags){
        for(Tag t : tags){
            p.addTags(t);
        }
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

    public ProgramBuilder addImport(String name, String path){
        p.addImport(name, path);
        return this;
    }

    public Program build(){
        return p;
    }

    public ProgramBuilder addImports(List<Pair<String, String>> imports) {
        for(Pair<String, String> impor : imports){
            p.addImport(impor.getValue0(),impor.getValue1());
        }
        return this;
    }

    public ProgramBuilder addConstructor(Function f){
        p.addConstructor(f);
        return this;
    }

}
