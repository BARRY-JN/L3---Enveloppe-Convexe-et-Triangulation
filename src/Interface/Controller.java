package Interface;

import Program.Cloud;
import Program.convex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class Controller {
    @FXML
    private Button genereB;

    @FXML
    private Canvas zone;

    @FXML
    private TextField textboxNBR;

    @FXML
    private AnchorPane god;

    private GraphicsContext gc;

    private Cloud cloud;
    private Integer actual_point_nbr;
    private Boolean adding_point;

    public Controller(){
        cloud=new Cloud();
        actual_point_nbr=-1;
        adding_point=false;

    }

    @FXML
    void genereB_click(ActionEvent event) {

        if(gc==null)
            gc = zone.getGraphicsContext2D();
        String content = textboxNBR.getText();


        Integer nbr_total;
        if(content.isEmpty()) {
            nbr_total = 0;
        }else {
            nbr_total = Integer.valueOf(content);
        }

        double Xmin = 10;
        double Ymin = 10;
        double Xmax = gc.getCanvas().getWidth() - 20;
        double Ymax = gc.getCanvas().getHeight() - 20;

        cloud.randomPoints(nbr_total,Xmin,Xmax,Ymin,Ymax);
        drawCloud();
    }

    void drawCloud(){
        int i;
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        gc.setFill(Color.BLACK);
        for(i=0;i<cloud.size();i++){
            if(i==actual_point_nbr){
                gc.setFill(Color.ORANGE);
                gc.fillOval(cloud.getPoint(i).getX()-5,cloud.getPoint(i).getY()-5 , 20, 20);
                gc.setFill(Color.RED);
                gc.fillOval(cloud.getPoint(i).getX(),cloud.getPoint(i).getY() , 10, 10);
                gc.setFill(Color.BLACK);
            }else{
                gc.fillOval(cloud.getPoint(i).getX(),cloud.getPoint(i).getY() , 10, 10);
            }
        }
    }

    @FXML
    void zone_pressed(MouseEvent m_event){
        int mx,my;
        Double tx,ty;

        tx=m_event.getX();
        ty=m_event.getY();

        mx=tx.intValue();
        my=ty.intValue();

        actual_point_nbr = cloud.MouseColision(mx, my);
        drawCloud();
    }

    @FXML
    void zone_released(MouseEvent m_event){
        //actual_point_nbr=-1;
    }

    @FXML
    public void zone_dragged(MouseEvent m_event) {
        int mx,my;
        Double tx,ty;

        tx=m_event.getX();
        ty=m_event.getY();

        mx=tx.intValue();
        my=ty.intValue();


        if(actual_point_nbr!=-1){
            cloud.movePoint(actual_point_nbr,mx-5,my-5);
            drawCloud();
        }
    }

    @FXML
    public void deleteB_click(ActionEvent actionEvent) {
        cloud.DelPoint(actual_point_nbr);
        drawCloud();
    }

    @FXML
    public void addB_click(ActionEvent actionEvent) {
        adding_point=true;
    }

    @FXML
    public void zone_clicked(MouseEvent m_event) {
        int mx,my;
        Double tx,ty;

        tx=m_event.getX();
        ty=m_event.getY();

        mx=tx.intValue()-5;
        my=ty.intValue()-5;

        if(gc==null)
            gc = zone.getGraphicsContext2D();

        if(adding_point){
            cloud.AddPointInCloud(new Point(mx,my));
            System.out.println("t: "+cloud.size());
            drawCloud();
            adding_point=false;
        }
    }

    @FXML
    public void convexB_click(ActionEvent actionEvent) {

        //Jarvis j=new Jarvis();
        convex c=new convex();

        Point previous,next;

        if(cloud.size()<1)
            return;



        ArrayList<Point> tmp_list=cloud.getAllPoints();
        ArrayList<Point> tmp_result;

        Cloud tmp_cloud=new Cloud();
        tmp_result=c.get_convex(tmp_list);

        for(int k=0;k<tmp_result.size();k++){
            tmp_cloud.AddPointInCloud(tmp_result.get(k));
        }

        gc.setStroke(Color.BLUE);

        for(int i=0;i<tmp_cloud.size()-1;i++){
            previous=tmp_cloud.getPoint(i);
            next=tmp_cloud.getPoint(i+1);
            gc.strokeLine(previous.getX()+5,previous.getY()+5,next.getX()+5,next.getY()+5);
        }
        gc.strokeLine(tmp_cloud.getPoint(tmp_cloud.size()-1).getX()+5,tmp_cloud.getPoint(tmp_cloud.size()-1).getY()+5,tmp_cloud.getPoint(0).getX()+5,tmp_cloud.getPoint(0).getY()+5);
        gc.setStroke(Color.BLACK);

        tmp_cloud.DelAllPoint();
    }
}
