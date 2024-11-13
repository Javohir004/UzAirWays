package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AboutUsRequest {
    private String turi;   // The type, e.g., Company, Press Center, Corporate Website
    private String title;  // The title of the AboutUs
    private String content; // The content (text) of the AboutUs
}
