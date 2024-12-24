package com.polarbookshop.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

// 领域模型是一个不可变的对象
public record Book(
        @NotBlank(message = "必须定义书籍 ISBN。") @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "ISBN格式必须有效。") String isbn, // 用于唯一标识一本书

        @NotBlank(message = "必须定义书籍标题。") String title,

        @NotBlank(message = "必须定义书籍作者。") String author,

        @NotNull(message = "必须定义书价。") @Positive(message = "书价必须大于零。") Double price) {
}
