public class Test {
 
    
    public static void main(String[] args) {
        notMain(4);
        notMain(notMain(4));
    }

    public static int notMain(int i){
        return i + 4;
    }
}