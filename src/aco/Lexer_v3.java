/**
 * @Project Title: Path Generation and Prioritization.
 *
 * @Submitted By: Anuj Kumar (2K13/SE/016) Atul Kumar (2K13/SE/023) Beshambher
 * Chaukhwan (2K13/SE/024) Gaurav Rai (2K13/SE/031)
 *
 * @Input: Source file(C/C++).
 *
 * @Output: Adjacency Matrix.
 *
 */
package aco;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * Main class: This class is the controller class of the project
 */
public class Lexer_v3 {

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
    public Lexer_v3() {
        try {
            /*N=a;
             this.e = new int[N];
             this.p = new int[N][150];*/
            this.get();

            F = new FileWriter(System.getProperty("user.dir") + "\\Inputs/Testing.txt");
            FF = new FileWriter(System.getProperty("user.dir") + "\\Inputs/Fitness.txt");
            M[0][1] = 1;
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

    int func7(C c, int x[]) { //All variables equal
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
        for (i = 1; i < n; i++) {
            System.out.print(i + " ");
            jf.setText(jf.getText() + i + " ");
            F.write(i + " ");
        }
        System.out.println(" ");
        F.write("\n");
        F.flush();
        jf.setText(jf.getText() + "\n");
        for (i = 1; i < n; i++) {
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
        for (i = 1; i < n; i++) {
            for (j = 1; j < n; j++) {
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

    public static void main(String args[]) {
        try {
            //Open file frame
            SourceFile s = new SourceFile();
            int a = 0;
            String S = "";
            //Getting the file from user. 
            s.showFile();
            while (s.jj < 1) {
                Thread.sleep(10);
            }

            /**
             * Genetic class object for calculating Feasible path set matrix and
             * cyclomatic complexity.
             */
            Lexer_v3 t = new Lexer_v3();

            //Input Output frame        
            //inputFile fl = new inputFile(s.file);
            s.f.dispose();

            BufferedReader bf = new BufferedReader(new FileReader(s.file));
            while ((S = bf.readLine()) != null) {
                //  fl.pt1.setText(fl.pt1.getText() + S + "\n");
            }
            bf.close();
            t.F.write("Source File: \"" + s.file.getName() + "\"");
            t.F.write("\n\n");
            t.F.flush();
            String file = s.file.getPath();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //Storing starting time of execution.
            Calendar Cal = new GregorianCalendar();
            int mint = Cal.get(Calendar.MINUTE);
            int sec = Cal.get(Calendar.SECOND);

            /**
             * Checking the extention of file by default we are processing C++
             * files This will detect .c extention and inform the program that
             * the input file is a C file.
             */
            if (s.file.getName().charAt((s.file.getName().length() - 1)) == 'c') {
                t.fex = 1;
            }

            /**
             * check2() function to generate CFG.
             */
            t.M[0][1] = 1;
            t.getCFG(br);
            t.printMatrix(t.M, t.n);
            //This gives CGF in GUI in separate JFrame
            CirclePane_v3 C = new CirclePane_v3();
            C.n = t.line - 1;
            C.m = t.M;
            CircleScrollable_v3 CR = new CircleScrollable_v3(C);
        } catch (Exception e) {
            System.err.println("ERROR!!! in main() " + e);
        }
    }

    /**
     * Function getCFG():
     *
     * @param f This function scan the source code of selected file generate the
     * adjacency matrix of its CFG by reading the source code line by line.
     */
    public void getCFG(BufferedReader f) {
        String s, ch;
        Stack st = new Stack();
        int i, j, len;
        try {
            //System.out.println("Line in beginning: " + line);
            while (st.empty()) {
                s = f.readLine().trim();
                len = s.length();
                //line++;
                //p(" getCFG " + s + st.toString());
                if (len > 0) {
                    if (s.charAt(0) == '{' || s.charAt(len - 1) == '{') {
                        line++;
                        M[line][line + 1] = 1;
                        st.add(line);
                        st.add('m');
                    } else if (s.charAt(0) != '#' && !s.contains("namespace")) {
                        line++;
                        M[line][line + 1] = 1;
                        //line++;
                    } //not supposed to be here
                }
                p(s);
                //M[line][line + 1] = 1;
            }
            //System.out.println("Line before while(!st.empty): " + line);

            while (!st.empty()) {
                s = f.readLine().trim();
                len = s.length();
                line++;
                i = 0;
                p(s);//p(s + " st: " + st.toString());
                if (len > 0 && s.charAt(0) != '/') {
                    if (fex == 0) {
                        if (len > 3) {
                            if (s.contains("cin>")) {
                                j = s.length(); //System.out.println(s+j);               
                                for (i += 4; i < j; i++) {
                                    if ((s.charAt(i) > 96 && s.charAt(i) < 123) || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) {
                                        input[b++] = s.charAt(i);
                                    } //System.out.println(i+" "+j);
                                }
                                M[line][line + 1] = 1;
                                s = f.readLine().trim();
                                len = s.length();
                                line++;
                                p(s);
                            } //System.out.println("Here in cpp");
                        }

                    } else if (s.charAt(i) == 's') { //System.out.println("Here in c");
                        if (len > 5) {
                            if (s.contains("scanf")) {
                                j = s.length(); //System.out.println(s+j);               
                                for (i += 5; i < j; i++) {
                                    if ((s.charAt(i) == '&')) {
                                        input[b++] = s.charAt(i + 1); //System.out.println(b+" - "+input[b-1]);
                                    } //System.out.println(i+" "+j);
                                }
                                M[line][line + 1] = 1;
                                s = f.readLine().trim();
                                len = s.length();
                                line++;
                                p(s);
                            }
                        }
                    }

                    if (s.charAt(0) == '/') {
                        line--;
                        continue;
                    }

                    i = 0;
                    if ((len > 6) && s.contains("switch")) {
                        // System.out.println("in switch-case");
                        if (s.charAt(len - 1) != '{') {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            len = s.length();
                            line++;
                            p(s + " st: " + st.toString());
                        }
                        j = line;
                        st.add('s');
                        while (s.charAt(0) != '}' || s.charAt(len - 1) != '}') {
                            s = f.readLine().trim();
                            len = s.length();
                            line++;
                            p(s + " st: " + st.toString());
                            if (s.contains("case") || s.contains("default")) {
                                M[j][line] = 1;
                            }
                            if (s.contains("break")) {
                                st.add(line);
                            } else {
                                M[line][line + 1] = 1;
                            }
                        }
                        while (!st.lastElement().toString().equals("s")) {
                            j = toInt(st.pop().toString());
                            M[j][line] = 1;
                        }
                        st.pop();
                        p(" st: " + st.toString());
                    } else if ((len > 1) && s.contains("do")) {
                        // System.out.println("in do()");
                        st.add(line);
                        p("Line at do-while(): " + s + " st: " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('d');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('d');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 3) && s.contains("for")) {
                        //System.out.println("in for()");      
                        st.add(line);
                        p("Line at for(): " + s + " st: " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('f');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('f');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 5) && s.contains("while") && s.charAt(len - 1) != ';') {
                        //System.out.println("in while()");
                        st.add(line);
                        p("Line at while(): " + s + " st: " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('w');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('w');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 6) && s.contains("else if")) {
                        //System.out.println("in else if()");
                        if (s.charAt(0) == '}') {
                            funcBrace(st, f, s);
                        }
                        //p("Line at else if(): " + s + " st: " + st.toString());
                        //st.pop();
                        //M[toInt(st.pop().toString())][line] = 1;
                        st.add("ei");
                        st.add(line);
                        if (s.charAt(len - 1) == '{') {
                            st.add('i');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('i');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 2) && s.contains("if")) {
                        st.add(line);
                        //p("Line at if(): " + s + " st: " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('i');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('i');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 3) && s.contains("else")) {
                        if (s.charAt(0) == '}') {
                            funcBrace(st, f, s);
                        }
                        //p("Line at else: " + s + " st: " + st.toString());
                        //st.add(line - 1);
                        //System.err.println(" at line " + line + " " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('e');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('e');
                            }
                        }
                    } else if ((len > 0) && (s.charAt(0) == '}' || s.charAt(len - 1) == '}')) {
                        RECALL:
                        {
                            if (st.lastElement().toString().equals("m")) {
                                st.removeAllElements();
                                M[line++][line] = 1;
                                M[line++][line] = 1;
                                //} else if (st.lastElement().toString().equals("ei")) {

                            } else if (st.lastElement().toString().charAt(0) == 'e') {
                                st.pop();
                                j = toInt(st.pop().toString());
                                M[j][line + 1] = 1;
                                M[line][line + 1] = 1;
                                while (st.lastElement().toString().equals("ei")) {
                                    st.pop();
                                    j = toInt(st.pop().toString());
                                    M[j][line + 1] = 1;
                                }
                            } else if (st.lastElement().toString().charAt(0) == 'i') {
                                st.pop();
                                j = toInt(st.pop().toString());
                                M[j][line + 1] = 1;
                                //p("\t*****************\t current line is " + s + " at no. " + (line) + " st: " + st.toString());
                                if ((len > 3) && s.contains("else")) {
                                    st.add(line);
                                } else {
                                    f.mark(line);
                                    s = f.readLine().trim();
                                    len = s.length();
                                    p(s);     //                  p("I read this " + s);
                                    if ((len > 3) && (s.contains("else"))) {
                                        st.add(line);
                                        //System.out.println("\tFOUND ELSE at current line is " + s + " at no. " + (line) + " st: " + st.toString());
                                    } else {
                                        //System.out.println("\tNOT FOUND Resetting " + s + " at no. " + (line) + " st: " + st.toString());
                                        M[line][line + 1] = 1;
                                    }
                                    f.reset();
                                }
                                p("\t*****************\t j is " + j + " and line+1 is " + (line + 1) + " st: " + st.toString());
                            } else if (st.lastElement().toString().charAt(0) == 'w'
                                    || st.lastElement().toString().charAt(0) == 'f') {
                                st.pop();
                                j = toInt(st.pop().toString());
                                M[j][line + 1] = 1;
                                M[line][j] = 1;

                            } else if (st.lastElement().toString().charAt(0) == 'd') {
                                st.pop();
                                j = toInt(st.pop().toString());
                                M[line][j] = 1;
                                M[line][line + 1] = 1;

                            } else {
                                /*j = 0;
                                 if (!st.empty()) {
                                 while (true) {
                                 if (st.empty() || !isDigit(st.lastElement().toString())) {
                                 break;
                                 }
                                 ch = st.pop().toString();
                                 p("poping " + s + " " + st.toString());
                                 j = toInt(ch);
                                 M[j][line] = 1;
                                 }
                                 }

                                 if (!st.empty() && j == 0) {
                                 ch = st.pop().toString();
                                 if (ch.charAt(0) == '{') {
                                 st.pop();
                                 }
                                 }*/

                                M[line][line + 1] = 1;
                            }
                            //  M[line][line + 1] = 1;
                            //System.out.println(" Poped: "+ch);
                        }
                    } else if ((len > 0) && (s.charAt(i) == '{' || s.charAt(len - 1) == '{')) {
                        //st.add(line);
                    } else {
                        M[line][line + 1] = 1;
                    }

                } else {
                    M[line][line + 1] = 1;
                }
                //p(" while(!st.empty()) st: " + s + st.toString());
            }
            line--;
            System.out.println(" out of while(!st.empty()). last line: " + line);
            //printMatrix(M, line);
            n = line;

            int e = 0;
            for (i = 1; i < n; i++) {
                for (j = 1; j < n; j++) {
                    e += M[i][j];
                }
            }
            cc = e - (n - 1) + 2;
            System.out.println(" (e, n): (" + e + ", " + (n - 1) + ") cc: " + cc);

            //This gives CGF in GUI in separate JFrame
            CirclePane_v3 C = new CirclePane_v3();
            C.n = n;
            C.m = M;
            CircleScrollable_v3 CR = new CircleScrollable_v3(C);
            printMatrix(C.m, n);

            /*This gives DDPath in GUI in separate JFrame
             DDPane D = new DDPane();
             D.setMatrixSize(n);
             D.getDD(f);
             DDPath DD = new DDPath(D);*/
            System.out.println(" END ");

        } catch (Exception e) {
            System.err.println("in getCFG() " + e);
        }
    }

    public int line = 0;

    private void funcBrace(Stack st, BufferedReader f, String s) throws Exception {
        int j, len = s.length();
        //System.out.println("@@@@@@@@@@@@@@@@ Here in funcBraces @@@@@@@@@@@@@");
        if (st.lastElement().toString().equals("m")) {
            st.removeAllElements();
            M[line++][line] = 1;
            M[line++][line] = 1;
            //} else if (st.lastElement().toString().equals("ei")) {

        } else if (st.lastElement().toString().charAt(0) == 'e') {
            st.pop();
            j = toInt(st.pop().toString());
            M[j][line] = 1;
            M[line][line + 1] = 1;
            while (st.lastElement().toString().equals("ei")) {
                st.pop();
                j = toInt(st.pop().toString());
                M[j][line + 1] = 1;
            }
        } else if (st.lastElement().toString().charAt(0) == 'i') {
            st.pop();
            j = toInt(st.pop().toString());
            M[j][line] = 1;
            M[line][line + 1] = 1;
            //p("\t*****************\t current line is " + s + " at no. " + (line) + " st: " + st.toString());
            if ((len > 3) && s.contains("else")) {
                st.add(line);
            } else {
                f.mark(line);
                s = f.readLine().trim();
                len = s.length();
                p(s);//                  p("I read this " + s);
                if ((len > 3) && (s.contains("else"))) {
                    st.add(line);
                    //System.out.println("\tFOUND ELSE at current line is " + s + " at no. " + (line) + " st: " + st.toString());
                } else {
                    //System.out.println("\tNOT FOUND Resetting " + s + " at no. " + (line) + " st: " + st.toString());
                    M[line][line + 1] = 1;
                }
                f.reset();
            }
            //p("\t*****************\t j is " + j + " and line is " + (line) + " st: " + st.toString());
        } else {
            /*j = 0;
             if (!st.empty()) {
             while (true) {
             if (st.empty() || !isDigit(st.lastElement().toString())) {
             break;
             }
             ch = st.pop().toString();
             p("poping " + s + " " + st.toString());
             j = toInt(ch);
             M[j][line] = 1;
             }
             }

             if (!st.empty() && j == 0) {
             ch = st.pop().toString();
             if (ch.charAt(0) == '{') {
             st.pop();
             }
             }*/

            M[line][line + 1] = 1;
        }
    }

    public void p(String s) {
        System.out.println(line + ".\t" + s);
    }

    public void printMatrix(int M[][], int n) {
        int i;
        for (i = 0; i < n; i++) {
            System.out.print(i + " ");
        }
        System.out.println("");
        for (i = 0; i < n; i++) {
            System.out.print(". ");
        }
        System.out.println("");

        for (i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println(" - " + i);
        }
        System.out.println("\n\n");
    }

    private int toInt(String s) {
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            i = (i * 10) + (s.charAt(j) - '0');
        }
        return i;
    }

    private boolean isDigit(String s) {
        int i, j = s.length();
        if (j > 0) {
            for (i = 0; i < j; i++) {
                if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int getOutDegree(int j) {
        int i = 0;
        for (int k = 0; k < n; k++) {
            i += M[j][k];
        }
        return i;
    }
}
/**
 * END OF PROJECT.
 */
