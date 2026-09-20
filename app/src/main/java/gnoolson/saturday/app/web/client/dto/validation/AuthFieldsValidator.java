package gnoolson.saturday.app.web.client.dto.validation;

import gnoolson.saturday.app.web.client.dto.ClientDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AuthFieldsValidator implements ConstraintValidator<AuthFieldsValidation, ClientDto.Auth> {

    /*
     *
     *
     * */
    private static boolean checkUsername(ClientDto.Auth dto, ConstraintValidatorContext context) {
        String username = dto.getUsername();

        if (username != null) {
            username = username.trim();
        }

        if (username == null || username.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{client.upsert.form.auth.username.error}")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        } else if (username.length() > 62) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{client.upsert.form.auth.username.error_2}")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private static boolean checkPassword(ClientDto.Auth dto, ConstraintValidatorContext context) {
        String password = dto.getPassword();
        if (password != null) {
            password = password.trim();
        }

        if (password == null || password.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{client.upsert.form.auth.password.error}")
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        } else if (password.length() > 62) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{client.upsert.form.auth.password.error_2}")
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public boolean isValid(ClientDto.Auth dto, ConstraintValidatorContext context) {
        if (!dto.isUse())
            return true;

        boolean valid = true;
        if (!checkUsername(dto, context))
            valid = false;

        if (!checkPassword(dto, context))
            valid = false;

        return valid;
    }

}