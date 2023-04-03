package dos.Types;

import java.util.List;

import org.javatuples.Pair;


import dos.Types.Lines.CodeBlock;

public class Function {
    
    String Name;
    List<Tag> tags;
    List<Pair<String, String>> params;  
    String type;
    CodeBlock body; 

    public Function(List<Tag> t, String ty, CodeBlock c, List<Pair<String,String>> p){
        tags = t;
        type = ty;
        params = p;
        body = c;
    }
}
