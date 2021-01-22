import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DesignFA {
    List<FAModel> list = new ArrayList<>();
    public void insertDataToFa(){
        Scanner input = new Scanner(System.in);
        FAModel faModel = new FAModel();


        print("\nHow many state?: ");
        int i = input.nextInt();
        String Q[] = new String[i];
        for (int j = 0; j<i;j++){
            Q[j] = "q"+j;

        }
        faModel.setQ(Q);

        print("\nHow many symbols?: ");
        int sys = input.nextInt();
        print("\nDo you want ot choose your own symbol?[y/n]: ");
        String sym =  input.next();
        if (sym.equals("y")){
            for (int k =0; k<sys; k++){

            }
        }else {
            String a[] = new String[sys];

            char c = 'a';
            for (int r=0; r<sys;r++){
                a[r] = c+"";
                c++;

            }
            faModel.setX(a);

        }
        String t = "";
        for (int m=0; m<Q.length;m++){
            t = t + Q[m]+", ";
        }
        print("\n"+t+"\n");
        print("Which is the start state?:");
        faModel.setStart_state(input.next());
        print("which is the final state?: ");
        faModel.setFinal_state(input.next());
        List<FAModel.S> listS = new ArrayList<>();
        FAModel.S s = new FAModel.S();

        println("Enter the state by typing only the number!\npress 'q' to exit the state!\nBe careful you cannot go back!");

        for (int qq=0; qq<Q.length;qq++){
            s.setState(Q[qq]);
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
        print(list.toString());
    }


    void print(String a){
        System.out.print(a);
    }
    void println(String a){
        System.out.println(a);
    }
}
