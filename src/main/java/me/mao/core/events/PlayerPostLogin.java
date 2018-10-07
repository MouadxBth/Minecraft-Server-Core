package me.mao.core.events;

import me.mao.api.event.Event;
import me.mao.core.user.User;
import me.mao.core.user.inserter.UserInserter;
import me.mao.core.user.provider.UserProvider;
import me.mao.system.exceptions.UserNotFoundException;
import net.md_5.bungee.api.event.PostLoginEvent;

public class PlayerPostLogin extends Event<PostLoginEvent> {

    @Override
    public void onExecute(PostLoginEvent event) {
        try {
            User user = new UserProvider(event.getPlayer().getUniqueId()).getUser();
            user.addCoins(100);

            new UserInserter(user).softSave();

        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
