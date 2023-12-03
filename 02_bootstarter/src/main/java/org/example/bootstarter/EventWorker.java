package org.example.bootstarter;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventWorker {

    @EventListener(StudentRemoveEvent.class)
    public void studentRemoveEventPrint(StudentRemoveEvent event) {
        System.out.println(event.getId() + " - " + event.getMsg());
    }

    @EventListener(StudentAddedEvent.class)
    public void studentAddedEventPrint(StudentAddedEvent event) {
        System.out.println(event.getId() + " - " + event.getMsg());
    }
}
