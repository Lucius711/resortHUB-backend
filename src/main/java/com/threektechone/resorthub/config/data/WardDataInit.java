package com.threektechone.resorthub.config.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threektechone.resorthub.models.Province;
import com.threektechone.resorthub.models.Ward;
import com.threektechone.resorthub.repositories.ProvinceRepository;
import com.threektechone.resorthub.repositories.WardRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(2)
public class WardDataInit implements CommandLineRunner {

    private final WardRepository wardRepository;
    private final ProvinceRepository provinceRepository;
    private final ObjectMapper objectMapper;

    private static final String CAS_API_BASE = "https://production.cas.so/address-kit/2025-07-01";

    @Override
    public void run(String... args) {

        if (wardRepository.count() > 0)
            return;

        RestTemplate restTemplate = new RestTemplate();

        List<Province> provinces = provinceRepository.findAll();

        for (Province province : provinces) {

            try {

                String provinceCode = province.getCode();
                if (provinceCode == null)
                    continue;

                String url = CAS_API_BASE
                        + "/provinces/"
                        + provinceCode
                        + "/communes";

                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                JsonNode root = objectMapper.readTree(response.getBody());

                JsonNode data = root.has("communes")
                        ? root.get("communes")
                        : root.has("data")
                                ? root.get("data")
                                : root;

                if (!data.isArray())
                    continue;

                List<Ward> wardsToSave = new ArrayList<>();

                for (JsonNode w : data) {

                    String name = w.has("name")
                            ? w.get("name").asText()
                            : null;

                    String nameEn = w.has("englishName")
                            ? w.get("englishName").asText()
                            : w.has("nameEn")
                                    ? w.get("nameEn").asText()
                                    : null;

                    if (name == null)
                        continue;

                    Ward ward = new Ward();
                    ward.setName(name);
                    ward.setNameEn(nameEn);
                    ward.setProvince(province);

                    wardsToSave.add(ward);
                }

                wardRepository.saveAll(wardsToSave);

            } catch (Exception e) {
                System.out.println("⚠ Failed province: " + province.getName());
            }
        }

        System.out.println("✅ Wards seeded from CAS API");
    }
}