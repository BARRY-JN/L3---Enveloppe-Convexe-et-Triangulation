package Program;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

import static java.util.Comparator.comparingInt;

public class Triangulation {
    private class Triangle{
        Point p1,p2,p3;

        public Triangle(Point p1,Point p2,Point p3){
            this.p1=p1;
            this.p2=p2;
            this.p3=p3;
        }
    }
    private ArrayList<Triangle> Triangulations;

    public boolean Triangle_Contains(Point p, Triangle t){

        double ABC = Math.abs (t.p1.x * (t.p2.y - t.p3.y) + t.p2.x * (t.p3.y - t.p1.y) + t.p3.x * (t.p1.y - t.p2.y));
        double ABP = Math.abs (t.p1.x * (t.p2.y - p.y) + t.p2.x * (p.y - t.p1.y) + p.x * (t.p1.y - t.p2.y));
        double APC = Math.abs (t.p1.x * (p.y - t.p3.y) + p.x * (t.p3.y - t.p1.y) + t.p3.x * (t.p1.y - p.y));
        double PBC = Math.abs (p.x * (t.p2.y - t.p3.y) + t.p2.x * (t.p3.y - p.y) + t.p3.x * (p.y - t.p2.y));

        return ABP + APC + PBC == ABC;

    }

    public double DistanceBetweenPoints(Point p1, Point p2){
            double x1=p1.x;
            double y1=p1.y;
            double x2=p2.x;
            double y2=p2.y;
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public ArrayList<Triangle> Trianguliser(ArrayList<Point> points){
        Collections.sort(points, comparingInt(o -> o.x));
        Triangle t1=new Triangle(points.get(0),points.get(1),points.get(2));
        Triangulations.add(t1);
//Pour chaque point du cloud
        for(int index=3;index<points.size();index++){
            //On teste si il est contenu dans un triangle
            for(Triangle t : Triangulations) {
                if (Triangle_Contains(points.get(index), t)) {  //Si il y est, on crée 3 nouveaux triangles, et supprimont celui initial
                    Triangle t2 = new Triangle(t.p1,points.get(index),t.p2);
                    Triangulations.add(t2);
                    Triangle t3 = new Triangle(t.p2,points.get(index),t.p3);
                    Triangulations.add(t3);
                    Triangle t4 = new Triangle(t.p1,points.get(index),t.p3);
                    Triangulations.add(t4);
                    Triangulations.remove(t);
                }else{          //Sinon, on le relie aux 2 point les plus proches
                    Point FirstClosest=new Point (900, 900);
                    Point SecondClosest=new Point (900, 900);
                    for(int index2=0;index2<points.size();index2++){
                        if(DistanceBetweenPoints(points.get(index), points.get(index2))<DistanceBetweenPoints(points.get(index), FirstClosest)){
                            FirstClosest=points.get(index2);
                        }
                        else{
                            if(DistanceBetweenPoints(points.get(index), points.get(index2))<DistanceBetweenPoints(points.get(index), SecondClosest)){
                                SecondClosest=points.get(index2);
                            }
                        }
                    }
                    Triangle t5 = new Triangle(points.get(index),FirstClosest,SecondClosest);
                    Triangulations.add(t5);
                }
            }
        }
        for(int index3=0;index3<points.size();index3++){
            System.out.println("Point "+index3+" : "+points.get(index3));
        }
        for(int index4=0;index4<Triangulations.size();index4++){
            System.out.println("Triangle "+index4+" : "+Triangulations.get(index4));
        }
        return Triangulations;
    }

}
