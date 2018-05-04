package avlmodels;

import java.util.ArrayList;
import java.util.List;

public class Avl<T extends Comparable<T>> {

    private Nodo<T> raiz;


    public Nodo<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo<T> raiz) {
        this.raiz = raiz;
    }

    public void insertaElemento(T elemento){
        raiz= insertaRecursiva(elemento,raiz);
    }

    public void eliminarElemento(T elemento){
        raiz= eliminarElementoRec(elemento,raiz);
    }

    private Nodo<T> eliminarElementoRec(T elemento, Nodo<T> raiz){

        // TODO Optimizar el método :)

        if(raiz==null) {
            return null;
        }if(elemento.compareTo(raiz.getElemento())<0) {
            raiz.setIzquierdo(eliminarElementoRec( elemento, raiz.getIzquierdo()));
        }else if (elemento.compareTo(raiz.getElemento())>0) {
            raiz.setDerecho(eliminarElementoRec( elemento, raiz.getDerecho()));
        }else {
            if(raiz.getIzquierdo()==null) {
                return raiz.getDerecho();
            }else if (raiz.getDerecho()==null) {
                return raiz.getIzquierdo();
            }
            raiz.setElemento(valMin(raiz.getDerecho()));
            raiz.setDerecho(eliminarElementoRec(raiz.getElemento(), raiz.getDerecho()));
        }

        return raiz;

    }

    public T valMin(Nodo<T> root) {
        T min = root.getElemento();
        while(root.getIzquierdo()!=null) {
            root=root.getIzquierdo();
            min=root.getElemento();
        }
        return min;
    }

    private Nodo<T> insertaRecursiva(T elemento, Nodo<T> raiz){
        if(raiz==null) {
            raiz= new Nodo<T>(elemento);
        }
        else if(elemento.compareTo(raiz.getElemento())<0) {
            raiz.setIzquierdo(insertaRecursiva(elemento, raiz.getIzquierdo()));
            if(altura(raiz.getIzquierdo())-altura(raiz.getDerecho())==2) {
                if(elemento.compareTo(raiz.getIzquierdo().getElemento())<0) {
                    raiz= rotarALaDerechaSimple(raiz);
                }else {
                    raiz= rotacionDobleALaDerecha(raiz);
                }
            }
        }
        else if(elemento.compareTo(raiz.getElemento())>0) {
            raiz.setDerecho(insertaRecursiva(elemento, raiz.getDerecho()));
            if(altura(raiz.getIzquierdo())-altura(raiz.getDerecho())==-2) {
                if(elemento.compareTo(raiz.getDerecho().getElemento())>0) {
                    raiz= rotarALaIzquierdaSimple(raiz);
                }else {
                    raiz= rotacionDobleALaIzquierda(raiz);
                }
            }
        }
        int altura= max(altura(raiz.getIzquierdo()),altura(raiz.getDerecho()));
        raiz.setAltura(altura+1);
        return raiz;
    }

    private Nodo<T> rotarALaIzquierdaSimple(Nodo<T> raiz) {
        System.out.println("Roto simple a la izquierda");
        Nodo<T>  temp = raiz.getDerecho();
        raiz.setDerecho( temp.getIzquierdo());
        temp.setIzquierdo(raiz);
        raiz.setAltura(max( altura( raiz.getIzquierdo() ), altura( raiz.getDerecho() ) ) + 1);
        temp.setAltura(max( altura( temp.getDerecho() ), raiz.getAltura() ) + 1);
        return temp;
    }

    private Nodo<T> rotarALaDerechaSimple(Nodo<T> raiz) {
        System.out.println("Roto simple a la derecha");

        Nodo<T> temp = raiz.getIzquierdo();
        raiz.setIzquierdo(temp.getDerecho());
        temp.setDerecho(raiz);
        raiz.setAltura(max( altura( raiz.getIzquierdo() ), altura( raiz.getDerecho() ) ) + 1);
        temp.setAltura(max( altura( temp.getIzquierdo() ), raiz.getAltura() ) + 1);
        return temp;
    }

    private Nodo<T> rotacionDobleALaDerecha(Nodo<T> raiz) {
        System.out.println("Roto doble a la derecha");

        raiz.setIzquierdo(rotarALaIzquierdaSimple( raiz.getIzquierdo() ));
        return rotarALaDerechaSimple( raiz );
    }

    private Nodo<T> rotacionDobleALaIzquierda(Nodo<T> raiz) {
        System.out.println("Roto doble a la izquierda");

        raiz.setDerecho(rotarALaDerechaSimple( raiz.getDerecho() ));
        return rotarALaIzquierdaSimple( raiz );
    }

    private int max(int a,int b) {
        if(a>b) {
            return a;
        }else {
            return b;
        }
    }

    private int altura(Nodo<T> nodo) {
        if(nodo==null) {
            return -1;
        }else {
            return nodo.getAltura();
        }
    }

}

