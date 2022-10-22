package com.tsa.orm;

import com.tsa.orm.entity.Guest;
import com.tsa.orm.entity.User;
import com.tsa.orm.interfaces.QueryGenerator;
import com.tsa.orm.services.QueryGeneratorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryGeneratorImplTest {

    public static QueryGenerator queryGenerator;

    @BeforeAll
    public static void init() {
        queryGenerator = new QueryGeneratorImpl();
    }

    @DisplayName("findAll With All Columns(Annotations are EMPTY)")
    @Test
    void findAllWithAllColumns() {
        String query = "SELECT * FROM user;";
        assertEquals(query, queryGenerator.findAll(User.class));
    }

    @DisplayName("findAll With All Parameters positive(Annotations are EMPTY)")
    @Test
    void findAllWithAllParameters() {
        String query = "SELECT name, id FROM user;";
        assertEquals(query, queryGenerator.findAll(User.class, "name", "id"));
    }
    @DisplayName("findAll Throws When Class Does Not Have Entity Annotation")
    @Test
    void findAllThrowsExceptionWhenClassDoesNotHaveEntityAnnotation() {
        assertThrows(IllegalStateException.class, () -> queryGenerator.findAll(String.class, "name", "id"),
                "not an Entity");
    }
    @DisplayName("findAll Throws Exception When Class Equals Null")
    @Test
    void findAllThrowsExceptionWhenClassEqualsNull() {
        assertThrows(IllegalArgumentException.class, () -> queryGenerator.findAll(null, "name", "id"),
                "is mandatory");
    }
    @DisplayName("findAll With Provided Columns Annotations Have Values")
    @Test
    void findAllWithProvidedColumnsAnnotationsHaveValues() {
        String query = "SELECT guest_id, guest_name FROM guest_table;";
        assertEquals(query, queryGenerator.findAll(Guest.class, "guest_id", "guest_name"));
    }
    @DisplayName("findById Throws Exception When One Of Arguments Is Null")
    @Test
    void findByIdThrowsExceptionWhenOneOfArgumentsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> queryGenerator.findById(null, 10));
        assertThrows(IllegalArgumentException.class, () -> queryGenerator.findById(Guest.class, null));
    }
    @DisplayName("findById Throws When Class Does Not Have Entity Annotation")
    @Test
    void findByIdThrowsExceptionWhenClassDoesNotHaveEntityAnnotation() {
        assertThrows(IllegalStateException.class, () -> queryGenerator.findById(String.class, 10),
                "not an Entity");
    }
    @DisplayName("findById Throws Exception When Id Is Not String Long Integer")
    @Test
    void findByIdThrowsExceptionWhenIdIsNotStringLongInteger() {
        assertThrows(IllegalArgumentException.class, () -> queryGenerator.findById(Guest.class, 25.00),
                "Entity id should be String or integer");
        assertThrows(IllegalArgumentException.class, () -> queryGenerator.findById(Guest.class, "lkjoi"),
                "Entity id should be String or integer");
        assertThrows(IllegalArgumentException.class, () -> queryGenerator.findById(Guest.class, false),
                "Entity id should be String or integer");
    }

    @Test
    void findById() {
        String query = "SELECT * FROM user WHERE id = 5;";
        assertEquals(query, queryGenerator.findById(User.class, "5"));

    }
    @Test
    void deleteById() {
        String query = "DELETE * FROM user WHERE id = 5;";
        assertEquals(query, queryGenerator.deleteById(User.class, "5"));
    }

    @Test
    void insertThrowsExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> queryGenerator.insert(null));
    }
    @Test
    void insertThrowsExceptionWhenIsNotEntity() {
        assertThrows(IllegalStateException.class, () -> queryGenerator.insert(""), " not an Entity");
    }
    @Test
    void insertEmptyAnnotations() throws IllegalAccessException {
        String query = "INSERT INTO user (password, name, id) VALUE (\"126587\", \"Mike\", 5);";
        User user = createUser();
        assertEquals(query, queryGenerator.insert(user));

    }
    @Test
    void insertAnnotationsWithData() throws IllegalAccessException {
        String query = "INSERT INTO guest_table (guest_id, guest_name, guest_password) VALUE (5, \"Hello\", \"null\");";
        Guest guest = createGuest();
        assertEquals(query, queryGenerator.insert(guest));
    }

    @Test
    void updateEmptyAnnotations() throws IllegalAccessException {
        String query1 = "UPDATE user SET password = \"126587\", name = \"Mike\" WHERE id = 5;";
        User user = createUser();
        assertEquals(query1, queryGenerator.update(user));

    }
    @Test
    void updateAnnotationsWithData() throws IllegalAccessException {
        String query2 = "UPDATE guest_table SET guest_name = \"Hello\", guest_password = \"null\" WHERE guest_id = 5;";
        Guest guest = createGuest();
        assertEquals(query2, queryGenerator.update(guest));
    }
    private User createUser() {
        return new User(5L, "Mike", "126587");
    }
    private Guest createGuest() {
        return new Guest(5L, "Hello", "null");
    }
}