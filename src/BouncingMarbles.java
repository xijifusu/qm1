package src;

import comp1110.universe.*;
import static comp1110.universe.Colour.*;
import static comp1110.universe.Image.*;
import static comp1110.universe.Universe.*;

import java.util.Random;

/**
 * COMP1110/1140/6710 Assignment 1
 * 
 * 本程序模拟四个不同的弹珠在矩形世界中以恒定速度移动，
 * 弹珠碰到边界时会改变方向。
 */
public class BouncingMarbles {
    
    // 世界的宽度和高度
    static final int WORLD_WIDTH = 300;
    static final int WORLD_HEIGHT = 500;
    
    // 弹珠半径
    static final int MARBLE_RADIUS = 10;
    
    /**
     * 方向枚举，表示弹珠的移动方向
     */
    enum Direction {
        NORTH,      // 北
        SOUTH,      // 南
        EAST,       // 东
        WEST,       // 西
        NORTH_EAST, // 东北
        NORTH_WEST, // 西北
        SOUTH_EAST, // 东南
        SOUTH_WEST  // 西南
    }
    
    /**
     * 边界枚举，表示矩形世界的边界部分
     */
    enum Boundary {
        TOP,           // 上边缘
        BOTTOM,        // 下边缘
        LEFT,          // 左边缘
        RIGHT,         // 右边缘
        TOP_LEFT,      // 左上角
        TOP_RIGHT,     // 右上角
        BOTTOM_LEFT,   // 左下角
        BOTTOM_RIGHT,  // 右下角
        NONE           // 不在边界
    }
    
    /**
     * 弹珠类，表示弹珠的属性和行为
     */
    static class Marble {
        private int x;           // 弹珠中心的X坐标
        private int y;           // 弹珠中心的Y坐标
        private Colour colour;   // 弹珠颜色
        private Direction direction; // 弹珠移动方向
        
        /**
         * 构造弹珠
         * @param x 初始X坐标
         * @param y 初始Y坐标
         * @param colour 颜色
         * @param direction 初始方向
         */
        public Marble(int x, int y, Colour colour, Direction direction) {
            this.x = x;
            this.y = y;
            this.colour = colour;
            this.direction = direction;
        }
        
        /**
         * 获取弹珠X坐标
         * @return X坐标
         */
        public int getX() {
            return x;
        }
        
        /**
         * 获取弹珠Y坐标
         * @return Y坐标
         */
        public int getY() {
            return y;
        }
        
        /**
         * 获取弹珠颜色
         * @return 颜色
         */
        public Colour getColour() {
            return colour;
        }
        
        /**
         * 获取弹珠方向
         * @return 方向
         */
        public Direction getDirection() {
            return direction;
        }
        
        /**
         * 设置弹珠方向
         * @param direction 新方向
         */
        public void setDirection(Direction direction) {
            this.direction = direction;
        }
        
        /**
         * 检查弹珠是否到达了世界边界的某个部分
         * @return 弹珠所在的边界部分，如果不在边界则返回NONE
         */
        public Boundary checkBoundary() {
            boolean atTop = (y - MARBLE_RADIUS <= 0);
            boolean atBottom = (y + MARBLE_RADIUS >= WORLD_HEIGHT);
            boolean atLeft = (x - MARBLE_RADIUS <= 0);
            boolean atRight = (x + MARBLE_RADIUS >= WORLD_WIDTH);
            
            // 检查角落
            if (atTop && atLeft) return Boundary.TOP_LEFT;
            if (atTop && atRight) return Boundary.TOP_RIGHT;
            if (atBottom && atLeft) return Boundary.BOTTOM_LEFT;
            if (atBottom && atRight) return Boundary.BOTTOM_RIGHT;
            
            // 检查边缘
            if (atTop) return Boundary.TOP;
            if (atBottom) return Boundary.BOTTOM;
            if (atLeft) return Boundary.LEFT;
            if (atRight) return Boundary.RIGHT;
            
            // 不在边界
            return Boundary.NONE;
        }
        
        /**
         * 根据当前方向和边界情况更新方向
         * @param boundary 弹珠所在的边界部分
         */
        public void updateDirection(Boundary boundary) {
            switch (boundary) {
                case TOP:
                    // 如果到达上边缘
                    if (direction == Direction.NORTH) {
                        direction = Direction.SOUTH;
                    } else if (direction == Direction.NORTH_EAST) {
                        direction = Direction.SOUTH_EAST;
                    } else if (direction == Direction.NORTH_WEST) {
                        direction = Direction.SOUTH_WEST;
                    }
                    break;
                    
                case BOTTOM:
                    // 如果到达下边缘
                    if (direction == Direction.SOUTH) {
                        direction = Direction.NORTH;
                    } else if (direction == Direction.SOUTH_EAST) {
                        direction = Direction.NORTH_EAST;
                    } else if (direction == Direction.SOUTH_WEST) {
                        direction = Direction.NORTH_WEST;
                    }
                    break;
                    
                case LEFT:
                    // 如果到达左边缘
                    if (direction == Direction.WEST) {
                        direction = Direction.EAST;
                    } else if (direction == Direction.NORTH_WEST) {
                        direction = Direction.NORTH_EAST;
                    } else if (direction == Direction.SOUTH_WEST) {
                        direction = Direction.SOUTH_EAST;
                    }
                    break;
                    
                case RIGHT:
                    // 如果到达右边缘
                    if (direction == Direction.EAST) {
                        direction = Direction.WEST;
                    } else if (direction == Direction.NORTH_EAST) {
                        direction = Direction.NORTH_WEST;
                    } else if (direction == Direction.SOUTH_EAST) {
                        direction = Direction.SOUTH_WEST;
                    }
                    break;
                    
                case TOP_LEFT:
                    // 如果到达左上角
                    if (direction == Direction.NORTH) {
                        direction = Direction.SOUTH;
                    } else if (direction == Direction.WEST) {
                        direction = Direction.EAST;
                    } else if (direction == Direction.NORTH_EAST) {
                        direction = Direction.SOUTH_EAST;
                    } else if (direction == Direction.NORTH_WEST) {
                        direction = Direction.SOUTH_EAST;
                    } else if (direction == Direction.SOUTH_WEST) {
                        direction = Direction.SOUTH_EAST;
                    }
                    break;
                    
                case TOP_RIGHT:
                    // 如果到达右上角
                    if (direction == Direction.NORTH) {
                        direction = Direction.SOUTH;
                    } else if (direction == Direction.EAST) {
                        direction = Direction.WEST;
                    } else if (direction == Direction.NORTH_EAST) {
                        direction = Direction.SOUTH_WEST;
                    } else if (direction == Direction.NORTH_WEST) {
                        direction = Direction.SOUTH_WEST;
                    } else if (direction == Direction.SOUTH_EAST) {
                        direction = Direction.SOUTH_WEST;
                    }
                    break;
                    
                case BOTTOM_LEFT:
                    // 如果到达左下角
                    if (direction == Direction.SOUTH) {
                        direction = Direction.NORTH;
                    } else if (direction == Direction.WEST) {
                        direction = Direction.EAST;
                    } else if (direction == Direction.SOUTH_EAST) {
                        direction = Direction.NORTH_EAST;
                    } else if (direction == Direction.SOUTH_WEST) {
                        direction = Direction.NORTH_EAST;
                    } else if (direction == Direction.NORTH_WEST) {
                        direction = Direction.NORTH_EAST;
                    }
                    break;
                    
                case BOTTOM_RIGHT:
                    // 如果到达右下角
                    if (direction == Direction.SOUTH) {
                        direction = Direction.NORTH;
                    } else if (direction == Direction.EAST) {
                        direction = Direction.WEST;
                    } else if (direction == Direction.SOUTH_EAST) {
                        direction = Direction.NORTH_WEST;
                    } else if (direction == Direction.SOUTH_WEST) {
                        direction = Direction.NORTH_WEST;
                    } else if (direction == Direction.NORTH_EAST) {
                        direction = Direction.NORTH_WEST;
                    }
                    break;
                    
                case NONE:
                    // 不在边界，不改变方向
                    break;
            }
        }
        
        /**
         * 根据当前方向移动弹珠
         */
        public void move() {
            switch (direction) {
                case NORTH:
                    y -= 1;
                    break;
                case SOUTH:
                    y += 1;
                    break;
                case EAST:
                    x += 1;
                    break;
                case WEST:
                    x -= 1;
                    break;
                case NORTH_EAST:
                    y -= 1;
                    x += 1;
                    break;
                case NORTH_WEST:
                    y -= 1;
                    x -= 1;
                    break;
                case SOUTH_EAST:
                    y += 1;
                    x += 1;
                    break;
                case SOUTH_WEST:
                    y += 1;
                    x -= 1;
                    break;
            }
        }
        
        /**
         * 随机设置为基本方向（北、南、东、西）
         */
        public void setRandomBasicDirection() {
            Direction[] basicDirections = {
                Direction.NORTH, Direction.SOUTH,
                Direction.EAST, Direction.WEST
            };
            int randomIndex = new Random().nextInt(basicDirections.length);
            direction = basicDirections[randomIndex];
        }
        
        /**
         * 随机设置为顺序方向（东北、西北、东南、西南）
         */
        public void setRandomOrdinalDirection() {
            Direction[] ordinalDirections = {
                Direction.NORTH_EAST, Direction.NORTH_WEST,
                Direction.SOUTH_EAST, Direction.SOUTH_WEST
            };
            int randomIndex = new Random().nextInt(ordinalDirections.length);
            direction = ordinalDirections[randomIndex];
        }
    }
    
    /**
     * 世界类，表示整个弹珠模拟世界的状态
     */
    static class World {
        private Marble marble1;  // 第一个弹珠
        private Marble marble2;  // 第二个弹珠
        private Marble marble3;  // 第三个弹珠
        private Marble marble4;  // 第四个弹珠
        
        /**
         * 构造世界
         * @param marble1 第一个弹珠
         * @param marble2 第二个弹珠
         * @param marble3 第三个弹珠
         * @param marble4 第四个弹珠
         */
        public World(Marble marble1, Marble marble2, Marble marble3, Marble marble4) {
            this.marble1 = marble1;
            this.marble2 = marble2;
            this.marble3 = marble3;
            this.marble4 = marble4;
        }
        
        /**
         * 获取第一个弹珠
         * @return 第一个弹珠
         */
        public Marble getMarble1() {
            return marble1;
        }
        
        /**
         * 获取第二个弹珠
         * @return 第二个弹珠
         */
        public Marble getMarble2() {
            return marble2;
        }
        
        /**
         * 获取第三个弹珠
         * @return 第三个弹珠
         */
        public Marble getMarble3() {
            return marble3;
        }
        
        /**
         * 获取第四个弹珠
         * @return 第四个弹珠
         */
        public Marble getMarble4() {
            return marble4;
        }
    }
    
    /**
     * 更新世界状态，让弹珠移动一步
     * @param world 当前世界状态
     * @return 更新后的世界状态
     */
    static World step(World world) {
        // 获取所有弹珠
        Marble[] marbles = {
            world.getMarble1(),
            world.getMarble2(),
            world.getMarble3(),
            world.getMarble4()
        };
        
        // 更新每个弹珠
        for (Marble marble : marbles) {
            // 检查弹珠是否到达边界
            Boundary boundary = marble.checkBoundary();
            
            // 如果到达边界，更新方向
            if (boundary != Boundary.NONE) {
                marble.updateDirection(boundary);
            }
            
            // 移动弹珠
            marble.move();
        }
        
        return world;
    }
    
    /**
     * 绘制世界状态
     * @param world 世界状态
     * @return 表示世界的图像
     */
    static Image draw(World world) {
        // 创建白色背景
        Image background = Rectangle(WORLD_WIDTH, WORLD_HEIGHT, WHITE);
        
        // 获取所有弹珠
        Marble[] marbles = {
            world.getMarble1(),
            world.getMarble2(),
            world.getMarble3(),
            world.getMarble4()
        };
        
        // 绘制每个弹珠
        for (Marble marble : marbles) {
            Image marbleImg = Circle(MARBLE_RADIUS, marble.getColour());
            background = PlaceXY(background, marbleImg, marble.getX(), marble.getY());
        }
        
        return background;
    }
    
    /**
     * 处理鼠标事件
     * @param world 当前世界状态
     * @param event 鼠标事件
     * @return 更新后的世界状态
     */
    static World mouseEvent(World world, MouseEvent event) {
        // 如果是左键单击，所有弹珠随机设置为顺序方向
        if (event.kind() == MouseEventKind.LEFT_CLICK) {
            world.getMarble1().setRandomOrdinalDirection();
            world.getMarble2().setRandomOrdinalDirection();
            world.getMarble3().setRandomOrdinalDirection();
            world.getMarble4().setRandomOrdinalDirection();
        }
        
        return world;
    }
    
    /**
     * 处理键盘事件
     * @param world 当前世界状态
     * @param event 键盘事件
     * @return 更新后的世界状态
     */
    static World keyEvent(World world, KeyEvent event) {
        // 如果按下空格键，所有弹珠随机设置为基本方向
        if (event.key().equals(" ")) {
            world.getMarble1().setRandomBasicDirection();
            world.getMarble2().setRandomBasicDirection();
            world.getMarble3().setRandomBasicDirection();
            world.getMarble4().setRandomBasicDirection();
        }
        
        return world;
    }
    
    /**
     * 获取初始世界状态
     * @return 初始世界状态
     */
    static World getInitialState() {
        Random random = new Random();
        
        // 随机选择初始方向
        Direction[] allDirections = Direction.values();
        Direction dir1 = allDirections[random.nextInt(allDirections.length)];
        Direction dir2 = allDirections[random.nextInt(allDirections.length)];
        Direction dir3 = allDirections[random.nextInt(allDirections.length)];
        Direction dir4 = allDirections[random.nextInt(allDirections.length)];
        
        // 创建四个弹珠，分别为蓝色、红色、绿色和黑色
        Marble marble1 = new Marble(75, 125, BLUE, dir1);
        Marble marble2 = new Marble(225, 125, RED, dir2);
        Marble marble3 = new Marble(75, 375, GREEN, dir3);
        Marble marble4 = new Marble(225, 375, BLACK, dir4);
        
        // 创建世界
        return new World(marble1, marble2, marble3, marble4);
    }
    
    // 方向测试接口
    static boolean isNorth(Direction direction) {
        return direction == Direction.NORTH;
    }
    
    static boolean isSouth(Direction direction) {
        return direction == Direction.SOUTH;
    }
    
    static boolean isEast(Direction direction) {
        return direction == Direction.EAST;
    }
    
    static boolean isWest(Direction direction) {
        return direction == Direction.WEST;
    }
    
    static boolean isNorthEast(Direction direction) {
        return direction == Direction.NORTH_EAST;
    }
    
    static boolean isNorthWest(Direction direction) {
        return direction == Direction.NORTH_WEST;
    }
    
    static boolean isSouthEast(Direction direction) {
        return direction == Direction.SOUTH_EAST;
    }
    
    static boolean isSouthWest(Direction direction) {
        return direction == Direction.SOUTH_WEST;
    }
    
    // 获取弹珠属性的测试接口
    static int getX(Marble marble) {
        return marble.getX();
    }
    
    static int getY(Marble marble) {
        return marble.getY();
    }
    
    static Direction getDirection(Marble marble) {
        return marble.getDirection();
    }
    
    // 获取世界中的弹珠
    static Marble getMarble1(World world) {
        return world.getMarble1();
    }
    
    static Marble getMarble2(World world) {
        return world.getMarble2();
    }
    
    static Marble getMarble3(World world) {
        return world.getMarble3();
    }
    
    static Marble getMarble4(World world) {
        return world.getMarble4();
    }
    
    /**
     * 主函数，启动弹珠模拟
     */
    public static void main(String[] args) {
        // 获取初始状态
        World initialState = getInitialState();
        
        // 启动模拟
        BigBang("Bouncing Marbles", initialState, BouncingMarbles::draw, BouncingMarbles::step,
                BouncingMarbles::keyEvent, BouncingMarbles::mouseEvent);
    }
} 