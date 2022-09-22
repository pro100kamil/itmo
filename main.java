import java.lang.Math;

public class main {
    public static void main(String[] args) {
        //1
        int n = 10;
        int[] a = new int[n];
        for (int i = 19, j = 0; i >= 1; i-=2, j++){
            a[j] = i;
        }
        //2
        double[] x = new double[18];
        for (int i = 0; i < x.length; i++){
            x[i] = Math.random() * 16 - 8;
        }
        //3
        double[][] a2 = new double[10][18];
        for (int i = 0; i < a2.length; i++) { 
            for (int j = 0; j < a2[i].length; j++) { 
                double X = x[j];
                if (a[i] == 9) {
                    a2[i][j] = Math.cos(Math.pow((Math.pow(0.25*(1-X), X)/(3.0/4+Math.log(Math.abs(X)))),Math.pow(1.0/4/(X+1),X)));
                }
                else if (a[i] == 1 || a[i] == 5 || a[i] == 7 || a[i] == 17 || a[i] == 19){
                    a2[i][j] = Math.log(Math.acos(X/16))/2;
                }
                else{
                    a2[i][j] = Math.pow(Math.E, Math.pow(((Math.sin(X)+4)/1)/(4/0.25), 2));
                }
            }0
        }
        //4
        for (int i = 0; i < a2.length; i++) { 
            for (int j = 0; j < a2[i].length; j++) { 
                System.out.printf("%.3f\t", a2[i][j]);
            }
            System.out.println();
        }
    }
}
//javac main.java 
//java main