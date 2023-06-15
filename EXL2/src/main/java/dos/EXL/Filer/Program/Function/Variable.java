package dos.EXL.Filer.Program.Function;

public class Variable {

    private String name; 
    private String type; 
    private int startLine; 
    private int endLine;
    private int memory;

    public Variable(String Name, String Type, int Start, int End,int Memory){
        name = Name;
        type = Type;
        startLine = Start;
        endLine = End;
        memory = Memory;
    }

    public String getName() {
        return name;
    }

    public int getStartLine() {
        return startLine;
    }

    public String getType() {
        return type;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getMemory() {
        return memory;
    }
    
}
