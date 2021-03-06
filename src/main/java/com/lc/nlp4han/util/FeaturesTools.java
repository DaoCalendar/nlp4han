﻿package com.lc.nlp4han.util;

import java.util.HashSet;

/**
 * 判断字符类型
 * 
 * @author 刘小峰
 * @author 王馨苇
 *
 */
public class FeaturesTools
{
    static HashSet<Character> digitSet = new HashSet<>();
    static HashSet<Character> punctSet = new HashSet<>();

    static
    {
        // 罗列了半角和全角的情况
        String digits = "０１２３４５６７８９0123456789零一二三四五六七八九十○";
        // asc_punc_str=u"!\"&\'()+,-./:;<=>?[\\]^_`{|}~"
        // chi_punc_str=u"。？！，、；：“”‘’（）{}【】—…《》「」『』〈〉＆"
        // 包含中文英文的半角下的标点符号【不包含特殊字符】
        // String punctuation =
        // "!\"&\'()+,-./:;<=>?[\\]^_`{|}~。？！，、；：“”‘’（）{}【】—…《》「」『』〈〉";
        String punctuationE = "',:--!-()[]{}<>/.?\";";// 英文半角
        String punctuationC = "‘’，：——！-（）【】{}《》/。？“”；…「」『』";// 中文半角
        String punctuation = punctuationC + punctuationE;
        
        for (int i = 0; i < digits.length(); i++)
        {
            digitSet.add(digits.charAt(i));
        }
        
        for (int i = 0; i < punctuation.length(); i++)
        {
            punctSet.add(punctuation.charAt(i));
        }
    }

    /**
     * 判断是否是数字【中文数字，阿拉伯数字（全角和半角）】
     * @param c
     * @return
     */
    public static boolean isDigit(char c)
    {
        if (digitSet.contains(c))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 判断是否为年月日
     * @param c
     * @return
     */
    public static boolean isDate(char c)
    {
        if (c == '年' || c == '月' || c == '日')
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 判断是否为应为字母（大小写，全角半角）【全角半角的差别在于ASCII码】
     * @param c
     * @return
     */
    public static boolean isLetter(char c)
    {
        if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c >= 65345 && c <= 65370) || (c >= 65313 && c <= 65338))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 全角转半角
     * @param text
     * @return
     */
    public static String strq2b(String text)
    {
        return String.valueOf(strq2b(text.toCharArray()));
    }

    public static char[] strq2b(char[] text)
    {
        char[] newText = new char[text.length];
        for (int i = 0; i < text.length; i++)
        {
            char ch = text[i];
            if (ch == 12288)
                newText[i] = ' ';
            else if (ch >= 65281 && ch <= 65374)
            {
                ch -= 65248;
                newText[i] = ch;
            }
            else
            {
                newText[i] = ch;
            }
        }
        return newText;
    }

    public static String featureType(String c)
    {
        if (isDigit(c.toCharArray()[0]))
        {
            return "1";
        }
        else if (isDate(c.toCharArray()[0]))
        {
            return "2";
        }
        else if (isLetter(c.toCharArray()[0]))
        {
            return "3";
        }
        else
        {// 其他的情形
            return "4";
        }
    }

    /**
     * 中文状态下的标点符号【前提：全部转为全角状态，再来判断是否是标点】
     * @param c
     * @return
     */
    public static boolean isChinesePunctuation(String c)
    {
        if (punctSet.contains(c.toCharArray()[0]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
