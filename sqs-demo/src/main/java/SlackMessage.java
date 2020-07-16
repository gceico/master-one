
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
@Setter
public class SlackMessage implements Serializable {

  private static final long serialVersionUID = 1L;
  private String username;
  private String text;
  private String icon_emoji;
  private String channel;
}