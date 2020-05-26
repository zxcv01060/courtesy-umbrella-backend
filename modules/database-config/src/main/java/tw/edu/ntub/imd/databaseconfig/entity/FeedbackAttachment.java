package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.MediaTypeConverter;
import tw.edu.ntub.imd.databaseconfig.enumerated.MediaType;

import javax.persistence.*;

@Entity
@Table(name = "feedback_attachment", schema = Config.DATABASE_NAME)
@Data
public class FeedbackAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "feedback_id", nullable = false)
    private Integer feedbackId;
    @Column(name = "type", length = 1, nullable = false)
    @Convert(converter = MediaTypeConverter.class)
    private MediaType type;
    @Column(name = "url", length = 300, nullable = false)
    private String url;
}
