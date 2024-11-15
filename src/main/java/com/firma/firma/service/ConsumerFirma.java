    package com.firma.firma.service;

    import com.firma.firma.entity.Firma;
    import com.firma.firma.repository.FirmaRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.stereotype.Component;

    import java.util.Optional;

    @Component
    public class ConsumerFirma {


        FirmaRepository firmaRepository;

        @Autowired
        public ConsumerFirma(FirmaRepository firmaRepository) {
            this.firmaRepository = firmaRepository;
        }

     public Optional<Firma> getFirmaById(Long id) {
            return firmaRepository.findById(id);
     }
    }
