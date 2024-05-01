/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.school.repositories;

import com.example.school.entities.School;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author neto
 */
@Repository
@Api
public interface SchoolRepository extends JpaRepository<School, Integer>

{

  
    public School save(School school);

    public void deleteById(Integer schoolPk);
    
    public School deleteByEmail(String email);

    public List<School> deleteByProvincia(String provincia);
    
    public School findBySchoolPk(Integer schoolPk);
    
    public List<School> findAll();

    public School findByEmail(String email);

    public List<School> findByProvincia(String provincia);

    public List<School> findByNome(String nome);

    public boolean existsBySchoolPk(Integer id);

    @Query(value="select * from school where school.\"nome\" = :name and school.\"provincia\" = :province", nativeQuery = true)
    public List<School> findSchoolsByNameAndProvince(@Param("name") String name, @Param("province")String province);
}
