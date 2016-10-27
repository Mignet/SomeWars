/*package com.v5ent.game.screens;
import com.badlogic.gdx.graphics.Color;  
import com.badlogic.gdx.scenes.scene2d.Actor;  
import com.badlogic.gdx.scenes.scene2d.InputEvent;  
import com.badlogic.gdx.scenes.scene2d.InputListener;  
import com.badlogic.gdx.scenes.scene2d.Touchable;  
import com.badlogic.gdx.scenes.scene2d.ui.Image;  
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;  
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;  
import com.badlogic.gdx.scenes.scene2d.ui.Skin;  
import com.badlogic.gdx.scenes.scene2d.ui.Table;  
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;  
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.v5ent.game.utils.Assets;  
//import com.swallowgames.supermario.game.Assets;  
//import com.swallowgames.supermario.screen.MainScreen;  
//import com.swallowgames.supermario.utils.Utils;  
  
public class ThemeScrollPanel extends ScrollPane{  
  
    private ButtonClickListener clickListener;  
    private MainScreen mainScreen;  
      
    public ThemeScrollPanel(Actor widget, Skin skin) {  
        super(widget, skin);  
    }  
      
    public ThemeScrollPanel (Actor widget, ScrollPaneStyle style) {  
        super(widget, style);  
    }  
      
    public ThemeScrollPanel init(){  
          
        clickListener = new ButtonClickListener();  
          
        Table table = new Table();  
        table.debug();  
          
        ImageButton themeBackground = new ImageButton(new TextureRegionDrawable(Assets.instance.assetUI.theme1),  
                new TextureRegionDrawable(Assets.instance.assetUI.theme1p));  
        themeBackground.setUserObject(0);  
        themeBackground.addCaptureListener(clickListener);  
        table.add(themeBackground);  
        table.columnDefaults(0);  
          
        themeBackground = new ImageButton(new TextureRegionDrawable(Assets.instance.assetUI.theme2),  
                new TextureRegionDrawable(Assets.instance.assetUI.theme2p));  
        themeBackground.setUserObject(1);  
        themeBackground.setTouchable(Touchable.disabled);  
        themeBackground.addCaptureListener(clickListener);  
        Image lock = new Image(new TextureRegionDrawable(Assets.instance.assetUI.lock));  
        lock.setPosition((themeBackground.getWidth() - lock.getWidth())/2, (themeBackground.getHeight() - lock.getHeight())/2);  
        themeBackground.addActor(lock);  
        table.add(themeBackground).width(themeBackground.getWidth()).height(themeBackground.getHeight());  
        table.columnDefaults(1);  
          
        themeBackground = new ImageButton(new TextureRegionDrawable(Assets.instance.assetUI.theme3),  
                new TextureRegionDrawable(Assets.instance.assetUI.theme3p));  
        themeBackground.setUserObject(2);  
        themeBackground.setTouchable(Touchable.disabled);  
        themeBackground.addCaptureListener(clickListener);  
        lock = new Image(new TextureRegionDrawable(Assets.instance.assetUI.lock));  
        lock.setPosition((themeBackground.getWidth() - lock.getWidth())/2, (themeBackground.getHeight() - lock.getHeight())/2);  
        themeBackground.addActor(lock);  
        table.add(themeBackground).width(themeBackground.getWidth()).height(themeBackground.getHeight());;  
        table.columnDefaults(2);  
          
        themeBackground = new ImageButton(new TextureRegionDrawable(Assets.instance.assetUI.theme4),  
                new TextureRegionDrawable(Assets.instance.assetUI.theme4p));  
        themeBackground.setUserObject(3);  
        themeBackground.setColor(Color.GRAY);  
        themeBackground.setTouchable(Touchable.disabled);  
        themeBackground.addCaptureListener(clickListener);  
        lock = new Image(new TextureRegionDrawable(Assets.instance.assetUI.lock));  
        lock.setPosition((themeBackground.getWidth() - lock.getWidth())/2, (themeBackground.getHeight() - lock.getHeight())/2);  
        themeBackground.addActor(lock);  
        lock.setColor(Color.GRAY);  
        table.add(themeBackground).width(themeBackground.getWidth()).height(themeBackground.getHeight());;  
        table.columnDefaults(3);  
          
        table.pack();  
          
        setWidget(table);  
        setScrollingDisabled(false, true);  
        addListener(new InputListener() {  
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {  
                event.stop();  
                return true;  
            }  
        });  
        setSmoothScrolling(true);  
        setFlickScroll(false);  
        setSize(themeBackground.getWidth()*2, table.getHeight());  
        setPosition(Utils.xAxisCenter(getWidth()), Utils.yAxisCenter(getHeight()) - 20);  
        return this;  
    }  
      
    public void setMainScreen(MainScreen mainScreen) {  
        this.mainScreen = mainScreen;  
    }  
  
  
    class ButtonClickListener extends ClickListener {  
        @Override  
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {  
            super.touchUp(event, x, y, pointer, button);  
            Actor actor = event.getListenerActor();  
            Integer themeIndex = (Integer)actor.getUserObject();  
            mainScreen.addLevelWindow(themeIndex);  
        }  
    }  
}  
*/