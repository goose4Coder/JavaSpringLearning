package tacocloud.tacocloud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TacoReport {
    @NonNull
    TacoDto value;
    boolean failed=false;
    String message="";
    public TacoReport(String error){
        value=new TacoDto(0L,"", List.of(),TacoSize.MEDIUM);
        message=error;
        failed=true;
    }
}
