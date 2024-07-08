package lj.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "usersignin")
public class UserSignin {
    private Long userSigninId;
    private Long userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate signinDate;

    public Long getUserSigninId() {
        return userSigninId;
    }

    public void setUserSigninId(Long userSigninId) {
        this.userSigninId = userSigninId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getSigninDate() {
        return signinDate;
    }

    public void setSigninDate(LocalDate signinDate) {
        this.signinDate = signinDate;
    }
}
