package com.luoyx.hauyne.message.support;

import lombok.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author 罗英雄
 * @date 2021/9/9 22:24
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

    private Long fileId;

    private String fileName;

    private byte[] fileData;

    public Attachment(Long fileId) {
        this.fileId = fileId;
    }

    public Attachment(File file) throws IOException {
        this.fileName = file.getName();
        this.fileData = FileUtils.readFileToByteArray(file);
    }

    public Attachment(File file, String fileName) throws IOException {
        this.fileName = fileName;
        this.fileData = FileUtils.readFileToByteArray(file);
    }
}
