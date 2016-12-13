package com.v5ent.game.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.v5ent.game.SomeWars;
import com.v5ent.game.entities.Block;
import com.v5ent.game.entities.Command;
import com.v5ent.game.entities.Hero;
import com.v5ent.game.entities.Magic;
import com.v5ent.game.utils.Constants;
import com.v5ent.game.utils.GameState;
import com.v5ent.game.utils.Transform;

import static com.v5ent.game.utils.GameState.FIGHT;
import static com.v5ent.game.utils.GameState.PREPAREFIGHT;
import static com.v5ent.game.utils.GameState.TO_FIGHT;

public class WorldController extends InputAdapter implements GestureListener {

    private static final String TAG = WorldController.class.getName();

    SomeWars global;
    public OrthographicCamera camera;
    public OrthographicCamera cameraGUI;

    public Sprite background;
    public GameState gameState;
    public int second = 5;

    private Stack<Command> roundList = new Stack<Command>();
    public List<Sprite> moveCells = new ArrayList<Sprite>();
    public List<Sprite> fightCells = new ArrayList<Sprite>();
    public List<Block> blocks = new ArrayList<Block>();
    public List<Magic> magics = new ArrayList<Magic>();
    public List<Hero> myHeros = new ArrayList<Hero>();
    public List<Hero> enemyHeros = new ArrayList<Hero>();

    private Hero selectedHeroForPrepare = null;
    private Hero selectedHeroForMove = null;

    public WorldController(SomeWars game) {
        global = game;
        gameState = GameState.PREPARE;
        Gdx.app.debug(TAG, "GameState:" + gameState);
        Timer timer = new Timer();
        Task timerTask = new Task() {
            @Override
            public void run() {
                second--;
                if (second == 0) {
                    gameState = GameState.MOVE;
                    Gdx.app.debug(TAG, "GameState:" + gameState);
                }
            }
        };
        timer.scheduleTask(timerTask, 0, 1, second);// 0s之后执行，每次间隔1s，执行20次。
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        cameraGUI = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.update();
        // we want the camera to setup a viewport with pixels as units, with the
        // y-axis pointing upwards. The origin will be in the lower left corner
        // of the screen.
//		camera.setToOrtho(false);
        initObjects();
    }

    private void initObjects() {
        background = new Sprite(Assets.instance.background);
        background.setSize(background.getWidth() / Constants.RV_W_RATIO, background.getHeight() / Constants.RV_H_RATIO);
        // Set origin to sprite's center
//		background.setOrigin(background.getWidth() / 2.0f, background.getHeight() / 2.0f);
        background.setPosition(-Constants.VIEWPORT_WIDTH / 2, -Constants.VIEWPORT_HEIGHT / 2);
        //moveCells
        // selected heros
        myHeros = new ArrayList<Hero>(global.myHeros.values());
//		int myHerosCnt = 4;
        // Create a list of texture regions
        // Create new sprites using a random texture region
        for (int i = 0; i < myHeros.size(); i++) {
//			create hero by id
            Hero spr = myHeros.get(i);
            // Calculate random position for sprite
            spr.setMapPosition(1, i + 1);
            spr.setGood(true);
        }
        //init blocks
        blocks = new ArrayList<Block>();
        blocks.add(new Block(3, 0));
        blocks.add(new Block(3, 4));
        //init enemy
        int enemyHerosCnt = 2;
        // Create a list of texture regions
        // Create new sprites using a random texture region
        for (int i = 0; i < enemyHerosCnt; i++) {
//			create hero by id
            Hero spr = new Hero("001");
            spr.setGood(false);
            // Calculate random position for sprite
            spr.setMapPosition(Constants.MAP_COLS - 1 - i, 3);
            // Put new sprite into array
            enemyHeros.add(spr);
        }
    }
    private boolean allHerosMoveCompleted(){
        int n = 0;
        for(Hero h:myHeros){
            if(h.getCurrentState()== Hero.State.IDLE){
                n++;
            }
        }
        for(Hero h:enemyHeros){
            if(h.getCurrentState()== Hero.State.IDLE){
                n++;
            }
        }
        if(n==(myHeros.size()+enemyHeros.size())){
            return true;
        }else{
            return false;
        }
    }

    int stop = 0;
    int TOTLE = -1;//计数器

    public void update(float deltaTime) {
//		handleDebugInput(deltaTime);
        updateObjects(deltaTime);
        //此时不接受任何输入
        if(gameState == PREPAREFIGHT){
            //TODO: command from net,AI
            for (Hero h : enemyHeros) {
                int x = h.getMapX() - 1;
                int y = h.getMapY();
                if (!isCollisionWithBlock(x, y) && !isCollisionWithHeros(x, y) && x >= 0 && y >= 0 && x < 7 && y < 5 && stop<2) {
                    h.moveTo(x, y);
                } else {
                    stop ++;
                }
            }
            gameState = TO_FIGHT;
        }
        //此时不接受任何输入
        if (gameState == TO_FIGHT && allHerosMoveCompleted()) {
            Gdx.app.debug(TAG,"to fight!");
            /**
             *逻辑：对所有的敌人和我军按照敏捷进行排序（降序），依次执行
             *根据攻击范围，如果范围内有多个敌人，随机选择1个进行攻击
             */
            //fighting
            //1.order all heros by dexterity
            List<Hero> temp = new ArrayList<Hero>();
            List<Hero> temp0 = new ArrayList<Hero>(myHeros);
            List<Hero> temp1 = new ArrayList<Hero>(enemyHeros);
            temp.addAll(temp0);
            temp.addAll(temp1);
            Collections.sort(temp, new Comparator<Hero>() {
                @Override
                public int compare(Hero lhs, Hero rhs) {
                    return lhs.getDexterity() > rhs.getDexterity() ? -1 : (lhs.getDexterity() < rhs.getDexterity()) ? 1 : 0;
                }
            });
            //2.select what's my target
            int len = temp.size();
            TOTLE = len;
            //计数开始
            for (Hero h : temp) {
                Hero target = null;
                if (h.isGood()) {
                    target = h.scanTarget(enemyHeros);
                } else {
                    target = h.scanTarget(myHeros);
                }
                if (target != null) {
                    //add command
                    this.roundList.push(new Command(h, target));
                    Gdx.app.debug(TAG, " Push Command!");
                } else {
                    TOTLE--;
                }
            }
            gameState = FIGHT;
        }
        if(gameState == FIGHT) {
            //战斗序列
            this.command();
        }
        if(myHeros.isEmpty()){
            Gdx.app.debug(TAG, " We Lost!");
            global.setScreen(global.gameoverScreen);
        }
        if(enemyHeros.isEmpty()){
            Gdx.app.debug(TAG, " We Win!");
            global.setScreen(global.gameoverScreen);
        }
    }

    //战斗序列需要计时器来完成每条command
    private void command() {
        if (!this.roundList.empty()) {
            final Command cmd = this.roundList.pop();
            if (cmd != null) {
                if (cmd.getTarget().getLife() > 0 && cmd.getRole().getLife() > 0) {
                    //this.playAttact(cmd.getRole(), cmd.getTarget());
                    cmd.getRole().setCurrentState(Hero.State.FIGHT);
                    //0.1秒之后，挨打
                    Timer.schedule(new Task() {
                        @Override
                        public void run() {
                            cmd.getTarget().setCurrentState(Hero.State.BEATEN);
                            //0.5秒之后，next command
                            Timer.schedule(new Task() {
                                @Override
                                public void run() {
                                    //真正的攻击
                                    cmd.getRole().hit(cmd.getTarget());
                                    //目标死亡,移除列表和地图，播放死亡动画
                                    Hero role = cmd.getRole();
                                    Hero target = cmd.getTarget();
                                    if(target.getLife()<=0){
                                        if (role.isGood()) {
                                            enemyHeros.remove(target);
                                        }else{
                                            myHeros.remove(target);
                                        }
                                        Gdx.app.debug(TAG,  cmd.getTarget().getId()+ " dead");
                                    }
                                    TOTLE--;
                                    command();//执行下一条指令
                                }
                            },1f);
                        }
                    },0.1f);

                } else {
                    TOTLE--;
                    this.command();//执行下一条指令
                }
            }
        } else {
            //所有战斗结束
            if (TOTLE != -1) {
                gameState = GameState.MOVE;
            }
        }
    }

    /**
     * 攻击者特效
     */
	/*private void playAttact(Hero role,Hero target) {
		var action1 = cc.TintBy.create(0.1, -127, -255, -127);
		var action1Back = action1.reverse();
		var action2 = cc.MoveBy.create(0.1, cc.ccp((target.x - role.x) * 25, (-target.y + role.y) * 25));
		var action2Back = action2.reverse();
		role.runAction(cc.Sequence.create(action1, action1Back, action1, action1Back, action1, action1Back, action2, action2Back, cc.CallFunc.create(this, this.playEffectAt, target)));
	}*/
    //blocks + heros
    private boolean isCollisionWithBlock(int x, int y) {
        for (Block b : blocks) {
            if (b.getMapX() == x && b.getMapY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollisionWithHeros(int x, int y) {
        for (Hero b : myHeros) {
            if (b.getTargetMapX() == x && b.getTargetMapY() == y) {
//                Gdx.app.debug(TAG,"isCollisionWithHeros("+x+","+y+")");
                return true;
            }
        }
        for (Hero b : enemyHeros) {
            if (b.getTargetMapX() == x && b.getTargetMapY() == y) {
//                Gdx.app.debug(TAG,"isCollisionWithEnemies("+x+","+y+")");
                return true;
            }
        }
        return false;
    }

    private void updateObjects(float deltaTime) {
        for (int i = 0; i < myHeros.size(); i++) {
            myHeros.get(i).update(deltaTime);
        }
        for (int i = 0; i < enemyHeros.size(); i++) {
            enemyHeros.get(i).update(deltaTime);
        }
        for (Magic m : magics) {
            m.update(deltaTime);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        // Reset game world
        if (keycode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        } else if (keycode == Keys.Q) {
            Gdx.app.exit();
        }
        return false;
    }

    @Override
    public boolean touchDown(float screenX, float screenY, int pointer, int button) {
        if (gameState ==PREPAREFIGHT|| gameState == TO_FIGHT ||gameState == FIGHT) {
            //此时不接受任何输入
            return true;
        }
        int x1 = Gdx.input.getX();
        int y1 = Gdx.input.getY();
        Vector3 input = new Vector3(x1, y1, 0);
        camera.unproject(input);
//		 Gdx.app.debug(TAG, "clicked # (" + x1+","+ y1 + " )");
//		 Gdx.app.debug(TAG, "clicked # (" +input.x +","+input.y + " )");
//		 Gdx.app.debug(TAG, "clicked # (" + x1/Constants.RV_W_RATIO+","+ y1/Constants.RV_H_RATIO + " )");
//		 Gdx.app.debug(TAG, "clicked # (" +Transform.mouseInMapX(input.x)+","+ Transform.mouseInMapY(input.y)+ " )");
        //Now you can use input.x and input.y, as opposed to x1 and y1, to determine if the moving
        //sprite has been clicked
        //如果点击的是我方，
        // 1.如果当前是准备阶段，而且moveCells是空，初始化moveCells。如果moveCells不是空，看点击点有没有hero，如果有，标记为prepare，如果没有，。none。如果点击点是moveCell，看看有没有prepareHero，如果有，移动过去。
        // a，如果没有选择要移动的英雄
        // b，如果已经选择了要移动的英雄，点击移动的位置就移动到该位置
        if (gameState == GameState.PREPARE && selectedHeroForPrepare != null) {
            for (Sprite m : moveCells) {
                if (m.getBoundingRectangle().contains(input.x, input.y)) {
                    selectedHeroForPrepare.setMapPosition(Transform.mouseInMapX(input.x), Transform.mouseInMapY(input.y));
                    selectedHeroForPrepare.setSelected(false);
                    selectedHeroForPrepare = null;
                    moveCells.clear();
                    return true;
                }
            }
        }
        //move single hero
        if (gameState == GameState.MOVE && selectedHeroForMove != null) {
            for (Sprite m : moveCells) {
                if (m.getBoundingRectangle().contains(input.x, input.y)) {
                    int x = Transform.mouseInMapX(input.x);
                    int y = Transform.mouseInMapY(input.y);
                    if (!isCollisionWithBlock(x, y) && !isCollisionWithHeros(x, y) && x >= 0 && y >= 0 && x < 7 && y < 5) {
                        selectedHeroForMove.moveTo(Transform.mouseInMapX(input.x), Transform.mouseInMapY(input.y));
                        selectedHeroForMove.setSelected(false);
                        selectedHeroForMove = null;
                        gameState = PREPAREFIGHT;
                        moveCells.clear();
                        Gdx.app.debug(TAG, " # Move To (" + x + "," + y + " )");
                    }
                    return true;
                }
            }
        }
        moveCells.clear();
        fightCells.clear();
        for (int i = 0; i < myHeros.size(); i++) {
            Hero h = myHeros.get(i);
            if (h.getBoundingRectangle().contains(input.x, input.y)) {
                Gdx.app.debug(TAG, " # (Sprite #"+h.getId()+"(" + h.getMapX()+","+h.getMapY() + ") clicked)");
                h.setSelected(true);
                if (gameState == GameState.PREPARE) {
                    selectedHeroForPrepare = h;
                    for (int n = 0; n < Constants.MAP_ROWS; n++) {
                        Sprite moveCell = new Sprite(Assets.instance.moveCell);
                        moveCell.setSize(moveCell.getWidth() / Constants.RV_W_RATIO, moveCell.getHeight() / Constants.RV_H_RATIO);
                        moveCell.setPosition(Transform.positionInWorldX(0), Transform.positionInWorldY(n));
                        moveCells.add(moveCell);
                        moveCell = new Sprite(Assets.instance.moveCell);
                        moveCell.setSize(moveCell.getWidth() / Constants.RV_W_RATIO, moveCell.getHeight() / Constants.RV_H_RATIO);
                        moveCell.setPosition(Transform.positionInWorldX(1), Transform.positionInWorldY(n));
                        moveCells.add(moveCell);
                        moveCell = new Sprite(Assets.instance.moveCell);
                        moveCell.setSize(moveCell.getWidth() / Constants.RV_W_RATIO, moveCell.getHeight() / Constants.RV_H_RATIO);
                        moveCell.setPosition(Transform.positionInWorldX(2), Transform.positionInWorldY(n));
                        moveCells.add(moveCell);
                    }
                }
                if (gameState == GameState.MOVE) {
                    selectedHeroForMove = h;
                    for (Vector2 p : h.getMoveRange()) {
                        Sprite moveCell = new Sprite(Assets.instance.moveCell);
                        moveCell.setSize(moveCell.getWidth() / Constants.RV_W_RATIO, moveCell.getHeight() / Constants.RV_H_RATIO);
                        moveCell.setPosition(Transform.positionInWorldX(h.getMapX() + p.x), Transform.positionInWorldY(h.getMapY() + p.y));
                        moveCells.add(moveCell);
                    }
                }
            } else {
                h.setSelected(false);
            }
        }
        //如果点击enemy，显示敌人的攻击范围
        for (int i = 0; i < enemyHeros.size(); i++) {
            Hero h = enemyHeros.get(i);
            if (h.getBoundingRectangle().contains(input.x, input.y)) {
                Gdx.app.debug(TAG, " # (Enemy Sprite #" + i + " clicked)");
                h.setSelected(true);
                for (Vector2 p : h.getFightRange()) {
                    Sprite fightCell = new Sprite(Assets.instance.fightCell);
                    fightCell.setSize(fightCell.getWidth() / Constants.RV_W_RATIO, fightCell.getHeight() / Constants.RV_H_RATIO);
                    fightCell.setPosition(Transform.positionInWorldX(h.getMapX() - p.x), Transform.positionInWorldY(h.getMapY() + p.y));
                    fightCells.add(fightCell);
                }
            } else {
                h.setSelected(false);
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (gameState ==PREPAREFIGHT|| gameState == TO_FIGHT ||gameState == FIGHT) {
            //不接受任何输入
            return true;
        }
        Gdx.app.log("GestureDetectorTest", "fling " + velocityX + ", " + velocityY);
        if (gameState == GameState.MOVE) {
            if (velocityX > 1000) {
                Gdx.app.debug(TAG, "RIGHT");
                //all heros move
                for (Hero h : myHeros) {
                    int x = h.getMapX() + 1;
                    int y = h.getMapY();
                    if (!isCollisionWithBlock(x, y) && !isCollisionWithHeros(x, y) && x >= 0 && y >= 0 && x < 7 && y < 5) {
                        h.moveTo(x, y);
                    }
                }
                gameState = PREPAREFIGHT;
            }
            if (velocityX < -1000) {
                Gdx.app.debug(TAG, "LEFT");
                //all heros move
                for (Hero h : myHeros) {
                    int x = h.getMapX() - 1;
                    int y = h.getMapY();
                    if (!isCollisionWithBlock(x, y) && !isCollisionWithHeros(x, y) && x >= 0 && y >= 0 && x < 7 && y < 5) {
                        h.moveTo(x, y);
                    }
                }
                gameState = PREPAREFIGHT;
            }
            if (velocityY > 1000) {
                Gdx.app.debug(TAG, "DOWN");
                //all heros move
                for (Hero h : myHeros) {
                    int x = h.getMapX();
                    int y = h.getMapY() - 1;
                    if (!isCollisionWithBlock(x, y) && !isCollisionWithHeros(x, y) && x >= 0 && y >= 0 && x < 7 && y < 5) {
                        h.moveTo(x, y);
                    }
                }
                gameState = PREPAREFIGHT;
            }
            if (velocityY < -1000) {
                Gdx.app.debug(TAG, "UP");
                //all heros move
                for (Hero h : myHeros) {
                    int x = h.getMapX();
                    int y = h.getMapY() + 1;
                    if (!isCollisionWithBlock(x, y) && !isCollisionWithHeros(x, y) && x >= 0 && y >= 0 && x < 7 && y < 5) {
                        h.moveTo(x, y);
                    }
                }
                gameState = PREPAREFIGHT;
            }
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                         Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
