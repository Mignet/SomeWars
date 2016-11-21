package com.v5ent.game.entities;

/**
 * Created by Mignet on 2016/11/20.
 */

public class Command {
    Hero role;
    Hero target;
    public Command(Hero role,Hero target){
        this.role = role;
        this.target = target;
    }

    public Hero getRole() {
        return role;
    }

    public Hero getTarget() {
        return target;
    }
}
