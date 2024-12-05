package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AirportInfo {
    private  String name;
    private  String imageUrl;
}
