package com.example.ec.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * メールアドレスが既にDBに存在しないことを検証する
 * @ interface は「ソースコードに付与できるマーカー（注釈）」を定義
 * */
@Target({ElementType.FIELD}) // このアノテーションをどこに付与できるか（FIELD → フィールド（プロパティ）にだけ使える。クラスやメソッドには付けられない）
@Retention(RetentionPolicy.RUNTIME) // アノテーションがどの段階まで残るかを指定（RUNTIME → 実行時まで保持され、リフレクションで読み取れる）
@Documented
@Constraint(validatedBy = UniqueEmailValidator.class) // このアノテーションが付けられたフィールドを検証するときは、UniqueEmailValidator クラスを使って判定
public @interface UniqueEmail {
    // Bean Validationのカスタム制約に必須の3つ
    /** エラーメッセージ。messages.properties のキー、または生文字列 */
    String message() default "{validation.email.unique}";

    /**
     * バリデーショングループ。通常は未使用でOK
     * Bean Validation でどのグループで検証するかを制御(特殊なケース（新規登録のときは必須、更新時は任意など）に利用)
     * */
    Class<?>[] groups() default {};

    /** エラーに追加情報を持たせる物。通常は未使用でOK */
    Class<? extends Payload>[] payload() default {};
}
