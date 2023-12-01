package org.example.bootstarter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.UUID;

@RequiredArgsConstructor
public class EventQueueWorker {

    private final EventQueue eventQueue;

    @EventListener(ApplicationReadyEvent.class)
    public void startEventQueue(String payload) {
        startEventProducer(payload);
        startEventConsumer();
    }

    private void startEventProducer(String payload) {
        UUID id = UUID.randomUUID();
        Event event = Event.builder()
                .id(id)
                .payload(payload)
                .build();

        eventQueue.enqueue(event);
    }

    private void startEventConsumer() {
        Thread eventConsumerThread = new Thread(() -> {
            while (true) {
                Event event = eventQueue.dequeue();
                System.out.println(event);
            }
        });
        eventConsumerThread.start();
    }
}
