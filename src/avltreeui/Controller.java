package avltreeui;

import avlmodels.Avl;
import avlmodels.Nodo;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import javax.swing.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML private TextField etNodeValue;
    @FXML private Pane mainPane;

    private float circleSize = 25.0f;

    private ArrayList<Integer> elements = new ArrayList<>();

    private Avl<Integer> avlTree = new Avl<>();

    @FXML
    private void onAddButtonClicked(){

        int nodeValue;

        try{

            nodeValue = Integer.parseInt(etNodeValue.getText());

            if(!doesElementExist(nodeValue, elements)){

                cleanMainPane();
                elements.add(nodeValue);
                addElementToAvl(nodeValue);
                drawAvl();

            } else showMessage("Ese elemento ya existe en el AVL");


        } catch(Exception ex){

            showMessage("El valor debe de ser numérico");

        }

    }

    private boolean doesElementExist(int element, ArrayList<Integer> elements){

        Long res = elements.stream().filter(n -> n == element).count();
        return res > 0;

    }

    private void addElementToAvl(int element){

        avlTree.insertaElemento(element);

    }

    private void drawNodeCircle(String element, double xPosition, double yPosition){

        // Este es el índice en el que se encuentra el texto dentro del Group
        int textIndex = 1;

        // Este es el espacio extra que se va agregando cada que se agrega un nodo
        double extraWidth = 1.0f;


        Circle circle = createCircle();
        Text text = createText(element);

        Group group = new Group(circle);

        StackPane stack = new StackPane();
        stack.setLayoutX(xPosition);
        stack.setLayoutY(yPosition);
        stack.getChildren().addAll(group, text);
        stack.setOnMouseClicked(event -> {

            String nodeValue = ((Text)stack.getChildren().get(textIndex)).getText();

            int option = JOptionPane.showConfirmDialog(null, "Quieres eliminar el nodo "+nodeValue+"?", "Cuidado!", JOptionPane.YES_NO_OPTION);

            if(option == JOptionPane.YES_OPTION)
                deleteNodeFromAvl(nodeValue);
            else if(option == JOptionPane.NO_OPTION)
                JOptionPane.showMessageDialog(null, "Abortado :)");
            else
                JOptionPane.showMessageDialog(null, "Abortado x2");

        });

        mainPane.getChildren().add(stack);
        mainPane.setPrefWidth(mainPane.getPrefWidth()+ extraWidth);

    }

    private void deleteNodeFromAvl(String element){

       //TODO Implementar el eliminar nodo
        JOptionPane.showMessageDialog(null, element+"Deberá ser borrado...");

    }

    private Text createText(String nodeValue){

        final Text text = new Text(nodeValue);
        text.setFont(new Font(12));
        text.setBoundsType(TextBoundsType.VISUAL);

        return text;

    }

    private Circle createCircle(){

        Circle circle = new Circle();

        circle.setRadius(circleSize);
        circle.setStroke(Color.DEEPSKYBLUE);
        circle.setStrokeWidth(2);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setFill(Color.AZURE);
        circle.relocate(0, 0);


        return circle;

    }

    private void cleanMainPane(){

        mainPane.getChildren().clear();

    }

    private void drawAvl(){

        // Bien 2
//        drawNodeCircle("1", circleSize*1, 0.0f);
//
//        drawNodeCircle("1", 0.0f, circleSize*2);
//        drawNodeCircle("2", circleSize*2, circleSize*2);


        // Bien 4
//        drawNodeCircle("1", circleSize*3, 0.0f);
//
//        drawNodeCircle("1", circleSize*1, circleSize*2);
//        drawNodeCircle("2", circleSize*5, circleSize*2);
//
//        drawNodeCircle("1", 0.0f, circleSize*4);
//        drawNodeCircle("2", circleSize*2, circleSize*4);
//        drawNodeCircle("3", circleSize*4, circleSize*4);
//        drawNodeCircle("4", circleSize*6, circleSize*4);

        // Bien 8
//        drawNodeCircle("1", circleSize*7, 0.0f);
//
//        drawNodeCircle("1", circleSize*3, circleSize*2);
//        drawNodeCircle("2", circleSize*11, circleSize*2);
//
//        drawNodeCircle("1", circleSize*1, circleSize*4);
//        drawNodeCircle("2", circleSize*5, circleSize*4);
//        drawNodeCircle("3", circleSize*9, circleSize*4);
//        drawNodeCircle("4", circleSize*13, circleSize*4);
//
//        drawNodeCircle("1", 0.0f, circleSize*6);
//        drawNodeCircle("2", circleSize*2, circleSize*6);
//        drawNodeCircle("3", circleSize*4, circleSize*6);
//        drawNodeCircle("4", circleSize*6, circleSize*6);
//        drawNodeCircle("1", circleSize*8, circleSize*6);
//        drawNodeCircle("2", circleSize*10, circleSize*6);
//        drawNodeCircle("3", circleSize*12, circleSize*6);
//        drawNodeCircle("4", circleSize*14, circleSize*6);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("/////////////////////Nueva Insercion///////////////////");

        Nodo root = avlTree.getRaiz();

        if(root == null) {
            // Como sólo agregas, nunca debería entrar acá
            JOptionPane.showMessageDialog(null, "El Árbol está vacío");
            return;
        }

        // Altura del árbol
        int height = root.getAltura()+1;

        // Ancho del árbol, es la máxima cantidad de posibles 'hojas' del árbol
        int width = (int)Math.pow(2, height-1);

        System.out.println("Altura -> "+height);
        System.out.println("Anchura -> "+width);

        // Variables para el ciclo
        List<Nodo> current = new ArrayList<>(1);
        List<Nodo> next = new ArrayList<>(2);

        current.add(root);

        // Cantidad de elements a usar en la siguiente iteración del ciclo
        // Cada iteración será el doble de elements
        int elements = 1;

        // Un acumulador que cuenta cada que recorres un nivel del arbol
        int acum = 0;

        // Posiciones en pixeles de los Circulos
        float actualPositionX = 0;
        float actualPositionY = 0;

        for(int i = 0; i < height; i++) {

            System.out.println("-----------Iteracion "+(i+1)+"------------------");
            actualPositionX = ((int)Math.pow(2, height-1-i) - 1)*circleSize;
            System.out.println("Altura -> "+i);
            System.out.println("Ancho -> "+width);
            System.out.println("actualPositionX -> "+actualPositionX);
            System.out.println("Multiplo de CircleSize -> "+actualPositionX/circleSize);

            // Print tree node elements
            for(Nodo n : current) {

                System.out.println("************Iteracion Interna****************");
                System.out.println("CircleSize -> "+(circleSize*Math.round(width / (acum + 1))));
                System.out.println("Multiplo CircleSize -> "+(Math.round(width / (acum + 1))));
                System.out.println("Valor de acum -> "+acum);
                System.out.println("Valor multiplo de PositionX -> "+(actualPositionX/circleSize));
                System.out.println("Valor actual de PositionX -> "+(actualPositionX));

                if(n == null) {
                    next.add(null);
                    next.add(null);

                } else {

                    drawNodeCircle(n.getElemento().toString(), actualPositionX, actualPositionY);
                    next.add(n.getIzquierdo());
                    next.add(n.getDerecho());
                }

                actualPositionX += circleSize*Math.round(width / (acum + 1));

                System.out.println("*********************************************");

            }

            if(i>0) acum++;

            actualPositionY+=circleSize*2;

            elements *= 2;
            current = next;
            next = new ArrayList<>(elements);

            System.out.println("------------------------------------------------");


        }


    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(null, message);
    }


}
