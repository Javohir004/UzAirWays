package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QuestionRequest {
    private String question;
    private String answer;
}
