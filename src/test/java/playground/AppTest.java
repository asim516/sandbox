  package playground;

class Shapes
{
  public static void main(String arg[])
  {
    Rectangle r = new Rectangle(10, 4);
    Square s = new Square(7);
    Circle c = new Circle(3.5);

    System.out.println("Rectangle Area : " + r.getArea());
    System.out.println("Square Area : " + s.getArea());
    System.out.println("Circle Area : " + c.getArea());
    System.out.println();


  }
}

class Rectangle
{
  double length;
  double breadth;

  Rectangle(double length, double breadth)
  {
    this.length = length;
    this.breadth = breadth;
  }

  double getArea()
  {
    return length * breadth;
  }
}

class Square
{
  double side;

  Square(double side)
  {
    this.side = side;
  }

  double getArea()
  {
    return side * side;
  }
}

class Circle
{
  double radius;

  Circle(double radius)
  {
    this.radius = radius;
  }

  double getArea()
  {
    return (22.0/7.0) * radius * radius;
  }
}
