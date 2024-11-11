package uz.jvh.uzairways.domain.entity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jvh.uzairways.domain.enumerators.InformationStatus;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "important_ifo")
public class ImportantInfo extends BaseEntity {

    private String title; /// 4 ta:  ushichdan oldin , xarid qilishdan oldin , aeraportda , samalyot bortida

    private InformationStatus status; /// statusi yani "yo'lovchi tashish qoidalari" yoki "aviatsiya xafvsizligi"

    private String content;  /// ya'ni o'sha qo'liq qoidalari malumotlari

}
