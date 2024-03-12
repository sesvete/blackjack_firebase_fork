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


public class RealtimeDBHelper {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference rootReference = database.getReference();
    private final DatabaseReference usersReference = rootReference.child("users");

    interface OnCheckExistingUser{
        void onCreateNewUser(String uid, String email);
        void onRetrieveExistingData(String uid, String email);
    }

    interface OnCreateUserCallback{
        void onCreateUser(String uid, String email, int points);
    }

    interface OnPointsReceivedListener{
        void onPointsReceived(int points);
    }

    public void writeNewUser(String uid, String email, OnCreateUserCallback callback){
        usersReference.child(uid).child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("emailSetSuccess", "Email set");
                } else {
                    Log.d("emailSetError", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });
        usersReference.child(uid).child("points").setValue(100).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("pointsSetSuccess", "Points set");
                } else {
                    Log.d("pointsSetError", task.getException().getMessage());
                }
            }
        });
        callback.onCreateUser(uid, email, 100);
    }

    // https://stackoverflow.com/questions/47893328/checking-if-a-particular-value-exists-in-the-firebase-database
    public void checkIfUserExists(String uid, String email, OnCheckExistingUser check){
        DatabaseReference userNameReference = usersReference.child(uid);
        ValueEventListener checkForUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    writeNewUser(uid, email, new OnCreateUserCallback() {
                        @Override
                        public void onCreateUser(String uid, String email, int points) {
                            Log.d("UserSuccess", "Successfully created user");
                        }
                    });
                    check.onCreateNewUser(uid, email);
                }
                else{
                    check.onRetrieveExistingData(uid, email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("checkUser", error.getMessage());
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
                        Log.d("SuccessPoints", "Points retrieved");
                        userPoints = dataSnapshot.getValue(Integer.class);
                    }
                    else{
                        Log.d("skipped points", "failure");
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
                    Log.d("updatePointsSuccess", "points updated successfully");
                } else {
                    Log.d("updatePointsError", task.getException().getMessage());
                }
            }
        });
    }
}
