package org.techtown.transmanageradmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileDataAdapter extends RecyclerView.Adapter<ProfileDataAdapter.ViewHolder> {

    private Context context;
    public ProfileDataAdapter(Context context) {this.context = context;}

    @NonNull
    @Override
    public ProfileDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_profile_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileDataAdapter.ViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(ProfileData item) {
            TextView username = itemView.findViewById(R.id.text_name);
            TextView phonenumber = itemView.findViewById(R.id.text_phonenumber);
            TextView vihiclenumber = itemView.findViewById(R.id.text_vihiclenumber);

            username.setText(item.getUsername());
            phonenumber.setText(item.getPhonenumber());
            vihiclenumber.setText(item.getVihiclenumber());
        }
    }

    private ArrayList<ProfileData> dataSet = new ArrayList<>();
    public void submitData(ArrayList<ProfileData> newData) {
        dataSet = newData;
        notifyDataSetChanged();
    }
}
