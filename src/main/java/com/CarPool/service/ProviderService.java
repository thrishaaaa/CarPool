package com.CarPool.service;

import com.CarPool.model.Provider;

public interface ProviderService {
    void saveProvider(Provider provider);

    Provider findByEmail(String email);
}
