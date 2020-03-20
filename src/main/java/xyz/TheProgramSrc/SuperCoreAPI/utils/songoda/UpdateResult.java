/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils.songoda;

public enum  UpdateResult {
    SUCCESS,
    NO_CONNECTION,
    FAILED;

    UpdateResult(){

    }

    private Exception ex;

    UpdateResult(Exception ex){
        this.ex = ex;
    }

    public Exception getException() {
        return ex;
    }

    public void setException(Exception ex) {
        this.ex = ex;
    }
}
