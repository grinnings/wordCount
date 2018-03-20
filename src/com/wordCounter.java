package com;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class wordCounter {
    //路径相关属性
    private ArrayList<File> sourceFile;
    private String outPath;
    private ArrayList<String> dropList;
    private String absolutePath;

    //文件读写缓存句柄
    private InputStreamReader isRead;
    private BufferedReader buffer;
    private OutputStream outPut;
    private File outFile;

    //构造函数
    public wordCounter(ArrayList<File> sourceFile, String outPath, ArrayList<String> dropList) throws FileNotFoundException {
        this.dropList =dropList;
        this.sourceFile = sourceFile;
        this.outPath = outPath;
        this.outFile = new File(outPath);
        this.outPut = new FileOutputStream(outFile);

        File content = new File("");
        this.absolutePath = content.getAbsolutePath()+"\\";

    }

    //分析参数组
    public void analyzeInstructions(ArrayList<String> instruction) throws IOException {
        for (int i = 0; i<sourceFile.size(); i++)
        {
            //打开文件读取缓存
            isRead =new InputStreamReader(new FileInputStream(sourceFile.get(i)));
            buffer =new BufferedReader(isRead);

            //指令组分析
            if (instruction.contains("-c"))
                countCharData(i);
            if (instruction.contains("-w"))
                countWordsData(i);
            if (instruction.contains("-l"))
                countLinesData(i);
            if (instruction.contains("-a"))
                countComplexData(i);

            //关闭文件读写
            isRead.close();
            buffer.close();
            outPut.close();
        }
    }

    //计数字符数
    public void countCharData(int i) throws IOException
    {
        //初始化三个参数：字符数，文件流中的行数，输出结果
        int charQuntity = 0;
        String typeResult = null;
        String string = buffer.readLine();

        for (;string != null;)
            charQuntity += string.length();

        typeResult = sourceFile.get(i).getAbsolutePath().substring(absolutePath.length()) + ",字符数: " + String.valueOf(charQuntity) + "\r\n";
        byte[] outData = typeResult.getBytes();
        outPut.write(outData);
    }

    //计数单词数量
    public void countWordsData(int i) throws IOException
    {
        //初始化三个参数：单词数，文件流中的行数，输出结果
        int wordQuantity = 0;
        String typeResult = null;
        String string = buffer.readLine();

        for (;string !=null;)
        {
            if (Pattern.matches("\\s*", string))
                continue;
            String[] words = string.split(" |,");
            for (String l : words)
                if (!dropList.contains(l.toLowerCase()) && !Pattern.matches("\\s*",l))
                    wordQuantity++;
        }

        typeResult = sourceFile.get(i).getAbsolutePath().substring(absolutePath.length()) + ",单词数: " + String.valueOf(wordQuantity) + "\r\n";
        byte[] outData = typeResult.getBytes();
        outPut.write(outData);
    }

    //计数行数
    public void countLinesData(int i) throws IOException
    {
        //初始化三个参数：行数，文件流中的行数，输出结果
        int lineQuntity = 0;
        String typeResult = null;
        String string = buffer.readLine();

        //计数
        for (;string != null;)
            lineQuntity++;

        //输出结果
        typeResult = sourceFile.get(i).getAbsolutePath().substring(absolutePath.length()) + ",行数: " + String.valueOf(lineQuntity) + "\r\n";
        byte[] outData = typeResult.getBytes();
        outPut.write(outData);
    }

    public void countComplexData(int i) throws IOException {
        String string;
        String typeResult = null;
        int codeLineQuntity = 0;
        int blankLineQuntity = 0;
        int commentLineQuntity = 0;
        string = buffer.readLine();

        //计数
        for (;string != null;)
        {
            if (Pattern.matches("\\s*.?\\s*//.*", string)) {
                commentLineQuntity++;
                continue;
            }else if(Pattern.matches("\\s*.?\\s*", string)){
                blankLineQuntity++;
                continue;
            }else{
                codeLineQuntity++;
                continue;
            }
        }

        //输出结果
        typeResult = sourceFile.get(i).getAbsolutePath().substring(absolutePath.length()) + ",代码行/空行/注释行: "
                + String.valueOf(codeLineQuntity)+ "/"+ String.valueOf(blankLineQuntity)+ "/"+ String.valueOf(commentLineQuntity) +"\r\n";
        byte data[] = typeResult.getBytes();
        outPut.write(data);
    }

}
