package com.jsoupway;

public class TRy
{
    public static void main(String[] args) {
        String s="2000 1999 1998 1997 1996 1995 1994 1993 1992 1991 " +
                "1990 1989 1988 1987 1986 1985 1984 1983 1982 1981 "+
                "1980 1979 1978 1977 1976 1975 1974 1973";
        for (int i = 0; i < s.split(" ").length; i++) {
            System.out.println(Integer.parseInt(s.split(" ")[i])+27);

        }

    }


}
