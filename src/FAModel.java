import java.util.List;

public class FAModel {
    boolean fa = false;
    String Q[];
    String X[];
    List<S> SR;
    String start_state;
    String final_state;

    public boolean isFa() {
        return fa;
    }

    public void setFa(boolean fa) {
        this.fa = fa;
    }

    public FAModel() {
    }

    public String[] getQ() {
        return Q;
    }

    public void setQ(String[] q) {
        Q = q;
    }

    public String[] getX() {
        return X;
    }

    public void setX(String[] x) {
        X = x;
    }

    public List<S> getSR() {
        return SR;
    }

    public void setSR(List<S> SR) {
        this.SR = SR;
    }

    public String getStart_state() {
        return start_state;
    }

    public void setStart_state(String start_state) {
        this.start_state = start_state;
    }

    public String getFinal_state() {
        return final_state;
    }

    public void setFinal_state(String final_state) {
        this.final_state = final_state;
    }

    static class S{
        String state;
        List<Symbol> symbols;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<Symbol> getSymbols() {
            return symbols;
        }

        public void setSymbols(List<Symbol> symbols) {
            this.symbols = symbols;
        }
    }

    static class Symbol{
        String symbol;
        List<Tx> tx;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public List<Tx> getTx() {
            return tx;
        }

        public void setTx(List<Tx> tx) {
            this.tx = tx;
        }
    }

    static class Tx{
        String tx;

        public String getTx() {
            return tx;
        }

        public void setTx(String tx) {
            this.tx = tx;
        }
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


    public String toTxToString(){
        String s = "";
        String q = "";
        String t = "";
        String re = "";
        for (FAModel.S i:SR){

            for (FAModel.Symbol j:i.getSymbols()){
                s = s+j.getSymbol();
                for (FAModel.Tx k:j.getTx()){
                    t = t+k.getTx();
                }
            }

        }

        return s;
    }

    @Override
    public String toString() {
//        String q = "{";
//        for (int i = 0; i<Q.length; i++){
//            q = q + Q[i] +", ";
//        }
//        q = q+"}";
//
//        String x = "{";
//
//        for(int i=0; i<X.length;i++){
//            x = x+X[i]+", ";
//        }
//        x=x+"}";
//
//
//
//        return "\nQ=" + q +
//                ",\nX=" + x +
//                ",\nS=" + x +
//                ",\nstart_state='" + start_state + '\'' +
//                ",\nfinal_state='" + final_state + '\'' +"\n";
        String fa = "DFA";
        if (isFa()){
            fa = "NFA";
        }
        return toTxToString()+
                "\n This FA is "+fa;
    }
}
