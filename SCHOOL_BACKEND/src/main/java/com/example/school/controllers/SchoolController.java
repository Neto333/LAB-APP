/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.school.controllers;

import com.example.school.entities.School;
import com.example.school.services.SchoolService;
import com.example.school.utils.HSSFCellUtilities;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author neto
 */
@RestController
@Validated
@Api(tags = "School Controller", description = "Endpoints para mapear as escolas")
@RequestMapping("/school")
public class SchoolController implements ErrorController
{

    @Autowired
    private SchoolService schoolService;

    @PostMapping("/createSchool")
    @ApiOperation(value = "Criar uma nova escola", notes = "Criar uma nova escola com os dados fornecidos")
    //Verificar se foi criado ou não
    public void createSchool(@ApiParam(value = "Modelo School para criar a escola", required = true) @Valid @RequestBody School school)
    {
        schoolService.createSchool(school);
    }

    @DeleteMapping("/deleteSchoolById/{id}")
    @ApiOperation(value = "Deletar uma escola pelo seu id", notes = "Deletar uma escola com o id fornecido")
    public void deleteSchoolById(@ApiParam(value = "O Id fornecido", required = true) @PathVariable Integer id)
    {
        schoolService.deleteSchoolById(id);
    }

    @PostMapping("/deleteSchoolsByProvince/{province}")
    @ApiOperation(value = "Deletar uma escola pela província", notes = "Deletar uma escola com a província fornecida")
    public void deleteSchoolsByProvince(@ApiParam(value = "A província fornecido", required = true) @PathVariable String province)
    {
        schoolService.deleteSchoolsByProvince(province);
    }

    @PostMapping("/deleteSchoolByEmail/{email}")
    @ApiOperation(value = "Deletar uma escola pela e-mail", notes = "Deletar uma escola com a e-mail fornecida")
    public void deleteSchoolByEmail(@ApiParam(value = "O E-mail fornecido", required = true) @PathVariable String email)
    {
        schoolService.deleteSchoolByEmail(email);
    }

    @PostMapping("/updateSchoolById/{id}")
    @ApiOperation(value = "Actualizar escola pelo seu id", notes = "Actualizar escola pelo seu id")
    public void updateSchoolById(@ApiParam(value = "O ID fornecido", required = true) @Valid @RequestBody School school, @PathVariable Integer id)
    {

        schoolService.updateSchoolById(id, school);

    }

    @PostMapping("/updateSchoolByEmail/{email}")
    @ApiOperation(value = "Actualizar escola pelo seu email", notes = "Actualizar escola pelo seu email")
    public void updateSchoolByEmail(@ApiParam(value = "O E-mail fornecido", required = true) @Valid @RequestBody School school, @PathVariable String email)
    {
        schoolService.updateSchoolByEmail(email, school);

    }

    @GetMapping("/findAll")
    @ApiOperation(value = "Buscar todas escolas", notes = "Pesquisar todas escolas")
    public ResponseEntity<?> findAll()
    {
        List<School> school = schoolService.findAll();
        return new ResponseEntity<>(school, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @ApiOperation(value = "Buscar escolas pelo seu id", notes = "Pesquisar escolas pelo seu id")
    public ResponseEntity<?> findById(@ApiParam(value = "O ID fornecido", required = true) @PathVariable Integer id)
    {
        School school = schoolService.findSchoolById(id);
        if (school != null)
        {
            return new ResponseEntity<>(school, HttpStatus.OK);
        }
        return new ResponseEntity<>("ID não encontrado " + id, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/findByEmail/{email}")
    @ApiOperation(value = "Buscar escola pelo seu e-mail", notes = "Pesquisar escolas pelo seu e-mail")
    public ResponseEntity<?> findByEmail(@ApiParam(value = "O E-mail fornecido", required = true) @PathVariable String email)
    {
        School school = schoolService.findSchoolByEmail(email);
        if (school != null)
        {
            return new ResponseEntity<>(school, HttpStatus.OK);
        }
        return new ResponseEntity<>("E-mail não encontrado" + email, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/findByProvince/{province}")
    @ApiOperation(value = "Buscar escolas pela província", notes = "Pesquisar escolas pela província")
    public ResponseEntity<?> findByProvince(@ApiParam(value = "A província fornecida", required = true) @PathVariable String province)
    {
        List<School> schools = schoolService.findSchoolsByProvince(province);
        if (!schools.isEmpty())
        {
            return new ResponseEntity<>(schools, HttpStatus.OK);
        }
        return new ResponseEntity<>("Província não encontrada " + province, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findByName/{name}")
    @ApiOperation(value = "Buscar escolas pelo seu nome", notes = "Pesquisar escolas pelo seu nome")
    public ResponseEntity<?> findByName(
            @ApiParam(value = "Parâmetro para pesquisar escola", required = true) @PathVariable String name)
    {
        List<School> schools = schoolService.findSchoolsByName(name);
        if (!schools.isEmpty())
        {
            return new ResponseEntity<>(schools, HttpStatus.OK);
        }
        return new ResponseEntity<>("Nome não encontrado " + name, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findByNameAndProvince/{name}/{province}")
    @ApiOperation(value = "Buscar escolas pelo seu nome e província", notes = "Pesquisar escolas pelo seu nome e província")
    public ResponseEntity<?> findByNameAndProvince(
            @ApiParam(value = "Parâmetro para pesquisar escola", required = true) @PathVariable String name, @PathVariable String province)
    {
        List<School> schools = schoolService.findSchoolsByNameAndProvince(name, province);
        if (!schools.isEmpty())
        {
            return new ResponseEntity<>(schools, HttpStatus.OK);
        }
        return new ResponseEntity<>("Escola não encontrada " + name, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createSchoolFromUploadExcel")
    @ApiOperation(value = "Criar escola/s com dados do EXcel", notes = "Criar escolas com dados do excel")
    public ResponseEntity<?> createSchoolFromUploadExcel(MultipartFile file) throws FileNotFoundException, IOException
    {
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        School school;

        workbook = new HSSFWorkbook(file.getInputStream());
        sheet = workbook.getSheetAt(0);
        Iterator iterator = sheet.rowIterator();
        row = (HSSFRow) iterator.next();//pular a primeira linha, os títulos

        while (iterator.hasNext())
        {
            row = (HSSFRow) iterator.next();
            school = processRow(row);

            if (school == null)
            {
                return new ResponseEntity("O ficheiro tem célula(s) vazia(s)", HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<?> response = findByEmail(school.getEmail());
            if (response.getStatusCode() == HttpStatus.OK)
            {
                updateSchoolByEmail(school, school.getEmail());
            }
            else
            {
                createSchool(school);
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    private School processRow(HSSFRow row)
    {
        HSSFCell cells[];
        cells = new HSSFCell[4];

        for (int i = 0; i < 4; i++)
        {

            cells[i] = row.getCell((short) i);
            if (HSSFCellUtilities.isCellEmpty(cells[i]))
            {
                return null;
            }
        }
        School school = new School();
        school.setNome(HSSFCellUtilities.getStringCellValue(cells[0]));
        school.setEmail(HSSFCellUtilities.getStringCellValue(cells[1]));
        school.setNSalas(HSSFCellUtilities.getIntCellValue(cells[2]));
        school.setProvincia(HSSFCellUtilities.getStringCellValue(cells[3]));

        return school;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex)
    {
        return "O parâmetro deve ser um número inteiro.";
    }

    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormatException(NumberFormatException ex)
    {
        return "O parâmetro deve ser uma string válida.";
    }
    
     @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFoundError(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Página não encontrada");
    }

}
