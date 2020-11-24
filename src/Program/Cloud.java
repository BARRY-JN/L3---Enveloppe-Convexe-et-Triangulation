package Program;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cloud {
    private ArrayList<Point> points;

    public Cloud(){
        points=new ArrayList<>();
    }

    public void AddPointInCloud(Point p){
        points.add(p);
    }

    public int size(){
        return points.size();
    }

    public Point getPoint(int indice){
        if(indice>=0&&indice<points.size())
            return points.get(indice);
        else
            return null;
    }

    public ArrayList<Point> getAllPoints(){
        return points;
    }

    public void DelPoint(int indice){
        if(indice>=0&&indice<points.size()) {
            points.remove(indice);
        }
    }

    public void NulPoint(int indice){
        points.set(indice,new Point(-1,-1));
    }

    public void DelAllPoint(){
        points.removeAll(points);
    }

    public void movePoint(int indice, int x, int y){
        getPoint(indice).setLocation(x,y);
    }

    public void randomPoints(int nbr, double xmin, double xmax, double ymin, double ymax){
        int i, random_x,random_y;
        DelAllPoint();

        for(i=0;i<nbr;i++){

            random_x = (int)(Math.random() * xmax + xmin);
            random_y = (int)(Math.random() * ymax + ymin);

            AddPointInCloud(new Point(random_x,random_y));
        }
    }

    public int MouseColision(double mx, double my){
        int i;
        for(i=0;i<size();i++){
            if(mx>=points.get(i).getX()-10&&mx<=points.get(i).getX()+20&&my>=points.get(i).getY()-10&&my<=points.get(i).getY()+20){
                return i;
            }
        }
        return -1;
    }

}

