package com.mykola.example.validation;

import static com.mykola.example.util.Constants.ALLOWED_FILE_EXTENSION;
import static com.mykola.example.util.Constants.FILE_SIZE_ERROR_MESSAGE;
import static com.mykola.example.util.Constants.NOT_SUPPORTED_FILE_EXTENSION_ERROR_MESSAGE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mykola.example.validation.annotation.ValidImportFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImportFileValidator implements ConstraintValidator<ValidImportFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        var errorCount = 0;
        if (!isValidExtension(file)) {
            addViolation(context, NOT_SUPPORTED_FILE_EXTENSION_ERROR_MESSAGE);
            errorCount++;
        }

        if (!isValidFileSize(file)) {
            addViolation(context, FILE_SIZE_ERROR_MESSAGE);
            errorCount++;
        }

        return errorCount == 0;
    }

    private void addViolation(final ConstraintValidatorContext ctx,
                              final String msgPattern) {
        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(msgPattern)
                .addConstraintViolation();
    }

    private boolean isValidExtension(MultipartFile file) {
        var extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return ALLOWED_FILE_EXTENSION.equals(extension);
    }

    private boolean isValidFileSize(MultipartFile file) {
        return file.getSize() > INTEGER_ZERO;
    }
}
