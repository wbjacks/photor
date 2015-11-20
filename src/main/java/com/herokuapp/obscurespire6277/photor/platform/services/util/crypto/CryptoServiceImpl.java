package com.herokuapp.obscurespire6277.photor.platform.services.util.crypto;

import jodd.petite.meta.PetiteBean;

@PetiteBean("cryptoService")
public class CryptoServiceImpl implements CryptoService {
    public CryptoServiceImpl() {
    }

    @Override
    public String getEnvironmentVariableValue(String variableName) {
        return System.getenv(variableName);
    }
}
