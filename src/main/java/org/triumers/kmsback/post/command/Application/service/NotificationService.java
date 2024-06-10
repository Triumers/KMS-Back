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

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @Async
    public boolean sendMailMime(CmdEmployeeDTO employeeDTO, CmdPost post) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            String title = post.getTitle() != null ? post.getTitle() : "";
            String link = "http://www.strangechance.com/search/detail/" + post.getId();

            helper.setTo(employeeDTO.getEmail());
            helper.setSubject(title + " 게시글이 수정되었습니다.");
            String htmlContent = "<h1>" + title + " 게시글이 수정되었습니다. </h1>" +
                    "<div><a href=\"" + link + "\">" + title + "</a></div>";

            helper.setText(htmlContent, true);

            javaMailSender.send(message);
            return true;  // 이메일 전송 성공
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;   // 이메일 전송 실패
        }
    }
}