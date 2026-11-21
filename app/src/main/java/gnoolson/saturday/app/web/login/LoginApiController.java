package gnoolson.saturday.app.web.login;

import gnoolson.saturday.app.web.dto.ValidationResultDto;
import gnoolson.saturday.app.web.login.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginApiController {

    private final AuthenticationManager authenticationManager;
    private final MessageSource messageSource;
    private final RequestCache requestCache = new HttpSessionRequestCache();

    /*
     *
     *
     *
     * */
    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException {
        if (result.hasErrors()) {
            List<ValidationResultDto> collect = result.getFieldErrors().stream().map(ValidationResultDto::new).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(collect);
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        } catch (AuthenticationException e) {
            Locale locale = LocaleContextHolder.getLocale();
            return ResponseEntity.badRequest().body(Collections.singletonList(new ValidationResultDto("service", messageSource.getMessage("login.message.error.3", null, locale))));
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirectTo = "/";
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectTo = new URL(targetUrl).getPath();
        }

        return ResponseEntity.ok().body(String.format("{\"redirect\": \"%s\"}", redirectTo));
    }

}
