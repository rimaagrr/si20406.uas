package stmik_amik.bandung.si20406uas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import stmik_amik.bandung.si20406uas.model.DataContact;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "SI20406-uas";
    private DatabaseReference database;

    private Toolbar myToolbar;

    private EditText etName, etPhone;
    private ProgressBar loading;
    private Button btn_cancel, btn_save;
    private TextView back;

    private AlertDialog alertDialog;

    private String sPid, sPname, sPphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Write a message to the database
        database = FirebaseDatabase.getInstance("https://si20406-uas-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        sPid = getIntent().getStringExtra("id");
        sPphone = getIntent().getStringExtra("phone");
        sPname = getIntent().getStringExtra("name");

        etPhone = findViewById(R.id.phone);
        etName = findViewById(R.id.name);
        btn_save = findViewById(R.id.save);
        btn_cancel = findViewById(R.id.cancel);

        back = findViewById(R.id.back);

        loading = findViewById(R.id.simpleProgressBar);


        etPhone.setText(sPphone);
        etName.setText(sPname);

        if (sPid.equals("")){
            btn_save.setText("Save");
            btn_cancel.setVisibility(View.GONE);
            myToolbar.setTitle("Add Contact");
        } else {
            btn_save.setText("Edit");
            back.setVisibility(View.GONE);
            myToolbar.setTitle("Contact Detail");
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Sname = etName.getText().toString().substring(0, 1).toUpperCase() + etName.getText().toString().substring(1).toLowerCase();
                String Sphone = etPhone.getText().toString();

                if (btn_save.getText().equals("Save")){

                    //PERINTAH SAVE
                    if(Sname.equals("")){
                        etName.setError("Silahkan masukan Nama Lengkap");
                        etName.requestFocus();
                    } else if(Sphone.equals("")){
                        etPhone.setError("Silahkan masukan Nomor HP");
                        etPhone.requestFocus();
                    } else {
                        loading.setVisibility(View.VISIBLE);

                        submitUser(new DataContact(
                                Sname,
                                Sphone));
                        finish();
                    }
                } else {

                    //PERINTAH EDIT
                    if(Sname.equals("")){
                        etName.setError("Silahkan masukan Nama Lengkap");
                        etName.requestFocus();
                    } else if(Sphone.equals("")){
                        etPhone.setError("Silahkan masukan Nomor HP");
                        etPhone.requestFocus();
                    } else {
                        loading.setVisibility(View.VISIBLE);

                        editUser(new DataContact(
                                        Sname,
                                        Sphone),
                                sPid);
                        finish();
                    }

                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_cancel.getText().equals("Cancel")) {
                    //tutup page
                    finish();
                } else {
                    // delete
                    showDialog();

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void submitUser(DataContact dataContact){
        database.child("Contact").push().setValue(dataContact).addOnSuccessListener(AddActivity.this, (OnSuccessListener) (aVoid) -> {

            loading.setVisibility(View.INVISIBLE);

            etName.setText("");
            etPhone.setText("");

            Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show();

        });
    }

    private void editUser(DataContact dataContact, String id) {
        database.child("Contact")
                .child(id)
                .setValue(dataContact)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.setVisibility(View.INVISIBLE);

                        etName.setText("");
                        etPhone.setText("");

                        Toast.makeText(AddActivity.this,
                                "Contact edited successfully",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Delete this contact?");

        // set pesan dari dialog
        alertDialogBuilder
//                .setMessage("Klik Ya untuk keluar!")
//                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        database.child("Contact").child(sPid).removeValue().addOnSuccessListener((OnSuccessListener) (aVoid) ->{

                            Toast.makeText(AddActivity.this, "Contact has been deleted ", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}