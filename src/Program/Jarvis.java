package Program;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;


public class Jarvis
{

    private List<Integer> num_points;

    public Jarvis(){
        num_points=new ArrayList<>();
    }

    public List<Integer> get_connex_indice(){
        return num_points;
    }

    private boolean CCW(Point p, Point q, Point r)
    {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val >= 0)
            return false;
        return true;
    }

    public void convexHull(Point[] points)

    {

        int n = points.length;

        /** if less than 3 points return **/

        if (n < 3)

            return;

        int[] next = new int[n];

        Arrays.fill(next, -1);



        /** find the leftmost point **/

        int leftMost = 0;

        for (int i = 1; i < n; i++)

            if (points[i].x < points[leftMost].x)

                leftMost = i;

        int p = leftMost, q;

        /** iterate till p becomes leftMost **/

        do

        {

            /** wrapping **/

            q = (p + 1) % n;

            for (int i = 0; i < n; i++)

                if (CCW(points[p], points[i], points[q]))

                    q = i;



            next[p] = q;

            p = q;

        } while (p != leftMost);



        /** Display result **/

        output(next);
    }

    public void output(int[] next)

    {

        for (int i = 0; i < next.length; i++)

            if (next[i] != -1) {
                num_points.add(i);

            }

    }

    public void searchConvex(Cloud cloud){
        Point[] tmp=new Point[cloud.size()];

        for(int i=0;i<cloud.size();i++){
            tmp[i]=cloud.getPoint(i);
        }

        convexHull(tmp);
    }
    /** Main function **/

}