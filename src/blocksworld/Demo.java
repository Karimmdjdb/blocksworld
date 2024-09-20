package blocksworld;

import blocksworld.modelling.ConstraintsManager;

public class Demo {
    public static void main(String[] args) {
        ConstraintsManager w = new ConstraintsManager(10,5);
        System.out.println(w.getConstraints());
    }
}