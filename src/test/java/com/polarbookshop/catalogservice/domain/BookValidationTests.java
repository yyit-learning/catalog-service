package com.polarbookshop.catalogservice.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 单元测试示例
 * 执行测试 （./gradlew test --tests BookValidationTests）
 */
class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 当所有字段都正确时，验证就会成功
     */
    @Test
    void whenAllFieldCorrectThenValidationSucceeds() {
        var book = new Book("1234567890", "Title", "Author", 9.90); // (3)
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty(); // (4)
    }

    /**
     * 如果国际标准书号（ISBN）未定义，那么验证失败
     */
    @Test
    void whenIsbnNotDefinedThenValidationFails() {
        var book = new Book("", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(2);
        List<String> constraintViolationMessages = violations.stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());

        assertThat(constraintViolationMessages)
                .contains("必须定义书籍 ISBN。")
                .contains("ISBN格式必须有效。");
    }

    /**
     * 当国际标准书号（ISBN）被定义但不正确时，验证就会失败。
     */
    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = new Book("a234567890", "Title", "Author", 9.90); // (5)
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("ISBN格式必须有效。"); // (6)
    }

    /**
     * 当标题未被定义时，验证失败
     */
    @Test
    void whenTitleIsNotDefinedThenValidationFails() {
        var book = new Book("1234567890", "", "Author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("必须定义书籍标题。");
    }

    /**
     * 如果作者未被定义，那么验证失败。
     */
    @Test
    void whenAuthorIsNotDefinedThenValidationFails() {
        var book = new Book("1234567890", "Title", "", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("必须定义书籍作者。");
    }

    /**
     * 当价格未定义时，验证失败
     */
    @Test
    void whenPriceIsNotDefinedThenValidationFails() {
        var book = new Book("1234567890", "Title", "Author", null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("必须定义书价。");
    }

    /**
     * 当价格已定义但为零时，验证失败
     */
    @Test
    void whenPriceDefinedButZeroThenValidationFails() {
        var book = new Book("1234567890", "Title", "Author", 0.0);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("书价必须大于零。");
    }

    /**
     * 当价格被定义但为负值时，那么验证失败
     */
    @Test
    void whenPriceDefinedButNegativeThenValidationFails() {
        var book = new Book("1234567890", "Title", "Author", -9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("书价必须大于零。");
    }

}
