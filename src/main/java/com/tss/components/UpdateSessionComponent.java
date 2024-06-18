package com.tss.components;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UpdateSessionComponent {

    private boolean updateEnabled = true;

    public boolean isUpdateEnabled() {
        return this.updateEnabled;
    }

    public void flip(boolean c) {
        this.updateEnabled = c;
    }
}
