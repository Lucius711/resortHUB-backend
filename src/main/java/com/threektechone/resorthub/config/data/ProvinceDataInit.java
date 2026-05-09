package com.threektechone.resorthub.config.data;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threektechone.resorthub.models.Province;
import com.threektechone.resorthub.repositories.ProvinceRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class ProvinceDataInit implements CommandLineRunner {

    private final ProvinceRepository provinceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String CAS_API_BASE = "https://production.cas.so/address-kit/2025-07-01";

    @Override
    public void run(String... args) {

        if (provinceRepository.count() > 0)
            return;

        RestTemplate restTemplate = new RestTemplate();

        String url = CAS_API_BASE + "/provinces";

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Map<String, Object> body = response.getBody();

        if (body == null)
            return;

        List<Map<String, Object>> provinces = objectMapper.convertValue(
                body.get("provinces"),
                new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {
                });

        if (provinces == null)
            return;

        for (Map<String, Object> p : provinces) {

            String name = (String) p.get("name");
            String nameEn = (String) p.get("englishName");
            String code = (String) p.get("code");

            if (provinceRepository.existsByName(name))
                continue;

            Province province = new Province();
            province.setCode(code);
            province.setName(name);
            province.setNameEn(nameEn);

            provinceRepository.save(province);
        }

        System.out.println("✅ Provinces seeded from CAS API");
    }
}