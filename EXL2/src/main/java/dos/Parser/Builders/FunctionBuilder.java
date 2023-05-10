package dos.Parser.Builders;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Types.Function;
import dos.Types.Tag;
import dos.Types.Lines.CodeBlock;

public class FunctionBuilder {

    String name; 
    String type;
    List<Tag> tags;
    List<Pair<String,String>> params;
    CodeBlock body; 

    public FunctionBuilder(){
        name = "";
        type = "";
        tags = new ArrayList<>();
        params = new ArrayList<>();
        body = new CodeBlock();
    }

    public FunctionBuilder setName(String s){
        name = s;
        return this;
    }

    public FunctionBuilder setType(String ty){
        type = ty;
        return this;
    }

    public FunctionBuilder setType(Token t){
        switch(t.getType()){
            case Value:
                throw new Error("Not implemented objects yet");
            case Int:
                type = "int";
                break;
            case Float:
                type = "int";
                break;
            case Double:
                type = "int";
                break;  
            case Boolean:
                type = "int";
                break;
            default:
                throw new Error("Unkown function type");
        }
        return this;
    }

    public FunctionBuilder addParameter(String type, String name){
        params.add(new Pair<String,String>(name, type));
        return this;
    }

    public FunctionBuilder addTag(Tag t){
        tags.add(t);
        return this;
    }

    public FunctionBuilder addTag(Token t){
        switch(t.getType()){
            case Private:
                tags.add(Tag.Private);
                break;
            case Static:
                tags.add(Tag.Static);
                break;
            case Public:
                tags.add(Tag.Public);
                break;
            default:
                throw new Error("Unkown Tag");
        }
        return this;
    }

    public FunctionBuilder setBody(CodeBlock bo){
        body = bo;
        return this;
    }    

    public Function build(){
        return new Function(name,tags, type, body, params);
    }
}
