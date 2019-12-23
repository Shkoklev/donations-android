package com.example.donations.persistence.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.donations.model.Organization;
import com.example.donations.model.OrganizationCategory;
import com.example.donations.persistence.DonationsDatabase;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class OrganizationRepository {

    private static final String DATABASE_NAME = "donations";

    private static DonationsDatabase donationsDatabase = null;

    private Context context;

    public OrganizationRepository(Context context) {
        if(donationsDatabase == null) {
            donationsDatabase = Room.databaseBuilder(context, DonationsDatabase.class, DATABASE_NAME).build();
        }
    }

    public void insertOrganization(final Organization organization) {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                Log.d(TAG, "organizacija: " + organization.toString());
                donationsDatabase.organizationDao().insertOrganization(organization);
                return null;
            }

        }.execute();
    }

    public void insertOrganizationCategory(final OrganizationCategory category) {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                Log.d(TAG, "kategorija: " + category.toString());
                donationsDatabase.organizationDao().insertOrganizationCategory(category);
                return null;
            }

        }.execute();
    }

    public LiveData<List<Organization>> listAllOrganizations() {
        return donationsDatabase.organizationDao().getAllOrganizations();
    }

    public LiveData<List<OrganizationCategory>> listAllOrganizationCategories() {
        return donationsDatabase.organizationDao().getAllOrganizationCategories();
    }

    public void deleteAllOrganizations() {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                donationsDatabase.organizationDao().deleteAllOrganizations();
                return null;
            }
        }.execute();
    }

    public void deleteAllOrganizationCategories() {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                donationsDatabase.organizationDao().deleteAllOrganizationCategories();
                return null;
            }
        }.execute();
    }

}
