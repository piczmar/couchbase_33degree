package com.couchbase.devday;

import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;

import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Ex03StorageJson2 {
    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\tCouchbase JSON Document Storage Operations");
        System.out.println("--------------------------------------------------------------------------");


        List<URI> uris = new LinkedList<URI>();

        uris.add(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseClient cb = null;
        try {
            cb = new CouchbaseClient(uris, "default", "");



            {
                System.out.println(" Store simple JSON using GSON parsing");
                Employee empl = new Employee();
                empl.first = "John"  ;
                empl.last = "Smith";
                empl.dob = new Date();


                Gson json = new Gson();
                String jsonString = json.toJson( empl );
                cb.set("employee:1", 0, jsonString);
                System.out.println("GSON value  :"+ cb.get("employee:1") );

                empl = json.fromJson( cb.get("employee:1").toString(), Employee.class );
                System.out.println("Doc name :"+ empl.first);

                System.out.println("");
            }


            System.out.println("\n\n");

            cb.shutdown(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: " + e.getMessage());
        }

    }
}

class Employee{
    String first;
    String last;
    Date dob;
}
