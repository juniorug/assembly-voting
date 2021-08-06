package com.juniormascarenhas.assemblyvoting.validator;

import com.juniormascarenhas.assemblyvoting.exception.InvalidFormatException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CpfValidator {

  public static void validateCPF(String cpf) {
    if (!isCpf(cpf)) {
      throw new InvalidFormatException("CPF");
    }
  }

  private static boolean isCpf(String cpf) {
    if ((null == cpf) || allCharactersSame(cpf) || ((cpf.length() != 11) && (cpf.length() != 14))) {
      return false;
    }
    cpf = cpf.replaceAll("[-.]", "");
    try {
      Long.parseLong(cpf);
    } catch (NumberFormatException e) {
      return false;
    }

    int d1;
    int d2;
    int digit1;
    int digit2;
    int remainder;
    int cpfDigit;
    String nDigResult;

    d1 = d2 = 0;
    digit1 = digit2 = remainder = 0;

    for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
      cpfDigit = Integer.parseInt(cpf.substring(nCount - 1, nCount));
      d1 = d1 + (11 - nCount) * cpfDigit;
      d2 = d2 + (12 - nCount) * cpfDigit;
    }

    remainder = (d1 % 11);
    if (remainder < 2) {
      digit1 = 0;
    } else {
      digit1 = 11 - remainder;
    }

    d2 += 2 * digit1;
    remainder = (d2 % 11);
    if (remainder < 2) {
      digit2 = 0;
    } else {
      digit2 = 11 - remainder;
    }
    String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
    nDigResult = String.valueOf(digit1) + String.valueOf(digit2);
    return nDigVerific.equals(nDigResult);
  }

  private static boolean allCharactersSame(String s) {
    int n = s.length();
    for (int i = 1; i < n; i++)
      if (s.charAt(i) != s.charAt(0))
        return false;

    return true;
  }

}
