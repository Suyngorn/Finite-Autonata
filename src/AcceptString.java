import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AcceptString {
    List<FAModel> list;
    InputStringCallback stringCallback;
    Scanner input = new Scanner(System.in);

    public AcceptString(List<FAModel> list, InputStringCallback stringCallback) {
        this.list = new ArrayList<>();
        this.list = list;
        this.stringCallback = stringCallback;
    }

    public boolean testAcceptString(){
        boolean test = true;
        FAModel faModel = list.get(0);
        String str = insertString("Test if a string is accepted by a FA\nPress 'q' if you don't want to test!\nEnter the string : ");
        if (!str.equals("q")){
            char[] symbol = new char[str.length()];
            int index = 0;
            symbol = ConString(str);

            String q = faModel.getStart_state();
            String nQ = "";

            for (FAModel.S k : faModel.getSR()){
                if (q.equals(k.getState())){
                    boolean t = false;
                    for (FAModel.Symbol j:k.getSymbols()) {
                        if (String.valueOf(symbol[index]).equals(j.getSymbol())) {
                            nQ = j.getTx().get(0).getTx();
                            print("\n" + nQ + "\n");
                            index++;
                            break;
                        }
                    }
                }
            }

            boolean a = false;
            do {

                for (FAModel.S k: faModel.getSR()){
                    if (nQ.equals(k.getState())){
                        for (FAModel.Symbol j:k.getSymbols()){
                            if (String.valueOf(symbol[index]).equals(j.getSymbol())){
                                nQ = j.getTx().get(0).getTx();
                                print(nQ+"\n");
                                index++;
                                if (!nQ.equals("None")){
                                    a = true;
                                }
                                break;
                            }
                        }
                        if (!a){
                            test = false;
                        }
                    }
                }
            }while (test&&index<symbol.length&&a);

            if (nQ.equals(faModel.getFinal_state())&&a){
                print("The string is accepted by a FA");
            }else {
                print("The string is rejected by FA");
            }
        }else {
            stringCallback.exitInputStringListener();
        }


        return true;
    }


    private char[] ConString(String str){
        int lStr = str.length();
        char[] ch = new char[lStr];
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        return ch;
    }


    private String insertString(String outPut){
        print(outPut);
        return input.next();
    }

    private void print(String a){
        System.out.print(a);
    }



}
