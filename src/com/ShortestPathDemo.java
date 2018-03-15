package com;

import edu.princeton.cs.algs4.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ShortestPathDemo {
    protected static int[] V;
    protected static int[] X;
    protected static int[] Y;

    public static void formatArray(int v) {
        ShortestPathDemo.V = new int[v];
        ShortestPathDemo.X = new int[v];      //点的x坐标
        ShortestPathDemo.Y = new int[v];      //点的y坐标
    }

    public static double getWeight(int one, int theOther) {
        int distX = X[theOther] - X[one];
        int distY = Y[theOther] - Y[one];
        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner input = null;
        try {
            input = new Scanner(new File("usa.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int v = input.nextInt(); //读入顶点数
        int e = input.nextInt(); //读入边数

        formatArray(v);          //初始化数组

        /*使用EdgeWeightedDigraph类的第一个构造函数，传入顶点个数v这一个参数
         * 构造出含有v个顶点的加权有向图
         * 此时图内仅有顶点，没有边*/
        EdgeWeightedDigraph map = new EdgeWeightedDigraph(v);  //使用该类的第一个构造函数


        /*用三个数组分别存入文件中的数据，分别为
         * V[] 顶点的集合
         * X[] 顶点的横坐标
         * Y[] 顶点的纵坐标*/
        for (int i = 0; i < v; i++) {
            V[i] = input.nextInt();
            X[i] = input.nextInt();
            Y[i] = input.nextInt();
        }

        /*通过计算每个边的权值，向加权有向图map中加入所有边
         * 此时图就形成了*/
        for (int i = 0; i < e; i++) {
            int one = input.nextInt();
            int theOther = input.nextInt();
            double weight = getWeight(one, theOther);
            DirectedEdge directedEdge = new DirectedEdge(one, theOther, weight); //生成（有向）加权边
            map.addEdge(directedEdge); //向图中添加边

        }

        Scanner usrInput = new Scanner(System.in);
        StdOut.println("Input the starting point & destination point & times: ");

        int usrStart = usrInput.nextInt();
        int usrEnd = usrInput.nextInt();
        int times = usrInput.nextInt();  //运行次数

        Stopwatch start = new Stopwatch(); //计时开始
        int i = 0;


        OptimizedDijkstra optimizedDijkstra = null;
        for (; i++ < times; ) {
            optimizedDijkstra = new OptimizedDijkstra(map, usrStart, usrEnd);
        }
        double elapsedTime = start.elapsedTime();
        StdOut.print(usrStart + " to " + usrEnd);
        StdOut.printf(" (%.2f): \n\n", optimizedDijkstra.distTo(usrEnd));
        if (optimizedDijkstra.hasPathTo(usrEnd)) {
            for (DirectedEdge de : optimizedDijkstra.pathTo(usrEnd)) {
                StdOut.println(de + "  ");
            }
        } else {
            StdOut.println(usrStart + " cannot reach " + usrEnd);
        }


        StdOut.println("Total time：" + elapsedTime + "s!");
        StdOut.println("-------------------------\n| Mission accomplished! |\n-------------------------");
    }
}
