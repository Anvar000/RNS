package RNSVer2;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Anvar on 17.01.2018.
 */
public class RNS {
    static InitConst consts;
    static BigInteger[] arr_modulos;
    int amount_of_modulos, maximalbit;

    RNS(BigInteger[] _arr_modulos){
        arr_modulos = _arr_modulos;
        consts = new InitConst(_arr_modulos);
    }
    RNS(int n, int k){
        this.amount_of_modulos = n;
        this.maximalbit = k;
        consts = new InitConst(amount_of_modulos, maximalbit);
        arr_modulos = consts.getArr_modulos();
    }

    public  BigInteger[] convert_fromDEC_toRNS(BigInteger x)// конвертатция числа Х по модулям
    {
        BigInteger[] arr = new BigInteger[arr_modulos.length];
        for (int i = 0; i < arr_modulos.length; i++) {
            arr[i]  = x.mod(arr_modulos[i]);
        }
        return arr;
    }
    public  BigInteger  convert_fromRNS_toDEC(BigInteger[] X_inRNS)//конвертатция число Х от СОКа в ПСС
    {
        BigInteger M = consts.getM();
        BigInteger[] B = consts.getBasic();
        BigInteger X = BigInteger.valueOf(0);
        for (int i = 0; i < X_inRNS.length; i++) {
            X = X.add(X_inRNS[i].multiply(B[i]));
        }
        return X.mod(M);
    }
    public  BigInteger[] convert_toMRS(BigInteger[] X_inRNS)// заполнение массива ОПСС
    {
        BigInteger[] X_inRNS2 = X_inRNS.clone();
        BigInteger A [] = new BigInteger[X_inRNS2.length];
        //System.out.println("Masssive Tou ");
        BigInteger[][] tou = consts.getTou();
        for (int i = 0; i < A.length; i++) {
            A[i] = X_inRNS2[i];
            for (int j = i; j < A.length; j++){
                X_inRNS2[j] = X_inRNS2[j].subtract(A[i]);
            }
            for (int j = i; j < A.length; j++) {
                if(X_inRNS2[j].compareTo(BigInteger.valueOf(0))==-1) X_inRNS2[j] = arr_modulos[j].add(X_inRNS2[j]);
                if(X_inRNS2[j].compareTo(BigInteger.valueOf(0))==1)
                    X_inRNS2[j] = (X_inRNS2[j].multiply(tou[i][j])).mod(arr_modulos[j]);
            }
            //print(tou[i]);

        }
        return A;
    }
    public BigInteger convert_fromMRS_toDEC(BigInteger[] X_inMRS)//восставновление числа Х с испл массива ОПСС и массива умножений модулей
    {
        BigInteger[] multiplication_arr_of_modulos = consts.getMultiplication_arr_of_modulos();
        BigInteger number = BigInteger.valueOf(0);
        for (int i = 0; i < arr_modulos.length; i++) {
            number = number.add(multiplication_arr_of_modulos[i].multiply(X_inMRS[X_inMRS.length-i-1]));
        }
        return number;
    }

    public  BigInteger[] preparation_forthirdmethod(BigInteger[] X_inRNS)
    {
        BigInteger A[] = new BigInteger[arr_modulos.length];
        BigInteger tmp = BigInteger.valueOf(0);
        BigInteger[][] mass_of_basic = consts.getMass_of_basic();
        BigInteger[][] mass_mult = new BigInteger[mass_of_basic.length][mass_of_basic.length];
        for(int i = 0; i < X_inRNS.length; i++) {
            for (int j = 0; j < X_inRNS.length; j++) {
                mass_mult[i][j] = mass_of_basic[i][j].multiply(X_inRNS[i]);
            }
        }
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                tmp = tmp.add(mass_mult[j][i]);
            }
            A[i] = tmp.mod(arr_modulos[i]);
            tmp = tmp.divide(arr_modulos[i]);
            /*BigInteger dividremainder[] = tmp.divideAndRemainder(arr_modulos[i]);
            A[i] = dividremainder[1];
            tmp = dividremainder[0];*/
        }
        return A;
    }
    public BigInteger fourthmethod(BigInteger[] X_inRNS)
    {
        int N = consts.getN();
        BigInteger[] mas_K = consts.getmas_K();
        BigInteger a = consts.get2Nm1();
        BigInteger M = consts.getM();
        BigInteger inertX = BigInteger.valueOf(0);
        for (int i = 0; i < X_inRNS.length; i++) {
            inertX = inertX.add(X_inRNS[i].multiply(mas_K[i]));
        }
        inertX = inertX.and(a);
        return (inertX.multiply(M).shiftRight(N));
    }
    public void print(BigInteger[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
    }
    public BigInteger getM(){
        return consts.getM();
    }
    public BigInteger[] getArr_modulos(){return arr_modulos;}


}
