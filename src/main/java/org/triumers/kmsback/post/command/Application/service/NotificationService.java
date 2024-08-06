package org.triumers.kmsback.post.command.Application.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @Async
    public CompletableFuture<Boolean> sendMailMime(CmdEmployeeDTO employeeDTO, CmdPost post) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            String htmlContent = generateHtmlContent(post);

            helper.setTo(employeeDTO.getEmail());
            helper.setSubject(post.getTitle() + " 게시글 수정 알림");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);

            return CompletableFuture.completedFuture(true);

        } catch (MessagingException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(false);
        }
    }

    private String generateHtmlContent(CmdPost post) {
        String title = post.getTitle() != null ? post.getTitle() : "제목 없음";
        String contentSummary = post.getContent() != null ? post.getContent()
                .substring(0, Math.min(post.getContent().length(), 100)) + "..." : "내용 없음";
        String link = "http://www.strangechance.com/search/detail/" + post.getId();

        return "<html>" +
                "<body>" +
                "<h1>안녕하세요,</h1>" +
                "<p>다음 게시글이 수정되었습니다:</p>" +
                "<h2>" + title + "</h2>" +
                "<p><strong>내용 요약:</strong> " + contentSummary + "</p>" +
                "<p>자세한 내용은 아래 링크를 확인해 주세요:</p>" +
                "<p><a href=\"" + link + "\">" + title + "</a></p>" +
                "<p>감사합니다!</p>" +
                "<p><small>이 메일은 자동으로 발송된 메일입니다. 문의 사항은 사이트로 연락해 주세요.</small></p>" +
                "</body>" +
                "</html>";
    }
}