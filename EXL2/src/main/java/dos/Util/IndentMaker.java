package dos.Util;

public class IndentMaker {

    public static String indent(int level){
        String res = "";
        for(int i = 0; i < level;i++){
            res += "\t";
        }
        return res;
    }
    
}
