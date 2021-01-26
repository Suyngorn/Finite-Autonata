import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DesignFA {
    List<FAModel> list = new ArrayList<>();
    public List<FAModel> insertDataToFa(){
        //Scanner created to ask user to input som values
        Scanner input = new Scanner(System.in);

        //Call model of FA to insert data to the object
        FAModel faModel = new FAModel();

        //Ask user to input the amount of state of FA!
        print("\nHow many state?: ");
        int i = input.nextInt();
        //when user input number 3 so there are q0,q1,q3;
        //so we need to convert the number 3 to the q1,q2,q3 like below
        String Q[] = new String[i];
        for (int j = 0; j<i;j++){
            Q[j] = "q"+j;

        }
        //add state to the object
        faModel.setQ(Q);

        //ask user about the symbol
        print("\nHow many symbols?: ");
        int sys = input.nextInt();
        print("\nDo you want ot choose your own symbol?[y/n]: ");
        String sym =  input.next();
        if (sym.equals("y")){
           //I haven't done it yet!
        }else {
            //auto generate symbol by "a,b,c,.."
            String a[] = new String[sys];
            char c = 'a';
            for (int r=0; r<sys;r++){
                a[r] = c+"";
                c++;
            }
            faModel.setX(a);

        }

        //Ask user what is the start state and final state
        String t = "";
        //here I loop the state to tell the user how many state are there.
        for (int m=0; m<Q.length;m++){
            t = t + Q[m]+", ";
        }
        print("\n"+t+"\n");
        print("Which is the start state?:");
        faModel.setStart_state("q"+input.next());
        print("How many final states? :");
        int FS = input.nextInt();
        String[] fin = new String[FS];
        for (int iFS=0; iFS<FS;iFS++){
            print("which are the final state?: ");
            fin[iFS] = "q"+input.next();
        }
        faModel.setFinal_state(fin);

        //ask user where is the state go ex: q1 by a : q2;
        List<FAModel.S> listS = new ArrayList<>();
        println("\nEnter the state by typing only the number!\npress 'q' to exit the state!\nBe careful you cannot go back!");
        for (int qq=0; qq<Q.length;qq++){

            //start a new model to add the new values to the object
            FAModel.S s = new FAModel.S();
            s.setState(Q[qq]);

            //create the list to store the symbol model.
            List<FAModel.Symbol> listSymbol = new ArrayList<>();

            for (int aa =0; aa<faModel.getX().length;aa++){
                List<FAModel.Tx> listTx = new ArrayList<>();
                FAModel.Symbol symbol = new FAModel.Symbol();
                symbol.setSymbol(faModel.getX()[aa]);
                String getState="";
                int count = 0;
                do {
                    FAModel.Tx tx = new FAModel.Tx();
                    print(Q[qq] + " by " + faModel.getX()[aa] + " : q");
                    getState = input.next();

                    if (count>=2 && !faModel.isFa()){
                        faModel.setFa(true);
                    }
                    if (getState.equals("q")){
                        if (count ==0){
                            tx.setTx("None");
                            listTx.add(tx);
                        }

                    }else {
                        tx.setTx("q"+getState);
                        listTx.add(tx);
                        count++;
                    }

                } while (!getState.equals("q"));
                symbol.setTx(listTx);
                listSymbol.add(symbol);
                s.setSymbols(listSymbol);
            }
            listS.add(s);
            faModel.setSR(listS);
            list.add(faModel);
        }

        long x = 99999999;
        printFA(list);

        return list;
    }

    /*
    S = {
        {
            q0,
            symbol{
                a{q0}
                b{q1}
            }
        }
        {
            q1,
            symbol{
                a{q0}
                b{q1}
            }
        }

    }

    //We want.
        +===============+
        |   a   |   b   |
        +===============+
    ->q0|   q0  | q0,q1 |
      q1|  none |   q2  |
     *q2|   q2  |   q2  |
      +===================

     */

    //Nothing in this function it build to print the state of the FA
    void printFA(List<FAModel> list){
        String row = "";
        String row1 ="";
        String hr = "+===============+";
        String pl = "+";
        String ru = "|";
        String ta = "\t";
        String newLine = "\n";
        row1 = ta+hr;
        for (int i=0;i<list.get(0).getX().length;i++){
            if (i==0){
                row = ta+ru+ta+list.get(0).getX()[i]+ta;
            }else if (i==list.get(0).getX().length-1){
                row = row+ru+ta+list.get(0).getX()[i]+ta+ru;
            }else {
                row = row+ru+ta+list.get(0).getX()[i]+ta;
            }
        }
        row1 = row1+newLine+row;
        for (FAModel.S k : list.get(0).getSR()){
            if (list.get(0).getStart_state().equals(k.getState())){
                row = "->"+k.getState()+ru;
            }else if (list.get(0).getFinal_state().equals(k.getState())){
                row = "*"+k.getState()+ta+ru;
            }else {
                row = k.getState()+ta+ru;
            }
            for (FAModel.Symbol s : k.getSymbols()){
                int i = 0;
                for (FAModel.Tx t:s.getTx()){
                    i++;
                    if (i>1){
                        row = row+","+t.getTx();
                    }else {
                        row = row+ta+t.getTx();
                    }
                }
                row=row+ta+ru;
            }
            row1 = row1+newLine+row;
        }
        row1 = row1+newLine+hr;
        println(row1);
        if (list.get(0).isFa()){
            println("This FA is non deterministic 'NFA'\n");
        }else println("This Fa is deterministic 'DFA'\n");

    }

    //These function build to print string!
    void print(String a){
        System.out.print(a);
    }
    void println(String a){
        System.out.println(a);
    }
}
