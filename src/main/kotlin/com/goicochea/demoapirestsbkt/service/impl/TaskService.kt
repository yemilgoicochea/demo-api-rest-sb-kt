package com.goicochea.demoapirestsbkt.service.impl

import com.goicochea.demoapirestsbkt.dto.TaskDTORequest
import com.goicochea.demoapirestsbkt.dto.TaskDTOResponse

interface TaskService {

    fun getTasks(): List<TaskDTOResponse>
    fun createTask(taskDTORequest: TaskDTORequest): TaskDTOResponse
    fun getTaskById(id: Long): TaskDTOResponse?
    fun updateTask(id: Long, taskDTORequest: TaskDTORequest): TaskDTOResponse?
    fun deleteTask(id: Long)
}
