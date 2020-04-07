package ch.yoursource.causationfinder.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordDto {

    @NotEmpty
    @NotNull
    private String oldPassword;
    
    @NotEmpty
    @NotNull
    @Size(min=8, max=64)
    private String newPassword;
    
    private String newPasswordConfirm;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String passwordConfirm) {
        this.newPasswordConfirm = passwordConfirm;
    }
    
}
