package com.CarPool.service.impl;

import com.CarPool.model.Provider;
import com.CarPool.repository.ProviderRepository;
import com.CarPool.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
