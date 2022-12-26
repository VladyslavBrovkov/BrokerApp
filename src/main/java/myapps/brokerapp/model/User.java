package myapps.brokerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import myapps.brokerapp.model.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String fullName;
    private String login;
    private String password;
    private UserRole userRole;
    private Account account;
}
