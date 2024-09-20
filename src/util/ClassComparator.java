package util;

public class ClassComparator {
    public static boolean hardComparaison(Object a, Object b){
        return a.getClass().getName().equals(b.getClass().getName());
    }
}
