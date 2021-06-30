package stmik_amik.bandung.si20406uas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import stmik_amik.bandung.si20406uas.adapter.ContactAdapter;
import stmik_amik.bandung.si20406uas.model.DataContact;

public class ContactActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<DataContact> dataContactArrayList;
    private ContactAdapter contactAdapter;

    private RecyclerView rv_list;
    private ProgressBar loading;

//    private ImageButton btn_edit, btn_delete;

    private FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        database = FirebaseDatabase.getInstance("https://si20406-uas-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        rv_list = findViewById(R.id.list_contact);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());

        loading = findViewById(R.id.simpleProgressBar);
        fab_add = findViewById(R.id.fab_add);
//        btn_edit = findViewById(R.id.btn_edit);
//        btn_delete = findViewById(R.id.btn_delete);

        loading.setVisibility(View.VISIBLE);

        database.child("Contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dataContactArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Wisata
                     * Dan juga menyimpan primary key pada object Wisata
                     * untuk keperluan Edit dan Delete data
                     */
                    DataContact dataContact = dataSnapshot.getValue(DataContact.class);
                    dataContact.setKey(dataSnapshot.getKey());

                    /**
                     * Menambahkan object Wisata yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    dataContactArrayList.add(dataContact);
                }

                /**
                 * Inisialisasi adapter dan data hotel dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                contactAdapter = new ContactAdapter(dataContactArrayList, ContactActivity.this);
                rv_list.setAdapter(contactAdapter);

                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(error.getDetails()+" "+error.getMessage());
                loading.setVisibility(View.INVISIBLE);
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactActivity.this, AddActivity.class)
                        .putExtra("id", "")
                        .putExtra("name", "")
                        .putExtra("phone", ""));
            }
        });

    }
}