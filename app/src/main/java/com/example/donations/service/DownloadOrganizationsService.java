package com.example.donations.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.donations.client.DonationsApiClient;
import com.example.donations.model.Organization;
import com.example.donations.model.OrganizationCategory;
import com.example.donations.model.response.OrganizationsResponse;
import com.example.donations.persistence.repository.OrganizationRepository;
import com.example.donations.repository.OrganizationApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class DownloadOrganizationsService extends IntentService {

    private OrganizationApiInterface organizationApiInterface;

    private OrganizationRepository organizationRepository;

    public DownloadOrganizationsService() {
        super("Download and save Organizations");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = DonationsApiClient.getRetrofit();
        organizationApiInterface = retrofit.create(OrganizationApiInterface.class);

        organizationRepository = new OrganizationRepository(DownloadOrganizationsService.this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        loadOrganizationCategories(intent);
        loadOrganizations();
    }

    private void loadOrganizationCategories(final Intent intent) {
        organizationApiInterface.getOrganizationCategories()
                .enqueue(new Callback<List<OrganizationCategory>>() {
                    @Override
                    public void onResponse(Call<List<OrganizationCategory>> call, Response<List<OrganizationCategory>> response) {
                        if(response.isSuccessful()) {
                            saveOrganizationCategoriesInDb(response.body());
                            int organizationCategoriesNumber = response.body().size();
                            sendBroadcast(intent, organizationCategoriesNumber);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OrganizationCategory>> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
    }

    private void loadOrganizations() {
        organizationApiInterface.getOrganizations()
                .enqueue(new Callback<OrganizationsResponse>() {
                    @Override
                    public void onResponse(Call<OrganizationsResponse> call, Response<OrganizationsResponse> response) {
                        if(response.isSuccessful()) {
                            saveOrganizationsInDb(response.body().getContent());
                        }
                    }

                    @Override
                    public void onFailure(Call<OrganizationsResponse> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
    }

    private void saveOrganizationsInDb(List<Organization> organizations) {
        organizationRepository.deleteAllOrganizations();
        for(Organization organization : organizations) {
            organizationRepository.insertOrganization(organization);
        }
    }

    private void saveOrganizationCategoriesInDb(List<OrganizationCategory> categories) {
        organizationRepository.deleteAllOrganizationCategories();
        for(OrganizationCategory category : categories) {
            organizationRepository.insertOrganizationCategory(category);
        }
    }

    private void sendBroadcast(Intent serviceIntent, int organizationCategoriesNumber) {
        serviceIntent.putExtra("organizationCategoriesNumber", organizationCategoriesNumber);
        LocalBroadcastManager.getInstance(this).sendBroadcast(serviceIntent);
    }
}
