public class Stack {
    private ArrayList<String> keeper;

    public Stack(){
        this.keeper = new ArrayList<String>();
    }

    public void push(String x){
        this.keeper.add(x);
    }

    public String pop(){
        return this.keeper.remove(this.keeper.size()-1);
    }

    public String top(){
        return this.keeper.get(this.keeper.size()-1);
    }
}

public class main{
    public static void main(String[] args) {
        Stack stack = new stack();
        stack.push("A");
        stack.push("B");
        stack.pop();
    }
}