package com.goicochea.demoapirestsbkt.service.impl

import com.goicochea.demoapirestsbkt.dto.TaskDTORequest
import com.goicochea.demoapirestsbkt.dto.TaskDTOResponse
import com.goicochea.demoapirestsbkt.model.TaskEntity
import com.goicochea.demoapirestsbkt.repository.TaskRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class TaskServiceImpl(
    var taskRepository: TaskRepository
): TaskService {

    /**
     * Get all tasks
     * @return List<TaskDTOResponse>
     */
    override fun getTasks(): List<TaskDTOResponse> {
        return taskRepository.findAll().map {
            TaskDTOResponse(
                id = it.id!!,
                name = it.name,
                description = it.description,
                done = it.done
            )
        }
    }

    /**
     * Create a task
     * @param taskDTORequest TaskDTORequest
     * @return TaskDTOResponse
     */
    override fun createTask(taskDTORequest: TaskDTORequest): TaskDTOResponse {
        val taskEntity = taskRepository.save(
            TaskEntity(
                id = null,
                name = taskDTORequest.name,
                description = taskDTORequest.description,
                done = taskDTORequest.done
            )
        )

        return TaskDTOResponse(
            id = taskEntity.id!!,
            name = taskEntity.name,
            description = taskEntity.description,
            done = taskEntity.done
        )
    }

    /**
     * Get a task by id
     * @param id Long
     * @return TaskDTOResponse?
     */
    override fun getTaskById(id: Long): TaskDTOResponse? {
        return taskRepository.findById(id).map {
            TaskDTOResponse(
                id = it.id!!,
                name = it.name,
                description = it.description,
                done = it.done
            )
        }.getOrNull()
    }

    /**
     * Update a task
     * @param id Long
     * @param taskDTORequest TaskDTORequest
     * @return TaskDTOResponse?
     */
    override fun updateTask(id: Long, taskDTORequest: TaskDTORequest): TaskDTOResponse? {
        return taskRepository.findById(id).map {
            val taskEntity = taskRepository.save(
                TaskEntity(
                    id = it.id,
                    name = taskDTORequest.name,
                    description = taskDTORequest.description,
                    done = taskDTORequest.done
                )
            )

            TaskDTOResponse(
                id = taskEntity.id!!,
                name = taskEntity.name,
                description = taskEntity.description,
                done = taskEntity.done
            )
        }.getOrNull()
    }

    /**
     * Delete a task
     * @param id Long
     */
    override fun deleteTask(id: Long) {
        taskRepository.deleteById(id)
    }

}
