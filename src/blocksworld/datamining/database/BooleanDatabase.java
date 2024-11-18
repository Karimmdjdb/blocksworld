package blocksworld.datamining.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import blocksworld.modelling.variables.BooleanVariable;

// Classe représentant une base de données transactionnelle.
public class BooleanDatabase {

    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;

    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new  ArrayList<>();
    }

    // Ajoute une transaction a la base de données.
    public void add(Set<BooleanVariable> transaction) {
        transactions.add(transaction);
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }
}
