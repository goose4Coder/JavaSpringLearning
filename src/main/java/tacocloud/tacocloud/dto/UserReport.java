package tacocloud.tacocloud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserReport {
    @NonNull
    UserDto value;
    boolean failed=false;
    String message="";
    public UserReport(String error){
        value=new UserDto();
        message=error;
        failed=true;
    }
}
