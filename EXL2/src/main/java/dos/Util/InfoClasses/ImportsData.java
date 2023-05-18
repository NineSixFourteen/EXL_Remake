package dos.Util.InfoClasses;

import java.util.HashMap;


public class ImportsData {

    HashMap<String, String> importPaths; 
    HashMap<String, ClassData> classes;

    public ClassData getData(String name) {
        return classes.get(name);
    }

    public String getPath(String name){
        return importPaths.get(name);
    }

}
