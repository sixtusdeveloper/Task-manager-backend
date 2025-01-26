package net.sixtusdev.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.sixtusdev.taskmanager.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
