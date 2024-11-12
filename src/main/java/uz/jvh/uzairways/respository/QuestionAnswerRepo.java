package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.QuestionAnswer;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionAnswerRepo extends JpaRepository<QuestionAnswer, UUID> {


    List<QuestionAnswer> findAllByIsActiveTrueAndQuestionIsNotNullAndAnswerIsNotNull();


    List<QuestionAnswer> findAllByAnswerIsNull();
}
