package com.netflix_clone.boardservice.enums;

import lombok.Getter;

public abstract class Rabbit {
//    @Getter
//    public enum Exchange {
//        PROFILE("netflix-clone-profile");
//
//        private String value;
//        Exchange(String value) { this.value = value; }
//    }
    @Getter
    public enum RoutingKey {
        PROFILE_SAVE("profile.save"), PROFILE_CHANGE("profile.change");
        private String value;

        RoutingKey(String name){ this.value = name; }
    }

    @Getter
    public enum Queue {
        BOARD("netflix-clone-board");
        private String name;

        Queue(String name){ this.name = name; }

    }
    @Getter
    public enum Topic {
        PROFILE("netflix-clone-board");

        private String name;
        Topic(String name){ this.name = name; }
    }
}
