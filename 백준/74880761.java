BAEKJOON_74880761.java
/*
** This code is just representing to how it solve it.
** There are more things that you can save the time.
** For example, at 63 lines, it calls 25 times using two for loops. However you can reduce it to 10 times. For applying this, you must update some logics.
*/

import java.util.Scanner;

public class Main {
    static int[][] grid;
    static int[] dx = {0, 1, 0, -1}; // left, down, right, up
    static int[] dy = {-1, 0, 1, 0}; // left, down, right, up
    static int N, result = 0;

    // percentages for sand dispersion
    static int[][] sandRatioLeft = { // Left
            {0, 0, 2, 0, 0},
            {0, 10, 7, 1, 0},
            {5, -1, 0, 0, 0},
            {0, 10, 7, 1, 0},
            {0, 0, 2, 0, 0}
    };
    static int[][] sandRatioDown = { // Down
            {0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {2, 7, 0, 7, 2},
            {0, 10, -1, 10, 0},
            {0, 0, 5, 0, 0}
    };
    static int[][] sandRatioRight = { // Right
            {0, 0, 2, 0, 0},
            {0, 1, 7, 10, 0},
            {0, 0, 0, -1, 5},
            {0, 1, 7, 10, 0},
            {0, 0, 2, 0, 0}
    };
    static int[][] sandRatioUp = { // Up
            {0, 0, 5, 0, 0},
            {0, 10, -1, 10, 0},
            {2, 7, 0, 7, 2},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0}
    };

    // check if a position is within the grid
    static boolean isInside(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    static void spread(int x, int y, int dir) {
        int total = grid[x][y];
        grid[x][y] = 0; // Clear the current cell as we will disperse its sand
        int[][] sandRatio = null;
        switch (dir) {
            case 0: sandRatio = sandRatioLeft; break;
            case 1: sandRatio = sandRatioDown; break;
            case 2: sandRatio = sandRatioRight; break;
            case 3: sandRatio = sandRatioUp; break;
        }
    
        int sandMoved = 0; // This will track the total amount of sand moved to other cells
    
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (sandRatio[i][j] == 0 || sandRatio[i][j] == -1) continue; // Skip the cell if no sand will be moved or it's the α cell
    
                int sand = total * sandRatio[i][j] / 100;
                int nx = x + i - 2; // Adjust index based on sandRatio's center
                int ny = y + j - 2; // Adjust index based on sandRatio's center
    
                sandMoved += sand; // Add the moved sand to the sandMoved counter
    
                if (isInside(nx, ny)) {
                    grid[nx][ny] += sand;
                } else {
                    result += sand;
                }
            }
        }
    
        // Calculate the sand for α after moving the other sand
        int alphaSand = total - sandMoved;
        int nx = x + dx[dir];
        int ny = y + dy[dir];
    
        if (isInside(nx, ny)) {
            grid[nx][ny] += alphaSand;
        } else {
            result += alphaSand;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        grid = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // start from the center
        int x = N / 2;
        int y = N / 2;

        int direction = 0; // start moving left
        int steps = 1;

        while (true) {
            for (int i = 0; i < steps; i++) {
                x += dx[direction];
                y += dy[direction];

                if (!isInside(x, y)) break;

                spread(x, y, direction);

                if (x == 0 && y == 0) break; // tornado reaches the end
            }
            direction = (direction + 1) % 4; // change direction
            if (direction == 0 || direction == 2) steps++; // increase step every two directions

            if (x == 0 && y == 0) break; // tornado reaches the end
        }

        System.out.println(result);
        sc.close();
    }
}
