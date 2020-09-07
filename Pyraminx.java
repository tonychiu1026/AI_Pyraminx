/*
The Pyraminx class definition

Provide a 2D array to store the face values of the pyraminx

Provide 12 rotation method for the pyraminx

 */

package com.pyraminx;

import java.util.Random;
import java.util.Scanner;

public class Pyraminx
{
    private char[][] pyraminx; // to store out pyraminx in a 2D array
    private char[] color; // use to store the default face color rotation

    // Const enum to represent rotation direction
    public enum Direction
    {
        CLOCKWISE,
        COUNTER_CLOCKWISE
    }

    // Constructor that initialize the pyraminx to original solved state
    // Takes no parameter, initialize to default color
    public Pyraminx()
    {
        pyraminx = new char[4][16];

        // default color settings
        color = new char[]{Pyraminx_const.GREEN, Pyraminx_const.BLUE, Pyraminx_const.YELLOW, Pyraminx_const.RED};
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                pyraminx[i][j] = color[i];
            }
        }
    }

    // Probably not necessary, but I added another constructor in case
    // anyone (probably no one) want to use custom color for each face
    public Pyraminx(char[] color_list)
    {
        pyraminx = new char[4][16];

        // default color settings
        color = color_list;
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                pyraminx[i][j] = color[i];
            }
        }
    }

    // reset the pyraminx to its original state--the solved state
    public void reset_pyraminx()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                pyraminx[i][j] = color[i];
            }
        }
    }

    // display the pyraminx using special order
    // see README file for the order explanation
    // I know this looks ugly, but I cannot find a better way to do it
    public void display_pyraminx()
    {
        // first line
        System.out.printf("L:");
        System.out.printf("     " + pyraminx[2][9] + "      ");
        System.out.printf("U:");
        System.out.printf("     " + pyraminx[0][0] + "      ");
        System.out.printf("R:");
        System.out.printf("     " + pyraminx[3][15] + "      \n");

        // second line
        System.out.printf("      " + pyraminx[2][11] + pyraminx[2][10] + pyraminx[2][4] + "           ");
        System.out.printf("" + pyraminx[0][1] + pyraminx[0][2] + pyraminx[0][3] + "           ");
        System.out.printf("" + pyraminx[3][8] + pyraminx[3][14] + pyraminx[3][13] + "\n");

        // third line
        System.out.printf("     " + pyraminx[2][13] + pyraminx[2][12] + pyraminx[2][6] + pyraminx[2][5] + pyraminx[2][1]);
        System.out.printf("         " + pyraminx[0][4] + pyraminx[0][5] + pyraminx[0][6] + pyraminx[0][7] + pyraminx[0][8]);
        System.out.printf("         " + pyraminx[3][3] + pyraminx[3][7] + pyraminx[3][6] + pyraminx[3][12] + pyraminx[3][11] + "\n");

        // fourth line
        System.out.printf("    " + pyraminx[2][15] + pyraminx[2][14] + pyraminx[2][8] + pyraminx[2][7] + pyraminx[2][3] + pyraminx[2][2] + pyraminx[2][0]);
        System.out.printf("       " + pyraminx[0][9] + pyraminx[0][10] + pyraminx[0][11] + pyraminx[0][12] + pyraminx[0][13] + pyraminx[0][14] + pyraminx[0][15]);
        System.out.printf("       " + pyraminx[3][0] + pyraminx[3][2] + pyraminx[3][1] + pyraminx[3][5] + pyraminx[3][4] + pyraminx[3][10] + pyraminx[3][9] + "\n");

        // fifth line and the rest
        System.out.println();
        System.out.println("              " + "B:  " + pyraminx[1][15] + pyraminx[1][14] + pyraminx[1][13] + pyraminx[1][12] + pyraminx[1][11] + pyraminx[1][10] + pyraminx[1][9]);
        System.out.println("                   " + pyraminx[1][8] + pyraminx[1][7] + pyraminx[1][6] + pyraminx[1][5] + pyraminx[1][4]);
        System.out.println("                    " + pyraminx[1][3] + pyraminx[1][2] + pyraminx[1][1]);
        System.out.println("                     " + pyraminx[1][0]);
    }

    // rotate with respect to the tip
    // level = 1: just the tip
    // level = 2: tip and the row below it
    // level = 3: add another row below level 2

    // with respect to U tip.
    public void u_rotation(Direction direction, int level)
    {
        // all it does is just swap faces according to different level number
        if (level == 1)
        {
            swap_faces(0, 2, 3, 1, direction);
        } else if (level == 2)
            swap_faces(0, 2, 3, 2, direction);
        else
        {
            swap_faces(0, 2, 3, 3, direction);
        }
    }

    // with respect to L tip
    public void l_rotation(Direction direction, int level)
    {
        if (level == 1)
        {
            swap_faces(2, 0, 1, 1, direction);
        } else if (level == 2)
            swap_faces(2, 0, 1, 2, direction);
        else
        {
            swap_faces(2, 0, 1, 3, direction);
        }
    }

    // with respect to R tip
    public void r_rotation(Direction direction, int level)
    {
        if (level == 1)
        {
            swap_faces(3, 1, 0, 1, direction);
        } else if (level == 2)
            swap_faces(3, 1, 0, 2, direction);
        else
        {
            swap_faces(3, 1, 0, 3, direction);
        }
    }

    // with respect to B tip
    public void b_rotation(Direction direction, int level)
    {
        if (level == 1)
        {
            swap_faces(1, 3, 2, 1, direction);
        } else if (level == 2)
            swap_faces(1, 3, 2, 2, direction);
        else
        {
            swap_faces(1, 3, 2, 3, direction);
        }
    }

    // Will swap the values of the given 3 pieces in the direction required.
    // Doing the swap value between 3 variables:
    // temp = a
    // a = b
    // b = c
    // c = temp
    // or other way around, depending on the direction parameter
    private void swap_values(int[] facing_pos, int[] left_pos, int[] right_pos, Direction direction)
    {
        if (direction == Direction.CLOCKWISE)
        {
            char temp = pyraminx[facing_pos[0]][facing_pos[1]];
            pyraminx[facing_pos[0]][facing_pos[1]] = pyraminx[right_pos[0]][right_pos[1]];
            pyraminx[right_pos[0]][right_pos[1]] = pyraminx[left_pos[0]][left_pos[1]];
            pyraminx[left_pos[0]][left_pos[1]] = temp;
        } else if (direction == Direction.COUNTER_CLOCKWISE)
        {
            char temp = pyraminx[facing_pos[0]][facing_pos[1]];
            pyraminx[facing_pos[0]][facing_pos[1]] = pyraminx[left_pos[0]][left_pos[1]];
            pyraminx[left_pos[0]][left_pos[1]] = pyraminx[right_pos[0]][right_pos[1]];
            pyraminx[right_pos[0]][right_pos[1]] = temp;
        }
    }

    // hard-coding the faces that need to be swapped
    // in each int array, array[0] is the face index, array[1] is the indexes of block(s) we are swapping
    private void swap_faces(int face_facing, int face_left, int face_right, int level, Direction direction)
    {
        // loop level times, each time swap a row
        for (int i = 1; i <= level; i++)
        {
            if (i == 1)
                swap_values(new int[]{face_facing, 0}, new int[]{face_left, 9}, new int[]{face_right, 15}, direction);
            if (i == 2)
            {
                swap_values(new int[]{face_facing, 1}, new int[]{face_left, 11}, new int[]{face_right, 8}, direction);
                swap_values(new int[]{face_facing, 2}, new int[]{face_left, 10}, new int[]{face_right, 14}, direction);
                swap_values(new int[]{face_facing, 3}, new int[]{face_left, 4}, new int[]{face_right, 13}, direction);
            }
            if (i == 3)
            {
                swap_values(new int[]{face_facing, 4}, new int[]{face_left, 13}, new int[]{face_right, 3}, direction);
                swap_values(new int[]{face_facing, 5}, new int[]{face_left, 12}, new int[]{face_right, 7}, direction);
                swap_values(new int[]{face_facing, 6}, new int[]{face_left, 6}, new int[]{face_right, 6}, direction);
                swap_values(new int[]{face_facing, 7}, new int[]{face_left, 5}, new int[]{face_right, 12}, direction);
                swap_values(new int[]{face_facing, 8}, new int[]{face_left, 1}, new int[]{face_right, 11}, direction);
            }
        }
        // all rows up to "level" should be swapped
    }

}
