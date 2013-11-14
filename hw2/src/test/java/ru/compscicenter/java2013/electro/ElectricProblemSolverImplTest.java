package ru.compscicenter.java2013.electro;

import org.junit.Test;
import org.junit.Assert;

/**
 * User: anlun
 */
public class ElectricProblemSolverImplTest {
  @Test(expected = SolverException.class)
  public void testSolve_Exception() throws Exception {
    final String testText = "Just some text";
    final ElectricProblemSolverImpl solver = new ElectricProblemSolverImpl();

    solver.solve(testText);
  }

  @Test
  public void testSolve_FromProblemDescription() throws Exception {
    final String testText =
        "Найдите сопротивление R участка цепи, если известно, что напряжение U = 10 В и сила тока I = 1 мА.";
    final ElectricProblemSolverImpl solver = new ElectricProblemSolverImpl();

    final ElectricQuantity result = solver.solve(testText);
    final ElectricQuantity expected = new ElectricQuantity(ElectricQuantityType.RESISTANCE, 10000);

    Assert.assertEquals(expected, result);
  }

  @Test
  public void testSolve_1() throws Exception {
    final String testText =
        "U R\n\n\t= 10 Ом и I = 1 мА.";
    final ElectricProblemSolverImpl solver = new ElectricProblemSolverImpl();

    final ElectricQuantity result = solver.solve(testText);
    final ElectricQuantity expected = new ElectricQuantity(ElectricQuantityType.VOLTAGE, 0.01);

    Assert.assertEquals(expected, result);
  }

  @Test
  public void testSolve_2() throws Exception {
    final String testText =
        "I R =\t 10  \t\n МОм и U    = 1 ГВ.";
    final ElectricProblemSolverImpl solver = new ElectricProblemSolverImpl();

    final ElectricQuantity result = solver.solve(testText);
    final ElectricQuantity expected = new ElectricQuantity(ElectricQuantityType.CURRENT, 100);

    Assert.assertEquals(expected, result);
  }

  @Test(expected = SolverException.class)
  public void testSolve_3() throws Exception {
    final String testText =
        "I R и U = 1 ГВ.";
    final ElectricProblemSolverImpl solver = new ElectricProblemSolverImpl();

    final ElectricQuantity result = solver.solve(testText);
  }

}
