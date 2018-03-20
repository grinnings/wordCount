package com;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Main {
    public static ArrayList<String> instructions;//传入指令组参数
    public static String outPath;//输出文件路径参数
    public static ArrayList<File> sourceFile = new ArrayList<File>();//文件组参数
    public static ArrayList<String > dropList;//停用词表
    public static String absolutePath;

    public static void main(String args[]) throws IOException {
        getInstructions(args);
        wordCounter counter = new wordCounter(sourceFile, outPath, dropList);
        counter.analyzeInstructions(instructions);

//        getFiles("./","*.j*");
//        for(File t:sourceFile){
//            System.out.println(t.getName());
//        }

    }

    //    获取参数
    public static void getInstructions(String[] args) throws IOException
    {
        File content = new File("");
        instructions = new ArrayList<String>();
        absolutePath = content.getAbsolutePath() + "\\";//获取绝对路径
        boolean flag = false;

        for (int i = 0; i < args.length; i++) {
            //无附加文件路径指令加入指令集
            if (args[i].equals("-c") || args.equals("-w") || args.equals("-l") || args.equals("-a") || args.equals("-s"))
                instructions.add(args[i]);

            else if (args[i].equals("-o"))//输出参数-o获取输出路径
                outPath = args[++i];

            else if (args.equals("-e"))//停用词表-e获取停用词表路径
                getDropList(args[++i]);

            else//源文件路径
            {
                if (!flag)
                    sourceFile.add(new File(args[i]));
                else {
                    String filter = args[i].substring(args[i].indexOf("."));
                    System.out.println(filter);
                    getFiles(absolutePath, filter);
                }
            }
        }

    }


    public static boolean isMatch(String fn, String filter)
    {
        int len1 = filter.length();
        int len2 = fn.length();
        boolean[][] dp = new boolean[len1 + 1][len2 + 1];

        dp[0][0] = true;

        for (int i = 1; i <= len1; ++i) {
            char c1 = filter.charAt(i - 1);
            dp[i][0] = dp[i - 1][0] && c1 == '*';

            for (int j = 1; j <= len2; ++j) {
                char c2 = fn.charAt(j - 1);

                if (c1 == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - 1]||dp[i][j-1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] && c1 == c2;
                }
            }
        }

        return dp[len1][len2];
    }

    //递归获取目录下文件路径
    public static void getFiles(String fileContent, String filter) {

        LinkedList<File> q = new LinkedList<File>();

        q.addLast(new File(fileContent));

        File temp = null;
        while (!q.isEmpty())
        {
            temp = q.removeFirst();

            if (temp.isDirectory())
            {
                for (File f : temp.listFiles())
                    q.addLast(f);
            }
            else if (isMatch(temp.getName(), filter))
                sourceFile.add(temp);
        }
    }

    public static void getDropList(String dropFile) throws IOException
    {
        InputStreamReader isRead = new InputStreamReader(new FileInputStream(absolutePath+dropFile));
        BufferedReader buffer = new BufferedReader(isRead);
        String string = buffer.readLine();

        for (;string != null;)
        {
            String[] list = string.split(" ");
            for (String l : list)
                if(!Pattern.matches("\\s*",l))
                    dropList.add(l.toLowerCase());
        }
    }
}
