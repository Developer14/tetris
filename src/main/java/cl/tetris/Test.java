package cl.tetris;

import java.util.Arrays;
import java.util.Scanner;

public class Test {
static Scanner leer;

public static void main(String[] args) {
    leer = new Scanner(System.in);
    
    int uno[]={18,20,32,24,67,12,7,5,11,10};
    int dos[]={22,56,21,89,77,45,16,14,13,1};
    int tres[]=new int[10];
    int opc;
    int x,num,aux;
    boolean poblado1 = false;
    boolean poblado2 = false;
    boolean existe = false;
    
    do{
        System.out.println("___________________________");
        System.out.println("___________Menú____________");
        System.out.println("1.- Poblar arreglo uno.");
        System.out.println("2.- Poblar arreglo dos.");
        System.out.println("3.- Intercambiar arreglos.");
        System.out.println("4.- Sumar cruzada.");
        System.out.println("5.- Salir.");
        
        System.out.println("Escoja una opción para iniciar");
        opc=leer.nextInt();
        
        switch(opc){
           
            case 1:
                        System.out.println("Poblaremos el arreglo uno");
                        x=0;
                        poblado1 = true;
                        while(x < 10){
                            System.out.println("Ingrese número:");
                            num = leer.nextInt();
                          
                            existe = false;
                    
                            for(int i=0;i<10;++i){
                                if(num == uno[i]){
                                    existe = true; 
                                }
                            }
                            if(!existe){
                                uno[x] = num;
                                ++x;
                               
                            }else{
                                System.out.println("Número ya fue ingresado reintente!");
                            }
                            
                        }
                     System.out.println(Arrays.toString(uno));
                       
                
                break;
            case 2:
                        System.out.println("Poblaremos el arreglo uno");
                        x=0;
                        poblado2 = true;
                        while(x < 10){
                            System.out.println("Ingrese número:");
                            num = leer.nextInt();
                            existe = false;
                    
                            for(int i=0;i<10;++i){
                                if(num ==dos[i]){
                                    existe = true; 
                                }
                            }
                            if(!existe){
                                dos[x] = num;
                                ++x;
                            }else{
                                System.out.println("Número ya fue ingresado reintente!");
                            }
                        }
              
                break;
            
            
            case 3:
                
               if(poblado1 && poblado2){
                    System.out.println("Intercambiamos los arreglos");
                        for(int i=0;i<10;++i){
                            aux = uno[i];
                            uno[i] = dos[i];
                            dos[i] = aux;
                        }
                        System.out.println(Arrays.toString(uno));
                        System.out.println(Arrays.toString(dos));
                
                
                }
                    System.out.println("Los arreglos no han sido poblados");
                
                break;
                
            case 4:
               
                    System.out.println("Suma cruzada");
                        for(x = 0; x < 10; ++x){
                            tres[x] = uno[x] + dos[9 -x];
                        }
                        System.out.println(Arrays.toString(uno));                    
                
                break;
            default:
                  if(opc<1 || opc>5){
                    System.out.println("Opción inválida! reintente[1-5]");
                  }    
            
        }
             
    }while(opc!=5);
  
}

}