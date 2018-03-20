在命令行该目录下运行wc.exe
支持以下参数：
-c file.c     //返回文件 file.c 的字符数
-w file.c     //返回文件 file.c 的单词总数
-l file.c     //返回文件 file.c 的总行数
-o outputFile.txt     //将结果输出到指定文件outputFile.txt
-s            //递归处理目录下符合条件的文件
-a file.c     //返回更复杂的数据（代码行 / 空行 / 注释行）
-e stopList.txt  // 停用词表，统计文件单词总数时，不统计该表中的单词