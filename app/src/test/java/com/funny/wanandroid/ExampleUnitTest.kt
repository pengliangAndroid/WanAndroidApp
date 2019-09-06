package com.funny.wanandroid

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val str = "149002035  \n" +
                "1008002036  \n" +
                "149002022  \n" +
                "149002021  \n" +
                "1008331053  \n" +
                "1008331052  \n" +
                "1008002012  \n" +
                "149331048  \n" +
                "149331047  \n" +
                "149331026  \n" +
                "149331025  \n" +
                "149002020  \n" +
                "149002019  \n" +
                "1008002018  \n" +
                "1008002017  \n" +
                "1008002016  \n" +
                "149002015  \n" +
                "149002014  \n" +
                "1008002011  \n" +
                "1008002010  \n" +
                "1008002009  \n" +
                "1008002008  \n" +
                "149002005  \n" +
                "149002004  \n" +
                "149002003  \n" +
                "149002002  \n" +
                "149002001 "

        val sb1 = StringBuilder()
        val sb2 = StringBuilder()

        str.split("\n")
            .groupBy { it.startsWith("149") }
            .forEach{
                if(it.key){
                    for(item in it.value){
                        sb1.append(item.trim()).append(";")
                    }
                }else{
                    for(item in it.value){
                        sb2.append(item.trim()).append(";")
                    }
                }
            }

        println(sb1.toString())
        println(sb2.toString())
        assertEquals(4, 2 + 2)
    }
}
