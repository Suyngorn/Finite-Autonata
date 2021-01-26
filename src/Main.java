import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<FAModel> list = new ArrayList<>();
    static boolean b = true;
    public static void main(String[] args) {
        while (true){
            b=true;
            DesignFA designFA = new DesignFA();
            list = designFA.insertDataToFa();



            NFA nfa = new NFA();
            nfa.accepted(list);


//            while (b){
//                AcceptString acceptString = new AcceptString(list, callback);
//                acceptString.testAcceptString();
//            }

        }

    }

    static InputStringCallback callback = new InputStringCallback() {
        @Override
        public void exitInputStringListener() {
            b=false;
        }
    };

    public static void print(String a){
        System.out.print(a);
    }

}
