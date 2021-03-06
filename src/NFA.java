import java.util.*;

public class NFA {
    String startState;
    Set<String> finalState;
    Map<String, Map<Character, List<String>>> transitions;

    public NFA(){}

    public NFA(String[] ss, String[] ts){
        finalState = new TreeSet<String>();
        transitions = new TreeMap<String, Map<Character, List<String>>>();

        //State
        for (String v : ss) {
            String[] pieces = v.split(",");
            if (pieces.length>1) {
                if (pieces[1].equals("S")) startState = pieces[0];
                else if (pieces[1].equals("E")) finalState.add(pieces[0]);
            }
        }

        //Transition
        for (String e : ts) {
            String[] pieces = e.split(",");
            String from = pieces[0], to = pieces[1];
            if (!transitions.containsKey(from)) transitions.put(from, new TreeMap<Character,List<String>>());
            for (int i=2; i<pieces.length; i++) {
                char c = pieces[i].charAt(0);
                // difference from DFA: list of next states
                if (!transitions.get(from).containsKey(c)) transitions.get(from).put(c, new ArrayList<String>());
                transitions.get(from).get(c).add(to);
            }
        }
        System.out.println("start:"+startState);
        System.out.println("end:"+finalState);
        System.out.println("transitions:"+transitions);
    }

    /**
     * Returns whether or not the DFA accepts the string --
     * follows transitions according to its characters, landing in an end state at the end of the string
     */
    public boolean match(String s) {
        // difference from DFA: multiple current states
        Set<String> currStates = new TreeSet<String>();
        currStates.add(startState);
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            Set<String> nextStates = new TreeSet<String>();
            // transition from each current state to each of its next states
            for (String state : currStates)
                if (transitions.get(state).containsKey(c))
                    nextStates.addAll(transitions.get(state).get(c));
            if (nextStates.isEmpty()) return false; // no way forward for this input
            currStates = nextStates;
        }
        // end up in multiple states -- accept if any is an end state
        for (String state : currStates) {
            if (finalState.contains(state)) return true;
        }
        return false;
    }

    /**
     * Helper method to test matching against a bunch of strings, printing the results
     */
    public void test(String name, String[] inputs) {
        System.out.println("***" + name);
        for (String s : inputs)
            System.out.println(s + ":" + match(s));
    }

    public static void main(String[] args) {
//        String[] ss1 = { "A,S", "B,E", "C" };
//        String[] ts1 = { "A,B,0", "A,C,1", "B,B,0,1", "C,C,0,1" };
//        NFA dfa1 = new NFA(ss1, ts1);
//
//        String[] testsT1 = { "0", "00", "00000", "0010101" };
//        dfa1.test("testsT1", testsT1);
//        String[] testsF1 = { "", "1", "1100110" };
//        dfa1.test("testsF1", testsF1);
//
//        String[] ss1b = { "A,S", "B,E" };
//        String[] ts1b = { "A,B,0", "B,B,0,1" };
//        NFA nfa1 = new NFA(ss1b, ts1b);
//
//        nfa1.test("testsT1b", testsT1);
//        nfa1.test("testsF1b", testsF1);
//
//        String[] ss2 = { "A,S", "B", "C", "D,E", "E,E" };
//        String[] ts2 = { "A,A,0,1", "A,B,1", "B,D,1", "D,D,0,1", "A,C,0", "C,E,0", "E,E,0,1" };
//        NFA nfa2 = new NFA(ss2, ts2);
//
//        String[] testsT2 = { "00", "001", "0001100", "010110101", "101010100" };
//        nfa2.test("testsT2", testsT2);
//        String[] testsF2 = { "0", "010", "0101010", "1", "101010101010101010101" };
//        nfa2.test("testsF2", testsF2);

    }

    public void accepted(List<FAModel> list){
        FAModel faModel = list.get(0);

        String S;   //Start state
        String[] E; //Final state
        String[] State; //State
       //get start state and final state from the list
        S = faModel.getStart_state();
        E = faModel.getFinal_state();

        int index = faModel.getQ().length;//1 is the length of the S;
        int count = 0;
        State = new String[index];
        State[count] = S+",S";
        count++;
        for (int i=0; i<E.length; i++){
            State[count]=E[i]+",E";
            count++;
        }

        for (int i=0; i<faModel.getQ().length;i++){
            String q = faModel.getQ()[i];
            for (int j=0; j<E.length; j++){
                if (!q.equals(S) && !q.equals(E[j]) && count<index){
                    State[count] = q;
                    count++;
                    break;
                }
            }
        }

        System.out.println(State);


        List<String> train = new ArrayList<>();
        for (FAModel.S tran:faModel.getSR()){
            String from = tran.getState();
            String to="";
            String sym="null";


            String ts1="";
            boolean checkSym = true;
            for (FAModel.Symbol symbol:tran.getSymbols()){
                String ts = from;
                for (FAModel.Tx tx:symbol.getTx()){
                    if (!sym.equals(symbol.getSymbol()) && tx.getTx().equals(to)){
                        sym = symbol.getSymbol();
                        ts = ts1+","+symbol.getSymbol();
                        train.set(train.size()-1,ts);
                    }else if (!sym.equals(symbol.getSymbol())&&!tx.getTx().equals(to)){
                        sym = symbol.getSymbol();
                        to = tx.getTx();
                        ts = ts+","+to+","+symbol.getSymbol();
                        train.add(ts);

                    }else if (sym.equals(symbol.getSymbol())&&!tx.getTx().equals(to)){
                        sym = symbol.getSymbol();
                        to = tx.getTx();
                        ts = from + ","+to+","+sym;
                        train.add(ts);

                    }
                    ts1=ts;

                }

            }
        }

//        System.out.print(train.toString());
        String[] train1 = new String[train.size()];
        int i=0;
        for (String v:train){
            train1[i]=v;
            i++;
        }

        NFA dfa1 = new NFA(State, train1);
        while (true){
            String[] testsF1 = { insertTest("Test if a string is accepted by a FA\nPress 'q' if you don't want to test!\nEnter the string : ") };
            dfa1.test(" ",testsF1);
        }


        
        

    }

    String insertTest(String outPut){
        Scanner input = new Scanner(System.in);
        System.out.print(outPut);
        return input.next();
    }
}
