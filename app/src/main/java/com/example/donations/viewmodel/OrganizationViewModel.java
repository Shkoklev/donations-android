package com.example.donations.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.donations.model.Organization;
import com.example.donations.persistence.repository.OrganizationRepository;

import java.util.List;

public class OrganizationViewModel extends AndroidViewModel {

    private LiveData<List<Organization>> organizations;

    private OrganizationRepository organizationRepository = null;

    public OrganizationViewModel(@NonNull Application application) {
        super(application);
        organizationRepository = new OrganizationRepository(OrganizationViewModel.this.getApplication());
        organizations = organizationRepository.listAllOrganizations();
    }

    public LiveData<List<Organization>> getOrganizations() {
        return organizations;
    }

    public void updateData(List<Organization> organizations) {
        if(organizations == null) {
            return;
        }

        organizationRepository.deleteAllOrganizations();
        for(Organization organization : organizations) {
            organizationRepository.insertOrganization(organization);
        }
    }
}
