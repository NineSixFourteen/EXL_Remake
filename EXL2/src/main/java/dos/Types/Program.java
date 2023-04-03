package dos.Types;

import java.util.ArrayList;
import java.util.List;

import dos.Types.Lines.Field;

public class Program {
    
    private List<Field> fields;  
    private String name;
    private List<Function> functions;

    public Program(){
        fields = new ArrayList<>();
        name = "";
        functions = new ArrayList<>();
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




}
