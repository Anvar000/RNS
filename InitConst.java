package RNSVer2;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Anvar on 17.01.2018.
 */
    public class InitConst {
    private static int n ;
    private static BigInteger arr_modulos[];
    private static BigInteger B[];
    private static BigInteger M = BigInteger.valueOf(1);
    private static BigInteger mass_M[];
    private static BigInteger tou[][];
    private static BigInteger [][] mass_of_basic;
    private static BigInteger multiplication_arr_of_modulos [];
    private static int N;
    private static BigInteger mas_K[];
    private static BigInteger a;
    private static BigInteger am1;

    private static BigInteger[] ran;
    private static int maximalbit;


    public InitConst(BigInteger[] arr_modulos)
    {
        this.arr_modulos = arr_modulos;
        this.n = arr_modulos.length;
        M = setinterval();
        B = setBasic();
        tou = setMassTou();
        multiplication_arr_of_modulos = setMultiplication_arr_of_modulos();
        mass_of_basic = setMass_of_basic();
        N = setN();
        mas_K = setMasK();
    }
    public InitConst(int amount_of_modulos, int maximalbit){
        this.n = amount_of_modulos;
        this.maximalbit = maximalbit;
        this.arr_modulos = setrandmodulos();
        M = setinterval();
        B = setBasic();
        tou = setMassTou();
        multiplication_arr_of_modulos = setMultiplication_arr_of_modulos();
        mass_of_basic = setMass_of_basic();
        N = setN();
        mas_K = setMasK();
    }

    private  BigInteger[] setrandmodulos(){
        BigInteger mass_odds[] = new BigInteger[n];
        BigInteger genereted_modulos[] = new BigInteger[n];
        BigInteger a = new BigInteger("2").pow(maximalbit);
        //System.out.println(a);
        BigInteger oddnumber = BigInteger.ONE;
        for (int i = 0; i < genereted_modulos.length&&oddnumber.compareTo(a)==-1; i++) {
            genereted_modulos[i] = a.subtract(oddnumber);
            mass_odds[i] = oddnumber;
            for (int j = i-1; j >= 0 &&oddnumber.compareTo(a)==-1; j--) {
                if(genereted_modulos[i].gcd(genereted_modulos[j]).compareTo(BigInteger.valueOf(1))!=0){
                    genereted_modulos[j] = a.subtract(oddnumber);
                    mass_odds[j] = oddnumber;
                    oddnumber = oddnumber.add(BigInteger.valueOf(2));
                    j++;
                }
            }
            oddnumber = oddnumber.add(BigInteger.valueOf(2));
        }

       // print(mass_odds);
        return genereted_modulos;
    }


    private BigInteger setinterval()// вычисление диапазона допустимых значений
    {
        for (int i = 0; i < n; i++) {
            M = M.multiply(arr_modulos[i]);
        }
        return M;
    }

    /*public  BigInteger inverse(BigInteger x, BigInteger m) {
        BigInteger[] mas = gcdExtended(x, m, BigInteger.ZERO, BigInteger.ZERO);
        if(mas[0].compareTo(BigInteger.valueOf(1))!=0){
            return BigInteger.valueOf(-1);
        }
        else
        {
            x = ((x.mod(m)).add(m)).mod(m);
            return x;
        }
    }

    private BigInteger[] gcdExtended(BigInteger a, BigInteger b, BigInteger x, BigInteger y)
    {
        BigInteger mas[] = new BigInteger[3];
        // Base Case
        if (a.compareTo(BigInteger.ZERO)==0)
        {
            mas = new BigInteger[]{b, BigInteger.ZERO, BigInteger.ONE};
            return mas;
        }

        //BigInteger x1 = new BigInteger("1"), y1 = new BigInteger("1"); // To store results of recursive call

        //System.out.println("a" + a.toString());
        BigInteger [] r = gcdExtended(b.remainder(a), a, x, y);
        BigInteger gcd = r[0];
        BigInteger x1 = r[1];
        BigInteger y1 = r[2];


        x = y1.subtract((b.divide(a)).multiply(x1));
        y = x1;
        mas[1] = x;
        mas[2] = y;
        mas[0] = gcd;
        return mas;
    }*/
    private BigInteger[] setBasic()// создание базиса
    {
        BigInteger x = new BigInteger("0");
        BigInteger y = new BigInteger("1");
        BigInteger g[];
        mass_M = new BigInteger [n];
        B = new BigInteger [n];
        for (int i = 0; i < n; i++) {
            /*BigInteger j = BigInteger.valueOf(1);
            mass_M[i] = M.divide(arr_modulos[i]);
            g = gcdExtended(mass_M[i],arr_modulos[i],x,y);
            B[i] = inverse(g[1],arr_modulos[i]);
            if(B[i].compareTo(BigInteger.valueOf(-1))==0){
                B[i] = B[i].add(arr_modulos[i]);
                //System.out.println("You can not find out multip_inversion for Basic"); System.exit(0);
            }
            B[i] = B[i].multiply(mass_M[i]);*/
            mass_M[i] = M.divide(arr_modulos[i]);
            B[i] = mass_M[i].modInverse(arr_modulos[i]).multiply(mass_M[i]);

        }

        return B;
    }
    private BigInteger[][] setMassTou()//заполнение массива с значениями тоу
    {
        tou = new BigInteger[n][n];
        for (int i = 0; i < tou.length-1; i++) {
            for (int j = 0; j < tou.length; j++) {
                if(j<=i) tou[i][j] = BigInteger.valueOf(0);
                if(j>i)  {
                    tou[i][j] = numbertou(arr_modulos[i], arr_modulos[j]);}
            }
            //print(tou[i]);
            //System.out.println();
        }
        return tou;
    }
    private BigInteger numbertou(BigInteger a, BigInteger b)//вычисление значений тоу
    {
        BigInteger k = a.modInverse(b);
        BigInteger x = new BigInteger("0");
        BigInteger y = new BigInteger("1");
        //BigInteger g[];
        //while((a.multiply(k)).mod(b).compareTo(BigInteger.valueOf(1))!=0){k = k.add(BigInteger.valueOf(1));}
        // g = gcdExtended(a,b,x,y);
        //System.out.println(b);
        //k = inverse(g[1],b);
        //System.out.println(k);
        //if(k.compareTo(BigInteger.valueOf(-1))==0){
          // System.out.println("You can not find out multip_inversion for number_tou"); System.exit(0);}

        return k;
    }
    private BigInteger[] setMultiplication_arr_of_modulos()//заполнение массива умножений модулей
    {
        multiplication_arr_of_modulos = new BigInteger[n];
        for (int i = 0; i < n; i++) {
            multiplication_arr_of_modulos[i] = BigInteger.valueOf(1);
            for (int j = 0; j < n - (i + 1); j++) {
                multiplication_arr_of_modulos[i] = multiplication_arr_of_modulos[i].multiply(arr_modulos[j]);
            }
        }
        return multiplication_arr_of_modulos;
    }
    private BigInteger[][] setMass_of_basic()
    {
        mass_of_basic = new BigInteger[n][n];
        for (int i = 0; i < n; i++) {
            mass_of_basic[i] = coversion_toMRS(B[i]);
        }
        return mass_of_basic;
    }
    private BigInteger[] coversion_toMRS(BigInteger b){
        BigInteger[] arr = new BigInteger[n];
        for (int i = 0; i < n; i++) {
            arr[i]  = b.mod(arr_modulos[i]);
        }
        BigInteger A [] = new BigInteger[n];
        for (int i = 0; i < A.length; i++) {
            A[i] = arr[i];
            for (int j = i; j < A.length; j++){
                arr[j] = arr[j].subtract(A[i]);
            }
            for (int j = i; j < A.length; j++) {
                if(arr[j].compareTo(BigInteger.valueOf(0))==-1) arr[j] = arr_modulos[j].add(arr[j]);
                if(arr[j].compareTo(BigInteger.valueOf(0))==1)
                    arr[j] = (arr[j].multiply(tou[i][j])).mod(arr_modulos[j]);
            }
        }
        return A;
    }

    private int setN()
    {
        BigInteger N = BigInteger.valueOf(0);
        BigInteger tmpM;
        BigInteger tmp = BigInteger.valueOf(0);
        for (int i = 0; i < n; i++) {
            tmp = tmp.add(arr_modulos[i]);
        }
        tmp = tmp.subtract(BigInteger.valueOf(n));
        tmpM = M.multiply(tmp);
        /*tmp = BigInteger.valueOf(1);
        while(tmpM.compareTo(tmp)==1){
            tmp = tmp.multiply(BigInteger.valueOf(2));
            N = N.add(BigInteger.valueOf(1));
        }*/
        return tmpM.bitLength();
    }

    private BigInteger[] setMasK()
    {
        a = BigInteger.valueOf(2).pow(N);
        am1 = a.subtract(BigInteger.valueOf(1));
        mas_K = new BigInteger[n];
        for (int i = 0; i < B.length; i++) {
            mas_K[i] = B[i].multiply(a);
            mas_K[i] = (mas_K[i].divide(M)).add(BigInteger.valueOf(1));
        }

        return mas_K;
    }

    // getters
    public BigInteger[] getModulus() {
        return arr_modulos;
    }

    public BigInteger[] getBasic() {
        return B;
    }

    public BigInteger getM() {
        return M;
    }

    public BigInteger[][] getTou(){
        return tou;
    }

    public BigInteger[] getMultiplication_arr_of_modulos(){
        return multiplication_arr_of_modulos;
    }

    public BigInteger[][]getMass_of_basic(){
        return mass_of_basic;
    }

    public int getN()
    {
        return N;
    }

    public BigInteger[] getmas_K() {
        return mas_K;
    }

    public BigInteger get2N(){
        return a;
    }
    public BigInteger get2Nm1(){
        return am1;
    }
    public BigInteger[] getArr_modulos(){
        return arr_modulos;
    }

    public void print(BigInteger[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
    }
}
