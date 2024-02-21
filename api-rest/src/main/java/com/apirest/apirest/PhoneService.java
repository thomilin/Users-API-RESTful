package com.apirest.apirest;

import com.apirest.apirest.model.Phone;
import com.apirest.apirest.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public Phone createPhone(Phone phone){
        return phoneRepository.save(phone);
    }

    public Phone updatePhone(Long id, Phone updatedPhone) {
        Phone phone = phoneRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Teléfono no encontrado"));

        phone.setNumber(updatedPhone.getNumber());
        phone.setCityCode(updatedPhone.getCityCode());
        phone.setCountryCode(updatedPhone.getCountryCode());

        return phoneRepository.save(phone);
    }

    public void deletePhone(Long id) {
        Phone phone = phoneRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Teléfono no encontrado"));

        phoneRepository.delete(phone);
    }
}
