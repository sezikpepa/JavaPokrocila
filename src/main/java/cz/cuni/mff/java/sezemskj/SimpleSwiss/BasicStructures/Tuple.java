package cz.cuni.mff.java.sezemskj.SimpleSwiss.BasicStructures;

/**
 * Basic tuple which is missing in java
 * @param <T> Any type can be in tuple
 */
public class Tuple<T> {


    /**
     * Creates new tuple with three items
     * @param item1 First item of the tuple
     * @param item2 Second item of the tuple
     * @param item3 Third item of the tuple
     */
    public Tuple(T item1, T item2, T item3){
        Item1 = item1;
        Item2 = item2;
        Item3 = item3;
    }

    /**
     * First item of the tuple
     */
    public T Item1;

    /**
     * Second item of the tuple
     */
    public T Item2;

    /**
     * Third item of the tuple
     */
    public T Item3;


}
