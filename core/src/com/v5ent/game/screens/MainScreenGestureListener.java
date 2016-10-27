/*package com.v5ent.game.screens;

import com.badlogic.gdx.input.GestureDetector.GestureAdapter;  

public class MainScreenGestureListener extends GestureAdapter{  
  
    private LevelWindow levelWindow;  
    private ThemeScrollPanel themeScrollPanel;  
      
    float panx = 0f;  
    float pany = 0f;  
    float panDeltaX = 0f;  
    float panDeltaY = 0f;  
      
    @Override  
    public boolean pan(float x, float y, float deltaX, float deltaY) {  
        if(levelWindow != null && levelWindow.getColor().a != 0f){  
            this.panx = x;  
            this.pany = y;  
            this.panDeltaX = deltaX;  
            this.panDeltaY = deltaY;  
            return true;  
        }else if(themeScrollPanel != null && themeScrollPanel.isVisible()){  
            themeScrollPanel.setScrollX(themeScrollPanel.getScrollX()-deltaX);  
            return true;  
        }else{  
            return false;  
        }  
    }  
  
    public void setLevelWindow(LevelWindow levelWindow) {  
        this.levelWindow = levelWindow;  
    }  
  
    public void setThemeScrollPanel(ThemeScrollPanel themeScrollPanel) {  
        this.themeScrollPanel = themeScrollPanel;  
    }  
  
    @Override  
    public boolean panStop(float x, float y, int pointer, int button) {  
        if(levelWindow != null && levelWindow.isVisible()){  
            if(panx == x && pany ==y && panDeltaX < 0f){//向左滑动  
                levelWindow.nextPage();  
            }  
            if(panx == x && pany ==y && panDeltaX > 0f){//向右滑动  
                levelWindow.prevPage();  
            }  
            return true;  
        }else{  
            return false;  
        }  
    }  
} 
*/