/*
 * Copyright (c) 2017. MIT-license for Jari Van Melckebeke
 * Note that there was a lot of educational work in this project,
 * this project was (or is) used for an assignment from Realdolmen in Belgium.
 * Please just don't abuse my work
 */

// Validation errors messages for Parsley
// Load this after Parsley

Parsley.addMessages('uk', {
  defaultMessage: "Некоректне значення.",
  type: {
    email:        "Введіть адресу електронної пошти.",
    url:          "Введіть URL-адресу.",
    number:       "Введіть число.",
    integer:      "Введіть ціле число.",
    digits:       "Введіть тільки цифри.",
    alphanum:     "Введіть буквено-цифрове значення."
  },
  notblank:       "Це поле повинно бути заповнено.",
  required:       "Обов'язкове поле",
  pattern:        "Це значення некоректно.",
  min:            "Це значення повинно бути не менше ніж %s.",
  max:            "Це значення повинно бути не більше ніж %s.",
  range:          "Це значення повинно бути від %s до %s.",
  minlength:      "Це значення повинно містити не менше ніж %s символів.",
  maxlength:      "Це значення повинно містити не більше ніж %s символів.",
  length:         "Це значення повинно містити від %s до %s символів.",
  mincheck:       "Виберіть не менше %s значень.",
  maxcheck:       "Виберіть не більше %s значень.",
  check:          "Виберіть від %s до %s значень.",
  equalto:        "Це значення повинно збігатися."
});

Parsley.setLocale('uk');