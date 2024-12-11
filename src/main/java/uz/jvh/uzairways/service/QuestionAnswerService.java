package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.domain.DTO.request.QuestionRequest;
import uz.jvh.uzairways.domain.DTO.response.AnswerResponse;
import uz.jvh.uzairways.domain.entity.QuestionAnswer;
import uz.jvh.uzairways.domain.exception.CustomException;
import uz.jvh.uzairways.respository.QuestionAnswerRepo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionAnswerService {

    private final QuestionAnswerRepo questionAnswerRepo;

    public AnswerResponse save(QuestionRequest questionAnswer) {
        if (questionAnswer == null) {
            throw new CustomException("QuestionRequest must not be null",4002, HttpStatus.NOT_FOUND);
        }

        QuestionAnswer questionAnswerEntity = mapToQuestionAnswer(questionAnswer);
        questionAnswerRepo.save(questionAnswerEntity);
        return mapToAnswerResponse(questionAnswerEntity);
    }

    public void delete(UUID id) {
        QuestionAnswer questionAnswer = questionAnswerRepo.findById(id)
                .orElseThrow(() -> new CustomException("QuestionAnswer not found with id: " + id,4002,HttpStatus.NOT_FOUND));
        questionAnswer.setActive(false);
        questionAnswerRepo.save(questionAnswer);
    }

    @Transactional
    public AnswerResponse update(QuestionRequest questionRequest, UUID questionId) {
        QuestionAnswer questionAnswerEntity = questionAnswerRepo.findById(questionId)
                .orElseThrow(() -> new CustomException("QuestionAnswer not found with id: " + questionId,4002,HttpStatus.NOT_FOUND));
        questionAnswerEntity.setAnswer(questionRequest.getAnswer());
        questionAnswerRepo.save(questionAnswerEntity);
        return mapToAnswerResponse(questionAnswerEntity);
    }

    /** bu faqat javob berilmagan savollarni olib keladi**/
    public List<AnswerResponse> findQuestions(){
        List<QuestionAnswer> allByAnswerIsNull = questionAnswerRepo.findAllByAnswerIsNullOrderByCreatedDesc();
        return allByAnswerIsNull.stream().
                map(qa -> mapToAnswerResponse(qa))
                .collect(Collectors.toList());
    }

    public List<AnswerResponse> findAll() {
        List<QuestionAnswer> questionAnswers =
                questionAnswerRepo.findAllByIsActiveTrueAndQuestionIsNotNullAndAnswerIsNotNullOrderByCreatedDesc();
        return questionAnswers.stream()
                .map(this::mapToAnswerResponse)
                .collect(Collectors.toList());
    }

    public QuestionAnswer mapToQuestionAnswer(QuestionRequest questionAnswer) {
     QuestionAnswer answer = new QuestionAnswer();
     answer.setQuestion(questionAnswer.getQuestion());
     return answer;
    }

    public AnswerResponse mapToAnswerResponse(QuestionAnswer questionAnswer) {
        AnswerResponse answer = new AnswerResponse();
        answer.setId(questionAnswer.getId());
        answer.setQuestion(questionAnswer.getQuestion());
        answer.setAnswer(questionAnswer.getAnswer());
        answer.setId(questionAnswer.getId());
        return answer;
    }
}
