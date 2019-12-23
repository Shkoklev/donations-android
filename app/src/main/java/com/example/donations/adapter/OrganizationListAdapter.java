package com.example.donations.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donations.R;
import com.example.donations.adapter.viewholder.OrganizationViewHolder;
import com.example.donations.model.Organization;

import java.util.List;

public class OrganizationListAdapter extends RecyclerView.Adapter<OrganizationViewHolder> {

    private List<Organization> organizations;
    private Context context;

    public OrganizationListAdapter(Context context, List<Organization> data) {
        this.organizations = data;
        this.context = context;
    }

    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.organization_view_item,viewGroup,false);
        OrganizationViewHolder holder = new OrganizationViewHolder(view, context);
        holder.setParent(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder organizationViewHolder, int i) {
        Organization entity = organizations.get(i);
        organizationViewHolder.bind(entity);
    }

    @Override
    public int getItemCount() {
        if(organizations != null) {
            return organizations.size();
        }
        return 0;
    }

    public void updateData(List<Organization> organizations) {
        this.organizations = organizations;
        notifyDataSetChanged();
    }
}
