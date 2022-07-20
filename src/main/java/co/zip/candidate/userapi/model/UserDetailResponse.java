package co.zip.candidate.userapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailResponse {

    private List<UserDetail> userDetails;
}
