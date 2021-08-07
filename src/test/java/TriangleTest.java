import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import triangle.Triangle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.DoubleStream;

public class TriangleTest {
    @Test
    public void testNotZero() {
        Triangle triangle = new Triangle(0, 0, 0);
        Assert.assertFalse(triangle.checkTriangle());
    }

    @Test
    public void testNotZeroOneSide() {
        Triangle triangle = new Triangle(0, 2, 2);
        triangle.checkTriangle();
        Assert.assertEquals(triangle.getMessage(), "a<=0");
    }

    @Test
    public void testSumOfSidesMore() {
        Triangle triangle = new Triangle(2, 2, 3);
        Assert.assertTrue(triangle.checkTriangle());
    }

    @Test
    public void testSumOfTwoSidesLess() {
        Triangle triangle = new Triangle(1, 2, 3);
        triangle.checkTriangle();
        Assert.assertEquals(triangle.getMessage(), "a+b<=c");
    }

    @Test
    public void testSumOfSidesLess() {
        Triangle triangle = new Triangle(1, 2, 3);
        Assert.assertFalse(triangle.checkTriangle());
    }

    @Test
    public void testEquilateral() {
        Triangle triangle = new Triangle(2, 2, 2);
        Assert.assertEquals(triangle.detectTriangle(), 1);
    }

    @Test
    public void testIncorrectInput() {
        Triangle triangle = new Triangle(0, 0, 0);
        Assert.assertEquals(triangle.detectTriangle(), "Not a triangle");
    }


    @Test(dataProvider = "isoscelesData")
    public void testIsosceles(int a, int b, int c) {
        Triangle triangle = new Triangle(a, b, c);
        Assert.assertEquals(triangle.detectTriangle(), 2);
    }

    @DataProvider
    public Object[][] isoscelesData() {
        return new Object[][] {
                {2, 2, 1},
                {2, 3, 3},
                {2, 1, 2},
        };
    }

    @Test(dataProvider = "rectangularData")
    public void testRectangular(double a, double b, double c) {
        Triangle triangle = new Triangle(a, b, c);
        Assert.assertEquals(triangle.detectTriangle(), 8);
    }

    @DataProvider
    public Object[][] rectangularData() {
        File text = new File("src/test/resources/input");//Decided to try using data from file...Method looks bad =(
        List<Object[]> numbersList = new ArrayList<Object[]>();
        try {
            Scanner scanner = new Scanner(text);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                var numbersInString = line.split(" ");
                var numbers = Arrays.stream(numbersInString).flatMapToDouble(num->
                        DoubleStream.of(Double.parseDouble(num))).mapToObj(x->x).toArray();
                numbersList.add(numbers);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return numbersList.toArray(new Object[numbersList.size()][]);
    }

    @Test(dataProvider = "RectangularAndIsoscelesData")
    public void testRectangularAndIsosceles(double a, double b, double c) {
        Triangle triangle = new Triangle(a, b, c);
        Assert.assertEquals(triangle.detectTriangle(), 10);
    }

    @DataProvider
    public Object[][] RectangularAndIsoscelesData() {
        return new Object[][] {
                {5, 5, 5*Math.sqrt(2)},
                {5*Math.sqrt(2), 5, 5},
                {5, 5*Math.sqrt(2), 5},
        };

    }

    @Test
    public void testOrdinary() {
        Triangle triangle = new Triangle(4, 2, 3);
        Assert.assertEquals(triangle.detectTriangle(), 4);
    }

    @Test
    public void testSquareWithInt() {
        Triangle triangle = new Triangle(4, 2, 3);
        Assert.assertEquals(triangle.getSquare(), 2.9047375096555625);
    }

    @Test
    public void testSquareWithDouble() {
        Triangle triangle = new Triangle(4.45, 2.3333333331, 3.0);
        Assert.assertEquals(triangle.getSquare(), 3.233527878075967);
    }

    @Test
    public void testSquareWithHugeValues() {
        Triangle triangle = new Triangle(Math.pow(10, 50), Math.pow(10, 50), Math.pow(10, 50));
        Assert.assertEquals(triangle.getSquare(), 4.330127018922194E99);
    }

    @Test
    public void testSquareWithSmallValues() {
        Triangle triangle = new Triangle(Math.pow(10, -50), Math.pow(10, -50), Math.pow(10, -50));
        Assert.assertEquals(triangle.getSquare(), 4.330127018922192E-101);
    }

    @Test
    public void testPerimeterOverflow () {
        Triangle triangle = new Triangle(Double.MAX_VALUE, Double.MAX_VALUE, 1);
        Assert.assertEquals(triangle.getSquare(), Double.POSITIVE_INFINITY);
    }

    @Test  //Decided to experiment with Double.MAX_VALUE
    public void testMaxDouble() {
        Double p = (Double.MAX_VALUE -50 + 50 + 1)/2;
        var p2 = (Double.MAX_VALUE - 50 + 50 + 1);
        var p3 = Double.MAX_VALUE + 1 == Double.MAX_VALUE;
        var p5 = Double.MAX_VALUE + 10 == Double.MAX_VALUE;
        var p4 = Double.MAX_VALUE + (Double.MAX_VALUE/2);
    }

    @Test(expectedExceptions = {NumberFormatException.class}) //Decided to experiment with expected exception
    public void testDataTypeInput() {
        int a = Integer.parseInt("q");

    }

}
