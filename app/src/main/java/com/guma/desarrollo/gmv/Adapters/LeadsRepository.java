package com.guma.desarrollo.gmv.Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maryan Espinoza on 19/02/2017.
 */

public class LeadsRepository {
    private static LeadsRepository repository = new LeadsRepository();
    private HashMap<String, Lead> leads = new HashMap<>();

    public static LeadsRepository getInstance() {
        return repository;
    }

    private LeadsRepository() {
        saveLead(new Lead("ARTICULO", "13705071", "57.96"));
    }

    private void saveLead(Lead lead) {
        leads.put(lead.getId(), lead);
    }

    public List<Lead> getLeads() {
        return new ArrayList<>(leads.values());
    }
}
