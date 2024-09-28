package fi.fcode.data.cache;

import fi.fcode.data.User;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserCache {

    private Map<UUID, User> users = new ConcurrentHashMap<>();

    public User createUser(Player player) {
        return users.put(player.getUniqueId(), new User(player));
    }
    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }

    public Map<UUID, User> getUsers() {
        return users;
    }
}