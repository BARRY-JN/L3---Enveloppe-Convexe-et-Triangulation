package Program;

import java.awt.*;
import java.util.*;

import static java.util.Comparator.*;

public class convex {

    static double surface(Point p0, Point p1, Point p2) {
        // retourne la surface
        // négatif si on tourne dans le sens contraire des aiguilles d'une montre
        // positif sinon
        return ((p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y)) / 2.;
    }

    static void partition(ArrayList<Point> lp, ArrayList<Point> env){
        env.clear();
        if(lp.size()<=3) {
            env.addAll(lp);
            if(  lp.size()==3 &&
                    surface(env.get(0), env.get(1), env.get(2))>0){
                Point p = env.get(1);
                env.remove(p); env.add(p);
            }
            return;
        }
        //On divise en deux
        ArrayList<Point> e1 = new ArrayList<Point>();
        ArrayList<Point> e2 = new ArrayList<Point>();
        for(int i = 0; i<lp.size()/2; ++i)         e1.add(lp.get(i));
        for(int i = lp.size()/2; i<lp.size(); ++i) e2.add(lp.get(i));
        // On calcule les enveloppes de chaque morceau
        ArrayList<Point> env1 = new ArrayList<Point>();
        ArrayList<Point> env2 = new ArrayList<Point>();
        partition(e1, env1);
        partition(e2, env2);
        // et on fusionne !
        env.addAll( fusion(env1, env2) );
    }

    static ArrayList<Point> fusion(ArrayList<Point> e1, ArrayList<Point> e2){
        // recherche du point d'abcisse le plus grand dans e1;
        int iMax = 0;
        for( int i = 1; i < e1.size(); ++i) if(e1.get(i).x > e1.get(iMax).x) iMax = i;
        // le point d'abcisse le plus petit dans e2 est en 0
        int iMin = 0;
        // le segment du haut
        int ip1 = iMax;
        int ip2 = iMin;
        boolean b = true;
        while(b){
            b = false;
            if(convex.surface(e2.get(ip2), e1.get(ip1), e1.get((ip1+1)%e1.size()))>0){
                ip1 = (ip1+1)%e1.size();
                b = true;
            }
            if(convex.surface(e1.get(ip1), e2.get(ip2), e2.get((ip2-1+e2.size())%e2.size()))<0){
                ip2 = (ip2-1+e2.size())%e2.size();
                b = true;
            }
        }
        // le segment du bas
        int im1 = iMax;
        int im2 = iMin;
        b = true;
        while(b){
            b = false;
            if(convex.surface(e2.get(im2), e1.get(im1), e1.get((im1-1+e1.size())%e1.size()))<0){
                im1 = (im1-1+e1.size())%e1.size();
                b = true;
            }
            if(convex.surface(e1.get(im1), e2.get(im2), e2.get((im2+1)%e2.size()))>0){
                im2 = (im2+1)%e2.size();
                b = true;
            }
        }
        // fabrication de l'enveloppe
        ArrayList<Point> env = new ArrayList();
        for( int i = 0; i<=im1; ++i)            env.add(e1.get(i));
        if(ip2==0){
            for( int i = im2; i<e2.size(); ++i) env.add(e2.get(i));
            env.add(e2.get(0));
        }
        else
            for( int i = im2;  i<=ip2; ++i)            env.add(e2.get(i));
        if(ip1!=0) for( int i = ip1; i<e1.size(); ++i) env.add(e1.get(i));

        return env;
    }

    public ArrayList<Point> get_convex(ArrayList<Point> c){
        ArrayList<Point> env=new ArrayList<>();
        Collections.sort(c, comparingInt(o -> o.x));
        partition(c, env);
        return env;
    }
}
