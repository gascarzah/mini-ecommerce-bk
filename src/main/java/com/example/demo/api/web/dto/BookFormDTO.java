package com.example.demo.api.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookFormDTO {
    @NotNull
    @Size(min = 3, max = 100)
    private String title;

    @NotNull
    @Min(0)
    private Float price;

    @NotNull
    @Pattern(regexp = "[a-z0-9-]+")
    private String slug;

    @NotBlank
    private String desc;

    @NotBlank
    private String coverPath;

//    @NotBlank
    private String filePath;
    
    @NotBlank
    private String category;
}
