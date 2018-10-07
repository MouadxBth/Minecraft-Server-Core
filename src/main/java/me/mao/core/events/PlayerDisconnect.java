package me.mao.core.events;

import me.mao.api.event.Event;
import me.mao.core.user.User;
import me.mao.core.user.inserter.UserInserter;
import me.mao.core.user.provider.UserProvider;
import me.mao.system.exceptions.UserNotFoundException;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;

public class PlayerDisconnect extends Event<PlayerDisconnectEvent> {

    @Override
    public void onExecute(PlayerDisconnectEvent event) {
        try {
            User user = new UserProvider(event.getPlayer().getUniqueId()).getUser();

            new UserInserter(user).save();

            //TODO the remove from the cache task begins here! X^)

        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
