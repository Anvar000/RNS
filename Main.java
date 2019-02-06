package RNSVer2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Anvar on 17.01.2018.
 */
public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main (String [] args){

        int itar_number = 100000;
        BigInteger tmp = BigInteger.ZERO;
        double tm1 = 0,tm2 = 0,tm3 = 0,tm4=  0;
        RNS rns;
        int n,k;
        BigInteger[] arr_modulos;
        int choice = sc.nextInt();
        if(choice==1){
            System.out.println("enter the number of modulos");
            n = sc.nextInt();
            System.out.println("enter the number of maximalbit");
            k = sc.nextInt();
            rns = new RNS(n,k);
            arr_modulos = rns.getArr_modulos();
        }
        else {
            arr_modulos = setmodulos();
            rns = new RNS(arr_modulos);
        }
        print(arr_modulos);
        System.out.print("The number X = ");
        BigInteger x = (new BigDecimal(Math.random()).multiply(new BigDecimal(rns.getM()))).toBigInteger();
        //BigInteger x = (new BigDecimal(Math.random()).multiply(new BigDecimal("1000000000"))).toBigInteger();
        //BigInteger x = new BigInteger("12345");
        System.out.println(x);
        BigInteger[] X_inRNS = rns.convert_fromDEC_toRNS(x);
        print(X_inRNS);

        for (int q = 0; q < 10; q++) {
            System.out.println("--------------------------------------------");


            ////////////////////////////////////////////////////////////////////
            long start = System.currentTimeMillis();
            for (int i = 0; i < itar_number; i++) {
                tmp = rns.convert_fromRNS_toDEC(X_inRNS);
            }
            long finish = System.currentTimeMillis();
            tm1+=(finish - start);
            System.out.println("Consumed time for converting from RNS to DEC = " + (finish - start) + " number = " + tmp);
            //////////////////////////////////////////////////////////////////////

            ////////////////////////////////////////////////////////////////////
            start = System.currentTimeMillis();
            for (int i = 0; i < itar_number; i++) {
                tmp = rns.convert_fromMRS_toDEC(rns.convert_toMRS(X_inRNS));
            }
            finish = System.currentTimeMillis();
            tm2+=(finish - start);
            System.out.println("Consumed time for converting algorithm Gorner = " + (finish - start) + " number = " + tmp);
            //////////////////////////////////////////////////////////////////////
            X_inRNS = rns.convert_fromDEC_toRNS(x);
            //print(X_inRNS);

            ////////////////////////////////////////////////////////////////////
            start = System.currentTimeMillis();
            for (int i = 0; i < itar_number; i++) {
                //print(rns.preparation_forthirdmethod(X_inRNS));
                tmp = rns.convert_fromMRS_toDEC(rns.preparation_forthirdmethod(X_inRNS));
            }
            finish = System.currentTimeMillis();
            tm3+=(finish - start);
            System.out.println("Consumed time for converting with algorithm table = " + (finish - start) + " number = " + tmp);
            //////////////////////////////////////////////////////////////////////

            BigInteger[] A = rns.preparation_forthirdmethod(X_inRNS);
            ////////////////////////////////////////////////////////////////////
            start = System.currentTimeMillis();
            for (int i = 0; i < itar_number; i++) {
                tmp = rns.fourthmethod(X_inRNS);
            }
            finish = System.currentTimeMillis();
            tm4+=(finish - start);
            System.out.println("Consumed time for converting with method approximated computations = " + (finish - start) + " number = " + tmp);
            //////////////////////////////////////////////////////////////////////
            //System.out.println(rns.fourthmethod(X_inRNS));
        }
        System.out.println(tm1/10+" "+tm2/10+" "+tm3/10+" "+tm4/10 );

    }
    public static BigInteger[] setmodulos()
    {
        int n;
        System.out.println("Введите количество модулей");
        BigInteger[] arr_modulos;
        n = sc.nextInt();
        arr_modulos =  new BigInteger[n];
        System.out.println("Введите модули");
        for (int i = 0; i < arr_modulos.length; i++) {
            arr_modulos[i] = sc.nextBigInteger();
        }
        return arr_modulos;
    }
    public static void print(BigInteger[] mas)
    {
        for (int i = 0; i < mas.length; i++) {
            System.out.print(mas[i]+" ");
        }
        System.out.println();
    }
}
