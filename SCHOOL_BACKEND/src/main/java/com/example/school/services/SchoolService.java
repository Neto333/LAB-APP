/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.example.school.services;

import com.example.school.entities.School;
import com.example.school.repositories.SchoolRepository;
import com.example.school.utils.ConvertJSON;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author neto
 */
@Service
public class SchoolService
{

    @Autowired
    SchoolRepository schoolRepository;

    ConvertJSON JSON;

    public SchoolService() throws IOException
    {
        this.JSON = new ConvertJSON();
    }
      
    
    public ResponseEntity<?> createSchool(School school)
    {
        List<String> pro = JSON.getProvincias();
        List<School> schools = findSchoolsByNameAndProvince(school.getNome(), school.getProvincia());

        if (findSchoolByEmail(school.getEmail()) != null)
        {
             return new ResponseEntity<>("Esse email já existe " + school.getEmail(), HttpStatus.BAD_REQUEST);
        }
        else if (!pro.contains(school.getProvincia()))
        {
            return new ResponseEntity<>("Província inválida" + school.getProvincia(), HttpStatus.BAD_REQUEST );
        }
        else if (!schools.isEmpty())
        {
            return new ResponseEntity<>("Essa escola já existe nessa Província", HttpStatus.BAD_REQUEST);
        }
        schoolRepository.save(school);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteSchoolById(Integer id)
    {
        if (!schoolRepository.existsById(id))
        {
            return new ResponseEntity<>("Esse id não existe" + id, HttpStatus.NOT_FOUND);
        }
        schoolRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public School deleteSchoolByEmail(String email)
    {
        if (findSchoolByEmail(email) == null)
        {
            throw new IllegalStateException("Esse email não existe");
        }
        return schoolRepository.deleteByEmail(email);
    }

    @Transactional
    public List<School> deleteSchoolsByProvince(String province)
    {
        if (findSchoolsByProvince(province).isEmpty())
        {
            throw new IllegalStateException("Essa província não existe");
        }
        return schoolRepository.deleteByProvincia(province);
    }

    public ResponseEntity<?> updateSchoolByEmail(String email, School school)
    {
        School oldSchool = new School();
        oldSchool = findSchoolByEmail(email);
        List<School> schools = findSchoolsByNameAndProvince(school.getNome(), school.getProvincia());

        if (oldSchool == null)
        {
            return new ResponseEntity<>("Esse email não existe"+ email, HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(oldSchool.getEmail(), school.getEmail()))
        {
            return new ResponseEntity<>("Os e-mail's não correspondem", HttpStatus.BAD_REQUEST);
        }
        if (!schools.isEmpty())
        {
            return new ResponseEntity<>("Essa escola já existe nessa Província", HttpStatus.BAD_REQUEST);
        }

        oldSchool.setNome(school.getNome());
        oldSchool.setProvincia(school.getProvincia());
        oldSchool.setNSalas(school.getNSalas());
        schoolRepository.save(oldSchool);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> updateSchoolById(Integer id, School school)
    {
        School oldSchool = new School();
        oldSchool = findSchoolById(id);
        School VerifingEmail = findSchoolByEmail(school.getEmail());
        List<School> schools = findSchoolsByNameAndProvince(school.getNome(), school.getProvincia());

        if (oldSchool == null)
        {

            return new ResponseEntity<>("Esse id não existe"+ id, HttpStatus.NOT_FOUND);
        }
        if (VerifingEmail != null && !Objects.equals(oldSchool.getSchoolPk(), VerifingEmail.getSchoolPk()))
        {
            return new ResponseEntity<>("Esse email não existe"+ school.getEmail(), HttpStatus.BAD_REQUEST);
        }
        if (!schools.isEmpty())
        {
            return new ResponseEntity<>("Essa escola já existe nessa Província", HttpStatus.BAD_REQUEST);
        }

        oldSchool.setEmail(school.getEmail());
        oldSchool.setNome(school.getNome());
        oldSchool.setProvincia(school.getProvincia());
        oldSchool.setNSalas(school.getNSalas());
        schoolRepository.save(oldSchool);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<School> findAll()
    {
        return schoolRepository.findAll();
    }

    public School findSchoolById(Integer id)
    {
        return schoolRepository.findBySchoolPk(id);
    }

    public School findSchoolByEmail(String email)
    {
        return schoolRepository.findByEmail(email);
    }

    public List<School> findSchoolsByProvince(String province)
    {
        return schoolRepository.findByProvincia(province) != null ? schoolRepository.findByProvincia(province) : Collections.emptyList();
    }

    public List<School> findSchoolsByName(String name)
    {
        return schoolRepository.findByNome(name) != null ? schoolRepository.findByNome(name) : Collections.emptyList();
    }

    public List<School> findSchoolsByNameAndProvince(String name, String province)
    {
        return schoolRepository.findSchoolsByNameAndProvince(name, province) != null ? schoolRepository.findSchoolsByNameAndProvince(name, province) : Collections.emptyList();
    }
}
