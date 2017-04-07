package com.example.mahmoudshahen.fcis;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mahmoud shahen on 06/04/2017.
 */

public class DialogAdd extends Dialog {

    EditText textName, textEmail;
    Button buttonDismiss, buttonAdd;

    FirebaseDatabase mFirebase;
    DatabaseReference databaseReference;
    public DialogAdd(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        textName = (EditText)findViewById(R.id.et_name);
        textEmail = (EditText)findViewById(R.id.et_email);
        buttonDismiss = (Button) findViewById(R.id.b_dismiss);
        buttonAdd = (Button)findViewById(R.id.b_add);

        mFirebase = FirebaseDatabase.getInstance();
        databaseReference = mFirebase.getReference("/FCIS");
        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textName.getText().toString().isEmpty() && !textEmail.getText().toString().isEmpty()) {
                    Instructor instructor = new Instructor();
                    instructor.setName(textName.getText().toString());
                    instructor.setEmail(textEmail.getText().toString());
                    String key = databaseReference.push().getKey();
                    databaseReference.child(MainActivity.subject).child(key).setValue(instructor);
                    Log.v("fff", "300");
                    DialogAdd.this.dismiss();
                }
            }
        });
    }
}
