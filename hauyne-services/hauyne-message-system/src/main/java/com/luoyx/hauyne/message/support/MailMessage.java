package com.luoyx.hauyne.message.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 罗英雄
 * @date 2021/9/9 22:18
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailMessage {

    /**
     * 邮件标题
     */
    String subject;

    /**
     * 邮件内容
     */
    String content;

    /**
     * 邮件模板
     */
    byte[] template;

    /**
     * 邮件模板参数
     */
    Map<String, Object> templateParam;

    /**
     * 是否为HTML内容展示
     */
    boolean isHtml;

    /**
     * 发信人
     */
    String from;

    /**
     * 收件人
     */
    String[] to;

    /**
     * 抄送人
     */
    String[] cc;

    /**
     * 匿名抄送人
     */
    String[] bcc;

    /**
     * 附件id
     */
    Attachment[] attachments;

    /**
     * 发送人账号，可以为空，为空默认取额BOND账号
     */
    String username;

    /**
     * 发送人密码，可以为空，为空默认取额BOND密码，有username时不能为空
     */
    String password;

    /**
     * 发送人账号，可以为空，为空默认取额BOND账号
     */
    String storeUsername;

    /**
     * 发送人密码，可以为空，为空默认取额BOND密码，有username时不能为空
     */
    String storePassword;

    /**
     * 判断邮件是否符合要求
     */
    String pattern;

    /**
     * 每次发送数量，默认一次全部发送
     */
    Integer num;

    /**
     * 显示操作人 默认显示
     */
    boolean showOperator = true;

    /**
     * 操作人
     */
    Long operatorId;

    public Long getOperatorId() {
        if (!ObjectUtils.isEmpty(this.operatorId)) {
            return this.operatorId;
        } else {
//            return SecurityUtils.getId()
            return null;
        }
    }


    private static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private MailMessage message;

        Builder() {
            this.message = new MailMessage();
        }

        public Builder subjects(String subject) {
            this.message.setSubject(subject);
            return this;
        }

        public Builder content(String content) {
            this.message.setContent(content);

            return this;
        }

        public Builder content(String content, boolean isHtml) {
            this.message.setContent(content);
            this.message.setHtml(isHtml);

            return this;
        }

        public Builder content(File file, Map<String, Object> param) throws IOException {
            this.message.setTemplate(FileUtils.readFileToByteArray(file));
            this.message.setTemplateParam(param);
            this.message.setHtml(true);

            return this;
        }

        public Builder content(InputStream inputStream, Map<String, Object> param) throws IOException {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            this.message.setTemplate(output.toByteArray());
            this.message.setTemplateParam(param);
            this.message.setHtml(true);

            return this;
        }

        public Builder isHtml(boolean isHtml) {
            this.message.setHtml(isHtml);
            return this;
        }

        public Builder from(String from) {
            this.message.setFrom(from);
            return this;
        }

        public Builder to(String to) {
            this.message.setTo(new String[]{to});
            return this;
        }

        public Builder to(String... tos) {
            this.message.setTo(tos);
            return this;
        }

        public Builder to(List<String> toList) {
            this.message.setTo(toList.toArray(new String[0]));
            return this;
        }

        public Builder cc(String to) {
            this.message.setCc(new String[]{to});
            return this;
        }

        public Builder cc(List<String> ccList) {
            this.message.setCc(ccList.toArray(new String[0]));
            return this;
        }

        public Builder bcc(String bcc) {
            this.message.setBcc(new String[]{bcc});
            return this;
        }

        public Builder bcc(String... bccs) {
            this.message.setBcc(bccs);
            return this;
        }

        public Builder bcc(List<String> bccList) {
            this.message.setBcc(bccList.toArray(new String[0]));
            return this;
        }

        public Builder attachments(Long fileId) {
            this.message.setAttachments(new Attachment[]{new Attachment(fileId)});
            return this;
        }

        public Builder attachments(Long... fileIds) {
            this.message.setAttachments(Arrays.stream(fileIds).map(Attachment::new).toArray(Attachment[]::new));
            return this;
        }

        public Builder attachments(File file) throws IOException {
            this.message.setAttachments(new Attachment[]{new Attachment(file)});
            return this;
        }

        public Builder attachments(File... files) throws IOException {
            List<Attachment> list = new ArrayList<>();
            for (File file : files) {
                Attachment attachment = new Attachment(file);
                list.add(attachment);
            }
            this.message.setAttachments(list.toArray(new Attachment[0]));
            return this;
        }

        public Builder attachments(File file, String fileName) throws IOException {
            this.message.setAttachments(new Attachment[]{new Attachment(file, fileName)});
            return this;
        }

        public Builder attachments(Attachment attachment) {
            this.message.setAttachments(new Attachment[]{attachment});
            return this;
        }

        public Builder attachments(Attachment... attachments) {
            this.message.setAttachments(attachments);
            return this;
        }

        public Builder auth(String username, String password) {
            this.message.setUsername(username);
            this.message.setPassword(password);
            return this;
        }

        public Builder storeAuth(String storeUsername, String storePassword) {
            this.message.setStoreUsername(storeUsername);
            this.message.setStorePassword(storePassword);
            return this;
        }

        public Builder pattern(String pattern) {
            this.message.setPassword(pattern);
            return this;
        }

        public Builder num(Integer num) {
            this.message.setNum(num);
            return this;
        }

        public Builder showOperator(boolean showOperator) {
            this.message.setShowOperator(showOperator);
            return this;
        }

        public MailMessage build() {
            return message;
        }
    }


}
