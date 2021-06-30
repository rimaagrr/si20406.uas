package stmik_amik.bandung.si20406uas.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import stmik_amik.bandung.si20406uas.AddActivity;
import stmik_amik.bandung.si20406uas.R;
import stmik_amik.bandung.si20406uas.model.DataContact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<DataContact> listContact;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout linearLayout;
        public ImageButton btn_edit, btn_delete;
        public TextView contact_name, contact_phone;

        public MyViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.item_contact);
            btn_edit = view.findViewById(R.id.btn_edit);
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

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, AddActivity.class);
                goDetail.putExtra("id", dataContact.getKey());
                goDetail.putExtra("name", dataContact.getName());
                goDetail.putExtra("phone", dataContact.getPhone());

                mActivity.startActivity(goDetail);

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, AddActivity.class);
                goDetail.putExtra("id", dataContact.getKey());
                goDetail.putExtra("name", dataContact.getName());
                goDetail.putExtra("phone", dataContact.getPhone());

                mActivity.startActivity(goDetail);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listContact.size();
    }

}
