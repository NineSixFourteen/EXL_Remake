package dos.EXL.Types;

import java.util.ArrayList;
import java.util.List;

import dos.EXL.Types.Lines.Field;

public class Program {
    
    private List<Field> fields;  
    private List<Tag> tags;
    private String name;
    private List<Function> functions;

    public Program(){
        fields = new ArrayList<>();
        name = "";
        functions = new ArrayList<>();
        tags = new ArrayList<>();
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




}
