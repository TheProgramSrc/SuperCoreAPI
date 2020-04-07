/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.faq;

public class FAQ {

    private String question, answer, message;

    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public FAQ setOnClickMessage(String message){
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
