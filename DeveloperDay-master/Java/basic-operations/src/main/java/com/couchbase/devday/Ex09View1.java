package com.couchbase.devday;


import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.*;
import com.google.gson.Gson;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Ex09View1 {

    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase - Views");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));
        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "beer-sample", "");

            Gson json = new Gson();

            DesignDocument designDocument = cb.getDesignDocument("beer");
            List<ViewDesign> views = designDocument.getViews();

            for (ViewDesign view : views) {
                System.out.println(view.getName());
                System.out.println("\t" + view.getMap());
            }


            {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Beer (by_name)");
                View view = cb.getView("beer", "by_name");
                Query query = new Query();
                //query.setLimit(10);

                ViewResponse viewResponse = cb.query(view, query);
                for (ViewRow row : viewResponse) {
                    System.out.println(row.getKey());
                }
                System.out.println("");
            }


            System.out.println("\n\n");

            cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
        }

    }


}
