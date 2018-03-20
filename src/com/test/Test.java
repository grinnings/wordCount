package com.test;

import com.Main;
import com.wordCounter;

import javax.swing.*;
import java.io.IOException;


public class Test {
    public static void main(String[] args) throws IOException
    {
        String[] info = {"-c","-w","-l","-a","./testCases","-e","./testCases/dropList","-o","./testCases/testResult1"};

        Main mDemo = new Main();

        //测试isMatch
        boolean matchTest = mDemo.isMatch("./testCases","*.j*");
        System.out.println(matchTest);

        //测试getFiles
        mDemo.getFiles("./testCases/recursion","*.j*");

        //测试getFiles的递归
        mDemo.getFiles("./testCases","*.j*");

        //测试getDropList
        mDemo.getDropList("./testCases/dropList.txt");
        System.out.println(mDemo.dropList);

        //测试getInstructions
        mDemo.getInstructions(info);
        System.out.println(mDemo.sourceFile+","+mDemo.outPath+","+mDemo.dropList);


        wordCounter counter = new wordCounter(mDemo.sourceFile,mDemo.outPath,mDemo.dropList);

        //测试counter中字符数计算测试对象为testCase
        counter.countCharData(0);

        //测试counter中词计数功能
        counter.countWordsData(0);

        //测试counter中行计数功能
        counter.countLinesData(0);

        //测试counter中复杂技术功能
        counter.countComplexData(0);

        //测试递归读取目录下第二个文件的复杂计数功能
        counter.countComplexData(2);
    }
}
