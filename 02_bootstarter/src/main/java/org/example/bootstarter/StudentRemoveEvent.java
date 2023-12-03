package org.example.bootstarter;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class StudentRemoveEvent extends ApplicationEvent {

    private final String msg;

    private final UUID id;

    public StudentRemoveEvent(Object source, int id) {
        super(source);
        this.id = UUID.randomUUID();
        msg = " Remove student: " + id;
    }
}
