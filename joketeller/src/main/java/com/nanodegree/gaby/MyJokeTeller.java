package com.nanodegree.gaby;

import java.util.Random;

public class MyJokeTeller {
    private static Random rand = new Random();
    private String[] jokesDB = {
            "What do you call a bear with no teeth? A gummy bear!",
            "How do you make holy water? You boil the hell out of it.",
            "How does a computer tell you it needs more memory? It says 'byte me'",
            "Why did the storm trooper buy an iphone? He couldn't find the Droid he was looking for.",
            "What do you call a magic dog? A Labracadabrador.",
            "Can a kangaroo jump higher than a house? Of course, a house doesn't jump at all.",
            "What happens when you cross a snowman and a vampire? You get a frostbite.",
            "How do you tell that a crab is drunk? It walks forwards.",
            "What do you call a computer that sings? A-Dell",
            "What did the baby corn say to the mama corn? -'Mom, where's Popcorn?'"
    };
    public String getJoke(){

        int randomNum = (int)(rand.nextDouble() * 10 + 0);

        return jokesDB[randomNum];
    }
}
