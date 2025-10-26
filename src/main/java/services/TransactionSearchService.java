package services;

import interfaces.FilterableService;
import model.entities.EventPassed;
import model.entities.Transaction;
import model.entities.TransactionList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionSearchService implements FilterableService {
    List<Transaction> list;

    @Override
    public ArrayList<Transaction> filterable(EventPassed<?> e) {


        String query = e.getType() == null ? "" : (String) e.getType();

        if (query.trim().isEmpty()) {
            return TransactionList.getInstance();
        }


        List<Transaction> list = TransactionList.getInstance()
                .stream()
                .filter(item -> item.getDescription().contains(query))
                .collect(Collectors.toList());

       return (ArrayList<Transaction>) list;
    }
}
