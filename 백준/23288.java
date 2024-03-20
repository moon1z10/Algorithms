import java.util.Scanner;
import java.util.*;

enum DICE_DIRECTION {
    NORTH, SOUTH, WEST, EAST
}

class Position {
    public int x = 0; public int y = 0;
    public Position() {}
    public Position(int x, int y) {
        this.x = x; this.y = y;
    }
    public void print() {
        System.out.println("Position (" + x +", " + y + ")");
    }
}

class Dice {
    public int[] surfaces = {2,4,1,3,5,6}; // [INDEX] 0 : 뒤, 1 : 좌, 2 : 윗면, 3 : 우, 4 : 앞, 5 : 아래
    public DICE_DIRECTION currentDirection = DICE_DIRECTION.EAST;
    public Position dicePosition = new Position();
    
    public void roll() {
        int swap = 0;
        switch (currentDirection) {
            case EAST: 
                // 4(앞), 0(뒤)은 변화 없음.
                swap = surfaces[1]; surfaces[1] = surfaces[5]; surfaces[5] = surfaces[3]; surfaces[3] = surfaces[2]; surfaces[2] = swap;
                dicePosition.y++;
                break;
            case WEST:
                // 4(앞), 0(뒤)은 변화 없음.
                swap = surfaces[1]; surfaces[1] = surfaces[2]; surfaces[2] = surfaces[3]; surfaces[3] = surfaces[5]; surfaces[5] = swap;
                dicePosition.y--;
                break;
            case NORTH:
                // 1(좌), 3(우)은 변화 없음.
                swap = surfaces[0]; surfaces[0] = surfaces[2]; surfaces[2] = surfaces[4]; surfaces[4] = surfaces[5]; surfaces[5] = swap;
                dicePosition.x--;
                break;
            case SOUTH:
                // 1(좌), 3(우)은 변화 없음.
                swap = surfaces[0]; surfaces[0] = surfaces[5]; surfaces[5] = surfaces[4]; surfaces[4] = surfaces[2]; surfaces[2] = swap;
                dicePosition.x++;
                break;
        }
        // System.out.println("[Dice] currentDirection : " + currentDirection);
        // this.dicePosition.print();
    }

    // Parameter B is the map(grid) number
    public void setDirection(int B) {
        // A > B인 경우 이동 방향을 90도 시계 방향으로 회전시킨다.
        // A < B인 경우 이동 방향을 90도 반시계 방향으로 회전시킨다.
        // A = B인 경우 이동 방향에 변화는 없다.
        int A = surfaces[5]; // A is the under surface number of dice
        if (A > B) {
            switch (currentDirection) {
                case EAST: currentDirection = DICE_DIRECTION.SOUTH;                    break;
                case WEST: currentDirection = DICE_DIRECTION.NORTH;                    break;
                case NORTH: currentDirection = DICE_DIRECTION.EAST;                    break;
                case SOUTH: currentDirection = DICE_DIRECTION.WEST;                    break;
            }
        } else if (A < B) {
            switch (currentDirection) {
                case EAST: currentDirection = DICE_DIRECTION.NORTH;                    break;
                case WEST: currentDirection = DICE_DIRECTION.SOUTH;                    break;
                case NORTH: currentDirection = DICE_DIRECTION.WEST;                    break;
                case SOUTH: currentDirection = DICE_DIRECTION.EAST;                    break;
            }
        }
    }
}

public class Main {
    public static int N, M, K;
    public static int[][] grid;
    public static int[][] scoreboard;
    public static int result = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        grid = new int[N][M];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                grid[r][c] = sc.nextInt();
            }
        }
        sc.close();
        // End of input

        // 최적화 : 각 map(grid)의 점수판은 어차피 정해져 있으므로 전체를 미리 만들어 놓는다.
        scoreboard = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                scoreboard[r][c] = calcPoint(r, c);
            }
        }

        Dice dice = new Dice();
        for (int i = 0; i < K; i++) {
            // 1. 주사위가 이동 방향으로 한 칸 굴러간다. 만약, 이동 방향에 칸이 없다면, 이동 방향을 반대로 한 다음 한 칸 굴러간다.
            // System.out.println("[Dice] Before checkDirection : " + dice.currentDirection);
            checkDirection(dice);
            dice.roll();
            // System.out.println("[Dice] After checkDirection : " + dice.currentDirection);

            // 2. 주사위가 도착한 칸에 대한 점수를 획득한다.
            result += scoreboard[dice.dicePosition.x][dice.dicePosition.y];
            // System.out.println("[Dice] " + dice.currentDirection + "(" + dice.dicePosition.x + ", " + dice.dicePosition.y + ") scoreboard : " + scoreboard[dice.dicePosition.x][dice.dicePosition.y] + ", result : " + result);

            // 3. 주사위의 아랫면에 있는 정수 A와 주사위가 있는 칸 (x, y)에 있는 정수 B를 비교해 이동 방향을 결정한다.
            dice.setDirection(grid[dice.dicePosition.x][dice.dicePosition.y]);
        }
        
        
        System.out.println(result);
    }

    // BFS 알고리즘 적용용
    static int calcPoint(int x, int y) {
        boolean[][] visited = new boolean[N][M];

        int count = 0;
        int B = grid[x][y];
        Queue<Position> q = new LinkedList<Position>();
        
        // System.out.println("[calcPoint] x : " + x + ", y : " + y);

        q.add(new Position(x, y));
        visited[x][y] = true;

        // 동서남북 같은 값이 있는지 검색 순회
        while(!q.isEmpty()) {
            Position p = q.poll();
            count++;
            // North
            if (p.x-1 >= 0 && grid[p.x-1][p.y] == B && !visited[p.x-1][p.y]) {
                visited[p.x-1][p.y] = true;
                q.add(new Position(p.x-1, p.y));
            }
            // South
            if (p.x+1 < N && grid[p.x+1][p.y] == B && !visited[p.x+1][p.y]) {
                visited[p.x+1][p.y] = true;
                q.add(new Position(p.x+1, p.y));
            }
            // West
            if (p.y-1 >= 0 && grid[p.x][p.y-1] == B && !visited[p.x][p.y-1]) {
                visited[p.x][p.y-1] = true;
                q.add(new Position(p.x, p.y-1));
            }
            // East
            if (p.y+1 < M && grid[p.x][p.y+1] == B && !visited[p.x][p.y+1]) {
                visited[p.x][p.y+1] = true;
                q.add(new Position(p.x, p.y+1));
            }
        }

        // System.out.println("[calcPoint] B : " + B + ", count : " + count);

        return B * count;
    }

    static void checkDirection(Dice dice) {
        switch (dice.currentDirection) {
            case EAST:
                if (dice.dicePosition.y + 1 == M) dice.currentDirection = DICE_DIRECTION.WEST;
                break;
            case WEST:
                if (dice.dicePosition.y - 1 < 0) dice.currentDirection = DICE_DIRECTION.EAST;
                break;
            case NORTH:
                if (dice.dicePosition.x - 1 < 0) dice.currentDirection = DICE_DIRECTION.SOUTH;
                break;
            case SOUTH:
                if (dice.dicePosition.x + 1 == N) dice.currentDirection = DICE_DIRECTION.NORTH;
                break;
        }
    }
}
