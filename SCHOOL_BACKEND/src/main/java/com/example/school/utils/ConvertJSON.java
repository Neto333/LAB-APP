/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.school.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author neto
 */
public class ConvertJSON
{
    
 private final List<String> provincias;

    public ConvertJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("province.json");
        InputStream inputStream = resource.getInputStream();
        
        this.provincias = extractProvincias(objectMapper.readTree(inputStream));
    }

    private List<String> extractProvincias(JsonNode jsonNode) {
        List<String> provinciasList = new ArrayList<>();
        JsonNode provinciasNode = jsonNode.path("Angola").path("Provincias");
        Iterator<JsonNode> iterator = provinciasNode.elements();
        while (iterator.hasNext()) {
            JsonNode provinciaNode = iterator.next();
            provinciasList.add(provinciaNode.get("nome").asText());
        }
        return provinciasList;
    }

    public List<String> getProvincias() {
        return provincias;
    }
}