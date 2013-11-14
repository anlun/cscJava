package ru.compscicenter.java2013.electro;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElectricProblemSolverImpl implements ElectricProblemSolver {

  private static double getPrefixValue(String prefix) {
    if (prefix == null) {
      return 1;
    }

    int power = 0;
    if (prefix.equals("к")) {
      power = 3;
    } else if (prefix.equals("М")) {
      power = 6;
    } else if (prefix.equals("Г")) {
      power = 9;
    } else if (prefix.equals("м")) {
      power = -3;
    } else if (prefix.equals("мк")) {
      power = -6;
    } else if (prefix.equals("н")) {
      power = -9;
    }

    return Math.pow(10, power);
  }

  private static ElectricQuantity getQuantity(String quantitySymbol, String variableSymbol, String problemText) {
    final String regexDecimal  = "-?\\d*\\.\\d+";
    final String regexInteger  = "-?\\d+";
    final String regexDouble   = regexDecimal + "|" + regexInteger;
    final String regexAssign   = "\\s*=\\s*" + "(" + regexDouble + ")";
    final String regexQuantity = "([кМГмн]|мк)?" + quantitySymbol;
    final String regex         = variableSymbol + regexAssign + "\\s*" + regexQuantity;

    final Pattern pattern = Pattern.compile(regex);
    final Matcher matcher = pattern.matcher(problemText);

    if (!matcher.find()) { return null; }

    final String valueStr = matcher.group(1);
    final String prefix   = matcher.group(2);

    Double value = Double.parseDouble(valueStr) * getPrefixValue(prefix);

    if (variableSymbol.equals("R")) {
      return ElectricQuantity.resistance(value);
    }
    if (variableSymbol.equals("U")) {
      return ElectricQuantity.voltage(value);
    }
    if (variableSymbol.equals("I")) {
      return ElectricQuantity.current(value);
    }

    return null;
  }

  public ElectricQuantity solve(String problemText) throws SolverException {
    final ElectricQuantity i = getQuantity("А" , "I", problemText);
    final ElectricQuantity u = getQuantity("В" , "U", problemText);
    final ElectricQuantity r = getQuantity("Ом", "R", problemText);

    if (i == null) {
      if (r == null) { throw new SolverException("Unknown resistance and current!"); }
      if (u == null) { throw new SolverException("Unknown voltage and current!"   ); }
      return ElectricQuantity.current(u.getValue() / r.getValue());
    }

    if (r == null) {
//      if (i == null) { throw new SolverException("Unknown resistance and current!"); }
      if (u == null) { throw new SolverException("Unknown resistance and voltage!"); }
      return ElectricQuantity.resistance(u.getValue() / i.getValue());
    }

    if (u == null) {
//      if (i == null) { throw new SolverException("Unknown voltage and current!"   ); }
//      if (r == null) { throw new SolverException("Unknown resistance and voltage!"); }
      return ElectricQuantity.voltage(r.getValue() * i.getValue());
    }

    throw new SolverException("All values are known.");
  }
}