package com.aws.restjdbc.service.impl;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.exception.types.DataNotContentException;
import com.aws.restjdbc.exception.types.IllegalArgumentException;
import com.aws.restjdbc.util.MysqlConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {
//
//    @InjectMocks
//    private PersonServiceImpl personService;
//
//    @Mock
//    private MysqlConnection mysqlConnection;
//
//    @Mock
//    private Connection mockConnection;
//
//    @Mock
//    private PreparedStatement mockPreparedStatement;
//
//    @Mock
//    private Statement mockStatement;
//
//    @Mock
//    private ResultSet mockResultSet;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @DisplayName("test list person success")
//    @Test
//    void testListPerson_Success() throws SQLException {
//        // Mock ResultSet data
//        when(mysqlConnection.getConnection()).thenReturn(mockConnection);
//        when(mockConnection.createStatement()).thenReturn(mockStatement);
//        when(mockStatement.executeQuery("select id, name, lastname, age from person")).thenReturn(mockResultSet);
//
//        // Mock ResultSet behavior
//        when(mockResultSet.next()).thenReturn(true, true, false); // 2 rows, then end
//        when(mockResultSet.getInt("id")).thenReturn(1, 2);
//        when(mockResultSet.getString("name")).thenReturn("John", "Jane");
//        when(mockResultSet.getString("lastname")).thenReturn("Doe", "Smith");
//        when(mockResultSet.getInt("age")).thenReturn(25, 30);
//
//        // Call the service
//        ResponseEntity<List<PersonDto>> response = personService.listPerson();
//
//        // Verify results
//        assertEquals(2, response.getBody().size());
//        assertEquals("John", response.getBody().get(0).getNombre());
//        assertEquals("Jane", response.getBody().get(1).getNombre());
//
//        verify(mysqlConnection, times(1)).getConnection();
//        verify(mockStatement, times(1)).executeQuery(anyString());
//    }
//
//    @DisplayName("test list person empty resultSet")
//    @Test
//    void testListPerson_EmptyResultSet() throws SQLException {
//        // Mock empty ResultSet
//        when(mysqlConnection.getConnection()).thenReturn(mockConnection);
//        when(mockConnection.createStatement()).thenReturn(mockStatement);
//        when(mockStatement.executeQuery("select id, name, lastname, age from person")).thenReturn(mockResultSet);
//
//        when(mockResultSet.next()).thenReturn(false); // No rows
//
//        // Call the service and expect an exception
//        assertThrows(DataNotContentException.class, () -> personService.listPerson());
//
//        verify(mysqlConnection, times(1)).getConnection();
//        verify(mockStatement, times(1)).executeQuery(anyString());
//    }
//
//    @DisplayName("test list person SQLException")
//    @Test
//    void testListPerson_SQLException() throws SQLException {
//        // Mock an SQLException
//        when(mysqlConnection.getConnection()).thenReturn(mockConnection);
//        when(mockConnection.createStatement()).thenThrow(new SQLException("Database error"));
//
//        // Call the service and expect an exception
//        assertThrows(SQLException.class, () -> personService.listPerson());
//
//        verify(mysqlConnection, times(1)).getConnection();
//    }
//
//    @Test
//    void testPerson_InvalidIdEqual0() throws SQLException {
//        // Case ID invalid (<= 0)
//        int invalidId = 0;
//
//        ResponseEntity<PersonDto> response = personService.personById(invalidId);
//
//        // Verify ResponseEntity code 400 (Bad Request)
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//
//    @Test
//    void testPerson_InvalidIdLess0() throws SQLException {
//        // Case ID invalid
//        int invalidId = -10;
//
//        ResponseEntity<PersonDto> response = personService.personById(invalidId);
//
//        // Verify ResponseEntity code 400 (Bad Request)
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//
//    @Test
//    void testPersonById_Success() throws SQLException {
//        // Mock behavior for database connection and query
//        int personId = 1;
//        when(mysqlConnection.getConnection()).thenReturn(mockConnection);
//        when(mockConnection.prepareStatement("select * from person where id = ?")).thenReturn(mockPreparedStatement);
//
//        // Mock PreparedStatement behavior
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//
//        // Mock ResultSet behavior for one result
//        when(mockResultSet.next()).thenReturn(true);
//        when(mockResultSet.getInt("id")).thenReturn(personId);
//        when(mockResultSet.getString("name")).thenReturn("John");
//        when(mockResultSet.getString("lastname")).thenReturn("Doe");
//        when(mockResultSet.getInt("age")).thenReturn(30);
//
//        // Call the service method
//        ResponseEntity<PersonDto> response = personService.personById(personId);
//
//        // Verify the results
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("John", response.getBody().getNombre());
//        assertEquals("Doe", response.getBody().getApellido());
//        assertEquals(30, response.getBody().getEdad());
//
//        // Verify interactions
//        verify(mockPreparedStatement).setInt(1, personId);
//        verify(mockPreparedStatement, times(1)).executeQuery();
//    }
//
//    @Test
//    void testPersonById_NotFound() throws Exception {
//        // Caso en que el ID es válido pero la persona no es encontrada
//        int validId = 1;
//
//        // Simulamos la conexión y la ejecución de la consulta
//        when(mysqlConnection.getConnection()).thenReturn(mockConnection);
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(false); // No hay resultados en la consulta
//
//        ResponseEntity<PersonDto> response = personService.personById(validId);
//
//        // Verificar que se devuelve un ResponseEntity con el código 404 (Not Found)
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    void testPersonById_ErrorResultSet() throws Exception {
//        // Caso en que ocurre una SQLException
//        int validId = 1;
//
//        // Simulamos la conexión y la ejecución de la consulta
//        when(mysqlConnection.getConnection()).thenReturn(mockConnection);
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenThrow(new IllegalArgumentException("Error SQL"));
//        when(mockResultSet.next()).thenReturn(false);
//
//        assertThrows(IllegalArgumentException.class, () -> personService.personById(validId));
//    }
//
//    @Test
//    void testPersonById_SQLException() throws SQLException {
//        when(mysqlConnection.getConnection()).thenReturn(mockConnection);
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));
//
//        // Llamar al metodo
//        ResponseEntity<PersonDto> response = personService.personById(1);
//
//        // Verificar que se devuelve un error 500
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }
//
//    @Test
//    void createPerson() {
//    }
//
//    @Test
//    void updatePerson() {
//    }
//
//    @Test
//    void deletePersonById() {
//    }
//
//    @Test
//    void deletePersonByName() {
//    }
//
//    @Test
//    void deletePersonByLastName() {
//    }
//
//    @Test
//    void existPersonById() {
//    }
}