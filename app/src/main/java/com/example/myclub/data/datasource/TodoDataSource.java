package com.example.myclub.data.datasource;

import android.util.Log;

import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.model.Todo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TodoDataSource {
    static TodoDataSource instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public static TodoDataSource getInstance() {
        if (instance == null) {
            instance = new TodoDataSource();
        }
        return instance;
    }

    //Firestore realtime
    public void loadListTodo(final FirebaseLoadListTodo firebaseLoadListTodo) {
            db.collection("Todo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<Todo> todos = new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Todo p = new Todo();
                            p.setName((String) document.get("name"));
                            p.setPassword((String) document.get("password"));
                            todos.add(p);
                        }
                        firebaseLoadListTodo.FirebaseLoadListTodo(todos);
                    } else {
                        Log.d("meomeo", "failed");
                        
                    }
                }
            });

    }



//

}
