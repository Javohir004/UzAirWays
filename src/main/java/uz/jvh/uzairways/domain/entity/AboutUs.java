package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "aboutus")
public class AboutUs extends BaseEntity{

   private String turi;  // asosiy nomi  yani: Kompaniya , matbuot markazi , korparativ veb-sayt

   private String title;  // nomi

   private String content;  // text lari


}
