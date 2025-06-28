package com.anydesk.app.resource;

import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.usecase.task.*;
import com.anydesk.domain.util.Page;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.anydesk.domain.model.TaskStatus.COMPLETED;
import static com.anydesk.domain.model.TaskStatus.TODO;
import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;

@QuarkusTest
class TaskResourceTest {

    @InjectMock
    ListTasksUseCase listTasksUseCase;
    @InjectMock
    CreateTaskUseCase createTaskUseCase;
    @InjectMock
    UpdateTaskUseCase updateTaskUseCase;
    @InjectMock
    DeleteTaskUseCase deleteTaskUseCase;
    @InjectMock
    ToggleTaskStatusUseCase toggleTaskStatusUseCase;

    private static final String USER = "user";
    private static final String PASS = "pass";

    @Test
    void testListAll_withContent() {
        var task = new ListTasksUseCase.Response(1L, "Test", "Desc", TODO);
        var page = new Page<>(List.of(task), 0, 10, 1);
        when(listTasksUseCase.execute(0, 10)).thenReturn(page);

        given()
                .auth().basic(USER, PASS)
                .queryParam("page", 0)
                .queryParam("pageSize", 10)
                .when()
                .get("/tasks")
                .then()
                .statusCode(OK.getStatusCode())
                .body("content[0].title", is("Test"));
    }

    @Test
    void testListAll_noContent() {
        var page = new Page<ListTasksUseCase.Response>(List.of(), 0, 10, 0);
        when(listTasksUseCase.execute(0, 10)).thenReturn(page);

        given()
                .auth().basic(USER, PASS)
                .queryParam("page", 0)
                .queryParam("pageSize", 10)
                .when()
                .get("/tasks")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void testListCompleted_withContent() {
        var task = new ListTasksUseCase.Response(1L, "Test", "Desc", COMPLETED);
        var page = new Page<>(List.of(task), 0, 10, 1);
        when(listTasksUseCase.execute(TaskStatus.COMPLETED, 0, 10)).thenReturn(page);

        given()
                .auth().basic(USER, PASS)
                .queryParam("page", 0)
                .queryParam("pageSize", 10)
                .when()
                .get("/tasks/completed")
                .then()
                .statusCode(OK.getStatusCode())
                .body("content[0].status", is("COMPLETED"));
    }

    @Test
    void testListCompleted_noContent() {
        var page = new Page<ListTasksUseCase.Response>(List.of(), 0, 10, 0);
        when(listTasksUseCase.execute(TaskStatus.COMPLETED,0, 10)).thenReturn(page);

        given()
                .auth().basic(USER, PASS)
                .queryParam("page", 0)
                .queryParam("pageSize", 10)
                .when()
                .get("/tasks/completed")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void testCreate_validRequest() {
        var request = new CreateTaskUseCase.Request("title", "desc", TODO);
        var response = new CreateTaskUseCase.Response(1L, "title", "desc", TODO);
        when(createTaskUseCase.exec(any())).thenReturn(response);

        given()
                .auth().basic(USER, PASS)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/tasks")
                .then()
                .statusCode(CREATED.getStatusCode())
                .body("id", is(1));
    }

    @Test
    void testCreate_invalidRequest_returnsBadRequest() {
        var invalidJson = """
            {
              "title": "",
              "description": "descricao válida"
            }
            """;

        given()
                .auth().basic(USER, PASS)
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when()
                .post("/tasks")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode())
                .body("message", containsString("Validation error"))
                .body("details", hasItems(containsString("title")));
    }

    @Test
    void testUpdate_validRequest() {
        var request = new UpdateTaskUseCase.Request("updated", "updated", TODO);
        var response = new UpdateTaskUseCase.Response(1L, "updated", "updated", TODO);
        when(updateTaskUseCase.exec(eq(1L), any())).thenReturn(response);

        given()
                .auth().basic(USER, PASS)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/tasks/1")
                .then()
                .statusCode(OK.getStatusCode())
                .body("title", is("updated"));
    }

    @Test
    void testUpdate_invalidRequest_returnsBadRequest() {
        var invalidJson = """
            {
              "title": "",
              "description": "",
              "status": null
            }
            """;

        given()
                .auth().basic(USER, PASS)
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when()
                .put("/tasks/1")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode())
                .body("message", containsString("Validation error"))
                .body("details", hasItems(containsString("title"), containsString("status")));
    }

    @Test
    void testDelete() {
        var response = new DeleteTaskUseCase.Response(1L, "deleted");
        when(deleteTaskUseCase.exec(1L)).thenReturn(response);

        given()
                .auth().basic(USER, PASS)
                .when()
                .delete("/tasks/1")
                .then()
                .statusCode(OK.getStatusCode())
                .body("id", is(1));
    }

    @Test
    void testToggleStatus() {
        var response = new ToggleTaskStatusUseCase.Response(1L, COMPLETED);
        when(toggleTaskStatusUseCase.exec(1L)).thenReturn(response);

        given()
                .auth().basic(USER, PASS)
                .when()
                .get("/tasks/1/toggle-status")
                .then()
                .statusCode(OK.getStatusCode())
                .body("status", is("COMPLETED"));
    }

    @Test
    void testUnauthorizedAccess() {
        given()
                .when()
                .get("/tasks")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode());
    }

}
