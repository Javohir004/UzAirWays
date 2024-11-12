package uz.jvh.uzairways.domain.entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "questionanswer")
public class QuestionAnswer extends BaseEntity {

    private String question;

    private String answer;

}
