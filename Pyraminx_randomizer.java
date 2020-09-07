/*
Name: Tian Qiu
Project: Modeling the Pyraminx - Randomizer
Date: 9/3/2020
Purpose: Create a pyraminx, and scramble the pyraminx in given moves
 */

package com.pyraminx;

import java.util.Random;
import java.util.Scanner;

public class Pyraminx_randomizer
{
    // it will take a Pyraminx type as a private member
    private Pyraminx my_pyranmix;

    // constructor that will initialize the Pyraminx object
    public Pyraminx_randomizer(Pyraminx pyraminx)
    {
        my_pyranmix = pyraminx;
    }

    // Do random rotations on the pyraminx given number of moves needed
    public void scramble(int moves)
    {
        Random rand = new Random();

        // perform the rotation "moves" times
        for (int i = 0; i < moves; i++)
        {
            // random integer from 0 to 3 represent 4 different rotations
            int type = rand.nextInt(4);

            // random integer from 0 to 2 represent 3 different levels
            int level = rand.nextInt(3);

            // the default is set to be rotating counterclockwise
            if (type == 0)
                my_pyranmix.u_rotation(Pyraminx.Direction.COUNTER_CLOCKWISE, level + 1);
            else if (type == 1)
                my_pyranmix.l_rotation(Pyraminx.Direction.COUNTER_CLOCKWISE, level + 1);
            else if (type == 2)
                my_pyranmix.r_rotation(Pyraminx.Direction.COUNTER_CLOCKWISE, level + 1);
            else
                my_pyranmix.b_rotation(Pyraminx.Direction.COUNTER_CLOCKWISE, level + 1);

        }
    }

    // Driver function that will display command list and wait for user's command.
    // Command List:
    // "d" for display pyraminx
    // "s" for scramble pyraminx
    // "r" for reset pyraminx
    // "q" to quit
    static public void main(String[] args)
    {
        Pyraminx my_pyraminx = new Pyraminx();
        Pyraminx_randomizer randomizer = new Pyraminx_randomizer(my_pyraminx);
        Scanner in = new Scanner(System.in);
        System.out.println("Command List: ");
        System.out.println("\"d\" for display pyraminx");
        System.out.println("\"s\" for scramble pyraminx");
        System.out.println("\"r\" for reset pyraminx");
        System.out.println("\"q\" to quit");
        System.out.println("Enter a command: ");
        String userVal = in.nextLine();

        while (userVal != "q")
        {
            switch (userVal)
            {
                case "q":
                    System.exit(0);
                    break;
                case "d":
                    my_pyraminx.display_pyraminx();
                    break;
                case "r":
                    my_pyraminx.reset_pyraminx();
                    System.out.println("Reset Success!");
                    break;
                case "s":
                {
                    System.out.println("How many moves: ");
                    int moves;
                    if (in.hasNextInt())
                    {
                        moves = in.nextInt();
                        in.nextLine(); // clear the \n
                        randomizer.scramble(moves);
                        System.out.println("Scrambled!");
                    } else
                    {
                        System.out.println("Invalid input!");
                        in.nextLine(); // clear the \n
                    }
                    break;
                }
                default:
                    System.out.println("Invalid command!");
                    break;
            }


            System.out.println("Enter a command: ");
            userVal = in.nextLine();
        }


    }
}
