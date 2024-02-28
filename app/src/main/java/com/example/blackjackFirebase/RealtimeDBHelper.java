package com.example.blackjackFirebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// TODO: organise code
// TODO: Fixed README / document your work
// TODO: determine security rules

public class RealtimeDBHelper {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference rootReference = database.getReference();
    private final DatabaseReference usersReference = rootReference.child("users");

    public void test(){
        rootReference.child("messages").setValue("Hello, World");
    }

    public void writeNewUser(String uid, String email){
        usersReference.child(uid).child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(context, "user added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });
        usersReference.child(uid).child("points").setValue(100).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(context, "user added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });
    }

    // https://stackoverflow.com/questions/47893328/checking-if-a-particular-value-exists-in-the-firebase-database
    public void checkIfUserExists(String uid, String email){
        DatabaseReference userNameReference = usersReference.child(uid);
        DatabaseReference pointsReference = userNameReference.child("points");
        ValueEventListener checkForUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    writeNewUser(uid, email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("checkUser", error.getMessage()); //Don't ignore errors!
            }
        };
        userNameReference.addListenerForSingleValueEvent(checkForUserListener);
    }

    public void getPoints(String uid, OnPointsReceivedListener listener){
        DatabaseReference userNameReference = usersReference.child(uid);
        DatabaseReference pointsReference = userNameReference.child("points");
        pointsReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    int userPoints = 0;
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                        userPoints = dataSnapshot.getValue(Integer.class);
                    }
                    listener.onPointsReceived(userPoints);
                } else {
                    // Handle the error
                    listener.onPointsReceived(0);
                }
            }
        });
    }

    public void updatePoints(String uid, int points){
        DatabaseReference userNameReference = usersReference.child(uid);
        DatabaseReference pointsReference = userNameReference.child("points");
        pointsReference.setValue(points).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(context, "user added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });
    }

    interface OnPointsReceivedListener{
        void onPointsReceived(int points);
    }

}
