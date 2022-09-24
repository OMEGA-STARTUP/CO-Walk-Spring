package com.omega.cowalk.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@EnableAsync
public class MailSender
{
    private static final String SENDER = "SUPPORT@cowalknow.com";

    private static final String SIGNUP_SUBJECT = "회원가입 인증번호 요청";
    private static final String FIND_ID_SUBJECT = "아이디 찾기 요청";
    private static final String PASSWORD_SUBJECT = "비번 새로생성 요청";

    private static final String HTML_EMAIL_PASSWORD_TEMPLATE = "<h1>비번 생성을 위한 인증번호 요청</h1>" +
            "<body>인증번호는 <strong>%s</strong> 입니다.</body>";

    private static final String HTML_EMAIL_SIGNUP_TEMPLATE = "<h1>회원가입 위한 인증번호 요청</h1>" +
                                                        "<body>인증번호는 <strong>%s</strong> 입니다.</body>";
    private static final String HTML_EAMIL_FIND_ID_TEMPLATE = "<h1>아이디 찾기 요청</h1>" +
            "<body>아이디는 <strong>%s</strong> 입니다.</body>";

    //credentials will not be used in production. Be sure not to use it in production!
    private AmazonSimpleEmailService client;

    public MailSender(AWSCredentials credentials)
    {
        client = AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.AP_NORTHEAST_2)
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))  //remove this line during production
                        .build();
    }


    @Async
    public CompletableFuture<Boolean> senMailForPassword(String recipient, String code)
    {
        log.debug("sendMailForPassword called!");

        String htmlBody = String.format(HTML_EMAIL_PASSWORD_TEMPLATE, code);

        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        boolean result = false;

        result = sendMail(recipient, PASSWORD_SUBJECT, htmlBody, null );

        completableFuture.complete(result);

        return completableFuture;
    }

    @Async
    public CompletableFuture<Boolean> sendMailForIdInquiry(String recipient, String id)
    {
        log.debug("sendMailForIdInquiry called!");

        String htmlBody = String.format(HTML_EAMIL_FIND_ID_TEMPLATE, id);

        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        boolean result = false;

        result = sendMail(recipient, FIND_ID_SUBJECT, htmlBody, null );

        completableFuture.complete(result);

        return completableFuture;
    }

    @Async
    public CompletableFuture<Boolean> sendMailForSignup(String recipient, String code)
    {
        log.debug("sendMailForSignUp called!");

        String htmlBody = String.format(HTML_EMAIL_SIGNUP_TEMPLATE, code);

        CompletableFuture<Boolean> completableFuture = new CompletableFuture<Boolean>();

        boolean result = false;

        result = sendMail(recipient, SIGNUP_SUBJECT, htmlBody, null);

        completableFuture.complete(result);

        return completableFuture;
    }


    private boolean sendMail(String recipient, String subject, String htmlBody, String textBody )
    {

        log.info("sendMail called!");
        Body body = new Body();
        if(htmlBody != null)
        {
            body.withHtml(new Content()
                    .withCharset("UTF-8").withData(htmlBody));
        }

        if(textBody != null)
        {
            body.withHtml(new Content()
                    .withCharset("UTF-8").withData(textBody));
        }


        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(recipient))
                .withMessage(new Message()
                        .withBody(body)
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(subject)))
                .withSource(SENDER);

        log.debug("right before send email");
        SendEmailResult sendEmailResult = client.sendEmail(request);

        log.debug(sendEmailResult.toString());

        //execption이 없으면 true를 리턴하게 함
        return true;
    }


}
