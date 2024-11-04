package com.goicochea.demoapirestsbkt.controller

import com.goicochea.demoapirestsbkt.DemoApiRestSbKtApplication
import com.goicochea.demoapirestsbkt.dto.TaskDTORequest
import com.goicochea.demoapirestsbkt.dto.TaskDTOResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import kotlin.test.assertTrue

@SpringBootTest(
    classes = [DemoApiRestSbKtApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class TaskDTOResponseCRUDIntegrationTest{

    @Autowired
    lateinit var restTempplate: TestRestTemplate

    var taskId: Long = 0

    @Test
    fun testCreateTask() {
        val taskDTORequest = TaskDTORequest(
            "Task 1",
            "Description 1",
            false
        )

        val response = this.restTempplate.postForEntity("/tasks/create", taskDTORequest, TaskDTOResponse::class.java)
        taskId = response.body?.id!!
        assert(response.statusCode.is2xxSuccessful)
        assertTrue { response.body?.name.equals("Task 1") }
        assertTrue { response.body?.description.equals("Description 1") }
    }

    @Test
    fun testrReturnTaskSuccessfully() {
        testCreateTask()
        val response = this.restTempplate.getForEntity("/tasks/{id}", TaskDTOResponse::class.java, taskId)
        assertTrue { response.body?.name.equals("Task 1") }
    }

    @Test
    fun testUpdateTaskSuccessfully() {
        testCreateTask()
        val taskDTORequest = TaskDTORequest(
            "Task 1 Updated",
            "Description 1 Updated",
            true
        )

        this.restTempplate.put("/tasks/{id}", taskDTORequest, taskId)
        val response = this.restTempplate.getForEntity("/tasks/{id}", TaskDTOResponse::class.java, taskId)
        assertTrue { response.statusCode.is2xxSuccessful }
        assertTrue { response.body?.done!! }
    }

    @Test
    fun testDeleteTaskSuccessfully() {
        testCreateTask()
        this.restTempplate.delete("/tasks/{id}", taskId)
        val response = this.restTempplate.getForEntity("/tasks/{id}", String::class.java, taskId)
        assertTrue { response.statusCode.equals(HttpStatus.NOT_FOUND) }
    }

}
