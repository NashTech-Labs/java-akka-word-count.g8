package com.knoldus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WordCountActorTest {
    static ActorSystem system;
    
    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("Word-count");
    }
    
    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }
    
    @Test
    public void testWordCountActor() {
        new TestKit(system) {{
            ActorRef actorRef = system.actorOf(Props.create(WordCountActor.class), "WordCountActor");
            actorRef.tell("calling", actorRef);
        }};
    }
    
    @Test
    public void testWordCountActorWhenWrongMessageIsSent() {
        new TestKit(system) {{
            ActorRef actorRef = system.actorOf(Props.create(WordCountActor.class), "WordCountActorWithInvalidMessage");
            actorRef.tell(1, actorRef);
        }};
    }
    
}
