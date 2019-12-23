package com.example.donations.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.donations.R;
import com.example.donations.model.Organization;


public class OrganizationViewHolder extends RecyclerView.ViewHolder {

    private View parent;
    private Context context;

    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView emailTextView;

    public OrganizationViewHolder(View view, Context context) {
        super(view);
        this.context = context;
        this.nameTextView = (TextView) view.findViewById(R.id.organization_name_text_view);
        this.phoneTextView = (TextView) view.findViewById(R.id.organization_phone_text_view);
        this.emailTextView = (TextView) view.findViewById(R.id.organization_email_text_view);
    }

    public void bind(final Organization entity) {
        getNameTextView().setText(entity.getName());
        getPhoneTextView().setText(context.getString(R.string.phone, entity.getPhone()));
        getEmailTextView().setText(context.getString(R.string.email, entity.getEmail()));
    }

    public View getParent() {
        return parent;
    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public TextView getPhoneTextView() {
        return phoneTextView;
    }

    public void setPhoneTextView(TextView phoneTextView) {
        this.phoneTextView = phoneTextView;
    }

    public TextView getEmailTextView() {
        return emailTextView;
    }

    public void setEmailTextView(TextView emailTextView) {
        this.emailTextView = emailTextView;
    }
}
