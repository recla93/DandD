package org.example.dandd.service;

import java.util.Random;

public class GameService
{
    private static final Random random = new Random();

    public static int precisionCheck(int dannoCalcolato, int precisioneAzione)
    {
        int tiroDado = random.nextInt(100) + 1;

        System.out.println("Tiro dado: " + tiroDado + " (VS  Precisione: " + precisioneAzione + ")");

        if (tiroDado > precisioneAzione)
        {
            System.out.println("Attacco Mancato!");
            return 0;
        }
        else
        {
            System.out.println("Attacco a segno!");
            return dannoCalcolato;
        }
    }
}
