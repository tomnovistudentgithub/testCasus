package nl.maastrichtuniversity.myusc.dtos;

import nl.maastrichtuniversity.myusc.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {


    public UserModel mapToModel(UserRequestDTO userDTO) {
        var result = new UserModel(-1L);
        result.setUserName(userDTO.getUserName());
        result.setPassword(userDTO.getPassword());

        return result;
    }

    public UserModel mapToModel(UserChangePasswordRequestDTO userDTO, Long id) {
        var result = new UserModel(id);
        result.setPassword(userDTO.getPassword());
        return result;
    }
}
