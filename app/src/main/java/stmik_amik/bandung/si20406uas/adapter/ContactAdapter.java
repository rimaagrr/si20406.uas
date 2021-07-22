package stmik_amik.bandung.si20406uas.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import stmik_amik.bandung.si20406uas.AddActivity;
import stmik_amik.bandung.si20406uas.ContactActivity;
import stmik_amik.bandung.si20406uas.R;
import stmik_amik.bandung.si20406uas.model.DataContact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<DataContact> listContact;
    private Activity mActivity;
    private DatabaseReference database = FirebaseDatabase.getInstance("https://si20406-uas-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    private Intent goDetail;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout linearLayout;
        public ImageButton btn_delete;
        public TextView contact_name, contact_phone;

        public MyViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.item_contact);
//            btn_edit = view.findViewById(R.id.btn_edit);
            btn_delete = view.findViewById(R.id.btn_delete);
            contact_name = view.findViewById(R.id.contact_name);
            contact_phone = view.findViewById(R.id.contact_phone);
        }
    }

    public ContactAdapter(List<DataContact> listContact, Activity activity) {
        this.listContact = listContact;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DataContact dataContact = listContact.get(position);

        holder.contact_name.setText(dataContact.getName());
        holder.contact_phone.setText(dataContact.getPhone());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goDetail = new Intent(mActivity, AddActivity.class);
                goDetail.putExtra("id", dataContact.getKey());
                goDetail.putExtra("name", dataContact.getName());
                goDetail.putExtra("phone", dataContact.getPhone());

                mActivity.startActivity(goDetail);

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mActivity);

                // set title dialog
                alertDialogBuilder.setTitle("Delete this contact?");

                // set pesan dari dialog
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                database.child("Contact").child(String.valueOf(listContact.get(position).getKey())).removeValue().addOnSuccessListener((OnSuccessListener) (aVoid) ->{

                                    Toast.makeText(mActivity, "Contact has been deleted ", Toast.LENGTH_SHORT).show();

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
        });

    }

    @Override
    public int getItemCount() {
        return listContact.size();
    }

}
