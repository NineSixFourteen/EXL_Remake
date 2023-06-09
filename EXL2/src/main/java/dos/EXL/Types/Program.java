package dos.EXL.Types;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.objectweb.asm.*;

import dos.EXL.Types.Lines.Field;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;


public class Program {
    
    private List<Field> fields;  
    private List<Pair<String,String>> imports;
    private List<Tag> tags;
    private String name;
    private List<Function> functions;
    private List<Function> constructors;
    private Maybe<Function> main; 

    public Program(){
        fields = new ArrayList<>();
        constructors = new ArrayList<>();
        name = "";
        functions = new ArrayList<>();
        tags = new ArrayList<>();
        imports = new ArrayList<>();
        main = new Maybe<>();
    }

    public void addTags(Tag t){
        tags.add(t);
    }

    public void addField(Field f){
        fields.add(f);
    }

    public void setName(String s ){
        name = s;
    }

    public void addFunction(Function f){
        functions.add(f);
    }

    public void addConstructor(Function f){
        constructors.add(f);
    }

    public void addImport(String name, String path){
        imports.add(new Pair<>(name, path));
    }

    public List<Field> getFields() {
        return fields;
    }

    public Maybe<Function> getMain() {
        return main;
    }

    public void setMain(Maybe<Function> main) {
        this.main = main;
    }
    
    public List<Function> getFunctions() {
        return functions;
    }

    public List<Function> getConstructors() {
        return constructors;
    }

    public List<Pair<String, String>> getImports() {
        return imports;
    }

    public String getName() {
        return name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String makeString(){
        StringBuilder sb = new StringBuilder();
        for(Tag t : tags){
            sb.append(t.name().toLowerCase()).append(" ");
        }
        sb.append("class ").append(name).append("{\n");
        for(int i = 0; i < fields.size();i++){
            sb.append(fields.get(i).makeString(0));
        }
        for(Function f : functions){
            sb.append(f.makeString());
        }
        sb.append("}");
        return sb.toString();
    }

    public Result<ClassWriter> toASM(){ 
        ClassWriter cw = new ClassWriter(0);
        return Results.makeResult(cw);
    }

}
