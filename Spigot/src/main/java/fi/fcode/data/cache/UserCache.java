package fi.fcode.data.cache;

import fi.fcode.data.User;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserCache {

    private Map<UUID, User> users = new ConcurrentHashMap<>();

    public void createUser(Player player) {
        users.put(player.getUniqueId(), new User(player));
    }

    public void removeUser(UUID uuid) {
        users.remove(uuid);
    }

    public Optional<User> findUserByUUID(UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }

    public Map<UUID, User> getUsers() {
        return users;
    }
}