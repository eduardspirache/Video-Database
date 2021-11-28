package database;

import actor.Actor;
import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

public class ActorInput {
    public static Actor returnActor(ActorInputData actorInput) {
        Actor actor;

        String name = actorInput.getName();
        String description = actorInput.getCareerDescription();
        ArrayList<String> filmography = actorInput.getFilmography();
        Map<ActorsAwards, Integer> awards = actorInput.getAwards();

        actor = new Actor(name, description, filmography, awards);
        return actor;
    }
}
