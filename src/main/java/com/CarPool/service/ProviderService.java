package com.CarPool.service;

import com.CarPool.model.Provider;

import java.util.List;

public interface ProviderService {
    void saveProvider(Provider provider);
    Provider findByEmail(String email);
    List<Provider> getProvidersByCollege(Long collegeId);
    List<Provider> searchProviders(String area, String carModel, Long collegeId);


}
