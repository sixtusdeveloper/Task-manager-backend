package net.sixtusdev.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.sixtusdev.taskmanager.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
