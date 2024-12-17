package com.aws.restjdbc.web;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {

//    @InjectMocks
//    private PersonController personController;
//
//    @Mock
//    private PersonService personService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    private PersonDto createMockPerson(int id, String nombre, String apellido, int edad) {
//        return new PersonDto(id, nombre, apellido, edad);
//    }
//
//    private List<PersonDto> createMockPersonList() {
//        return List.of(
//                createMockPerson(1, "John", "Doe", 25),
//                createMockPerson(2, "Jane", "Doe", 30)
//        );
//    }
//
//    private <T> void assertResponse(ResponseEntity<T> response, HttpStatus expectedStatus, T expectedBody) {
//        assertEquals(expectedStatus, response.getStatusCode());
//        assertEquals(expectedBody, response.getBody());
//    }
//
//    @DisplayName("test list person")
//    @Test
//    void testListPerson() throws SQLException {
//        // Mock data
//        List<PersonDto> mockPersons = createMockPersonList();
//        when(personService.listPerson()).thenReturn(ResponseEntity.ok(mockPersons));
//
//        // Call the method
//        ResponseEntity<List<PersonDto>> response = personController.listPerson();
//
//        // Verify results
//        assertResponse(response, HttpStatus.OK, mockPersons);
//        verify(personService, times(1)).listPerson();
//    }
//
//    @DisplayName("test person by id")
//    @Test
//    void testPersonById() throws SQLException {
//        // Mock data
//        PersonDto mockPerson = new PersonDto(1, "John", "Doe", 25);
//        when(personService.personById(1)).thenReturn(ResponseEntity.ok(mockPerson));
//
//        // Call the method
//        ResponseEntity<PersonDto> response = personController.personById(1);
//
//        // Verify results
//        assertResponse(response, HttpStatus.OK, mockPerson);
//        verify(personService, times(1)).personById(1);
//    }
//
//    @DisplayName("test create person")
//    @Test
//    void testCreatePerson() throws SQLException {
//        // Mock data
//        PersonDto mockPerson = new PersonDto(1, "John", "Doe", 25);
//        when(personService.createPerson(mockPerson)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(mockPerson));
//
//        // Call the method
//        ResponseEntity<PersonDto> response = personController.createPerson(mockPerson);
//
//        // Verify results
//        assertResponse(response, HttpStatus.CREATED, mockPerson);
//        verify(personService, times(1)).createPerson(mockPerson);
//    }
//
//    @DisplayName("test update person")
//    @Test
//    void testUpdatePerson() throws SQLException {
//        // Mock data
//        PersonDto updatedPerson = new PersonDto(1, "John", "Smith", 26);
//        when(personService.updatePerson(eq(1), any(PersonDto.class))).thenReturn(ResponseEntity.ok(updatedPerson));
//
//        // Call the method
//        ResponseEntity<PersonDto> response = personController.updatePerson(1, updatedPerson);
//
//        // Verify results
//        assertResponse(response, HttpStatus.OK, updatedPerson);
//        verify(personService, times(1)).updatePerson(1, updatedPerson);
//    }
//
//    @DisplayName("test delete person")
//    @Test
//    void deletePersonById() throws SQLException {
//        // Mock response
//        String mockMessage = "Deleted successfully";
//        when(personService.deletePersonById(1)).thenReturn(ResponseEntity.ok(mockMessage));
//
//        // Call the method
//        ResponseEntity<String> response = personController.deletePersonById(1);
//
//        // Verify results
//        assertResponse(response, HttpStatus.OK, mockMessage);
//        verify(personService, times(1)).deletePersonById(1);
//    }
}