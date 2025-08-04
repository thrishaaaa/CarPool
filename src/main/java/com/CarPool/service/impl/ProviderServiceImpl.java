package com.CarPool.service.impl;

import com.CarPool.model.Provider;
import com.CarPool.repository.ProviderRepository;
import com.CarPool.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public void saveProvider(Provider provider) {
        providerRepository.save(provider);
    }

    @Override
    public Provider findByEmail(String email) {
        return providerRepository.findByEmail(email);
    }

    @Override
    public List<Provider> getProvidersByCollege(Long collegeId) {
        return providerRepository.findByCollegeId(collegeId);
    }

    @Override
    public List<Provider> searchProviders(String area, String carModel, Long collegeId) {
        // Provide default empty strings to avoid null pointer issues
        if (area == null) area = "";
        if (carModel == null) carModel = "";
        return providerRepository.findByCollegeIdAndAreaContainingIgnoreCaseAndCarModelContainingIgnoreCase(
                collegeId, area, carModel
        );
    }


}
