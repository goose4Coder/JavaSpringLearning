package tacocloud.tacocloud.dto;

import lombok.*;
import java.util.List;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class TacoCreationRequest {
    String name;
    List<Long> meat;
    List<Long> sauce;
    List<Long> vegetables;
    @NonNull
    Long bread;
    List<Long> other;
    TacoSize size;
}
