package com.tss.components;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionQueryCounter {

    private int counter = 0;

    public int getCounter() {
        return this.counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void increaseCounter() {
        counter++;
    }
}
