/**
 * @Project Title: Automated Test data generation Modified Version of Genetic
 * Algorithm (White box testing- path coverage)
 *
 * @Submitted By: Anuj Kumar (2K13/SE/016) Atul Kumar (2K13/SE/023) Beshambher
 * Chaukhwan (2K13/SE/024) Gaurav Rai (2K13/SE/031)
 *
 * @Inputs: Source files(C/C++).
 *
 * @Outputs: Control Flow Graph, Independent Paths, Test Data.
 *
 */
package aco;

import java.io.*;
import static java.lang.Math.pow;
import java.util.*;
import javax.swing.*;

/**
 * Main class: This class is the controller class of the project
 */
public class Lexer {

    /**
     * Test cases class: The objects of this class holds the generated test data
     * for each independent path in CFG.
     */
    public class T {

        public int input[] = new int[150];
        public int j;

        T() {
            j = 0;
            for (int i = 0; i < 150; i++) {
                input[i] = 0;
            }
        }
    }

    /**
     * Class B: The Ith object of this class store the information about the
     * next odes from Ith node in CFG. 'n' will store the number of node.
     */
    public class B {

        int n, f;

        B() {
            f = 0;
        }
    }

    /**
     * Class A: The objects of this class have the information about the next
     * nodes from current node. 'k' will store the total number of trailing
     * nodes from the current node. b[1..k] will store the nodes information.
     */
    public class A {

        int k;
        B b[] = new B[20];

        A() {
            k = 0;
            for (int i = 0; i < 20; i++) {
                b[i] = new B();
            }
        }
    }

    /**
     * Class C: The objects of this class store the information about the
     * decision nodes which is used to identify which type of decision the node
     * is taking on which set of variables.
     */
    public class C {

        int v[] = new int[100], val, k;
        String s;

        C() {
            val = 0;
            k = 0;
            s = "*";
            for (int i = 0; i < 100; i++) {
                v[i] = 0;
            }
        }
    }

    /**
     * Global Declarations: M[][] = Adjacency Matrix of CFG path[][] =
     * Independent Path Matrix of CFG a[] = contains outgoing edges information.
     * i.e., Element a[i] have the number of outgoing edges from node 'i'.
     * func[] = contains the decision information. i.e., Element func[i] tell
     * whether node 'i' is a decision node or not, and if it is then it also
     * decide what operation is to be performed on which input variables.
     * input[]= set of input variables in source file. tc[j]= generated test
     * data for path 'j'.
     */
    C func[] = new C[100];
    int M[][] = new int[100][100], path[][] = new int[100][100];
    int a[] = new int[100], l = 0, n, b = 0, pri = 0, psz = 0, cc = 0;
    char input[] = new char[150];
    T tc[] = new T[50];
    int cnt = 0, fex = 0, N = 0;

    /**
     * Function get(): This function works as a constructor for the arrays of
     * objects.
     */
    public void get() {
        for (int i = 0; i < 100; i++) {
            func[i] = new C();
        }
        for (int i = 0; i < 50; i++) {
            tc[i] = new T();
        }
    }

    /**
     * FileWriter F: This is a file handling parameter that writes each activity
     * of the project (or software) into a text file "Testing.txt" which is
     * stored in "\Inputs" directory and this can be considered as a temp file
     * because it is refreshed each time an object of the main class is created.
     *
     * FileWriter FF: This is a file handling parameter that writes each
     * activity of the Test() function into a text file "Fitness.txt" which is
     * stored in "\Inputs" directory and this can be considered as a temp file
     * because it is refreshed each time an object of the main class is created.
     */
    public static FileWriter F, FF;

    /**
     * p[][] and e[]: The matrix p[][] store the set of chromosomes taken for
     * Genetic Algorithm and e[] array store the fitness value of the
     * corresponding chromosome. i.e., e[i] have the fitness value of p[i]
     * chromosome.
     */
    public int p[][], e[];

    /**
     * Function Genetic(): This is the constructor of main class. This
     * constructor creates a set of 4 chromosomes for the Genetic Algorithm and
     * opens the temp file "Testing.txt" and "Fitness.txt" for writing the data.
     *
     * @param a (Number of chromosomes to be generated)
     */
    public Lexer() {
        try {
            /*N=a;
             this.e = new int[N];
             this.p = new int[N][150];*/
            this.get();

            F = new FileWriter(System.getProperty("user.dir")+"\\Inputs/Testing.txt");
            FF = new FileWriter(System.getProperty("user.dir")+"\\Inputs/Fitness.txt");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Function skip(); This function is used to skip white spaces from a
     * string.
     *
     * @return (position of the first character which is not a white space)
     */
    int skip(String s, int k) {
        int j = 0;
        if (k > 0) {
            try {
                while ((s.charAt(j) == ' ' || s.charAt(j) == '\t') && (j < k)) {
                    j++;
                }
            } catch (Exception e) {
                System.err.println(" in skip " + e + k);
            }
        }
        return j;
    }

    /**
     * Function check():
     *
     * @param f
     * @param q This function scan the source code of selected file and it
     * search for the decision nodes. This function generate and store the
     * information of a node in the func[] array by reading the source code line
     * by line.
     */
    void check(BufferedReader f, int q) {
        String s = "";
        int i, c = 1, j, p = q, len;
        try {
            while (c == 1) {
                i = 1;
                s = f.readLine();
                len = s.length();
                i = skip(s, len);

                if (len > 0) {
                    if (s.charAt(i) == '}') {
                        c = 0;
                    }
                    if (s.charAt(i) == '{') {
                        c = 1;
                    }

                    j = 0;

                    if (fex == 0) {
                        if (len > 3) {
                            if (s.charAt(i) == 'c' && s.charAt(i + 1) == 'i' && s.charAt(i + 2) == 'n' && s.charAt(i + 3) == '>') {
                                j = s.length(); //System.out.println(s+j);               
                                for (i += 4; i < j; i++) {
                                    if ((s.charAt(i) > 96 && s.charAt(i) < 123) || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) {
                                        input[b++] = s.charAt(i);
                                    } //System.out.println(i+" "+j);
                                }
                                i = 0;
                            } //System.out.println("Here in cpp");
                        }
                    } else if (s.charAt(i) == 's') { //System.out.println("Here in c");
                        if (len > 5) {
                            if (s.charAt(i + 3) == 'n' && s.charAt(i + 1) == 'c' && s.charAt(i + 4) == 'f' && s.charAt(i + 5) == '(') {
                                j = s.length(); //System.out.println(s+j);               
                                for (i += 5; i < j; i++) {
                                    if ((s.charAt(i) == '&')) {
                                        input[b++] = s.charAt(i + 1); //System.out.println(b+" - "+input[b-1]);
                                    } //System.out.println(i+" "+j);
                                }
                                i = 0;
                            }
                        }
                    }

                    if (len > 6) {
                        if (s.charAt(i) == 's' && s.charAt(i + 5) == 'h' && s.charAt(i + 6) == '(') {
                            int r; //System.out.println(s);
                            l = 0;
                            n++;
                            M[p][n] = 1;
                            p = n;
                            r = s.charAt(i + 7) - 65;
                            func[n].val = 0;
                            func[n].s = s;
                            n++;
                            while (true) {
                                s = f.readLine();
                                i = skip(s, len);
                                if (s.charAt(i) == 'c') {
                                    a[l++] = n;
                                    i += 5;
                                    j = 0;
                                    func[n].v[1] = s.charAt(i + 1) - 65;
                                    while (s.charAt(i) != ':') {
                                        j = (j * 10) + (s.charAt(i) - 48);
                                        i++;
                                    }
                                    func[n].v[0] = j;
                                    func[n].v[2] = r;
                                    func[n].s = s;  //System.out.println("*** "+j);
                                    n++;
                                }
                                if (s.charAt(i) == '}') {
                                    break;
                                } else {
                                    func[n].s = s;
                                }
                            }
                            func[p].k = l;
                            for (i = 0; i < l; i++) {
                                j = a[i];
                                func[j].val = 624;
                                M[p][j] = 1;
                                func[n].v[func[n].k++] = j;
                                M[j][n + 1] = 1;
                            }
                            func[n].val = 1624;
                            M[p][n] = 1;
                            M[n][n + 1] = 1;
                            n++;
                            M[n][n + 1] = 1;
                            n++;
                            p = n;
                            l = 0;
                        }
                    }

                    if (len > 1) {
                        if (s.charAt(i) == 'd' && s.charAt(i + 1) == 'o') { // System.out.println("in do()");
                            n++;
                            M[p][n] = 1;
                            p = n;
                            func[n].val = 0;
                            func[n].s = s;
                            l = p;
                            //s=f.readLine();
                            check(f, p);
                            M[n][l] = 1;
                        }
                    }

                    if (len > 3) {
                        if (s.charAt(i) == 'f' && s.charAt(i + 2) == 'r' && s.charAt(i + 3) == '(') {
                            //System.out.println("in while()");
                            n++;
                            M[p][n] = 1;
                            p = n;
                            func[n].s = s;
                            func[n].val = 0;
                            l = p;
                            //s=f.readLine();
                            check(f, p);
                            M[n][l] = 1;
                        }
                    }

                    if (len > 5) {
                        if (s.charAt(i) == 'w' && s.charAt(i + 5) == '(') {
                            n++;
                            M[p][n] = 1;
                            l = n;
                            M[n][n + 1] = 1;
                            n++;
                            p = n;
                            func[n].val = 618;
                            func[n].s = s;
                            func[n].v[0] = s.charAt(i + 6) - 65;
                            func[n].val += s.charAt(i + 7);
                            if (s.charAt(i + 8) == '=') {
                                func[n].val += 61;
                                if (s.charAt(i + 9) > 96 && s.charAt(i + 9) < 123) {
                                    func[n].v[1] = s.charAt(i + 9);
                                } else {
                                    j = 0;
                                    while (s.charAt(i + 9) != ')') {
                                        j = (j * 10) + (s.charAt(i + 9) - '0');
                                        i++;
                                    }
                                    func[n].v[1] = j;
                                }
                            } else {
                                if (s.charAt(i + 8) > 96 && s.charAt(i + 8) < 123) {
                                    func[n].v[1] = s.charAt(i + 8);
                                } else {
                                    j = 0;
                                    while (s.charAt(i + 8) != ')') {
                                        j = (j * 10) + (s.charAt(i + 8) - '0');
                                        i++;
                                    }
                                    func[n].v[1] = j;
                                }
                            }
                            //s=f.readLine();//getline(f, s);
                            check(f, n);
                            M[n][l] = 1;
                            M[l][n + 1] = 1;
                            n++;
                            M[n][n + 1] = 1;
                            func[n].val = func[p].val + 1000;
                            func[n].v[0] = func[p].v[0];
                            func[n].v[1] = func[p].v[1];
                            func[n].s = s;
                            n++;
                        }
                    }

                    if (len > 2) {
                        if (s.charAt(i) == 'i' && s.charAt(i + 1) == 'f' && s.charAt(i + 2) == '(') {
                            int pv;
                            j = s.length();
                            n++;
                            M[p][n] = 1;
                            p = n;
                            func[n].val = 0;
                            func[n].s = "\t\tif-else";
                            n++;
                            M[p][n] = 1;
                            pv = n;

                            func[n].s = s;
                            func[n].val = 288;
                            if ((j - i) < 11) {
                                func[n].v[0] = s.charAt(i + 3) - 65; //System.out.println("here "+s+" "+(char)s.charAt(i+3));
                                func[n].val += s.charAt(i + 4);
                                func[n].k = 2;
                                if (s.charAt(i + 5) == '=') {
                                    func[n].val += 61;
                                    if ((s.charAt(i + 6) > 96 && s.charAt(i + 6) < 123) || (s.charAt(i + 6) >= 'A' && s.charAt(i + 6) <= 'Z')) {
                                        func[n].v[1] = s.charAt(i + 6);
                                    } else {
                                        j = 0;
                                        while (s.charAt(i + 6) != ')') {
                                            j = (j * 10) + (s.charAt(i + 6) - '0');
                                            i++;
                                        }
                                        func[n].v[1] = j;
                                    }
                                } else {  //System.out.println("here"+s);
                                    if ((s.charAt(i + 5) > 96 && s.charAt(i + 5) < 123) || (s.charAt(i + 5) >= 'A' && s.charAt(i + 5) <= 'Z')) {
                                        func[n].v[1] = s.charAt(i + 5);
                                    } else {
                                        j = 0;
                                        while (s.charAt(i + 5) != ')') {
                                            j = (j * 10) + (s.charAt(i + 5) - '0');
                                            i++;
                                        }
                                        func[n].v[1] = j;
                                    }
                                }
                                //s=f.readLine();//getline(f, s);
                            } else {
                                func[n].val = 247; //System.out.println("here j>=11 "+s+" "+(char)s.charAt(i+3));
                                i += 3;
                                while (i < j) {
                                    if ((s.charAt(i) > 96 && s.charAt(i) < 123) || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) {
                                        func[n].v[func[n].k++] = s.charAt(i);
                                    } else {
                                        func[n].val += s.charAt(i);
                                    }
                                    i++;
                                }
                                j = 0;
                            }
                            check(f, n);
                            s = f.readLine();
                            len = s.length();
                            i = skip(s, len); //System.out.println(s);

                            if (len > 3) {
                                if (s.charAt(i) == 'e' && s.charAt(i + 1) == 'l' && s.charAt(i + 2) == 's' && s.charAt(i + 3) == 'e') {
                                    n++;
                                    M[p][n] = 1;
                                    func[n].val = func[pv].val + 1000;
                                    func[n].k = func[pv].k;
                                    for (int y = 0; y < func[pv].k; y++) {
                                        func[n].v[y] = func[pv].v[y];
                                    }

                                    func[n].s = s;
                                    pv = n - 1;
                                    //s=f.readLine();//getline(f, s);
                                    check(f, n);
                                    M[n][n + 1] = 1;
                                    n++;
                                    M[pv][n] = 1;
                                }
                            }
                        }
                    } //if-else

                }
            }
        } catch (Exception e) {
            System.out.println("in check " + e);
        }
    }

    /**
     * Function getComplexity() It is used to calculate the cyclomatic
     * complexity of CFG by using the formula: CC = E - N + 2P
     */
    int getComplexity() {
        int i, j, e = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                e += M[i][j];
            }
        }
        cc = e - n + 2;
        return cc;
    }

    /**
     * Function getPaths(): It is used to identify independent paths in the CFG
     * and it store them in path[][] matrix.
     */
    void getPaths() {
        int i, j, ff = 1, e = 0, kk = 0, ll = 0, t = 0;

        A a[] = new A[n];
        for (i = 0; i < n; i++) {
            a[i] = new A();
        }
        B s[] = new B[n];
        for (i = 0; i < n; i++) {
            s[i] = new B();
        }
        try {
            for (i = 0; i < n; i++) {
                ll = 0;
                for (j = 0; j < n; j++) {
                    ff = M[i][j];
                    a[i].k += ff;
                    if (ff > 0) {
                        a[i].b[ll].n = j;
                        ll++;
                    }
                    e += ff;
                }
            }

            ff = 0;
            t = 0;
            for (i = 0; i < n; i++) {
                if (a[i].k > 1) {
                    for (j = 0; j < a[i].k; j++) {
                        a[i].b[j].f = 2;
                    }
                    s[t].n = i;
                    s[t++].f = a[i].k;
                }
            }

            System.out.println("Cyclomatic Complexity: E-N+2P = " + cc);
            F.write("Cyclomatic Complexity: E-N+2P = " + cc + "\n\n");
            F.flush();

            kk = t = 0;
            while (s[t].f > 0) {
                ll = 2;
                path[kk][1] = 0;
                for (i = 0; i < n; i++) {
                    if (a[i].k > 1 && s[t].f > 0 && s[t].n == i) {
                        for (j = 0; j < a[i].k; j++) {
                            if (a[i].b[j].f == 2) {
                                path[kk][ll] = a[i].b[j].n;
                                a[i].b[j].f--;
                                s[t].f--;
                                ll++;
                                if (s[t].f == 0) {
                                    t++;
                                }
                                break;
                            }
                        }
                    } else if (a[i].b[0].n != path[kk][ll - 1]) {
                        path[kk][ll] = a[i].b[0].n;
                        ll++;
                    }
                }
                path[kk][0] = ll - 1;
                kk++;

            }   //end of while
            psz = kk;

        } catch (Exception E) {
            System.out.println("in getpaths " + E);
        }
    }

    /**
     * Function func1 - func9 These functions are used to validate the generated
     * test data.
     *
     * @param c (an object of func[] array)
     * @param x (the set of input variables to validate)
     * @return {0, 1}
     */
    int func1(C c, int x[]) {
        int a = c.v[0], b = c.v[1]; //System.out.print(" \t here "+(char)(a+65)+" "+(char)b+" \t");
        if ((b > 96 && b < 123) || (b >= 'A' && b <= 'Z')) {
            b -= 65;
            if (x[a] > x[b]) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (x[a] > b) {
                return 1;
            }
        }
        return 0;
    }

    int func2(C c, int x[]) {
        int a = c.v[0], b = c.v[1];
        if ((b > 96 && b < 123) || (b >= 'A' && b <= 'Z')) {
            b -= 65;
            if (x[a] < x[b]) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (x[a] < b) {
                return 1;
            }
        }
        return 0;
    }

    int func3(C c, int x[]) {
        int a = c.v[0], b = c.v[1];
        if ((b > 96 && b < 123) || (b >= 'A' && b <= 'Z')) {
            b -= 65;
            if (x[a] == x[b]) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (x[a] == b) {
                return 1;
            }
        }
        return 0;
    }

    int func4(C c, int x[]) {
        int a = c.v[0], b = c.v[1] + 65, d = c.v[2];
        if ((b > 96 && b < 123) || (b >= 'A' && b <= 'Z')) {
            pri = 1;
            if (x[d] == b) {
                return 1;
            } else {
                return 0;
            }
        } else {  //System.out.print("\ta= "+a+" n="+x[d]+'\t');
            if (x[d] == a) {
                return 1;
            } else {
                return 0;
            }
        }

    }

    int func5(C c, int x[]) {
        int i, j = c.k, g = 0, r;
        for (i = 0; i < j; i++) {
            r = c.v[i];
            if (func4(func[r], x) == 1) {
                g = 1;
            }
        }
        return g;
    }

    int func6(C c, int x[]) {
        int a = c.v[0] - 65, b = c.v[1] - 65, d = c.v[2] - 65;
        // System.out.println(a+"= "+x[a]+" "+b+"= "+x[b]+" "+d+"= "+x[d]);
        if ((x[a] + x[b]) > x[d]) {
            return 1;
        }
        return 0;
    }

    int func7(C c, int x[]) //All variables equal
    {
        int a, b, d, e = c.v[0] - 65;
        d = 0;
        for (b = 0; b < c.k; b++) {
            a = c.v[b] - 65;
            if (x[a] == x[e]) {
                d++;
            }
        }
        if (d == c.k) {
            return 1;
        }
        return 0;
    }

    int func8(C c, int x[]) {
        int a = c.v[0] - 65, b = c.v[1] - 65, d = c.v[3] - 65;
        if ((x[a] == x[b]) || (x[b] == x[d]) || (x[a] == x[d])) {
            return 1;
        }
        return 0;
    }

    int func9(C c, int x[]) {
        int a = c.v[0] - 65, b = c.v[1] - 65, d = c.v[2] - 65;
        if (((x[a] + x[b]) > x[d]) && ((x[a] + x[d]) > x[b]) && ((x[b] + x[d]) > x[a])) {
            return 1;
        }
        return 0;
    }

    /**
     * Function getNode(): This function returns a unique code which is used to
     * identify a decision node.
     *
     * @return (code value for the decision node)
     */
    int getNode(C funcc, int y[]) {
        switch (funcc.val) {
            case 0:
                return 1;  //nothing
            case 350:
                return func1(funcc, y); //if( > )
            case 1350:
                if (func1(funcc, y) == 1) {
                    return 0;
                }
                return 1; //else
            case 348:
                return func2(funcc, y); //if( < )
            case 1348:
                if (func2(funcc, y) == 1) {
                    return 0;
                }
                return 1; //else
            case 410:
                return func3(funcc, y);  //if( == )
            case 1410:
                if (func3(funcc, y) == 1) {
                    return 0;
                }
                return 1;  //else

            case 411:
                if (func1(funcc, y) == 1 || func3(funcc, y) == 1) {
                    return 1;
                }
                return 0; //if( >= )
            case 1411:
                return func2(funcc, y);  //else
            case 409:
                if (func2(funcc, y) == 1 || func3(funcc, y) == 1) {
                    return 1;
                }
                return 0; //if( <= )
            case 1409:
                return func1(funcc, y);  //else
            case 382:
                if (func3(funcc, y) == 1) {
                    return 0;
                }
                return 1;  //if( != )
            case 1382:
                return func3(funcc, y);  //else

            case 624:
                return func4(funcc, y);  //sitch-case
            case 1624:
                if (func5(funcc, y) == 1) {
                    return 0;
                }
                return 1; //default case

            case 680:
                return func1(funcc, y); //while( > )
            case 1680:
                if (func1(funcc, y) == 1) {
                    return 0;
                }
                return 1;  //while( !> )
            case 678:
                return func2(funcc, y);  //while( < )
            case 1678:
                if (func2(funcc, y) == 1) {
                    return 0;
                }
                return 1;   //while( !< )
            case 740:
                return func3(funcc, y);  //while( == )
            case 1740:
                if (func3(funcc, y) == 1) {
                    return 0;
                }
                return 1; //while( != )

            case 741:
                if (func1(funcc, y) == 1 || func3(funcc, y) == 1) {
                    return 1;
                }
                return 0;  //while( >= )
            case 1741:
                return func2(funcc, y);  //else
            case 739:
                if (func2(funcc, y) == 1 || func3(funcc, y) == 1) {
                    return 1;
                }
                return 0;  //while( <= )
            case 1739:
                return func1(funcc, y);  //else
            case 712:
                if (func3(funcc, y) == 1) {
                    return 0;
                }
                return 1;  //if( != )
            case 1712:
                return func3(funcc, y);  //else

            case 474:
                return func6(funcc, y); //if(( + )> )
            case 1474:
                if ((func6(funcc, y)) == 1) {
                    return 0;
                }
                return 1; //else
            case 770:
                return func7(funcc, y); //if(( == )&&( == ))
            case 1770:
                if ((func7(funcc, y)) == 1) {
                    return 0;
                }
                return 1; //else
            case 1393:
                return func8(funcc, y); //if(( == )||( == )||( == ))
            case 2393:
                if ((func8(funcc, y)) == 1) {
                    return 0;
                }
                return 1; //else
            case 1241:
                return func9(funcc, y); //if((( + )> )&&(( + )> )&&(( + )> ))
            case 2241:
                if ((func9(funcc, y)) == 1) {
                    return 0;
                }
                return 1; //else
        }
        return 0;
    }
    
    /**
     * Function print(): This function prints the adjacency matrix on the JFrame
     * and write it in the testing file also.
     *
     * @param jf
     * @throws Exception
     */
    void print(JTextPane jf) throws Exception {
        int i, j;
        for (i = 0; i < n; i++) {
            System.out.print(i + " ");
            jf.setText(jf.getText() + i + " ");
            F.write(i + " ");
        }
        System.out.println(" ");
        F.write("\n");
        F.flush();
        jf.setText(jf.getText() + "\n");
        for (i = 0; i < n; i++) {
            System.out.print(". ");
            F.write(". ");
            jf.setText(jf.getText() + " . ");
            if (i > 9) {
                System.out.print(" ");
                F.write(" ");
                jf.setText(jf.getText() + "  ");
            }
        }
        System.out.println(" ");
        F.write("\n");
        F.flush();
        jf.setText(jf.getText() + "\n");
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.print(M[i][j] + " ");
                F.write(M[i][j] + " ");
                jf.setText(jf.getText() + M[i][j] + " ");
                if (j > 9) {
                    System.out.print(" ");
                    F.write(" ");
                    jf.setText(jf.getText() + " ");
                }
            }
            System.out.println(" - " + i);
            F.write(" - " + i + "\n");
            F.flush();
            jf.setText(jf.getText() + "    - " + i + "\n");
        }
        System.out.println(" ");
        F.write("\n\n");
        F.flush();
        jf.setText(jf.getText() + "\n");
    }

}
/**
 * END OF PROJECT.
 */
