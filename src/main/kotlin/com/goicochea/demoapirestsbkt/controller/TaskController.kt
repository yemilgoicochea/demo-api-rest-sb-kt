package com.goicochea.demoapirestsbkt.controller

import com.goicochea.demoapirestsbkt.dto.TaskDTORequest
import com.goicochea.demoapirestsbkt.dto.TaskDTOResponse
import com.goicochea.demoapirestsbkt.service.impl.TaskService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/tasks")
class TaskController(
    var taskService: TaskService
) {

    @GetMapping
    fun getTasks(): List<TaskDTOResponse> {
        return taskService.getTasks()
    }

    @PostMapping("/create")
    fun createTask(@RequestBody taskDTORequest: TaskDTORequest): TaskDTOResponse{
        return taskService.createTask(taskDTORequest)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): TaskDTOResponse? {
        return taskService.getTaskById(id)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody taskDTORequest: TaskDTORequest): TaskDTOResponse? {
        return taskService.updateTask(id, taskDTORequest)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long){
        taskService.deleteTask(id)
    }
}
