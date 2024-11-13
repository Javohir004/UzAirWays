package uz.jvh.uzairways.domain.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerResponse {
    private UUID id;
    private String answer;
    private String question;

}
