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
import javafx.scene.shape.Line;
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

    private float circleRadiusSize = 25.0f;

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
//                printHardcoded8Avl();

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
        double extraHeight = 10.0f;


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
        mainPane.setPrefHeight(mainPane.getPrefHeight()+ extraHeight);

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

        circle.setRadius(circleRadiusSize);
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

    private void drawLine(double xPosition1, double yPosition1, double xPosition2, double yPosition2){

        Line line = new Line(xPosition1, yPosition1, xPosition2, yPosition2);
        mainPane.getChildren().add(line);

    }

    private void drawAvl(){

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

        List<Float> previousXPositions = new ArrayList<>(1);
        List<Float> actualXPositions = new ArrayList<>(2);

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
            actualPositionX = ((int)Math.pow(2, height-1-i) - 1)*circleRadiusSize;
            System.out.println("Altura -> "+i);
            System.out.println("Ancho -> "+width);
            System.out.println("actualPositionX -> "+actualPositionX);
            System.out.println("Multiplo de circleRadiusSize -> "+actualPositionX/circleRadiusSize);

            int contadorParNodos = 0;

            // Print tree node elements
            for(Nodo n : current) {

                System.out.println("************Iteracion Interna****************");
                System.out.println("circleRadiusSize -> "+(circleRadiusSize*Math.round(width / (acum + 1))));
                System.out.println("Multiplo circleRadiusSize -> "+(Math.round(width / (acum + 1))));
                System.out.println("Valor de acum -> "+acum);
                System.out.println("Valor multiplo de PositionX -> "+(actualPositionX/circleRadiusSize));
                System.out.println("Valor actual de PositionX -> "+(actualPositionX));

                if(n == null) {

                    next.add(null);
                    next.add(null);

                } else {

                    drawNodeCircle(n.getElemento().toString(), actualPositionX, actualPositionY);
                    next.add(n.getIzquierdo());
                    next.add(n.getDerecho());

                }

                //Se agregan los puntos de X de los nodos de 'arriba' (padres)
                if(contadorParNodos%2==0)
                    previousXPositions.add(actualPositionX);

                contadorParNodos++;

                actualXPositions.add(actualPositionX);
                actualPositionX += circleRadiusSize*Math.round(width / (acum + 1));

                System.out.println("*********************************************");

            }

            if(i>0) acum++;

            drawConnectingLines(actualXPositions, previousXPositions, current, actualPositionY);

            actualPositionY+=circleRadiusSize*4;

            previousXPositions = actualXPositions;

            elements *= 2;
            current = next;
            next = new ArrayList<>(elements);
            actualXPositions = new ArrayList<>(elements);

            System.out.println("------------------------------------------------");


        }

    }

    private void drawConnectingLines(List<Float> actualXPositions, List<Float> previousXPositions, List<Nodo> current, float actualPositionY){

        int indexPreviousX = 0;
        for(int j=0;j<actualXPositions.size();j++){

            if(j%2==0) {
                if (current.get(j) != null)
                    drawLine(previousXPositions.get(indexPreviousX)+circleRadiusSize, actualPositionY-circleRadiusSize*2, actualXPositions.get(j)+circleRadiusSize, actualPositionY);
            } else {
                if (current.get(j) != null)
                    drawLine(previousXPositions.get(indexPreviousX)+circleRadiusSize, actualPositionY-circleRadiusSize*2, actualXPositions.get(j)+circleRadiusSize, actualPositionY);
                indexPreviousX++;
            }


        }

    }

    private void printHardcoded2Avl(){

        // Bien 2
        drawNodeCircle("1", circleRadiusSize*1, 0.0f);

        drawLine(circleRadiusSize*1+circleRadiusSize, circleRadiusSize*2, 0.0f+circleRadiusSize, circleRadiusSize*4);
        drawLine(circleRadiusSize*1+circleRadiusSize, circleRadiusSize*2, circleRadiusSize*2+circleRadiusSize, circleRadiusSize*4);

        drawNodeCircle("1", 0.0f, circleRadiusSize*4);
        drawNodeCircle("2", circleRadiusSize*2, circleRadiusSize*4);

    }

    private void printHardcoded4Avl(){

        // Bien 4
        drawNodeCircle("1", circleRadiusSize*3, 0.0f);

        drawLine(circleRadiusSize*3+circleRadiusSize, circleRadiusSize*2, circleRadiusSize*1+circleRadiusSize, circleRadiusSize*4);
        drawLine(circleRadiusSize*3+circleRadiusSize, circleRadiusSize*2, circleRadiusSize*5+circleRadiusSize, circleRadiusSize*4);

        drawNodeCircle("1", circleRadiusSize*1, circleRadiusSize*4);
        drawNodeCircle("2", circleRadiusSize*5, circleRadiusSize*4);

        drawLine(circleRadiusSize*1+circleRadiusSize, circleRadiusSize*4+circleRadiusSize*2, 0.0f+circleRadiusSize, circleRadiusSize*8);
        drawLine(circleRadiusSize*1+circleRadiusSize, circleRadiusSize*4+circleRadiusSize*2, circleRadiusSize*2+circleRadiusSize, circleRadiusSize*8);

        drawLine(circleRadiusSize*5+circleRadiusSize, circleRadiusSize*4+circleRadiusSize*2, circleRadiusSize*4+circleRadiusSize, circleRadiusSize*8);
        drawLine(circleRadiusSize*5+circleRadiusSize, circleRadiusSize*4+circleRadiusSize*2, circleRadiusSize*6+circleRadiusSize, circleRadiusSize*8);


        drawNodeCircle("1", 0.0f, circleRadiusSize*8);
        drawNodeCircle("2", circleRadiusSize*2, circleRadiusSize*8);
        drawNodeCircle("3", circleRadiusSize*4, circleRadiusSize*8);
        drawNodeCircle("4", circleRadiusSize*6, circleRadiusSize*8);

    }

    private void printHardcoded8Avl(){

        //         Bien 8
        drawNodeCircle("1", circleRadiusSize*7, 0.0f);

        drawLine(circleRadiusSize*7+circleRadiusSize, 0.0f+circleRadiusSize*2, circleRadiusSize*3+circleRadiusSize, 0.0f+circleRadiusSize*4);
        drawLine(circleRadiusSize*7+circleRadiusSize, 0.0f+circleRadiusSize*2, circleRadiusSize*11+circleRadiusSize, 0.0f+circleRadiusSize*4);

        drawNodeCircle("1", circleRadiusSize*3, circleRadiusSize*4);
        drawNodeCircle("2", circleRadiusSize*11, circleRadiusSize*4);

        drawLine(circleRadiusSize*3+circleRadiusSize, circleRadiusSize*4+circleRadiusSize*2, circleRadiusSize*1+circleRadiusSize, circleRadiusSize*8);
        drawLine(circleRadiusSize*3+circleRadiusSize, circleRadiusSize*4+circleRadiusSize*2, circleRadiusSize*5+circleRadiusSize, circleRadiusSize*8);

        drawLine(circleRadiusSize*11+circleRadiusSize, circleRadiusSize*4+circleRadiusSize*2, circleRadiusSize*9+circleRadiusSize, circleRadiusSize*8);
        drawLine(circleRadiusSize*11+circleRadiusSize, circleRadiusSize*4+circleRadiusSize*2, circleRadiusSize*13+circleRadiusSize, circleRadiusSize*8);


        drawNodeCircle("1", circleRadiusSize*1, circleRadiusSize*8);
        drawNodeCircle("2", circleRadiusSize*5, circleRadiusSize*8);
        drawNodeCircle("3", circleRadiusSize*9, circleRadiusSize*8);
        drawNodeCircle("4", circleRadiusSize*13, circleRadiusSize*8);

        drawLine(circleRadiusSize*1+circleRadiusSize, circleRadiusSize*8+circleRadiusSize*2, circleRadiusSize*0.0f+circleRadiusSize, circleRadiusSize*12);
        drawLine(circleRadiusSize*1+circleRadiusSize, circleRadiusSize*8+circleRadiusSize*2, circleRadiusSize*2+circleRadiusSize, circleRadiusSize*12);

        drawLine(circleRadiusSize*5+circleRadiusSize, circleRadiusSize*8+circleRadiusSize*2, circleRadiusSize*4+circleRadiusSize, circleRadiusSize*12);
        drawLine(circleRadiusSize*5+circleRadiusSize, circleRadiusSize*8+circleRadiusSize*2, circleRadiusSize*6+circleRadiusSize, circleRadiusSize*12);

        drawLine(circleRadiusSize*9+circleRadiusSize, circleRadiusSize*8+circleRadiusSize*2, circleRadiusSize*8+circleRadiusSize, circleRadiusSize*12);
        drawLine(circleRadiusSize*9+circleRadiusSize, circleRadiusSize*8+circleRadiusSize*2, circleRadiusSize*10+circleRadiusSize, circleRadiusSize*12);

        drawLine(circleRadiusSize*13+circleRadiusSize, circleRadiusSize*8+circleRadiusSize*2, circleRadiusSize*12+circleRadiusSize, circleRadiusSize*12);
        drawLine(circleRadiusSize*13+circleRadiusSize, circleRadiusSize*8+circleRadiusSize*2, circleRadiusSize*14+circleRadiusSize, circleRadiusSize*12);


        drawNodeCircle("1", 0.0f, circleRadiusSize*12);
        drawNodeCircle("2", circleRadiusSize*2, circleRadiusSize*12);
        drawNodeCircle("3", circleRadiusSize*4, circleRadiusSize*12);
        drawNodeCircle("4", circleRadiusSize*6, circleRadiusSize*12);
        drawNodeCircle("1", circleRadiusSize*8, circleRadiusSize*12);
        drawNodeCircle("2", circleRadiusSize*10, circleRadiusSize*12);
        drawNodeCircle("3", circleRadiusSize*12, circleRadiusSize*12);
        drawNodeCircle("4", circleRadiusSize*14, circleRadiusSize*12);

    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(null, message);
    }


}
