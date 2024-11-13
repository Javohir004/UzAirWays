package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AboutUsResponse {
    private UUID id;
    private String turi;
    private String title;
    private String content;
}
