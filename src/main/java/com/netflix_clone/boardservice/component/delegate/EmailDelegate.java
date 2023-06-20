package com.netflix_clone.boardservice.component.delegate;

import com.github.newkayak12.config.SimpleEmailSender;
import com.github.newkayak12.message.HtmlMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailDelegate {
    private final SimpleEmailSender simpleEmailSender;

    public void sendCsMail(String title, String email, Map<String,Object> data){
        HtmlMailForm form = HtmlMailForm.write(title, email);
        form.setText(simpleEmailSender.resolvingTemplate("cs", data));
        simpleEmailSender.send(form);
    }

}
