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
        String str = insertString("Test if a string is accepted by a FA\nPress 'q' if you don't want to test!\nEnter the string : ");
        if (!str.equals("q")){
            char[] symbol = new char[str.length()];

            symbol = ConString(str);
            testNFA(symbol);

//            for (FAModel.S k : faModel.getSR()){
//                if (q.equals(k.getState())){
//                    for (FAModel.Symbol j:k.getSymbols()) {
//                        if (String.valueOf(symbol[index]).equals(j.getSymbol())) {
//                            nQ = j.getTx().get(0).getTx();
//                            print("\n" + nQ + "\n");
//                            index++;
//                            break;
//                        }
//                    }
//                }
//            }
//
//            boolean a = false;
//            do {
//                for (FAModel.S k: faModel.getSR()){
//                    if (nQ.equals(k.getState())){
//                        for (FAModel.Symbol j:k.getSymbols()){
//                            if (String.valueOf(symbol[index]).equals(j.getSymbol())){
//                                nQ = j.getTx().get(0).getTx();
//                                print(nQ+"\n");
//                                index++;
//                                if (!nQ.equals("None")){
//                                    a = true;
//                                }
//                                break;
//                            }
//                        }
//                        if (!a){
//                            test = false;
//                        }
//                    }
//                }
//            }while (test&&index<symbol.length&&a);


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

    private void testNFA(char[] symbol){
        boolean test = true;
        int c;
        int mIndex =0;
        String[][] memory = new String[100][3];
        FAModel faModel = list.get(0);
        int index = 0;
        String startState = faModel.getStart_state();
        String nQ = "";

        //Loop to get the transaction q0;
        for (FAModel.S k : faModel.getSR()) {
            if (startState.equals(k.getState())) {
                for (FAModel.Symbol s:k.getSymbols()){
                    if (String.valueOf(symbol[index]).equals(s.getSymbol()) && s.getTx()!=null && !s.getTx().get(0).getTx().equals("None")){
                        int count = 0;
                        for (FAModel.Tx tx:s.getTx()){
                            if (count==0){
                                nQ = tx.getTx();
                                index++;
                            }else {
                                memory[mIndex][0] = tx.getTx();
                                memory[mIndex][1] = index+"";
                                memory[mIndex][2] = "false";
                                mIndex++;
                            }
                            count++;
                        }
                        break;
                    }
                }
                break;
            }
        }

        boolean accept = true;
        do {
            for (FAModel.S k: faModel.getSR()){
                if (nQ.equals(k.getState())){
                    for (FAModel.Symbol s:k.getSymbols()){
                        if (index<symbol.length){
                            if (String.valueOf(symbol[index]).equals(s.getSymbol()) && s.getTx()!=null && !s.getTx().get(0).getTx().equals("None")){
                                int count = 0;
                                for (FAModel.Tx tx:s.getTx()){
                                    if (count==0){
                                        nQ = tx.getTx();
                                        index++;
                                        accept=true;
                                    }else {
                                        memory[mIndex][0] = tx.getTx();
                                        memory[mIndex][1] = index+"";
                                        memory[mIndex][2] = "false";
                                        mIndex++;
                                    }
                                    count++;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }while (index<symbol.length);

        if (accept && nQ.equals(faModel.getFinal_state())){
            print("The string has been accepting by FA");
        }else {
            print("The string has not accepted by FA");
        }
    }

    private String insertString(String outPut){
        print(outPut);
        return input.next();
    }

    private void print(String a){
        System.out.print(a);
    }
}
