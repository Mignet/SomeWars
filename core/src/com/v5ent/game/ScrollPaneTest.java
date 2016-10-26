package com.v5ent.game;

import com.badlogic.gdx.Game;

/******************************************************************************* 
 * Copyright 2011 See AUTHORS file. 
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *  
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 ******************************************************************************/  
  
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;  
  
public class ScrollPaneTest extends Game  {  
    private Stage stage;  
    private Table container;  
  
    public void create () {  
        stage = new Stage();  
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));  
        Gdx.input.setInputProcessor(stage);  
  
        // Gdx.graphics.setVSync(false);  
  
        container = new Table();  
        stage.addActor(container);  
        container.setFillParent(true);  
  
        Table table = new Table();  
        // table.debug();  
  
        final ScrollPane scroll = new ScrollPane(table, skin);  
     
        InputListener stopTouchDown = new InputListener() {  
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {  
                event.stop();  
                return false;  
            }  
        };  
  
        table.pad(10).defaults().expandX().space(4);  
        for (int i = 0; i < 100; i++) {  
            table.row();  
            final CheckBox checkBox = new CheckBox("CheckBox", skin);
            checkBox.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (checkBox.isChecked()) {
                        Gdx.app.log("TAG", "box is checked");
                    } else {
                        Gdx.app.log("TAG", "box is unchecked");
                    }
                }
            });
            table.add(checkBox).expandX().fillX();  
  
            table.add(new Label(i + "tres long0 long1 ", skin));  
        }  
  
        final TextButton flickButton = new TextButton("Flick Scroll", skin.get("toggle", TextButtonStyle.class));  
        flickButton.setChecked(true);  
        flickButton.addListener(new ChangeListener() {  
            public void changed (ChangeEvent event, Actor actor) {  
                scroll.setFlickScroll(flickButton.isChecked());  
            }  
        });  
  
        final TextButton fadeButton = new TextButton("Fade Scrollbars", skin.get("toggle", TextButtonStyle.class));  
        fadeButton.setChecked(true);//将按钮设置为选中状态  
        fadeButton.addListener(new ChangeListener() {  
            public void changed (ChangeEvent event, Actor actor) {  
                scroll.setFadeScrollBars(fadeButton.isChecked());  
            }  
        });  
//  
//      final TextButton smoothButton = new TextButton("Smooth Scrolling", skin.get("toggle", TextButtonStyle.class));  
//      smoothButton.setChecked(true);  
//      smoothButton.addListener(new ChangeListener() {  
//          public void changed (ChangeEvent event, Actor actor) {  
//              scroll.setSmoothScrolling(smoothButton.isChecked());  
//          }  
//      });  
//  
//      final TextButton onTopButton = new TextButton("Scrollbars On Top", skin.get("toggle", TextButtonStyle.class));  
//      onTopButton.setChecked(true);  
//      onTopButton.addListener(new ChangeListener() {  
//          public void changed (ChangeEvent event, Actor actor) {  
//              scroll.setScrollbarsOnTop(onTopButton.isChecked());  
//          }  
//      });  
  
        /** 
         * 以下是Table的相关API的一些个人的解释 
         * row():开启一个新行 
         * right():向右移动一点,默认在最左边 
         * left():在最左边显示,默认在中间 
         * expandX():扩展X轴(最明显的效果就是用了这个以后位置向右移动了) 
         * add()添加一列 
         * right().expandX():可以理解为不断地向右拓展... 
         * space(10): 单元格与单元格之间的距离以及单元格与上边界之间的距离 
         * padBottom(0): 单元格与下边界之间的距离为0 
         * fill():将fillX()、fillY()设置成1 
         */  
//      container.add(scroll).expand().fill().colspan(4);  
        container.add(scroll).fill().expand().colspan(4);  
        container.row().space(10).padBottom(0);  
        container.add(flickButton).right().expandX();  
//      container.add(onTopButton);  
//      container.add(smoothButton);  
        container.add(fadeButton).left().expandX();  
    }  
  
    public void render () {  
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  
        stage.act(Gdx.graphics.getDeltaTime());  
        stage.draw();  
        Table.drawDebug(stage);  
    }  
  
    public void resize (int width, int height) {  
        stage.getViewport().update(width, height, false);  
    }  
  
    public void dispose () {  
        stage.dispose();  
    }  
  
    public boolean needsGL20 () {  
        return false;  
    }  
}  