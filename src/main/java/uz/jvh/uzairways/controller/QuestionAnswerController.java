package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.QuestionRequest;
import uz.jvh.uzairways.domain.DTO.response.AnswerResponse;
import uz.jvh.uzairways.service.QuestionAnswerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/question/answer")
@RequiredArgsConstructor
public class QuestionAnswerController {

    private final QuestionAnswerService service;


    /**savol berish**/
    @PostMapping("/create-question")
    public ResponseEntity<AnswerResponse> createQuestion(@RequestBody QuestionRequest questionRequest) {
        return ResponseEntity.ok(service.save(questionRequest));
    }


    @DeleteMapping("/delete-question/{questionId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID questionId) {
        service.delete(questionId);
        return ResponseEntity.ok("Savol muvaffaqiyatli o'chirildi.");
    }

    /**savolga javob berish**/
    @PutMapping("/answer-to-question/{id}")
    public AnswerResponse answerToQuestion(@PathVariable("id") UUID id, @RequestBody QuestionRequest questionRequest) {
        return service.update(questionRequest,id);
    }

   /** javobi yo'q savollari keladi**/
    @GetMapping("/get-questions")
    public List<AnswerResponse> getQuestions() {
        return service.findQuestions();
    }

    /**to'liq javobi bor savollar **/
    @GetMapping("/get-all-questions-wtih-answer")
    public List<AnswerResponse> getAllQuestionsWithAnswer() {
        return service.findAll();
    }

}