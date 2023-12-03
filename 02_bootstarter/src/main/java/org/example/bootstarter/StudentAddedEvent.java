package org.example.bootstarter;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import java.util.UUID;

@Getter
public class StudentAddedEvent extends ApplicationEvent {

    private final String msg;

    private final UUID id;

    public StudentAddedEvent(Object source, Student student) {
        super(source);
        id = UUID.randomUUID();
        msg = " Create student: " + student.toString();
    }
}
